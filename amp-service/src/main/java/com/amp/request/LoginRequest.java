package com.amp.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author guoyu
 * @date 2022/09/28 15:24
 * @description
 */
@ApiModel("登录请求")
@Getter
@Setter
public class LoginRequest implements Serializable {

    @ApiModelProperty(value = "登录账号")
    private String loginId;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

    public LoginRequest(String loginId, String password){
        this.loginId = loginId;
        this.password = password;
    }
}
