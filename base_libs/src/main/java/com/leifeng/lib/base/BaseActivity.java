package com.leifeng.lib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leifeng.lib.R;
import com.leifeng.lib.utils.PermissionHelper;
import com.leifeng.lib.utils.StatusBarUtil;

/**
 * 描述:基础Activity
 *
 * @author leifeng
 *         2018/3/16 11:24
 */


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private long lastClick = 0;// 上次点击时间
    protected Activity mActivity;
    protected Context mContext;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = this.getApplicationContext();
        TAG = getPackageName().getClass().getSimpleName();
        // 加载布局
        loadViewLayout();
        // 初始化控件
        initView();
        // 初始化数据
        initData();
        // 设置监听
        setListener();
        // 加载数据
        loadData();
    }

    /**
     * 加载布局
     */
    protected abstract void loadViewLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 设置监听时间
     */
    protected abstract void setListener();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 设置左侧按钮图标
     *
     * @param resId 图片资源id
     */
    public void setTitleLeftImage(int resId) {
        ImageView mLeftImageView = findViewById(R.id.title_left_iv);
        mLeftImageView.setVisibility(View.VISIBLE);
        mLeftImageView.setImageResource(resId);
        mLeftImageView.setOnClickListener(this);
    }

    /**
     * 设置标题
     *
     * @param resId 文字资源id
     */
    public void setTitleName(int resId) {
        TextView mTitleName = findViewById(R.id.title_name_tv);
        mTitleName.setVisibility(View.VISIBLE);
        mTitleName.setText(resId);
    }

    /**
     * 设置标题
     *
     * @param text 文字
     */
    public void setTitleName(String text) {
        TextView mTitleName = findViewById(R.id.title_name_tv);
        mTitleName.setVisibility(View.VISIBLE);
        mTitleName.setText(text);
    }

    /**
     * 设置右侧按钮图片
     *
     * @param resId 图片资源id
     */
    public void setTitleRightImage(int resId) {
        ImageView mRightImageView = findViewById(R.id.title_right_iv);
        mRightImageView.setVisibility(View.VISIBLE);
        mRightImageView.setImageResource(resId);
        mRightImageView.setOnClickListener(this);
    }

    /**
     * 设置标题右侧文字
     *
     * @param resId 文字资源id
     */
    public void setTitleRightTextView(int resId) {
        TextView mTitleRightTextView = findViewById(R.id.title_right_tv);
        mTitleRightTextView.setVisibility(View.VISIBLE);
        mTitleRightTextView.setText(resId);
    }

    /**
     * 设置标题右侧文字
     *
     * @param text 文字
     */
    public void setTitleRightTextView(String text) {
        TextView mTitleRightTextView = findViewById(R.id.title_right_tv);
        mTitleRightTextView.setVisibility(View.VISIBLE);
        mTitleRightTextView.setText(text);
    }

    public void setOnRightClickListener() {

    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (isFastClick())
            return;
        int i = v.getId();
        if (i == R.id.title_left_iv) {
            // 左侧按钮
            onBackPressed();
        } else if (i == R.id.title_right_iv) {
            // 右侧按钮
            setOnRightClickListener();

        }
    }


}
