package com.ming.sjll.base.tool.http;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.ming.sjll.appication.SJLLApplication;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.login.LoginActivity;

import org.json.JSONArray;

import java.lang.reflect.Array;

/**
 *
 */
public class CommonCallback {


    /**
     * 自定义了一些我们常见的一些异常类型
     */
    public final int NETWORK_ERROR = -1;//网络错误
    public final int JSON_ERROR = -2;//json解析错误
    public final int OTHER_ERROR = -3;//其他错误
//
//    public T Lastdata;
//
//    public T data;

//    public CommonCallback cpThis = this;
    /**
     * 验证失败（token过期）
     */
    public final static int NONT_LOGIN = 402;//其他错误

//    /**
//     * 失败
//     */
//    public final static int CODE_SUCCESS = 1;

    /**
     * 成功
     */
    public final static int CODE_SUCCESS = 0;


//    /**
//     *
//     * @param data 数据集
//     * @param key 需要获取的key
//     * @param type 获取后的数据类型
//     * @param <T>
//     * @return
//     */
//    public CommonCallback setKey(String key ,String type,Object Data)
//    {
//
////        Object data = (Object)Lastdata;
//        if (Lastdata instanceof JSONArray){
//            /*JSONObject 获取jsonArray ：需要数组的字段名*/
//            JSONArray tempDaata = (JSONArray)Lastdata;
//            Integer k = Integer.parseInt(key);
//            if(k > tempDaata.length())
//            {
//                cpThis.data =null;
//                return cpThis;
//            }
//        }
//        else
//        {
//
//        }
//        return cpThis;
//    }
//    public T getValue()
//    {
//        return
//    }
//
//    public  void onSuccess(Object data, int code, CommonCallback my)
//    {
//        my.onSuccess(data,code);
//    }

//    上传成功返回对象
    public void onSuccess(Object data, int code) {
//        Log.e("111111111","发送成功++++++++");
//        ToastShow.s("发送成功++++++++");
    }

//    上传成功返回json
    public void onSuccessJson(String data, int code) {

    }

//    无论失败成功，最终都会请求的方法
    public void onFinal()
    {

    }


    //    图像加载成功
    public void onSuccessBitmap(String arg0, View arg1,
                                Bitmap arg2) {
//        Log.e("111111111","发送成功++++++++");
//        ToastShow.s("发送成功++++++++");
    }

    /**
     * 請求中
     * @param currentProgress 当前进度 0-100
     * @param currentSize 当前已上传的字节大小
     * @param totalSize 要上传的总字节大小
     */
    public void onProgress(int currentProgress,long currentSize,long totalSize)
    {

    }

    /**
     * 请求错误
     */
    public void onFailure() {
        ToastShow.s("网络错误，请稍后重试");
    }


    //    下一步
    public void onNext()
    {

    }

    //    取消
    public void onCancel()
    {

    }

    /**
     * 项目返回错误
     *
     * @param msg
     * @param code
     */
    public void onError(String msg, int code) {
        switch (code) {
            case NONT_LOGIN://登录失败
                onLoginExpired();
            default:
                ToastShow.s(msg);
        }
    }

    public static void onLoginExpired() {
        onLoginExpired(true);
    }
    public static void onLoginExpired(boolean isTip) {
        if(isTip)
        {
            ToastShow.s("登录过期或者账号在别的设备登录，请重新登录");
        }

//        Tools.jump(SJLLApplication.getInstance(), LoginActivity.class, true);
        Cache.setUserToken("");
        Context context = SJLLApplication.getInstance();
//        SavePreferencesData mSavePreferencesData = new SavePreferencesData(context);
//        mSavePreferencesData.putStringData("token", null);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}