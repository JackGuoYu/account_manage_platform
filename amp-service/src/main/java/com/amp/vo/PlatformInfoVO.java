package com.amp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台信息vo
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 2:38 下午
 */
@Data
public class PlatformInfoVO implements Serializable {

    @ApiModelProperty(value = "平台名称")
    private String name;

    @ApiModelProperty(value = "图标")
    private byte[] icon;

    @ApiModelProperty(value = "类目id")
    private String categoryId;

    @ApiModelProperty(value = "状态 draft-草稿 approve-审核 active-激活 invalid-失效")
    private String status;

    @ApiModelProperty(value = "平台描述")
    private String description;
}
