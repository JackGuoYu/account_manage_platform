package com.amp.service.impl;

import com.amp.domain.UserInfo;
import com.amp.request.LoginRequest;
import com.amp.service.ICommonService;
import com.amp.service.IUserInfoService;
import com.amp.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * 公共服务
 *
 * @author guoyu
 * @version 1.0
 * @date 2022/9/28 4:23 下午
 */
@Slf4j
@Service
public class CommonServiceImpl implements ICommonService {

    @Value("${shiro.enable}")
    public void setShiroEnable(boolean flag){
        shiroEnable = flag;
    }

    private static boolean shiroEnable;

    @Value("${shiro.inactive-time:108000}")
    private int inactiveTime;

    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public UserInfo login(LoginRequest request) {
        UserInfo user;
        if (shiroEnable) {
            UsernamePasswordToken token = new UsernamePasswordToken(request.getLoginId(), request.getPassword());
            Subject subject = SecurityUtils.getSubject();
            //单位毫秒
            subject.getSession().setTimeout(inactiveTime * 1000);
            subject.login(token);
            user = (UserInfo) subject.getPrincipal();
        } else {
            user = userInfoService.getUserInfoByPhone(request.getLoginId());
            HttpSession session = ServletUtils.getSession();
            session.setAttribute("user", user);
            //session过期时间，单元秒
            session.setMaxInactiveInterval(inactiveTime);
        }

        return user;
    }
}
