package com.amp.page;


import com.amp.constant.CommonConstant;
import com.amp.utils.ServletUtils;

/**
 * 表格数据处理1
 */
public class TableSupport
{
    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(CommonConstant.PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(CommonConstant.PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(CommonConstant.ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(CommonConstant.IS_ASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPageDomain();
    }
}
