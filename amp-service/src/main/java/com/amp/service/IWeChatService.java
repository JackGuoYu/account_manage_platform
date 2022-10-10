package com.amp.service;

import com.alibaba.fastjson.JSONObject;
import com.amp.domain.UserInfo;
import com.amp.vo.WeChatAuthVO;

/**
 * 微信小程序平台后端相关接口
 *
 * @author guoyu
 * @date 2022-07-21
 */
public interface IWeChatService {

    String getToken();

    WeChatAuthVO getAuth(String code);

    JSONObject getUserInfo(String encryptedData, String sessionKey, String iv);

    String getUserPhone(String code);

    UserInfo login(String phone, String openid);

    UserInfo bind(String phone, String openid, String nickname, String imageUrl);
}
