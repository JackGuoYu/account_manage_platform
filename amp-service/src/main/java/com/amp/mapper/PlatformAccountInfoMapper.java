package com.amp.mapper;

import com.amp.domain.PlatformAccountInfo;
import com.amp.vo.UserAccountVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlatformAccountInfoMapper extends BaseMapper<PlatformAccountInfo>{

    UserAccountVO selectOneByPlatformId(String platformId);

    List<UserAccountVO> selectAccountList(PlatformAccountInfo platformAccountInfo);
}
