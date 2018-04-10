package com.leifeng.lib.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.leifeng.lib.base.BaseApplication;

import static android.Manifest.permission.WRITE_SETTINGS;

/**
 * 屏幕相关工具类
 */
public final class ScreenUtils {

    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取屏幕的宽度（单位：px）
     */
    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) BaseApplication.getBaseApplication().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return BaseApplication.getBaseApplication().getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕的高度（单位：px）
     */
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) BaseApplication.getBaseApplication().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return BaseApplication.getBaseApplication().getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取屏幕密度
     */
    public static float getScreenDensity() {
        return BaseApplication.getBaseApplication().getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕密度 DPI
     */
    public static int getScreenDensityDpi() {
        return BaseApplication.getBaseApplication().getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 截屏
     *
     * @param activity 指定Activity
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * 截屏
     *
     * @param activity          指定Activity
     * @param isDeleteStatusBar 是否不截取状态栏 true:不截取 false：截取
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * 判断是否锁屏
     */
    public static boolean isScreenLock() {
        KeyguardManager km =
                (KeyguardManager) BaseApplication.getBaseApplication().getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置进入休眠时长
     *
     * @param duration 时长
     */
    @RequiresPermission(WRITE_SETTINGS)
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(
                BaseApplication.getBaseApplication().getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * 获取进入休眠时长
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(
                    BaseApplication.getBaseApplication().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    /**
     * 判断是否是平板
     */
    public static boolean isTablet() {
        return (BaseApplication.getBaseApplication().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
