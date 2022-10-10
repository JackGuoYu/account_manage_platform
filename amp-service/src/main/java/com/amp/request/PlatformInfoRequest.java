package com.amp.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台管理请求
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/8 10:51 上午
 */
@Data
public class PlatformInfoRequest {

    @ApiModelProperty(value = "平台名称")
    private String name;

    @ApiModelProperty(value = "图标")
    private byte[] icon;

    @ApiModelProperty(value = "类目id")
    private String categoryId;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "draft-草稿 approve-审核 active-激活 invalid-失效")
    private String status;
}
