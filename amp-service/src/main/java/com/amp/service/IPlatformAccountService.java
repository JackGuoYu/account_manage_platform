package com.amp.service;

import com.amp.dto.PlatformAccountInfoDTO;
import com.amp.vo.UserAccountVO;

import java.util.List;

public interface IPlatformAccountService {

    /**
     * 获取用户帐号
     * @param platformId
     * @return
     */
    UserAccountVO getAccount(String platformId);

    /**
     * 查询帐号列表
     * @param dto
     * @return
     */
    List<UserAccountVO> list(PlatformAccountInfoDTO dto);

    /**
     * 新增帐号信息
     * @param dto
     */
    void add(PlatformAccountInfoDTO dto);

    /**
     * 更新帐号信息
     * @param dto
     */
    void update(PlatformAccountInfoDTO dto);
}
