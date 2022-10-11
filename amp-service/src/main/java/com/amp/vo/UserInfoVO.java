package com.amp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息vo
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 2:38 下午
 */
@Data
public class UserInfoVO implements Serializable {

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "用户描述信息")
    private String description;

    @ApiModelProperty(value = "用户头像")
    private byte[] avatarImage;
}
