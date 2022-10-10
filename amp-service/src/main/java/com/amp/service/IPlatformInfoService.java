package com.amp.service;

import com.amp.dto.PlatformInfoDTO;
import com.amp.vo.PlatformInfoVO;

import java.util.List;

public interface IPlatformInfoService {

    void add(PlatformInfoDTO dto);

    void update(PlatformInfoDTO dto);

    List<PlatformInfoVO> list(PlatformInfoDTO dto);

}
