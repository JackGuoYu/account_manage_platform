package com.amp.exception;

import com.amp.enums.ResultCodeEnum;

/**
 * GatewayException.
 */
public class RedisLockException extends RuntimeException {

    private static final long serialVersionUID = 8068509879445395353L;

    private String errorCode = ResultCodeEnum.RETRY_ERROR.getCode();

    /**
     * Instantiates a new gateway exception.
     *
     * @param e the e
     */
    public RedisLockException(final Throwable e) {
        super(e);
    }

    /**
     * Instantiates a new gateway exception.
     *
     * @param message the message
     */
    public RedisLockException(final String message) {
        super(message);
    }


    public RedisLockException(final String code, final String message) {
        super(message);
        this.errorCode = code;
    }

    /**
     * Instantiates a new gateway exception.
     *
     * @param message   the message
     * @param throwable the throwable
     */
    public RedisLockException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
