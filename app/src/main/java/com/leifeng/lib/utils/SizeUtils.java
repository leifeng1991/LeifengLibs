package com.leifeng.lib.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.leifeng.lib.base.BaseApplication;

/**
 * 尺寸相关工具类
 */
public final class SizeUtils {

    private SizeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * dp 转换 px
     *
     * @param dpValue 值（单位dp）
     * @return 返回值（单位px）
     */
    public static int dp2px(final float dpValue) {
        final float scale = BaseApplication.getBaseApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转换 dp
     *
     * @param pxValue 值（单位px）
     * @return 返回值（单位dp）
     */
    public static int px2dp(final float pxValue) {
        final float scale = BaseApplication.getBaseApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转换 px
     *
     * @param spValue 值（单位sp）
     * @return 返回值（单位px）
     */
    public static int sp2px(final float spValue) {
        final float fontScale = BaseApplication.getBaseApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 转换 sp
     *
     * @param pxValue 值（单位px）
     * @return 返回值（单位sp）
     */
    public static int px2sp(final float pxValue) {
        final float fontScale = BaseApplication.getBaseApplication().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 各种单位转换
     *
     * @param value 值
     * @param unit  单位
     * @return
     */
    public static float applyDimension(final float value, final int unit) {
        DisplayMetrics metrics = BaseApplication.getBaseApplication().getResources().getDisplayMetrics();
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    /**
     * 在 onCreate 中获取视图的尺寸
     *
     * @param view     视图
     * @param listener 监听事件
     */
    public static void forceGetViewSize(final View view, final onGetSizeListener listener) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onGetSize(view);
                }
            }
        });
    }

    /**
     * 获取测量视图宽度
     *
     * @param view 视图
     */
    public static int getMeasuredWidth(final View view) {
        return measureView(view)[0];
    }

    /**
     * 获取测量视图高度
     * @param view 视图
     */
    public static int getMeasuredHeight(final View view) {
        return measureView(view)[1];
    }

    /**
     * 测量视图尺寸
     *
     * @param view 视图
     */
    public static int[] measureView(final View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }


    public interface onGetSizeListener {
        void onGetSize(View view);
    }
}
