package com.leifeng.lib.constants;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/20 18:19
 */


public interface BaseConstants {
    interface S {
        String TITLE = "title";
        String URL = "url";
    }

    interface I {

    }

    interface L {
        long CONNECT_TIME_OUT = 60;
        long READ_TIME_OUT = 60;
        long WRITE_TIME_OUT = 60;
    }

    interface B {
        boolean IS_DEBUG = true;
    }
}
