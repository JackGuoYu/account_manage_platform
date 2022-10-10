package com.amp.dto;

import lombok.Data;

/**
 * 平台管理DTO
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/8 10:51 上午
 */
@Data
public class PlatformInfoDTO {

     //平台名称
    private String name;

     //图标
    private byte[] icon;

     //类目id
    private String categoryId;

     //描述
    private String description;

     //draft-草稿 approve-审核 active-激活 invalid-失效
    private String status;
}
