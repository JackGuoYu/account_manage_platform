package com.amp.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.page.PageMethod;

/**
 * 分页扩展类，用于分页sql执行过程中执行其他不分页sql操作
 */
public class PageHelperExtend extends PageMethod {
    protected static final ThreadLocal<Page> LOCAL_PAGE_EXTEND = new ThreadLocal();

    /**
     * 暂停分页操作
     */
    public static void stopPage(){
        LOCAL_PAGE_EXTEND.set(LOCAL_PAGE.get());
        clearPage();
    }

    /**
     * 重新开启分页操作
     */
    public static void restartPage(){
        LOCAL_PAGE.set(LOCAL_PAGE_EXTEND.get());
    }
}
