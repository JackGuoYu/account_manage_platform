package com.amp.controller;

import com.alibaba.fastjson.JSONObject;
import com.amp.annotation.ApiExt;
import com.amp.domain.Result;
import com.amp.domain.UserInfo;
import com.amp.request.WeChatBindRequest;
import com.amp.request.WeChatLoginRequest;
import com.amp.service.IWeChatService;
import com.amp.utils.ParamsUtils;
import com.amp.vo.WeChatAuthVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信相关接口
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/28 2:50 下午
 */
@Api(tags = "微信接口")
@RequestMapping(value = "/wechat")
@RestController
@Slf4j
public class WeChatController {

    @Autowired
    private IWeChatService weChatService;

    @ApiOperation(value = "获取微信调用接口凭证")
    @GetMapping("/app/getToken")
    public Result<String> getToken() {
        return Result.success(weChatService.getToken());
    }

    @ApiOperation(value = "小程序认证")
    @PostMapping("/app/auth")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "wx.login生成码", required = true, dataType = "String", paramType = "query", example = "509381"),
    })
    public Result<WeChatAuthVO> wechatAuth(@ApiExt(ignore = {"openid", "phoneCode"}) @RequestBody WeChatLoginRequest request) {
        ParamsUtils.checkParamsIsNull(request.getCode(), "生成码不能为空");
        WeChatAuthVO vo = weChatService.getAuth(request.getCode());
        return Result.success(vo);
    }

    @ApiOperation(value = "小程序登录")
    @PostMapping("/app/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "123", required = true, dataType = "String", paramType = "query", example = "509381"),
            @ApiImplicitParam(name = "phoneCode", value = "手机号生成码", required = true, dataType = "String", paramType = "query", example = "509381"),
    })
    public Result<UserInfo> login(@ApiExt(ignore = {"code"}) @RequestBody WeChatLoginRequest request) {
        ParamsUtils.checkParamsIsNull(request.getOpenid(), "openid不能为空");
        ParamsUtils.checkParamsIsNull(request.getPhoneCode(), "手机号生成码不能为空");
        String phone = weChatService.getUserPhone((request.getPhoneCode()));
        UserInfo loginInfo = weChatService.login(phone, request.getOpenid());
        return Result.success(loginInfo);
    }

    @ApiOperation(value = "小程序绑定认证")
    @PostMapping("/app/bind")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query", example = "509381"),
            @ApiImplicitParam(name = "openid", value = "openid", required = true, dataType = "String", paramType = "query", example = "509381"),
            @ApiImplicitParam(name = "encryptedData", value = "加密数据", required = true, dataType = "String", paramType = "query", example = "509381"),
            @ApiImplicitParam(name = "sessionKey", value = "会话密钥", required = true, dataType = "String", paramType = "query", example = "509381"),
            @ApiImplicitParam(name = "iv", value = "解密偏移量", required = true, dataType = "String", paramType = "query", example = "509381"),
    })
    public Result<UserInfo> wechatBind(@RequestBody WeChatBindRequest request) {
        ParamsUtils.checkParamsIsNull(request.getPhone(), "手机号不能为空");
        ParamsUtils.vailPhone(request.getPhone(), "手机号格式不正确");
        ParamsUtils.checkParamsIsNull(request.getOpenid(), "openid不能为空");
        ParamsUtils.checkParamsIsNull(request.getEncryptedData(), "加密数据不能为空");
        ParamsUtils.checkParamsIsNull(request.getSessionKey(), "会话密钥不能为空");
        ParamsUtils.checkParamsIsNull(request.getIv(), "加密偏移量不能为空");

        JSONObject json = weChatService.getUserInfo(request.getEncryptedData(), request.getSessionKey(), request.getIv());
        log.info("解析出来的用户信息:{}",json);
        String nickname = request.getPhone();
        String imgUrl = null;
        if(json != null){
            nickname = json.getString("nickName");
            imgUrl = json.getString("avatarUrl");
        }

        UserInfo result = weChatService.bind(request.getPhone(), request.getOpenid(), nickname, imgUrl);
        return Result.success(result);
    }
}
