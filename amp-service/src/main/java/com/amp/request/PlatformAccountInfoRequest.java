package com.amp.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 平台管理DTO
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/8 10:51 上午
 */
@Data
public class PlatformAccountInfoRequest {

    @ApiModelProperty(value = "用户帐号")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "平台id")
    private String platformId;

    @ApiModelProperty(value = "状态 draft-草稿 approve-审批 active-激活 invalid-失效")
    private String status;

    @ApiModelProperty(value = "原价")
    private BigDecimal price;

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "截止时间")
    private Timestamp endTime;
}
