package com.amp.service.impl;

import com.amp.domain.PlatformInfo;
import com.amp.enums.StatusEnum;
import com.amp.exception.AmpException;
import com.amp.mapper.PlatformAccountInfoMapper;
import com.amp.mapper.PlatformInfoMapper;
import com.amp.service.IPlatformAccountService;
import com.amp.utils.StringUtils;
import com.amp.vo.UserAccountVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 平台账户服务
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/9 3:22 下午
 */
@Service
public class PlatformAccountServiceImpl implements IPlatformAccountService {

    @Autowired
    private PlatformAccountInfoMapper platformAccountInfoMapper;

    @Autowired
    private PlatformInfoMapper platformInfoMapper;

    @Override
    public UserAccountVO getAccount(String platformId) {
        PlatformInfo platformInfo = platformInfoMapper.selectOne(new LambdaQueryWrapper<PlatformInfo>().eq(PlatformInfo::getId, platformId)
                .eq(PlatformInfo::getStatus, StatusEnum.ACTIVE.getValue()));
        if(platformInfo == null){
            throw new AmpException("查询不到平台信息");
        }
        UserAccountVO vo = platformAccountInfoMapper.selectOneByPlatformId(platformId);
        if(vo != null && StringUtils.isNotEmpty(vo.getPassword())){
            //todo 解密密码
        }
        return vo;
    }
}
