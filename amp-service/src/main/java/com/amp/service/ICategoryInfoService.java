package com.amp.service;

import com.amp.dto.CategoryInfoDTO;
import com.amp.vo.CategoryInfoVO;

import java.util.List;

public interface ICategoryInfoService {

    void add(CategoryInfoDTO dto);

    List<CategoryInfoVO> list(CategoryInfoDTO dto);
}
