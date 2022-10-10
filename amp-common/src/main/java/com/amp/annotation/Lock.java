package com.amp.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解，默认不填参数则针对某一个方法做防重复提交，如果填写参数则可以做精细化控制
 * 
 * @author guoyu
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock
{
    /**
     * 业务名称
     */
     String keyName() default "";

    /**
     * 动态参数，可传多个字段获取动态参数进行拼接
     * @return
     */
    String[] fields() default {""};

    /**
     * 锁时长
     */
    long time() default 5L;

    /**
     * 锁时长类型
     */
     TimeUnit timeType() default TimeUnit.SECONDS;
}
