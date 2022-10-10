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
public class PlatformAccountQueryRequest extends BaseQueryRequest{

    @ApiModelProperty(value = "平台Id")
    private String platformId;

    @ApiModelProperty(value = "状态 draft-草稿 approve-审批 active-激活 invalid-失效")
    private String status;


}
