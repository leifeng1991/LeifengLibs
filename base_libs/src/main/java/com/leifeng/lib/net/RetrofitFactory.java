package com.leifeng.lib.net;


import com.leifeng.lib.constants.BaseConstants;
import com.leifeng.lib.constants.BaseURLConstant;
import com.leifeng.lib.net.interceptor.InterceptorUtil;
import com.leifeng.lib.net.interceptor.ReadCookiesInterceptor;
import com.leifeng.lib.net.interceptor.SaveCookiesInterceptor;
import com.leifeng.lib.net.observer.FileDownLoadObserver;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.annotations.NonNull;
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


    /**
     * 下载单文件，该方法不支持断点下载
     *
     * @param url                  文件地址
     * @param destDir              存储文件夹
     * @param fileName             存储文件名
     * @param fileDownLoadObserver 监听回调
     */
    public void downloadFile(@NonNull String url, final String destDir, final String fileName, final FileDownLoadObserver<File> fileDownLoadObserver) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(BaseURLConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
      /*  retrofit.create(BaseAPI.class)
                .downLoadFile(url)
                .subscribeOn(Schedulers.io())//subscribeOn和ObserOn必须在io线程，如果在主线程会出错
                .observeOn(Schedulers.io())
                .observeOn(Schedulers.computation())//需要
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(@NonNull ResponseBody responseBody) throws Exception {
                        return fileDownLoadObserver.saveFile(responseBody, destDir, fileName);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileDownLoadObserver);*/
    }

}
