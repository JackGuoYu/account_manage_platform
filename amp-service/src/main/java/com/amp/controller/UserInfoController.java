package com.amp.controller;

import com.amp.annotation.ApiExt;
import com.amp.converter.UserInfoConverter;
import com.amp.domain.Result;
import com.amp.domain.UserInfo;
import com.amp.dto.UserInfoDTO;
import com.amp.request.LoginRequest;
import com.amp.request.UserInfoQueryRequest;
import com.amp.request.UserInfoRequest;
import com.amp.service.ICommonService;
import com.amp.service.IUserInfoService;
import com.amp.vo.UserInfoVO;
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
 * 用户管理
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 10:52 上午
 */
@Api(tags = "用户管理")
@RequestMapping(value = "/user")
@RestController
@Slf4j
public class UserInfoController extends BaseController{

    @Autowired
    private IUserInfoService userService;

    @Autowired
    private ICommonService commonService;

    @ApiOperation("新增用户信息")
    @PostMapping("/add")
    public Result<Void> addUser(@RequestBody UserInfoRequest request) {
        UserInfoDTO dto = UserInfoConverter.INSTANCE.request2dto(request);
        userService.add(dto);
        return Result.success();
    }

    @ApiOperation("用户列表")
    @PostMapping("/list")
    public Result<PageInfo<UserInfoVO>> pageList(@RequestBody UserInfoQueryRequest request) {
        startPage(request);
        UserInfoDTO dto = UserInfoConverter.INSTANCE.request2dto(request);
        List<UserInfoVO> list = userService.list(dto);
        return Result.success(getPageInfo(list));
    }

    @ApiOperation("用户登录接口，用于测试联调使用")
    @PostMapping("/login")
    public Result<UserInfoVO> login(@ApiExt(ignore = {"password", "code"})  @RequestBody LoginRequest request) {
        UserInfo userInfo = commonService.login(request);
        return Result.success(UserInfoConverter.INSTANCE.domain2vo(userInfo));
    }

}
