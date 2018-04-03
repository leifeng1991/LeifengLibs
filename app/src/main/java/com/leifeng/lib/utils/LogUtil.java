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
    private static int LOG_MAX_LENGTH = 2000;

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

    /******************************日志打印不全解决方法*************************************/

    public static void V(String msg) {
        V("LogUtil", msg);
    }

    public static void V(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.v(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    Log.v(tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void D(String msg) {
        D("LogUtil", msg);
    }
    public static void D(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.d(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    Log.d(tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void I(String msg) {
        I("LogUtil", msg);
    }

    public static void I(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.i(tagName, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    Log.i(tagName, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void W(String msg) {
        W("LogUtil", msg);
    }

    public static void W(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.w(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    Log.w(tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void E(String msg) {
        E("LogUtil", msg);
    }
    public static void E(String tagName, String msg) {
        if (isDebug) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    Log.e(tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

}
