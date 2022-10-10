package com.amp;

import com.amp.domain.UserInfo;
import com.amp.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TODO
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 10:00 上午
 */
@SpringBootTest(classes = {AmpApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class UserTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void testAddUser(){
        UserInfo userInfo = new UserInfo();
        userInfo.preInsert();
        userInfo.setAvatarImage(null);
        userInfo.setDescription("郭煜xxx信息");
        userInfo.setNickname("guoyu");
        userInfo.setPassword("123456");
        userInfo.setOpenid("123xasdwr3ad23423");
        userInfo.setPhone("13570871528");
        userInfoMapper.insert(userInfo);
        log.info("新增一条用户信息");
    }

}

