package com.leifeng.lib.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.leifeng.lib.BuildConfig;
import com.leifeng.lib.R;

import java.io.File;

/**
 * 描述:版本更新
 *
 * @author leifeng
 * 2018/4/12 9:48
 */

public class VersionUpdateUtils {
    public static final int UPDATE_NOTIFY_ID = 10000;

    /**
     * 相关权限请求
     *
     * @param permissionHelper 权限辅助工具
     * @param url              下载地址
     * @param type             1：通知 2：弹框
     */
    public static void requestStoragePermission(final Activity activity, final PermissionHelper permissionHelper, final String url, final int type) {
        permissionHelper.requestPermissions("请授予[写入]权限，否则无法下载本apk到本地", new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {
                // 请求权限成功
                if (type == 1)
                    // 通知栏显示下载进度
                    showNotification(activity, permissionHelper, url);
                if (type == 2)
                    // 进度弹框显示下载进度
                    showDialog(activity, permissionHelper, url);
            }

            @Override
            public void doAfterDenied(String... permission) {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 进度对话框展示下载进度
     *
     * @param url 下载地址
     */
    private static void showDialog(final Activity activity, final PermissionHelper permissionHelper, String url) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置点击外部不关闭对话框
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在更新下载...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // 下载apk
        downLoadApk(activity, url, new DownFileUtils.OnDownloadListener<File>() {
            @Override
            public void onDownloadSuccess(File file) {
                LogUtil.e("===========下载完成");
                // 关闭对话框
                progressDialog.dismiss();
                // 提示
                ToastUtils.showShortToast(activity.getApplicationContext(), "下载完成");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // 请求安装权限
                    requestInstallPermission(file, permissionHelper);
                } else {
                    // 直接安装
                    AppUtils.installApp(file, BuildConfig.APPLICATION_ID + ".fileProvider");
                }
            }

            @Override
            public void onProgress(int progress, long total) {
                progressDialog.setProgress(progress);
            }

            @Override
            public void onDownloadFailed() {
                // 关闭对话框
                progressDialog.dismiss();
                LogUtil.e("===========下载出错");
                // 提示
                ToastUtils.showShortToast(activity.getApplicationContext(), "下载出错");
            }
        });
    }

    /**
     * 通知栏展示下载进度
     *
     * @param url 下载地址
     */
    private static void showNotification(final Activity activity, final PermissionHelper permissionHelper, String url) {
        // 通知栏显示下载进度
        final Notification.Builder builder = new Notification.Builder(activity.getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(activity.getString(R.string.app_name) + "更新下载")
                .setContentText("正在下载")
                .setProgress(100, 0, false);
        final NotificationManager manager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        // 适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 此id必须与下面id一致
            builder.setChannelId("1");
            NotificationChannel channel = new NotificationChannel("1", "channel", NotificationManager.IMPORTANCE_HIGH);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (manager != null) {
                manager.notify(UPDATE_NOTIFY_ID, builder.build());
            }
        }
        // 下载apk
        downLoadApk(activity, url, new DownFileUtils.OnDownloadListener<File>() {
            @Override
            public void onDownloadSuccess(File file) {
                LogUtil.e("===========下载完成");
                ToastUtils.showShortToast(activity.getApplicationContext(), "下载完成");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // 请求安装权限
                    requestInstallPermission(file, permissionHelper);
                } else {
                    // 直接安装
                    AppUtils.installApp(file, BuildConfig.APPLICATION_ID + ".fileProvider");
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onProgress(int progress, long total) {
                builder.setProgress(100, progress, false).setContentText("下载" + progress + "%");
                if (manager != null) {
                    manager.notify(UPDATE_NOTIFY_ID, builder.build());
                }
            }

            @Override
            public void onDownloadFailed() {
                if (manager != null) {
                    manager.cancel(UPDATE_NOTIFY_ID);
                }
                LogUtil.e("===========下载出错");
                ToastUtils.showShortToast(activity.getApplicationContext(), "下载出错");
            }
        });

    }

    /**
     * 下载apk到本地
     *
     * @param url 下载地址
     */
    private static void downLoadApk(final Activity activity, String url, DownFileUtils.OnDownloadListener<File> listener) {
        DownFileUtils.get().downloadFile(activity, "downLoadApp.apk", url, listener);
    }

    /**
     * 相关权限请求
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static void requestInstallPermission(final File file, PermissionHelper permissionHelper) {
        permissionHelper.requestPermissions("请授予安装权限，否则无法安装", new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {
                // 请求权限成功
                AppUtils.installApp(file, BuildConfig.APPLICATION_ID + ".fileProvider");
            }

            @Override
            public void doAfterDenied(String... permission) {

            }
        }, Manifest.permission.REQUEST_INSTALL_PACKAGES);
    }
}
