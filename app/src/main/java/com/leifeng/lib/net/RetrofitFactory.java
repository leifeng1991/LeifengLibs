package com.leifeng.lib.net;


import com.leifeng.lib.constants.BaseConstants;
import com.leifeng.lib.constants.BaseURLConstant;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 描述:
 *
 * @author leifeng
 *         2018/3/21 16:43
 */
public class RetrofitFactory {
    private static RetrofitFactory mRetrofitFactory;
    private static BaseAPI mBaseAPI;

    private RetrofitFactory() {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(BaseConstants.L.CONNECT_TIME_OUT, TimeUnit.SECONDS)// 设置连接超时时间
                .readTimeout(BaseConstants.L.READ_TIME_OUT, TimeUnit.SECONDS)// 设置读取超时时间
                .writeTimeout(BaseConstants.L.WRITE_TIME_OUT, TimeUnit.SECONDS)// 设置写的超时时间
                .retryOnConnectionFailure(true)// 错误重连
                .connectionSpecs(Arrays.asList(ConnectionSpec.CLEARTEXT, ConnectionSpec.MODERN_TLS)) // 支持HTTPS 明文Http与比较新的Https
//                .addInterceptor(InterceptorUtil.HeaderInterceptor())
                .addInterceptor(new ReadCookiesInterceptor())
                .addInterceptor(new SaveCookiesInterceptor())
                .addInterceptor(InterceptorUtil.LogInterceptor())// 添加日志拦截器
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BaseURLConstant.BASE_URL)// 添加接口基地址
                .addConverterFactory(GsonConverterFactory.create())// 添加Gson转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 添加rxjava转换器
                .client(mOkHttpClient)
                .build();
        mBaseAPI = mRetrofit.create(BaseAPI.class);

    }

    /**
     * 单例
     */
    public static RetrofitFactory getInstance() {
        if (mRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (mRetrofitFactory == null)
                    mRetrofitFactory = new RetrofitFactory();
            }
        }
        return mRetrofitFactory;
    }


    public BaseAPI getAPI() {
        return mBaseAPI;
    }


}
