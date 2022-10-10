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
public class WeChatBindRequest implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "加密数据")
    private String encryptedData;

    @ApiModelProperty(value = "会话密钥")
    private String sessionKey;

    @ApiModelProperty(value = "解密偏移量")
    private String iv;

}
