package com.amp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 平台信息
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/8 10:51 上午
 */
@TableName("amp_platform_account_info")
@Data
@ApiModel(value = "PlatformAccountInfo对象", description = "平台账户信息表")
public class PlatformAccountInfo extends BaseEntity {

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
    private Timestamp endTime;
}