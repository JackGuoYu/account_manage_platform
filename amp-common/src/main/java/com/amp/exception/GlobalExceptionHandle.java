package com.amp.exception;


import com.amp.domain.Result;
import com.amp.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author guoyu
 * @date 2021/09/03 10:12
 * @description 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandle {

    /**
     * 业务异常处理
     * @param request 请求体
     * @param e 异常对象
     * @return 异常信息
     */
    @ExceptionHandler(value = AmpException.class)
    public Object cdmsExceptionHandler(HttpServletRequest request, AmpException e){
        log.error("业务异常: "+e.getMessage()+",请求路径："+request.getRequestURI(),e);
        return Result.fail(e.getCode(),e.getMsg());
    }

    /**
     * 空指针异常处理
     * @param request 请求体
     * @return 异常信息
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Object nullPointerExceptionHandler(HttpServletRequest request,NullPointerException e){
        log.error("请求路径:{},空指针异常: ",request.getRequestURI(),e);
        return Result.fail(ResultCodeEnum.SERVER_ERROR);
    }

    /**
     * IO异常
     * @param request 请求体
     * @param e 异常对象
     * @return 异常信息
     */
    @ExceptionHandler(value = IOException.class)
    public Object ioExceptionHandler(HttpServletRequest request, IOException e){
        log.error("请求路径:{},IO异常: ",request.getRequestURI(),e);
        return Result.fail(ResultCodeEnum.SERVER_ERROR);
    }

    /**
     * 用户登录认证异常
     * @param request 请求体
     * @param e 异常对象
     * @return 异常信息
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public Object authenticationExceptionHandler(HttpServletRequest request,AuthenticationException e){
        log.error("请求路径:{},用户登录认证异常: ",request.getRequestURI(),e);
        if(e.getCause() instanceof AmpException){
            AmpException ampException = (AmpException) e.getCause();
            return Result.fail(ampException.getCode(), ampException.getMessage());
        }
        return Result.fail(ResultCodeEnum.USERNAME_OR_PASSWORD_WRONG);
    }



    /**
     * 未知异常
     * @param request 请求体
     * @param e 异常实体
     * @return 异常信息
     */
    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(HttpServletRequest request,Exception e){
        log.error("请求路径:{},未知异常: "+request.getRequestURI(),e);
        return Result.fail(ResultCodeEnum.SERVER_ERROR);
    }

}
