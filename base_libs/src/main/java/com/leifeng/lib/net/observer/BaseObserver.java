package com.leifeng.lib.net.observer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


import com.leifeng.lib.net.APIException;
import com.leifeng.lib.net.BaseBean;
import com.leifeng.lib.utils.LogUtil;
import com.leifeng.lib.utils.ToastUtils;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * 描述:观察者
 *
 * @author leifeng
 * 2018/3/21 17:26
 */
public abstract class BaseObserver<T extends BaseBean> implements Observer<T> {
    private Context mContext;
    private ProgressDialog pd;
    private Disposable mDisposable;

    protected BaseObserver(Context context) {
        this.mContext = context;
    }

    /**
     * @param pd 加载对话框
     */
    protected BaseObserver(Context context, ProgressDialog pd) {
        this.mContext = context;
        this.pd = pd;
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mDisposable != null)
                    mDisposable.dispose();
            }
        });
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        LogUtil.e("==============onSubscribe");
    }

    @Override
    public void onNext(T t) {
        LogUtil.e("==============onNext");
        if (t.getCode() == 200) {
            // 成功
            onSuccess(t);
        } else {
            // 失败
            onFailed(t);
            onError();
        }
        dismiss();
    }

    @Override
    public void onError(Throwable e) {
        onError();
        LogUtil.e("==============onError");
        String errorMsg;
        int code = -1;
        if (e instanceof IOException) {
            // 没有网络
            errorMsg = "请检查你的网络";
        } else if (e instanceof HttpException) {
            // 网络异常，http 请求失败，即 http 状态码不在 [200, 300) 之间.
            errorMsg = ((HttpException) e).response().message();
        } else if (e instanceof APIException) {
            // 网络正常，http 请求成功，服务器返回逻辑错误
            errorMsg = e.getMessage();
            // 封装code 和 message
            code = ((APIException) e).getErrorCode();
        } else {
            // 其他未知错误
            errorMsg = !TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : "unknown error";
        }
        BaseBean baseBean = new BaseBean();
        baseBean.setCode(code);
        baseBean.setMessage(errorMsg);
        onFailed((T) baseBean);
        dismiss();

    }

    @Override
    public void onComplete() {
        dismiss();
        LogUtil.e("==============onComplete");
    }

    /**
     * 只有200才回调此方法
     *
     * @param t 返回对应的bean
     */
    public abstract void onSuccess(T t);

    /**
     * 失败回调并吐司
     */
    public void onFailed(T t) {
        // 吐司提示
        ToastUtils.showShortToast(mContext, t.getMessage());
    }

    /**
     * 不管网络正常与否都回调
     */
    public void onError() {

    }

    public void setProgressDialog(ProgressDialog pd){
        this.pd = pd;
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mDisposable != null)
                    mDisposable.dispose();
            }
        });
    }

    private void dismiss(){
        if (pd != null && pd.isShowing()){
            pd.dismiss();
        }
    }
}
