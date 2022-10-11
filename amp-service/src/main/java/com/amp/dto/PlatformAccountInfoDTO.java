package com.amp.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 平台管理DTO
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/8 10:51 上午
 */
@Data
public class PlatformAccountInfoDTO {

    //id
    private String id;

    //用户帐号
    private String userName;

    //密码
    private String password;

    //平台id
    private String platformId;

    //状态 draft-草稿 approve-审批 active-激活 invalid-失效
    private String status;

    //原价
    private BigDecimal price;

    //用户Id
    private String userId;

    //截止时间
    private Long expireTime;
}
