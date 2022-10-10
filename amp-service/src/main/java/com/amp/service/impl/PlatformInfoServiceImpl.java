package com.amp.service.impl;

import com.amp.converter.PlatformInfoConverter;
import com.amp.domain.PlatformInfo;
import com.amp.dto.PlatformInfoDTO;
import com.amp.enums.StatusEnum;
import com.amp.mapper.PlatformInfoMapper;
import com.amp.service.IPlatformInfoService;
import com.amp.vo.PlatformInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 平台管理
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/09 10:49 下午
 */
@Service
public class PlatformInfoServiceImpl implements IPlatformInfoService {

    @Autowired
    private PlatformInfoMapper platformInfoMapper;

    @Override
    public void add(PlatformInfoDTO dto) {
        PlatformInfo platformInfo = PlatformInfoConverter.INSTANCE.dto2domain(dto);
        platformInfo.preInsert();
        platformInfoMapper.insert(platformInfo);
    }

    @Override
    public List<PlatformInfoVO> list(PlatformInfoDTO dto) {
        PlatformInfo platformInfo = PlatformInfoConverter.INSTANCE.dto2domain(dto);
        platformInfo.setStatus(StatusEnum.ACTIVE.getValue());
        List<PlatformInfo> list = platformInfoMapper.selectList(platformInfo);
        return PlatformInfoConverter.INSTANCE.domain2vo(list);
    }
}
