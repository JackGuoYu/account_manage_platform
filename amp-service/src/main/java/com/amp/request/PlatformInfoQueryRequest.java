package com.amp.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 类目查询请求
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 2:23 下午
 */
@Data
public class PlatformInfoQueryRequest extends BaseQueryRequest{

    @ApiModelProperty(value = "平台名称")
    private String name;

    @ApiModelProperty(value = "类目id")
    private String categoryId;


}
