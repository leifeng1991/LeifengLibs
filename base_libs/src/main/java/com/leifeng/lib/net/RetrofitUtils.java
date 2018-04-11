package com.leifeng.lib.net;

import android.content.Context;

import com.leifeng.lib.net.observer.BaseObserver;
import com.leifeng.lib.net.observer.FileDownLoadObserver;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 描述:
 *
 * @author leifeng
 * 2018/3/26 13:45
 */


public class RetrofitUtils {
    /**
     * @param observable 被观察者
     * @param observer   观察者
     * @param <T>        实体
     */
    public static <T extends BaseBean> void httRequest(Observable<T> observable, BaseObserver<T> observer) {
        observable.compose(MySchedulers.<T>compose()).subscribe(observer);
    }

    /**
     * @param observable 被观察者
     */
    public static void downLoad(final Context context, Observable<ResponseBody> observable, final FileDownLoadObserver<File> fileDownLoadObserver) {
        observable.subscribeOn(Schedulers.io())//subscribeOn和ObserOn必须在io线程，如果在主线程会出错
                .observeOn(Schedulers.io())
                .observeOn(Schedulers.computation())//需要
                .map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Exception {
                        return fileDownLoadObserver.saveFile(context,responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()).subscribe(fileDownLoadObserver);
    }


}
