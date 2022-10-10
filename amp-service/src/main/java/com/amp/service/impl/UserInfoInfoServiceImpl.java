package com.amp.service.impl;

import com.amp.converter.UserInfoConverter;
import com.amp.domain.UserInfo;
import com.amp.dto.UserInfoDTO;
import com.amp.mapper.UserInfoMapper;
import com.amp.service.IUserInfoService;
import com.amp.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户管理服务
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 11:01 上午
 */
@Service
public class UserInfoInfoServiceImpl implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void add(UserInfoDTO dto) {
        UserInfo userInfo = UserInfoConverter.INSTANCE.dto2domain(dto);
        userInfo.preInsert();
        userInfoMapper.insert(userInfo);
    }

    @Override
    public List<UserInfoVO> list(UserInfoDTO dto) {
        UserInfo userInfo = UserInfoConverter.INSTANCE.dto2domain(dto);
        List<UserInfo> list = userInfoMapper.selectList(userInfo);
        return UserInfoConverter.INSTANCE.domain2vo(list);
    }

    public UserInfo getUserInfoByPhone(String phone){
        return userInfoMapper.selectByPhone(phone);
    }
}
