package com.amp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 扫码认证返回参数
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/7/21 4:20 下午
 */
@Data
public class WeChatAuthVO extends WeChatBaseVO{


    @ApiModelProperty(value = "用户在开放平台的唯一标识符")
    private String unionid;

    @ApiModelProperty(value = "会话密钥")
    private String sessionKey;

    @ApiModelProperty(value = "用户唯一标识")
    private String openid;
}
