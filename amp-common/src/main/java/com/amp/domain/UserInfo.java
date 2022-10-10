package com.amp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/26 5:32 下午
 */
@TableName("amp_user_info")
@Data
@ApiModel(value = "UserInfo对象", description = "用户信息表")
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @ApiModelProperty(value = "微信openid")
    private String openid;

    @ApiModelProperty(value = "账户状态,正常:enable,禁用:disable")
    private String status;

    @ApiModelProperty(value = "用户描述信息")
    private String description;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户头像")
    private byte[] avatarImage;

}
