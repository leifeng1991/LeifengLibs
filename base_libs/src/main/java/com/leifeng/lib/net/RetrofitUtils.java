package com.leifeng.lib.net;

import com.leifeng.lib.net.observer.BaseObserver;

import io.reactivex.Observable;

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

}
