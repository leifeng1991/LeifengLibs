package com.leifeng.lib.base;

import android.support.annotation.NonNull;

import com.leifeng.lib.utils.PermissionHelper;

/**
 * 描述:需要进行权限处理的可以继承此activity
 *
 * @author leifeng
 * 2018/4/4 17:18
 */

public abstract class PermissionActivity extends StatusBarActivity {
    protected PermissionHelper mHelper;

    @Override
    protected void initData() {
        super.initData();
        mHelper = new PermissionHelper(this);
    }

    /**
     * 使用PermissionHelper进行权限请求时加上
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
