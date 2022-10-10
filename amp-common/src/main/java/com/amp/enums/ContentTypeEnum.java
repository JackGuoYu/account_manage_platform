package com.amp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Content-Type类型
 *
 * @author guoyu
 * @version 1.0
 * @date 2021/10/29 3:37 下午
 */
@Getter
@AllArgsConstructor
public enum ContentTypeEnum {
    IMAGE_JPEG("image/jpeg", "图片类型jpeg"),
    APPLICATION_JSON("application/json", "json类型");

    private final String value;
    private final String desc;
}
