package com.leifeng.lib.net;

import com.leifeng.lib.utils.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 描述:在IO线程进行请求，在主线程进行回调
 *
 * @author leifeng
 * 2018/3/21 17:20
 */


public class MySchedulers {
    public static <T> ObservableTransformer<T, T> compose() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                LogUtil.e("===========MySchedulers==accept");
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
