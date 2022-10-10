package com.amp.controller;

import com.amp.converter.PlatformInfoConverter;
import com.amp.domain.Result;
import com.amp.dto.PlatformInfoDTO;
import com.amp.request.PlatformInfoQueryRequest;
import com.amp.service.IPlatformAccountService;
import com.amp.service.IPlatformInfoService;
import com.amp.utils.ParamsUtils;
import com.amp.vo.PlatformInfoVO;
import com.amp.vo.UserAccountVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平台信息管理
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/8 10:47 上午
 */
@Api(tags = "平台管理")
@RequestMapping(value = "/platform")
@RestController
@Slf4j
public class PlatformController extends BaseController{

    @Autowired
    private IPlatformInfoService platformService;

    @Autowired
    private IPlatformAccountService platformAccountService;

    @ApiOperation("平台列表")
    @PostMapping("/list")
    public Result<PageInfo<PlatformInfoVO>> list(@RequestBody PlatformInfoQueryRequest request) {
        startPage(request);
        PlatformInfoDTO dto = PlatformInfoConverter.INSTANCE.request2dto(request);
        List<PlatformInfoVO> list = platformService.list(dto);
        return Result.success(getPageInfo(list));
    }

    @ApiOperation("点击获取帐号")
    @PostMapping("/getAccount")
    public Result<UserAccountVO> getAccount(@RequestParam(value = "platformId") String platformId) {
        ParamsUtils.checkParamsIsNull(platformId, "平台ID不能为空");
        return Result.success(platformAccountService.getAccount(platformId));
    }

}
