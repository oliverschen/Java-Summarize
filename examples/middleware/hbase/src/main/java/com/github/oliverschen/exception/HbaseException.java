package com.github.oliverschen.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常处理
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HbaseException extends RuntimeException {

    private String code;
    private String msg;

    public HbaseException() {
        super();
    }

    public HbaseException(ExceptionInterface e) {
        super(e.getCode());
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

}
