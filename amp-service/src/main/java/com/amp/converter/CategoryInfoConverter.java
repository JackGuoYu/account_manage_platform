package com.amp.converter;

import com.amp.domain.CategoryInfo;
import com.amp.dto.CategoryInfoDTO;
import com.amp.request.CategoryInfoQueryRequest;
import com.amp.request.CategoryInfoRequest;
import com.amp.vo.CategoryInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryInfoConverter {
    CategoryInfoConverter INSTANCE = Mappers.getMapper(CategoryInfoConverter.class);

    CategoryInfoDTO request2dto(CategoryInfoRequest source);

    CategoryInfoDTO request2dto(CategoryInfoQueryRequest source);

    CategoryInfo dto2domain(CategoryInfoDTO source);

    List<CategoryInfoVO> domain2vo(List<CategoryInfo> source);
}
