package com.leifeng.lib.net;

import java.io.Serializable;

/**
 * 描述:基础bean
 *
 * @author leifeng
 *         2018/3/21 17:26
 */
public class BaseBean implements Serializable{
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
