package com.amp.enums;

import lombok.Getter;

/**
 * @author guoyu
 * @date 2021/09/03 14:56
 * @description 状态码
 */
@Getter
public enum ResultCodeEnum {
    /**
     * 请求提交相关
     */
    SUCCESS("200", "提交成功"),
    FAIL("500", "提交失败"),
    RETRY_ERROR("501", "系统繁忙,请稍后重试"),
    ILLEGAL_ARGUMENT("8001", "参数非法"),

    /**
     * 系统异常
     */
    SERVICE_COMMUNICATION_ERROR("9998", "服务通讯异常"),
    SERVER_ERROR("9999", "系统异常,请联系管理员"),
    USERNAME_OR_PASSWORD_WRONG("9005", "用户名或密码错误"),

    /**
     * 用户注册登录
     */
    USER_BIND_VAIL("7001", "该用户不存在，请进行绑定"),
    OPENID_BIND_ERROR("7002", "openid绑定错误"),
    USER_EXIST_PHONE_ERROR("7003", "用户手机号已存在"),

    ;
    private String code;
    private String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
