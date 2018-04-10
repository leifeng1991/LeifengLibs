package com.leifeng.lib.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.leifeng.lib.constants.BaseConstants;
import com.leifeng.lib.utils.CrashUtils;

import static com.just.agentweb.LogUtils.isDebug;

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // dex突破65535的限制
        MultiDex.install(this);
    }

    private void init(){
        // 初始化内存泄漏检查工具
//        initLeakCanary();
        // 初始化捕获异常
        initCrash();
        initRouter();
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

    /**
     * 路由器
     */
    private void initRouter() {
        if (BaseConstants.B.IS_DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();           // 打印日志
            ARouter.openDebug();         // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);       // 尽可能早，推荐在Application中初始化
    }

}
