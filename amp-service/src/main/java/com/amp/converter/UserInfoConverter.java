package com.amp.converter;

import com.amp.domain.UserInfo;
import com.amp.dto.UserInfoDTO;
import com.amp.request.UserInfoQueryRequest;
import com.amp.request.UserInfoRequest;
import com.amp.vo.UserInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserInfoConverter {
    UserInfoConverter INSTANCE = Mappers.getMapper(UserInfoConverter.class);

    UserInfoDTO request2dto(UserInfoRequest source);

    UserInfoDTO request2dto(UserInfoQueryRequest source);

    UserInfo dto2domain(UserInfoDTO source);

    List<UserInfoVO> domain2vo(List<UserInfo> source);
}
