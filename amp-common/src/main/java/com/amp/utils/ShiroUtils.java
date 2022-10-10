package com.amp.utils;


import com.amp.constant.CommonConstant;
import com.amp.domain.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * shiro 工具类
 *
 * @author guoyu
 */
@Component
public class ShiroUtils {
    private static final Logger log = LoggerFactory.getLogger(ShiroUtils.class);

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static void logout() {
        getSubject().logout();
    }


    private static boolean shiroEnable;

    @Value("${shiro.enable:true}")
    private void setEnable(boolean enable){
        ShiroUtils.shiroEnable = enable;
    }

    private static boolean isDebug;

    @Value("${shiro.isDebug:false}")
    private void setDebug(boolean isDebug){
        ShiroUtils.isDebug = isDebug;
    }

    /**
     * 会话时长
     */
    private static int inactiveTime;

    @Value("${shiro.inactive-time:108000}")
    public void setInactiveTime(int time){
        inactiveTime = time;
    }




    public static UserInfo getUser(){
        UserInfo user = null;
        if (shiroEnable) {
            Object obj = SecurityUtils.getSubject().getPrincipal();
            if (Objects.nonNull(obj)) {
                user = new UserInfo();
                BeanConvertUtils.beanCopier(obj,user);
            }

        } else {
            user = (UserInfo) ServletUtils.getSession().getAttribute("user");
        }
        return user;
    }

    public static String getUserName(){
        String userName = null;
        UserInfo userInfo = getUser();
        if(userInfo != null){
            userName = userInfo.getNickname();
        }

        if(StringUtils.isNotEmpty(userName)){
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


    public static boolean isDebug(){
        return isDebug;
    }

    public static String getSessionId() {
        return String.valueOf(getSubject().getSession().getId());
    }
}
