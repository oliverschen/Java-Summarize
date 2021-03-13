package io.kimmking.rpcfx.exception;

import lombok.Data;

/**
 * @author ck
 */
@Data
public class RpcfxException extends RuntimeException {

    private int code;
    private String msg;

    public RpcfxException(String message) {
        super(message);
        this.code = 500;
        this.msg = message;
    }

    public RpcfxException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public RpcfxException(String message, Throwable cause) {
        super(message, cause);
    }
}
