package com.amp.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求基类
 * @author guoyu
 */
@Getter
@Setter
public class BaseQueryRequest extends BaseRequest{

    @ApiModelProperty(value = "是否开启分页 0-不开启 1-开启", example = "1")
    private int isPage = 1;

    @ApiModelProperty(value = "当前页码", example = "1")
    private int pageNum = 1;

    @ApiModelProperty(value = "每页数量", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "排序列", example = "id", hidden = true)
    private String orderByColumn;

    @ApiModelProperty(value = "排序的方向 desc 或者 asc", example = "desc", hidden = true)
    private String isAsc;

}
