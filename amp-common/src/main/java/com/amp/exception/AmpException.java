package com.amp.exception;

import com.amp.enums.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guoyu
 * @date 2021/09/03 16:07
 * @description 业务异常处理类
 */
@Getter
@Setter
public class AmpException extends RuntimeException{

    private String code = ResultCodeEnum.FAIL.getCode();

    private String msg;

    public AmpException() {
      super();
    }

    public AmpException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public AmpException(ResultCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    public AmpException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
