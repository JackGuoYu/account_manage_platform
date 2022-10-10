package com.amp.converter;

import com.amp.domain.PlatformAccountInfo;
import com.amp.dto.PlatformAccountInfoDTO;
import com.amp.request.PlatformAccountInfoRequest;
import com.amp.request.PlatformAccountQueryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlatformAccountInfoConverter {
    PlatformAccountInfoConverter INSTANCE = Mappers.getMapper(PlatformAccountInfoConverter.class);

    PlatformAccountInfoDTO request2dto(PlatformAccountQueryRequest source);

    PlatformAccountInfoDTO request2dto(PlatformAccountInfoRequest source);

    PlatformAccountInfo dto2domain(PlatformAccountInfoDTO source);

}
