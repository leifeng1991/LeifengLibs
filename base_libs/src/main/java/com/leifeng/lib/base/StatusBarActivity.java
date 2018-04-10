package com.leifeng.lib.base;

import com.leifeng.lib.R;
import com.leifeng.lib.utils.StatusBarUtil;

/**
 * 描述:处理状态栏可以继承此Activity
 *
 * @author leifeng
 * 2018/4/4 17:23
 */

public abstract class StatusBarActivity extends BaseActivity{
    @Override
    protected void initData() {
        // 设置状态栏
        setStatusBar();
    }

    /**
     * 设置状态栏颜色
     */
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
    }
}
