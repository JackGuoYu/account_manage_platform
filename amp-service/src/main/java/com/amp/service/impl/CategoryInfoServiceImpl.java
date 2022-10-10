package com.amp.service.impl;

import com.amp.converter.CategoryInfoConverter;
import com.amp.domain.CategoryInfo;
import com.amp.dto.CategoryInfoDTO;
import com.amp.mapper.CategoryInfoMapper;
import com.amp.service.ICategoryInfoService;
import com.amp.utils.StringUtils;
import com.amp.vo.CategoryInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目管理
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 4:38 下午
 */
@Service
public class CategoryInfoServiceImpl implements ICategoryInfoService {

    @Autowired
    private CategoryInfoMapper categoryInfoMapper;

    @Override
    public void add(CategoryInfoDTO dto) {
        CategoryInfo categoryInfo = CategoryInfoConverter.INSTANCE.dto2domain(dto);
        categoryInfo.preInsert();
        categoryInfoMapper.insert(categoryInfo);
    }

    @Override
    public List<CategoryInfoVO> list(CategoryInfoDTO dto) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.select("name", "description");
        wrapper.like(StringUtils.isNotEmpty(dto.getName()), "name", dto.getName());
        List<CategoryInfo> list = categoryInfoMapper.selectList(wrapper);
        return CategoryInfoConverter.INSTANCE.domain2vo(list);
    }
}
