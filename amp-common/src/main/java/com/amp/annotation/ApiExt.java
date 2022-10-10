package com.amp.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiExt {
    String[] ignore() default {""}; //忽略对象属性值
    String[] required() default {""}; //必输
    String[] optional() default {""}; //非必输
}
