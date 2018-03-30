package com.leifeng.lib.net;

import android.support.annotation.NonNull;

import com.leifeng.lib.base.BaseApplication;
import com.leifeng.lib.utils.LogUtil;
import com.leifeng.lib.utils.SPUtils;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/22 13:54
 */
public class ReadCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        ArrayList<String> preferences = SPUtils.getStrList(BaseApplication.getBaseApplication().getApplicationContext(), "cookies");
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            LogUtil.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }

        return chain.proceed(builder.build());
    }

}
