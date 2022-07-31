package com.github.oliverschen.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 异常处理
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends RuntimeException {

    private String code;
    private String msg;

    public ServiceException() {
        super();
    }

    public ServiceException(ExceptionInterface e) {
        super(e.getCode());
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

}
