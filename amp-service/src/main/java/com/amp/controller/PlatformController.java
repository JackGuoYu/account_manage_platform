package com.amp.controller;

import com.amp.converter.PlatformInfoConverter;
import com.amp.domain.Result;
import com.amp.dto.PlatformInfoDTO;
import com.amp.enums.ResultCodeEnum;
import com.amp.exception.AmpException;
import com.amp.request.PlatformInfoQueryRequest;
import com.amp.request.PlatformInfoRequest;
import com.amp.service.IPlatformAccountService;
import com.amp.service.IPlatformInfoService;
import com.amp.utils.AmpFileUtils;
import com.amp.utils.ParamsUtils;
import com.amp.utils.ServletUtils;
import com.amp.vo.PlatformInfoVO;
import com.amp.vo.UserAccountVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @ApiOperation(value = "新增平台信息", notes = "新增平台信息")
    @PostMapping("/add")
    public Result<Void> add(@RequestBody PlatformInfoRequest request) {
        ParamsUtils.checkParamsIsNull(request.getName(), "平台名称不能为空");
        ParamsUtils.checkParamsIsNull(request.getCategoryId(), "类目ID不能为空");
        PlatformInfoDTO dto = PlatformInfoConverter.INSTANCE.request2dto(request);
        platformService.add(dto);
        return Result.success();
    }

    @ApiOperation(value = "更新平台信息", notes = "更新平台信息")
    @PostMapping("/update")
    public Result<Void> update(@RequestBody PlatformInfoRequest request) {
        ParamsUtils.checkParamsIsNull(request.getName(), "平台名称不能为空");
        ParamsUtils.checkParamsIsNull(request.getCategoryId(), "类目ID不能为空");
        PlatformInfoDTO dto = PlatformInfoConverter.INSTANCE.request2dto(request);
        platformService.update(dto);
        return Result.success();
    }


    @ApiOperation(value = "平台图标上传", notes = "平台图标上传")
    @PostMapping("/upload")
    public Result<Void> upload(MultipartFile multipartFile) throws IOException {
        String fileName = ServletUtils.getRequest().getHeader("fileName");
        String extendName = AmpFileUtils.getFileExtendName(fileName);
        if (!AmpFileUtils.isSupportImage(extendName)) {
            throw new AmpException(ResultCodeEnum.DOC_FILE_TYPE_UNSUPPORTED);
        }

        AmpFileUtils.checkFileSize(multipartFile.getSize(), AmpFileUtils.IMAGE_FILE_SIZE_LIMIT);

        PlatformInfoDTO platformInfoDTO = new PlatformInfoDTO();
        platformInfoDTO.setIcon(multipartFile.getBytes());
        platformService.update(platformInfoDTO);
        return Result.success();
    }

}
