package com.amp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 帐号扩展信息
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/8 10:51 上午
 */
@TableName("amp_account_extend")
@Data
@ApiModel(value = "AccountExtend对象", description = "账户扩展信息表")
public class AccountExtend extends BaseEntity {

    //帐号id
    private String accountId;

    //使用次数
    private Integer useCount;

    //帐号收益
    private BigDecimal totalIncome;
}