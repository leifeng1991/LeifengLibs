package com.leifeng.lib.utils;

import android.os.Environment;

import com.leifeng.lib.base.BaseApplication;

import java.io.File;

/**
 * 清除工具类
 */
public final class CleanUtils {

    private CleanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 清除内部缓存
     *
     * @return true:清除成功 false:清除失败
     */
    public static boolean cleanInternalCache() {
        return deleteFilesInDir(BaseApplication.getBaseApplication().getCacheDir());
    }

    /**
     * 清除内部文件
     *
     * @return true:清除成功 false:清除失败
     */
    public static boolean cleanInternalFiles() {
        return deleteFilesInDir(BaseApplication.getBaseApplication().getFilesDir());
    }

    /**
     * 清除内部数据库
     *
     * @return true:清除成功 false:清除失败
     */
    public static boolean cleanInternalDbs() {
        return deleteFilesInDir(new File(BaseApplication.getBaseApplication().getFilesDir().getParent(), "databases"));
    }

    /**
     * 根据名称清除数据库
     *
     * @param dbName 数据库名称
     * @return true:清除成功 false:清除失败
     */
    public static boolean cleanInternalDbByName(final String dbName) {
        return BaseApplication.getBaseApplication().deleteDatabase(dbName);
    }

    /**
     * 清除内部 SP
     *
     * @return true:清除成功 false:清除失败
     */
    public static boolean cleanInternalSp() {
        return deleteFilesInDir(new File(BaseApplication.getBaseApplication().getFilesDir().getParent(), "shared_prefs"));
    }

    /**
     * 清除外部缓存
     *
     * @return true:清除成功 false:清除失败
     */
    public static boolean cleanExternalCache() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && deleteFilesInDir(BaseApplication.getBaseApplication().getExternalCacheDir());
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dirPath 文件路径
     */
    public static boolean cleanCustomDir(final String dirPath) {
        return deleteFilesInDir(dirPath);
    }

    /**
     * 清除自定义目录下的文件
     *
     * @param dir
     */
    public static boolean cleanCustomDir(final File dir) {
        return deleteFilesInDir(dir);
    }

    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    private static boolean deleteFilesInDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    private static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
