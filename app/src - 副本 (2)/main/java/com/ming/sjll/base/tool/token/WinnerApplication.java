package com.ming.sjll.base.tool.token;


import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * 我们的住应用，全局变量，第三方SDK初始化等都在这个类中
 * Created by acer-pc on 2018/6/12.
 */

public class WinnerApplication extends MultiDexApplication {
    public static Activity activity;
    private static WinnerApplication application;
    private Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        context = getApplicationContext();

    }


    /**
     * 获取到本应用的对象
     */
    public static WinnerApplication getApplication() {
        return application;
    }

    /**
     * 获取上下文
     */
    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

}
