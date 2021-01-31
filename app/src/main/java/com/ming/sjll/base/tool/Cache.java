package com.ming.sjll.base.tool;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ming.sjll.appication.SJLLApplication;
import com.ming.sjll.base.permission.PermissionHelper;
import com.ming.sjll.base.tool.token.APIUtil;
import com.ming.sjll.base.tool.token.AndroidFileUtil;
import com.ming.sjll.base.tool.token.DataConfig;
import com.ming.sjll.base.tool.token.WinnerApplication;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Cache {

    private static String cacheFilePath = Environment.getExternalStorageDirectory() + "/kpsd/strong";
    private static String userToken;
    public static Context context;
    private static SharedPreferences mSharePreferences;
    private static SharedPreferences.Editor mEditor;


    public static Activity getActivity() {
        Class activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取用户id
     * @return
     */
    public static String getUserId() {
        return CacheValue("userId");
    }


    /**
     * 设置用户id
     *
     * @return
     */
    public static String setUserId(String id) {
        CacheValue( "userId", id);
        return userToken;
    }

    /**
     * 获取用户token
     *
     * @return
     */
    public static String getUserToken() {
        userToken = CacheValue("token");
        return userToken;
    }

    /**
     * 设置token
     *
     * @return
     */
    public static String setUserToken(String token) {
//        PermissionHelper.reqCameraAndSDcard(getActivity(), new PermissionHelper.PermissionListener(){
//            @Override
//            public void onSuccess() {
                CacheValue( "token", token);
//            }
//
//            @Override
//            public void onFailure() {
//                getActivity().finish();
//            }
//        });
        return userToken;
    }

    /**
     * 设置key-value
     *
     * @param Key
     * @return
     */
    public static void CacheValue(String Key, String Value) {
//        PermissionHelper.reqCameraAndSDcard(getActivity(), new PermissionHelper.PermissionListener(){
//            @Override
//            public void onSuccess() {
                APIUtil.saveToken(cacheFilePath, Key, Value);
//            }
//
//            @Override
//            public void onFailure() {
//                getActivity().finish();
//            }
//        });

    }

    /**
     * 获取key-value
     *
     * @param Key
     * @return
     */
    public static String CacheValue(String Key) {
        return AndroidFileUtil.readFileByLines(cacheFilePath + "/" + Key);
    }




    public static void initCache(Context tcontext) {
        context = tcontext;
        mSharePreferences = context.getSharedPreferences("SSlPreferencesData", Context.MODE_PRIVATE);
        mEditor = mSharePreferences.edit();
    }

    /**
     * 应用缓存
     *
     * @param key
     * @param value
     */
    public static void setCache(String key, String value) {

        mEditor.putString(key, value);
        mEditor.commit();
    }

    public static String getCache(String key) {
        return mSharePreferences.getString(key, "");
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /***
     * 清理所有缓存
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    /**
     * 删除某个文件
     */
    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }


}
