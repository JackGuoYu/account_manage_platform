package com.amp.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 类目信息
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 4:51 下午
 */
@TableName("amp_category_info")
@Data
@ApiModel(value = "CategoryInfo对象", description = "类目信息表")
public class CategoryInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类目名称")
    private String name;

    @ApiModelProperty(value = "类目描述")
    private String description;
}
