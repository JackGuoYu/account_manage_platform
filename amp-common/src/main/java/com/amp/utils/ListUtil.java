package com.amp.utils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * list 工具类
 *
 * add by wuxw 2020-10-16
 */
public class ListUtil {

    /**
     * 判断 List 是否为空
     *
     * @param values list 数据
     * @return 空为true 有值为false
     */
    public  static boolean isNull(List values) {

        if (values == null) {
            return true;
        }

        if (values.size() < 1) {
            return true;
        }

        return false;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
