package com.amp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 平台信息
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/8 10:51 上午
 */
@TableName("amp_platform_info")
@Data
@ApiModel(value = "PlatformInfo对象", description = "平台信息表")
public class PlatformInfo extends BaseEntity{

     //平台名称
    private String name;

     //图标
    private byte[] icon;

     //类目id
    private String categoryId;

     //draft-草稿 approve-审核 active-激活 invalid-失效
    private String status;
}
