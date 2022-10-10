package com.amp.dto;

import lombok.Data;

/**
 * 类目请求
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 4:40 下午
 */
@Data
public class CategoryInfoDTO {

    /**
     * 类目名称
     */
    private String name;

    /**
     * 类目描述
     */
    private String description;
}
