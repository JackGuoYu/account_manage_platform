package com.amp.service.impl;

import com.amp.converter.PlatformAccountInfoConverter;
import com.amp.domain.AccountExtend;
import com.amp.domain.PlatformAccountInfo;
import com.amp.domain.PlatformInfo;
import com.amp.dto.PlatformAccountInfoDTO;
import com.amp.enums.StatusEnum;
import com.amp.exception.AmpException;
import com.amp.mapper.AccountExtendMapper;
import com.amp.mapper.PlatformAccountInfoMapper;
import com.amp.mapper.PlatformInfoMapper;
import com.amp.service.IPlatformAccountService;
import com.amp.utils.UserUtils;
import com.amp.utils.StringUtils;
import com.amp.vo.UserAccountVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

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

    @Autowired
    private AccountExtendMapper accountExtendMapper;

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
        //todo 增加使用次数

        return vo;
    }

    @Override
    public List<UserAccountVO> list(PlatformAccountInfoDTO dto) {
        PlatformAccountInfo platformAccountInfo = PlatformAccountInfoConverter.INSTANCE.dto2domain(dto);
        platformAccountInfo.setUserId(UserUtils.getUserId());
        return platformAccountInfoMapper.selectAccountList(platformAccountInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(PlatformAccountInfoDTO dto) {
        PlatformInfo platformInfo = platformInfoMapper.selectById(dto.getPlatformId());
        if(platformInfo == null){
            throw new AmpException("平台信息不存在");
        }
        PlatformAccountInfo platformAccountInfo = PlatformAccountInfoConverter.INSTANCE.dto2domain(dto);
        int count = platformAccountInfoMapper.isExists(platformAccountInfo);
        if(count > 0){
            throw new AmpException("该用户帐号已存在");
        }
        platformAccountInfo.setUserId(UserUtils.getUserId());
        platformAccountInfo.preInsert();
        if(dto.getExpireTime() !=null){
            platformAccountInfo.setEndTime(new Timestamp(dto.getExpireTime()));
        }
        platformAccountInfoMapper.insert(platformAccountInfo);
        AccountExtend accountExtend = new AccountExtend();
        accountExtend.preInsert();
        accountExtend.setAccountId(platformAccountInfo.getId());
        accountExtendMapper.insert(accountExtend);
    }

    @Override
    public void update(PlatformAccountInfoDTO dto) {
        PlatformInfo platformInfo = platformInfoMapper.selectById(dto.getPlatformId());
        if(platformInfo == null){
            throw new AmpException("平台信息不存在");
        }

        PlatformAccountInfo platformAccountInfo = PlatformAccountInfoConverter.INSTANCE.dto2domain(dto);
        int count = platformAccountInfoMapper.isExists(platformAccountInfo);
        if(count > 0){
            throw new AmpException("该用户帐号已存在");
        }
        platformAccountInfo.preUpdate();
        if(dto.getExpireTime() !=null){
            platformAccountInfo.setEndTime(new Timestamp(dto.getExpireTime()));
        }
        platformAccountInfoMapper.update(platformAccountInfo);
    }
}
