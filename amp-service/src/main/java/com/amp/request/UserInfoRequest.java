package com.amp.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户请求
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 10:53 上午
 */

@Data
@ApiModel("用户请求")
public class UserInfoRequest {

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "用户描述信息")
    private String description;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户头像")
    private byte[] avatarImage;
}
