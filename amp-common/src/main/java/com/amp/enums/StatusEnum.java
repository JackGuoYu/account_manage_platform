package com.amp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 渠道类型
 * @author guoyu
 * @date 2021/12/13 18:04
 * @description
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    DRAFT("draft", "草稿"),
    APPROVE("approve", "审核"),
    ACTIVE("active", "激活"),
    INVALID("invalid", "失效");

    private String value;
    private String desc;

}
