package com.leifeng.lib.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/20 10:25
 */


public final class TimeConstants {
    // 毫秒
    public static final int MSEC = 1;
    // 秒
    public static final int SEC = 1000;
    // 分
    public static final int MIN = 60000;
    // 时
    public static final int HOUR = 3600000;
    // 天
    public static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
