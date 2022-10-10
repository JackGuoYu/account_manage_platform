package com.amp.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 类目请求
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 4:40 下午
 */
@Data
@ApiModel("类目请求")
public class CategoryInfoRequest {

    @ApiModelProperty(value = "类目名称")
    private String name;

    @ApiModelProperty(value = "类目描述")
    private String description;
}
