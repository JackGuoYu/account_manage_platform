package com.amp.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author guoyu
 * @date 2022/07/21 16:11
 * @description
 */
@ApiModel("微信小程序登录请求")
@Getter
@Setter
public class WeChatLoginRequest implements Serializable {

    @ApiModelProperty(value = "wx.login生成码")
    private String code;

    @ApiModelProperty(value = "手机号生成码")
    private String phoneCode;

    @ApiModelProperty(value = "openid")
    private String openid;

}
