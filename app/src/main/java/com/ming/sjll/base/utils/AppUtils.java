package com.ming.sjll.base.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ArrayRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;

import java.util.Locale;


/**
 * @author：created by leaf on 2019-05-08
 * Github地址：https://github.com/Ye-Miao
 * Desc: 工具类
 */
public class AppUtils {

    private static Context mContext;
    private static Thread mUiThread;

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static void init(Context context) { //在Application中初始化
        mContext = context;
        mUiThread = Thread.currentThread();
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static AssetManager getAssets() {
        return mContext.getAssets();
    }

    public static float getDimension(int id) {
        return getResource().getDimension(id);
    }

    public static Resources getResource() {
        return mContext.getResources();
    }

    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(mContext, resId);
    }

    public static int getColor(int resId) {
        return ContextCompat.getColor(mContext, resId);
    }

    public static String getString(@StringRes int resId) {
        return mContext.getResources().getString(resId);
    }

    public static String[] getStringArray(@ArrayRes int resId) {
        return mContext.getResources().getStringArray(resId);
    }

    public static boolean isUIThread() {
        return Thread.currentThread() == mUiThread;
    }

    public static void runOnUI(Runnable r) {
        sHandler.post(r);
    }

    public static void runOnUIDelayed(Runnable r, long delayMills) {
        sHandler.postDelayed(r, delayMills);
    }


    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }










    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取状态栏高度
     */
    public static int getSystemBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}