package com.amp.service;

import com.amp.domain.UserInfo;
import com.amp.dto.UserInfoDTO;
import com.amp.vo.UserInfoVO;

import java.util.List;

public interface IUserInfoService {

    void add(UserInfoDTO dto);

    List<UserInfoVO> list(UserInfoDTO dto);

    /**
     * 通过手机号获取用户信息
     * @param phone
     * @return
     */
    UserInfo getUserInfoByPhone(String phone);
}
