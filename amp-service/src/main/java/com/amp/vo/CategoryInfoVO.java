package com.amp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 类目信息vo
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 2:38 下午
 */
@Data
public class CategoryInfoVO implements Serializable {

    @ApiModelProperty(value = "类目名称")
    private String name;

    @ApiModelProperty(value = "类目描述")
    private String description;
}
