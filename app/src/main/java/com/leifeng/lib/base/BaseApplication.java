package com.leifeng.lib.base;

import android.support.multidex.MultiDexApplication;

import com.leifeng.lib.utils.CrashUtils;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/20 15:18
 */


public class BaseApplication extends MultiDexApplication {
    private static BaseApplication application;

    public static BaseApplication getBaseApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
    }

    private void init(){
        // 初始化内存泄漏检查工具
//        initLeakCanary();
        // 初始化捕获异常
        initCrash();
    }

    /**
     * 初始化内存泄漏检查工具
     */
   /* private void initLeakCanary() {
        // 内存泄露检查工具
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }*/

    /**
     * 初始化捕获异常
     */
    private void initCrash() {
        new CrashUtils(getApplicationContext(), "LF_LOG");// 设置捕获日志
    }

}
