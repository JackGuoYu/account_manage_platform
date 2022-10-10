package com.amp.constant;

/**
 * 通用常量
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/5 4:34 下午
 */
public class CommonConstant {

    /**
     * 通用标识：1-是
     */
    public static final Integer YES = 1;
    /**
     * 通用标识：0-否
     */
    public static final Integer NO = 0;

    /**
     * +分隔符
     */
    public final static String DELIMITER_ADD_SIGN = "+";

    /**
     * +分隔符转义
     */
    public final static String DELIMITER_ADD_SIGN_TRANSFER = "%2B";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 默认系统名称
     */
    public final static String SYSTEM_NAME = "system";

    /**
     * 默认用户密码
     */
    public final static String USER_DEFAULT_PASSWD = "123456";
}
