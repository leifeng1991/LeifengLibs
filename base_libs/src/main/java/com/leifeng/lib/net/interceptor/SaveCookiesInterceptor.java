package com.leifeng.lib.net.interceptor;

import android.support.annotation.NonNull;


import com.leifeng.lib.base.BaseApplication;
import com.leifeng.lib.utils.LogUtil;
import com.leifeng.lib.utils.SPUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/22 14:04
 */
public class SaveCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        LogUtil.e("============SaveCookiesInterceptor"+originalResponse.body());
        LogUtil.e("============SaveCookiesInterceptor"+originalResponse.headers().names().toString());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            ArrayList<String> cookies = new ArrayList<>();
            cookies.addAll(originalResponse.headers("Set-Cookie"));
            SPUtils.putStrList(BaseApplication.getBaseApplication().getApplicationContext(),"cookies",cookies);
        }
        return originalResponse;
    }

}
