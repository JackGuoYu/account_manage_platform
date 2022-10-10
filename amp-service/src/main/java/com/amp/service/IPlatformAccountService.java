package com.amp.service;

import com.amp.vo.UserAccountVO;

public interface IPlatformAccountService {

    /**
     * 获取用户帐号
     * @param platformId
     * @return
     */
    UserAccountVO getAccount(String platformId);
}
