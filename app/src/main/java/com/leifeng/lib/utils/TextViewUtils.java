package com.leifeng.lib.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/26 14:12
 */


public class TextViewUtils {
    /**
     * 设置TextView文本（无默认值）
     *
     * @param textView 目标
     * @param text     文本
     */
    public static void setText(TextView textView, CharSequence... text) {
        if (textView == null || text == null || text.length == 0)
            return;
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence charSequence : text) {
            if (charSequence == null || charSequence.length() == 0)
                charSequence = " ";
            stringBuilder.append(charSequence);
        }
        textView.setText(stringBuilder);
    }

    /**
     * 设置TextView文本（有默认值）
     *
     * @param textView 目标
     * @param text     文本
     */
    public static void setHasDefaultText(TextView textView, CharSequence... text) {
        if (textView == null || text == null || text.length == 0)
            return;
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence charSequence : text) {
            if (charSequence == null || charSequence.length() == 0)
                charSequence = "- -";
            stringBuilder.append(charSequence);
        }
        textView.setText(stringBuilder);
    }

    /**
     * 设置TextView文本（有默认值）
     *
     * @param textView    目标
     * @param defaultText 默认值
     * @param text        文本
     */
    public static void setText(TextView textView, String defaultText, CharSequence... text) {
        if (textView == null || text == null || text.length == 0)
            return;
        StringBuilder stringBuilder = new StringBuilder();
        for (CharSequence charSequence : text) {
            if (charSequence == null || charSequence.length() == 0)
                charSequence = defaultText;
            stringBuilder.append(charSequence);
        }
        textView.setText(stringBuilder);
    }

    /**
     * @param context 上下文
     * @param resId   资源id
     * @param gravity 图片位置
     */
    public static void setImageResources(Context context, int resId, int gravity, TextView textView) {
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        Drawable[] compoundDrawables = textView.getCompoundDrawables();
        switch (gravity) {
            case Gravity.LEFT:// 左
                textView.setCompoundDrawables(drawable, compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
                break;
            case Gravity.TOP:// 上
                textView.setCompoundDrawables(compoundDrawables[0], drawable, compoundDrawables[2], compoundDrawables[3]);
                break;
            case Gravity.RIGHT:// 右
                textView.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], drawable, compoundDrawables[3]);
                break;
            case Gravity.BOTTOM:// 下
                textView.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], drawable);
                break;
        }

    }
}
