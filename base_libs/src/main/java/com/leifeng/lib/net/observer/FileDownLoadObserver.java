package com.leifeng.lib.net.observer;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.observers.DefaultObserver;
import okhttp3.ResponseBody;

/**
 * 描述:文件下载
 *
 * @author leifeng
 * 2018/4/11 11:36
 */

public abstract class FileDownLoadObserver<T> extends DefaultObserver<T> {

    @Override
    public void onNext(T t) {
        onDownLoadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onDownLoadFail(e);
    }

    //可以重写，具体可由子类实现
    @Override
    public void onComplete() {
    }

    /**
     * 下载成功的回调
     */
    public abstract void onDownLoadSuccess(T t);

    /**
     * 下载失败回调
     */
    public abstract void onDownLoadFail(Throwable throwable);

    /**
     * 下载进度监听
     *
     * @param progress 进度
     * @param total    大小
     */
    public abstract void onProgress(int progress, long total);

    /**
     * 将文件写入本地
     *
     * @param responseBody 请求结果全体
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    public File saveFile(Context context, ResponseBody responseBody) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[1024 * 1024];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = responseBody.byteStream();
            final long total = responseBody.contentLength();
            long sum = 0;
            // 保存的路径
            File file;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                file = new File(Environment.getExternalStorageDirectory(), "downloadApk.apk");
            } else {
                file = new File(context.getCacheDir(), "downloadApk.apk");
            }

            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                //这里就是对进度的监听回调
                onProgress((int) (finalSum * 100 / total), total);
            }
            fos.flush();

            return file;

        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
