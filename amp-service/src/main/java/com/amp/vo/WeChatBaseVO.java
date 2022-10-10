package com.amp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信base响应类
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/7/26 11:21 上午
 */
@Data
public class WeChatBaseVO {

    @ApiModelProperty(value = "错误码")
    private String errcode;

    @ApiModelProperty(value = "错误信息")
    private String errmsg;
}
