package com.leifeng.lib.utils;

import android.util.Log;

import static com.leifeng.lib.constants.BaseConstants.B.IS_DEBUG;

/**
 * 描述: Log的封装类
 *
 * @author zhangrq
 *         2016/8/16 14:27
 */
public class LogUtil {

    private LogUtil() {
    }

    public static boolean isDebug = IS_DEBUG;// 是否打印log
    private static final String TAG = "LogUtil";

    public static void v(String msg) {
        if (isDebug) Log.v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug) Log.v(tag, msg);
    }

    public static void d(String msg) {
        if (isDebug) Log.d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug) Log.d(tag, msg);
    }

    public static void i(String msg) {
        if (isDebug) Log.i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }

    public static void w(String msg) {
        if (isDebug) Log.w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug) Log.w(tag, msg);
    }


    public static void e(String msg) {
        if (isDebug) Log.e(TAG, msg);
    }

    public static void e(Throwable tr) {
        if (isDebug) Log.e(TAG, "Exception", tr);
    }


    public static void e(String tag, String msg) {
        if (isDebug) Log.i(tag, msg);
    }

}
