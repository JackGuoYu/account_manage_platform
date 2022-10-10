package com.amp.controller;


import com.amp.constant.CommonConstant;
import com.amp.domain.Result;
import com.amp.page.PageDomain;
import com.amp.page.PageHelperDialect;
import com.amp.page.TableSupport;
import com.amp.request.BaseQueryRequest;
import com.amp.utils.DateUtils;
import com.amp.utils.ServletUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.amp.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * web层通用数据处理
 *
 * @author guoyu
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 封装一层，设置当前页、每页条数
     * @param pageRequest
     */
    protected void startPage(BaseQueryRequest pageRequest) {
        if(pageRequest.getIsPage() == CommonConstant.YES){
            this.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        }
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage(Integer num, Integer size) {
        PageHelperDialect.LOCAL_PAGE.remove();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (num != null) {
            pageNum = num;
        }
        if (size != null) {
            pageSize = size;
        }
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize, SqlUtils.escapeOrderBySql(pageDomain.getOrderBy()));
    }

    /**
     * 设置请求排序数据
     */
    protected void startOrderBy() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
            String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected PageInfo getPageInfo(List<?> list) {
        Page page = PageHelperDialect.LOCAL_PAGE.get();
        if(!Objects.isNull(list) && !Objects.isNull(page)){
            PageInfo pageInfo = new PageInfo<>(page);
            pageInfo.setList(list);
            return pageInfo;
        }
        if(!Objects.isNull(page)){
            PageHelperDialect.LOCAL_PAGE.remove();
            return new PageInfo<>(page);
        }

        if(!Objects.isNull(list)){
            return new PageInfo<>(list);
        }
        return new PageInfo<>(new ArrayList<>());
    }


    /**
     * 返回成功消息
     */
    public Result success(String message) {
        return Result.successWithoutData(message);
    }


}
