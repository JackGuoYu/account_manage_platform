package com.amp.mapper;

import com.amp.domain.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户持久层
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 9:58 上午
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo selectByPhone(@Param(value = "phone") String phone);
}
