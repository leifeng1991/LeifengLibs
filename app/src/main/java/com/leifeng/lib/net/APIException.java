package com.leifeng.lib.net;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/21 17:36
 */
public class APIException extends RuntimeException {
    private int errorCode;

    public APIException(String message) {
        super(message);
    }

    public APIException(int resultCode, String message) {
        super(message);
        this.errorCode = resultCode;
    }

    public int getErrorCode() {
        return errorCode;
    }


}
