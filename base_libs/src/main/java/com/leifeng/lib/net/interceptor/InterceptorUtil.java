package com.leifeng.lib.net.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 描述:拦截器工具类
 *
 * @author leifeng
 * 2018/3/21 16:39
 */
public class InterceptorUtil {

    /**
     * 日志拦截器
     */
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }

    /**
     * header拦截器
     */
    public static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request mRequest = chain.request();
                //在这里你可以做一些想做的事,比如token失效时,重新获取token
                //或者添加header等等
                return chain.proceed(mRequest);
                // 多个基地址可以在这里进行处理
              /*  //获取原始的originalRequest
                Request originalRequest = chain.request();
                //获取老的url
                HttpUrl oldUrl = originalRequest.url();
                //获取originalRequest的创建者builder
                Request.Builder builder = originalRequest.newBuilder();
                //获取头信息的集合如：manage,mdffx
                List<String> urlnameList = originalRequest.headers("urlname");
                if (urlnameList != null && urlnameList.size() > 0) {
                    //删除原有配置中的值,就是namesAndValues集合里的值
                    builder.removeHeader("urlname");
                    //获取头信息中配置的value,如：manage或者mdffx
                    String urlname = urlnameList.get(0);
                    HttpUrl baseURL=null;
                    //根据头信息中配置的value,来匹配新的base_url地址
                    if ("manage".equals(urlname)) {
                        baseURL = HttpUrl.parse(Api.base_url);
                    } else if ("mdffx".equals(urlname)) {
                        baseURL = HttpUrl.parse(Api.base_url_mdffx);
                    }
                    //重建新的HttpUrl，需要重新设置的url部分
                    HttpUrl newHttpUrl = oldUrl.newBuilder()
                            .scheme(baseURL.scheme())//http协议如：http或者https
                            .host(baseURL.host())//主机地址
                            .port(baseURL.port())//端口
                            .build();
                    //获取处理后的新newRequest
                    Request newRequest = builder.url(newHttpUrl).build();
                    return  chain.proceed(newRequest);
                }else{
                    return chain.proceed(originalRequest);
                }
*/
            }
        };

    }
}
