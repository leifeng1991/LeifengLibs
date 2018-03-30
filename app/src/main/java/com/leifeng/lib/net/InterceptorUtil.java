package com.leifeng.lib.net;

import android.support.annotation.NonNull;

import com.leifeng.lib.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 描述:拦截器工具类
 *
 * @author leifeng
 *         2018/3/21 16:39
 */
class InterceptorUtil {
    private static String TAG = "====";

    /**
     * 日志拦截器
     */
    static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.i(TAG, "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }

    /**
     * header拦截器
     */
    static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request mRequest = chain.request();
                //在这里你可以做一些想做的事,比如token失效时,重新获取token
                //或者添加header等等
                return chain.proceed(mRequest);
            }
        };

    }
}
