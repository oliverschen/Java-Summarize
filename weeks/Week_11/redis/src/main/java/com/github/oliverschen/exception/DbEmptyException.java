package com.github.oliverschen.exception;

import lombok.Data;

/**
 * @author ck
 */
@Data
public class DbEmptyException extends RuntimeException {

    private final String msg;

    private final int code;

    public DbEmptyException() {
        super();
        this.code = 500;
        this.msg = "服务异常";
    }

    public DbEmptyException(String msg) {
        super(msg);
        this.msg = msg;
        this.code = 500;
    }

    public DbEmptyException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }
}
