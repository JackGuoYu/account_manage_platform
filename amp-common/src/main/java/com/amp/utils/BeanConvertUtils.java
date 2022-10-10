package com.amp.utils;

import org.springframework.cglib.beans.BeanCopier;

/**
 * @author guoyu
 * @date 2021/09/16 19:07
 * @description
 */
public class BeanConvertUtils {

public static void beanCopier(Object source, Object target ){
    BeanCopier copier =  BeanCopier.create(source.getClass(),target.getClass(),false);
    copier.copy(source,target,null);
}
}
