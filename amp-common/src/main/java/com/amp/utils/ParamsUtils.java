package com.amp.utils;

import com.amp.enums.ResultCodeEnum;
import com.amp.exception.AmpException;

import java.util.List;
import java.util.regex.Pattern;

public class ParamsUtils {
    public static void checkParamsIsNull(Object obj, String msg) {
        if (obj == null || StringUtils.isBlank(obj.toString())) {
            throw new AmpException(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(), msg);
        }
    }

    public static void checkListIsNull(List<?> list, String msg) {
        if (list == null || list.size() == 0) {
            throw new AmpException(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(), msg);
        }
    }


    /**
     * 校验邮箱
     * @param email
     */
    public static void vailEmail(String email, String message){
        if(!vailEmail(email)){
            throw new AmpException(message);
        }
    }

    /**
     * 校验邮箱
     *
     * @param email 邮箱
     * @return true 正常
     * @author Emil.Wu
     * @date 2022-01-25 11:18
     * @version 1.0
     */
    public static boolean vailEmail(String email) {
        return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
    }


        /**
         * 校验手机号
         * @param phone
         * @param message
         */
    public static void vailPhone(String phone, String message){
        if(!vailPhone(phone)){
            throw new AmpException(message);
        }
    }

    /**
     * 校验手机号
     *
     * @param phone
     * @return true 正常
     * @author Emil.Wu
     * @date 2022-01-25 11:16
     * @version 1.0
     */
    public static boolean vailPhone(String phone) {
        return Pattern.matches("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1、5、8、9]))\\d{8}$", phone);
    }
}
