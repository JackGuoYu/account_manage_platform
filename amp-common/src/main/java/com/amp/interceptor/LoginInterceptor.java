package com.amp.interceptor;

import com.amp.domain.Result;
import com.amp.enums.ResultCodeEnum;
import com.amp.utils.JacksonUtils;
import com.amp.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 登录鉴权
 *
 * @author guoyu
 * @date
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    static String[] passUrls;
    static String[] serverUrls;

    static {

        //应用登录校验-路径注册(新增接口需注册)
        serverUrls = new String[]{
                "/amp/account/",
                "/amp/platform/",
                "/amp/user/",
                "/amp/category/",
        };


        //白名单路径
        passUrls = new String[]{
                "/amp/wechat/",
                "/amp/user/login",
                "/amp/doc.html",
                "/amp/doc.html/",
                "/amp/swagger-resources",
                "/amp/webjars/"
        };
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("请求地址:{}", requestURI);

        //白名单过滤（主要用于过滤命中的校验路径下的子路径）
        if (isPass(requestURI)) {
            return true;
        }

        //校验内部应用是否登录
        if (isLogin(requestURI)) {
            return true;
        }

        return this.loginFail(response, ResultCodeEnum.UN_AUTHENTICATED);
    }

    private boolean loginFail(HttpServletResponse response, ResultCodeEnum codeEnum) throws Exception {
        Result result = new Result(codeEnum.getCode(), codeEnum.getMsg());
        response.setContentType("application/json;charset=UTF-8");
        response.getOutputStream().write(JacksonUtils.obj2json(result).getBytes());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    /**
     * 白名单放行
     *
     * @param reqUrl
     * @return
     */
    private boolean isPass(String reqUrl) {
        return Arrays.asList(passUrls).stream().anyMatch(url -> reqUrl.startsWith(url));
    }


    /**
     * 内部应用登录校验
     *
     * @param reqUrl
     * @return
     */
    public boolean isLogin(String reqUrl) {
        boolean flag = Arrays.asList(serverUrls).stream().anyMatch(url -> reqUrl.startsWith(url));
        if (flag) {
            return UserUtils.getUser() != null;
        }
        return false;
    }

}
