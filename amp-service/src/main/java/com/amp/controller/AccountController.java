package com.amp.controller;

import com.amp.converter.PlatformAccountInfoConverter;
import com.amp.domain.Result;
import com.amp.dto.PlatformAccountInfoDTO;
import com.amp.request.PlatformAccountQueryRequest;
import com.amp.service.IPlatformAccountService;
import com.amp.vo.UserAccountVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 帐号信息管理
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/10/8 10:47 上午
 */
@Api(tags = "帐号管理")
@RequestMapping(value = "/account")
@RestController
@Slf4j
public class AccountController extends BaseController{


    @Autowired
    private IPlatformAccountService platformAccountService;

    @ApiOperation("帐号列表")
    @PostMapping("/list")
    public Result<PageInfo<UserAccountVO>> list(@RequestBody PlatformAccountQueryRequest request) {
        startPage(request);
        PlatformAccountInfoDTO dto = PlatformAccountInfoConverter.INSTANCE.request2dto(request);
        List<UserAccountVO> list = platformAccountService.list(dto);
        return Result.success(getPageInfo(list));
    }

}
