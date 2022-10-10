package com.amp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.amp.constant.CommonConstant;
import com.amp.domain.UserInfo;
import com.amp.enums.ResultCodeEnum;
import com.amp.exception.AmpException;
import com.amp.mapper.UserInfoMapper;
import com.amp.request.LoginRequest;
import com.amp.service.ICommonService;
import com.amp.service.IUserInfoService;
import com.amp.service.IWeChatService;
import com.amp.utils.MD5Utils;
import com.amp.utils.RestTemplateUtils;
import com.amp.utils.redis.RedisConstant;
import com.amp.utils.redis.RedisTemplateUtil;
import com.amp.vo.WeChatAuthVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * 微信小程序平台后端相关接口
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/7/21 4:28 下午
 */
@Slf4j
@Service
public class WeChatServiceImpl implements IWeChatService {

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appSecret}")
    private String appSecret;


    //登录认证url
    public final static String authUrl = "https://api.weixin.qq.com/sns/jscode2session";

    //获取用户手机号url
    public final static String phoneUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber";

    //获取接口调用凭据url
    public final static String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token";

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Autowired
    private RedisTemplateUtil redisTemplateUtil;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private UserInfoMapper userInfoMapper;


    /**
     * 获取微信调用接口凭证
     * @return
     */
    @Override
    public String getToken() {
        String token = redisTemplateUtil.getString(RedisConstant.WX_SERVER_API_TOKEN_KEY);
        if(StringUtils.isEmpty(token)){
            String tokenUrl = String.format("%s?appid=%s&secret=%s&grant_type=client_credential",
                    WeChatServiceImpl.tokenUrl, appid, appSecret);
            ResponseEntity<String> responseEntity = restTemplateUtils.get(tokenUrl, String.class);
            log.info("获取微信调用凭证响应 result:{}", responseEntity);
            if(responseEntity != null && StringUtils.isNotEmpty(responseEntity.getBody())){
                JSONObject result = JSONObject.parseObject(responseEntity.getBody());
                if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
                    token = result.getString("access_token");
                    redisTemplateUtil.set(RedisConstant.WX_SERVER_API_TOKEN_KEY, token, RedisConstant.HOUR_TIME*2);
                }else{
                    String errCode = result.getString("errcode");
                    String errMsg = result.getString("errmsg");
                    throw new AmpException(errCode, errMsg);
                }
            }
        }

        return token;
    }

    /**
     * 微信小程序登录认证
     * @param code
     * @return
     */
    @Override
    public WeChatAuthVO getAuth(String code) {
        WeChatAuthVO vo = null;
        String authUrl = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                WeChatServiceImpl.authUrl, appid, appSecret, code);
        ResponseEntity<String> responseEntity = restTemplateUtils.get(authUrl, String.class);
        log.info("微信小程序登录认证响应 result:{}", responseEntity);
        if(responseEntity != null && StringUtils.isNotEmpty(responseEntity.getBody())){
            vo = new WeChatAuthVO();
            JSONObject result = JSONObject.parseObject(responseEntity.getBody());
            if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
                vo.setOpenid(result.getString("openid"));
                vo.setSessionKey(result.getString("session_key"));
                vo.setUnionid(result.getString("unionid"));
            }else{
                String errCode = result.getString("errcode");
                String errMsg = result.getString("errmsg");
                throw new AmpException(errCode, errMsg);
            }
        }
        return vo;
    }

    @Override
    public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {

        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getUserPhone(String code) {
        String phone = null;
        String userPhoneUrl = String.format("%s?access_token=%s", phoneUrl, getToken());
        JSONObject param = new JSONObject();
        param.put("code", code);
        ResponseEntity<String> responseEntity = restTemplateUtils.post(userPhoneUrl, param, String.class);
        log.info("获取用户手机号响应 result:{}", responseEntity);
        if(responseEntity != null && StringUtils.isNotEmpty(responseEntity.getBody())){
            JSONObject result = JSONObject.parseObject(responseEntity.getBody());
            if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
                JSONObject phoneInfo = result.getJSONObject("phone_info");
                if(phoneInfo != null){
                    phone = phoneInfo.getString("purePhoneNumber");
                }

                String errcode = result.getString("errcode");
                String errmsg = result.getString("errmsg");
                if(StringUtils.isNotEmpty(errcode) && !"0".equals(errcode)){
                    throw new AmpException(errcode, errmsg);
                }

            }
        }
        return phone;
    }

    @Override
    public UserInfo login(String phone, String openid) {
        UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
        //用户手机号、微信绑定了才可以登录
        if(userInfo != null && StringUtils.isNotEmpty(userInfo.getOpenid()) && userInfo.getOpenid().equals(openid)){
            userInfo = commonService.login(new LoginRequest(userInfo.getPhone(), userInfo.getPassword()));
        }else{
            throw new AmpException(ResultCodeEnum.OPENID_BIND_ERROR.getCode(), phone);
        }

        //手机号、openid不存在，则先进行绑定
        if(userInfo == null || StringUtils.isEmpty(userInfo.getOpenid())){
            throw new AmpException(ResultCodeEnum.USER_BIND_VAIL.getCode(), phone);
        }
        return userInfo;
    }

    @Override
    public UserInfo bind(String phone, String openid,  String nickname, String imageUrl) {
        UserInfo userInfo = new UserInfo();
        int count = userInfoMapper.selectCount(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getPhone, phone));
        if(count >= 1){
            throw new AmpException(ResultCodeEnum.USER_EXIST_PHONE_ERROR);
        }

        if(count == 0){   //新用户需要进行注册
            userInfo = register(phone, openid, nickname, imageUrl);
        }
        return userInfo;
    }

    private UserInfo register(String phone, String openid, String nickname, String imageUrl){
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(nickname);
        userInfo.setOpenid(openid);
        userInfo.setPhone(phone);
        userInfo.setAvatarImage(getAvatarImage(imageUrl));
        userInfo.preInsert();
        //使用盐将密码进行加密
        userInfo.setPassword(MD5Utils.encrypt(userInfo.getId(), CommonConstant.USER_DEFAULT_PASSWD));
        userInfoMapper.insert(userInfo);
        return  userInfo;
    }

    /**
     * 获取用户头像
     * @param imgUrl
     * @return
     */
    private byte[] getAvatarImage(String imgUrl){
        if (StringUtils.isEmpty(imgUrl)) {
            return null;
        }
        BufferedInputStream bis = null;
        ByteArrayOutputStream bos = null;
        HttpURLConnection http;
        URL url;

        try {
            url = new URL(imgUrl);
            http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(5000);
            http.connect();
            bis = new BufferedInputStream(http.getInputStream());
            bos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int size;
            while ((size = bis.read(buf)) != -1) {
                bos.write(buf, 0, size);
            }
            http.disconnect();
            return bos.toByteArray();
        }catch (Exception e){
          log.error("获取用户头像失败:{}", e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error("流关闭失败，原因:{}", e);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    log.error("流关闭失败，原因:{}", e);
                }
            }
        }
        return null;
    }


}
