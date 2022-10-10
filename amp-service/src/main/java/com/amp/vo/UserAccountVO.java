package com.amp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户帐号信息
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/9 3:05 下午
 */
@Data
public class UserAccountVO {

    @ApiModelProperty(value = "用户帐号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    @ApiModelProperty(value = "平台名称")
    private String platformName;

    @ApiModelProperty(value = "平台图标")
    private byte[] icon;

    @ApiModelProperty(value = "帐号状态")
    private String status;
}
