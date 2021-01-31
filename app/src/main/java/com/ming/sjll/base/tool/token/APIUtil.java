package com.ming.sjll.base.tool.token;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.ming.sjll.base.permission.PermissionHelper;
import com.ming.sjll.base.utils.SavePreferencesData;

import java.lang.ref.WeakReference;

/**
 * Token处理类
 */

public class APIUtil {

    public interface CallBack<T extends BaseResult> {
        void handleResult(T result);
    }

    /**
     * 保存用户打开APP的时候的token信息
     *
     * @param token 用户
     */
    public static void saveToken(String token) {

        RuntimeConfig.token = token;
//        String absolutePath = WinnerApplication.getContext().getCacheDir().getAbsolutePath();
        String absolutePath = Environment.getExternalStorageDirectory() + "/kpsd";
        AndroidFileUtil.writeStringToFile(token, absolutePath, DataConfig.TOKEN_FILE_NAME);
        RuntimeConfig.FIRST_STARTUP = true;
    }


    /**
     * 设置数据
     */
    public static void saveToken(String absolutePath, String key, String value) {
//        PermissionHelper.reqCameraAndSDcard();
        RuntimeConfig.token = value;
        AndroidFileUtil.writeStringToFile(value, absolutePath, key);
        RuntimeConfig.FIRST_STARTUP = true;
    }
}