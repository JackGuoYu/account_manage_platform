package com.amp.dto;

import lombok.Data;

/**
 * 用户DTO
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 10:53 上午
 */
@Data
public class UserInfoDTO {

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 用户描述信息
     */
    private String description;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户头像
     */
    private byte[] avatarImage;
}
