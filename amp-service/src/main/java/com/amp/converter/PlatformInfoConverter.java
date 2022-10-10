package com.amp.converter;

import com.amp.domain.PlatformInfo;
import com.amp.dto.PlatformInfoDTO;
import com.amp.request.PlatformInfoQueryRequest;
import com.amp.request.PlatformInfoRequest;
import com.amp.vo.PlatformInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PlatformInfoConverter {
    PlatformInfoConverter INSTANCE = Mappers.getMapper(PlatformInfoConverter.class);

    PlatformInfoDTO request2dto(PlatformInfoRequest source);

    PlatformInfoDTO request2dto(PlatformInfoQueryRequest source);

    PlatformInfo dto2domain(PlatformInfoDTO source);

    List<PlatformInfoVO> domain2vo(List<PlatformInfo> source);
}
