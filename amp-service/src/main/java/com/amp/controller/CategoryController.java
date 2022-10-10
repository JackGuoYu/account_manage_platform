package com.amp.controller;

import com.amp.converter.CategoryInfoConverter;
import com.amp.domain.Result;
import com.amp.dto.CategoryInfoDTO;
import com.amp.request.CategoryInfoQueryRequest;
import com.amp.request.CategoryInfoRequest;
import com.amp.service.ICategoryInfoService;
import com.amp.utils.ParamsUtils;
import com.amp.vo.CategoryInfoVO;
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
 * 类目管理
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/27 4:24 下午
 */
@Api(tags = "类目管理")
@RequestMapping(value = "/category")
@RestController
@Slf4j
public class CategoryController extends BaseController{

    @Autowired
    private ICategoryInfoService categoryInfoService;

    @ApiOperation("新增类目")
    @PostMapping("/add")
    public Result<Void> addCategory(@RequestBody CategoryInfoRequest request) {
        ParamsUtils.checkParamsIsNull(request.getName(), "类目名称不能为空");
        CategoryInfoDTO dto = CategoryInfoConverter.INSTANCE.request2dto(request);
        categoryInfoService.add(dto);
        return Result.success();
    }

    @ApiOperation("类目列表")
    @PostMapping("/list")
    public Result<List<CategoryInfoVO>> list(@RequestBody CategoryInfoQueryRequest request) {
        CategoryInfoDTO dto = CategoryInfoConverter.INSTANCE.request2dto(request);
        return Result.success(categoryInfoService.list(dto));
    }
}
