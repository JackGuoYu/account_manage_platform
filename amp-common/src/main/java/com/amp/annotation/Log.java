package com.amp.annotation;


import com.amp.enums.OperatorTypeEnum;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * 
 * @author guoyu
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log
{
    /**
     * 业务类型
     */
    public String businessType() default "";

    /**
     * 操作类型
     */
    public OperatorTypeEnum operatorType() default OperatorTypeEnum.OTHER;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
