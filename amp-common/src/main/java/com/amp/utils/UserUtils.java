package com.amp.utils;


import com.amp.constant.CommonConstant;
import com.amp.domain.UserInfo;
import org.springframework.stereotype.Component;

/**
 * 用户会话工具类
 *
 * @author guoyu
 */
@Component
public class UserUtils {

    public static UserInfo getUser(){
        return  (UserInfo) ServletUtils.getSession().getAttribute("user");
    }

    public static String getUserName(){
        String userName = null;
        UserInfo userInfo = getUser();
        if(userInfo != null){
            userName = userInfo.getNickname();
        }

        if(StringUtils.isEmpty(userName)){
            userName = CommonConstant.SYSTEM_NAME;
        }
        return userName;
    }

    public static String getUserId(){
        String userName = null;
        UserInfo userInfo = getUser();
        if(userInfo != null){
            userName = userInfo.getId();
        }
        return userName;
    }
}
