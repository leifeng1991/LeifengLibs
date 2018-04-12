package com.leifeng.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 描述:
 *
 * @author leifeng
 * 2018/4/12 16:49
 */

public class DownFileUtils {

    private static DownFileUtils downloadUtil;
    private final OkHttpClient okHttpClient;

    public static DownFileUtils get() {
        if (downloadUtil == null) {
            downloadUtil = new DownFileUtils();
        }
        return downloadUtil;
    }

    private DownFileUtils() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名（名字.后缀）
     * @param url      下载连接
     * @param listener 下载监听
     */
    public void downloadFile(final Activity activity, final String fileName, final String url, final OnDownloadListener listener) {
        final Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // 下载失败
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                // 写入本地
                ResponseBody responseBody = response.body();
                if (responseBody != null) {
                    downUpdateProgress(activity, fileName, responseBody.contentLength(), responseBody.byteStream(), listener);
                }
            }
        });
    }

    /**
     * 更新下载的进度
     *
     * @param fileName      文件名（名字.后缀）
     * @param contentLength 文件长度
     * @param listener      回调
     */
    private void downUpdateProgress(Activity activity, String fileName, long contentLength, InputStream inputStream, final OnDownloadListener<File> listener) {
        int downloadedSize = 0;
        byte buf[] = new byte[1024 * 1024];
        int numBytesRead;
        // 保存的路径
        final File file;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory(), fileName);
        } else {
            file = new File(activity.getCacheDir(), fileName);
        }
        BufferedOutputStream fos = null;
        try {
            fos = new BufferedOutputStream(new FileOutputStream(file));
            do {
                numBytesRead = inputStream.read(buf);
                if (numBytesRead > 0) {
                    fos.write(buf, 0, numBytesRead);// 写流
                    downloadedSize += numBytesRead;
                    // 当前进度值
                    int progress = (int) (((float) downloadedSize / contentLength) * 100);
                    // 更新进度
                    if (listener != null)
                        listener.onProgress(progress, contentLength);
                }
            } while (numBytesRead > 0);
            // 上面执行完了，即下载完成了
            if (listener != null){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDownloadSuccess(file);
                    }
                });
            }

            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            if (listener != null){
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDownloadFailed();
                    }
                });
            }

        } finally {
            // 关流
            try {
                if (inputStream != null)
                    inputStream.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                if (listener != null){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadFailed();
                        }
                    });
                }
            }
        }
    }

    public interface OnDownloadListener<T> {
        /**
         * 下载成功
         */
        void onDownloadSuccess(T t);

        /**
         * @param progress 下载进度
         */
        void onProgress(int progress, long total);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }

}
