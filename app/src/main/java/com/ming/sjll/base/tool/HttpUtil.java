package com.ming.sjll.base.tool;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.HttpPostGet;
import com.ming.sjll.base.tool.http.Status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.param.RxHttp$FormParam;

//import rxhttp.RxHttp;

//import rxhttp.RxHttp;

//import rxhttp.wrapper.param.RxHttp;


/**
 * 网络工具类
 *
 * @author wuwf
 */
public class HttpUtil {

    //    public HttpPostGet http ;
    private CommonCallback commonCallback;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            Bundle hData = message.getData();
            String result = hData.getString("value");
            try {
                JSONObject json = new JSONObject(result);
                //
                Integer code = Integer.parseInt(json.getString("code"));
                String msg = json.getString("msg");
                Object data = json.get("data");
                //                        JSONObject data= json.ge("data");
                Log.e("返回代码：", code.toString());
                Log.e("返回消息：", msg);
                System.out.print(data);
                if (!code.equals(commonCallback.CODE_SUCCESS)) {
                    commonCallback.onError(msg, code);
                } else {
                    commonCallback.onSuccess(data, code);
                    commonCallback.onSuccessJson(result, code);
                }
                commonCallback.onFinal();
            } catch (Exception e) {
                Log.e("Handler 回调", e.getMessage());
            }

        }
    };

    public HttpUtil() {
//        http = new HttpPostGet();

//        mHandler  =

    }

    /**
     * 发送网络请求的方法
     */
//    public void sendRequest(String url, HashMap o) {
//        String result = http.requestPost(url, o);
//        if(result.equals(""))
//        {
//            onFailure();
//        }
//    }
    public Handler mHandler;

    /**
     * 发送网络请求的方法
     */
    public void sendRequest(String url, HttpParamsObject o, CommonCallback cbFun) {

        this.commonCallback = cbFun;
        o.setParam("token", Cache.getUserToken());


        String sendUrl = Constant.BASE_API + url;
//                String result = http.requestPost(url, o);

        String paraUrl = o.getUrlParam();

//        Log.e("发送参数:", paraUrl);

//        if(Status.HttpStart.equals(true))
//        {
//            return ;
//        }
//        Status.HttpStart = true;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                Looper.prepare();

                Log.e("发送URL:", sendUrl+"?"+paraUrl);
                String result = com.ming.sjll.base.tool.http.HttpUtil.sendPost(sendUrl, paraUrl, java.nio.charset.Charset.defaultCharset().name());
                Log.e("返回数据:", result);
                if (result.equals("")) {
                    Log.e(url+"失败返回数据:", result);
                    cbFun.onFinal();
                    cbFun.onFailure();

                } else {
                    try {
                        Log.e(url+paraUrl+"成功返回数据:", result);

                        Message msg = new Message();
                        Bundle data = new Bundle();

                        //将获取到的String装载到msg中
                        data.putString("value", result);
                        msg.setData(data);
                        msg.what = 1;
                        //发消息到主线程
                        handler.sendMessage(msg);

//                        JSONObject json = new JSONObject(result);
////
//                        Integer  code = Integer.parseInt(json.getString("code"));
//                        String  msg = json.getString("msg");
//                        Object data = json.get("data");
////                        JSONObject data= json.ge("data");
//                        Log.e("返回代码：",code.toString());
//                        Log.e("返回消息：",msg);
//                        System.out.print(data);
//                        if(code.equals(CommonCallback.CODE_SUCCESS))
//                        {
//                            cbFun.onError(msg,code);
//                        }
//                        else if(code.equals(CommonCallback.WXCODE_SUCCESS))
//                        {
//                            cbFun.onSuccess(json,code);
//                        }
//
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

//                Status.HttpStart = false;
                Looper.loop();
//
            }
        });

        t.start();

    }

    public void sendRequest(String sendUrl, String paraUrl, CommonCallback cbFun) {
        this.commonCallback = cbFun;
        String result = com.ming.sjll.base.tool.http.HttpUtil.sendPost(sendUrl, paraUrl, java.nio.charset.Charset.defaultCharset().name());
        if (result.equals("")) {
            Log.e(sendUrl+"失败返回数据:", result);
            cbFun.onFinal();
            cbFun.onFailure();

        } else {
            try {
                Log.e(sendUrl+paraUrl+"成功返回数据:", result);

                Message msg = new Message();
                Bundle data = new Bundle();

                //将获取到的String装载到msg中
                data.putString("value", result);
                msg.setData(data);
                msg.what = 1;
                //发消息到主线程
                handler.sendMessage(msg);

//                        JSONObject json = new JSONObject(result);
////
//                        Integer  code = Integer.parseInt(json.getString("code"));
//                        String  msg = json.getString("msg");
//                        Object data = json.get("data");
////                        JSONObject data= json.ge("data");
//                        Log.e("返回代码：",code.toString());
//                        Log.e("返回消息：",msg);
//                        System.out.print(data);
//                        if(code.equals(CommonCallback.CODE_SUCCESS))
//                        {
//                            cbFun.onError(msg,code);
//                        }
//                        else if(code.equals(CommonCallback.WXCODE_SUCCESS))
//                        {
//                            cbFun.onSuccess(json,code);
//                        }
//
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    public static void downloadFile(String path, CommonCallback cbFun)
    {
        RxHttp.postForm( Constant.BASE_API + path)
//        RxHttp.postForm( "https://www.coolpaishop.com/api/common/upload")
                .addFile("image",new File(path))
                .setMultiForm()
                .asUpload(progress -> {
                    //上传进度回调,0-100，仅在进度有更新时才会回调,最多回调101次，最后一次回调Http执行结果
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                    long totalSize = progress.getTotalSize();     //要上传的总字节大小
                    cbFun.onProgress(currentProgress,currentSize,totalSize);
                }, AndroidSchedulers.mainThread())     //指定回调(进度/成功/失败)线程,不指定,默认在请求所在线程回调
//                .asString()                 //返回字符串数据
//                .timeout(0, TimeUnit.SECONDS)
                .subscribe (s -> {            //这里的s为String类型
                    //请求成功
                    Log.e("图片：","111111"+s);
                    cbFun.onFinal();
                }, throwable -> {
                    //请求失败
                    cbFun.onFailure();
                    cbFun.onFinal();
                    Log.e("上传图片失败：:",throwable.getMessage()+"路径："+path);
                });
    }

    /**
     * 多图上传
     * @param path
     * @param o
     * @param cbFun
     */
    public static void uplaodFiles(List<String> path, HttpParamsObject o, CommonCallback cbFun)
    {
        RxHttp$FormParam rxhttp =  RxHttp.postForm( Constant.BASE_API + Constant.UPLOADIMG);
        for (String str :
                path) {

            rxhttp.addFile("image[]",new File(str));
        }

        rxhttp.setMultiForm()
                .asUpload(progress -> {
                    //上传进度回调,0-100，仅在进度有更新时才会回调,最多回调101次，最后一次回调Http执行结果
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                    long totalSize = progress.getTotalSize();     //要上传的总字节大小
                    cbFun.onProgress(currentProgress,currentSize,totalSize);
                }, AndroidSchedulers.mainThread())     //指定回调(进度/成功/失败)线程,不指定,默认在请求所在线程回调
//                .asString()                 //返回字符串数据
//                .timeout(0, TimeUnit.SECONDS)
                .subscribe (s -> {            //这里的s为String类型
                    //请求成功
                    Log.e("上传图片成功：:",s+"");
                    JSONObject json = new JSONObject(s);
                    Integer code = Integer.parseInt(json.getString("code"));
                    String msg = json.getString("msg");
                    Object data = json.get("data");
                    if (!code.equals(cbFun.CODE_SUCCESS)) {
                        cbFun.onError(msg, code);
                    } else {
                        cbFun.onSuccess(data, code);
                        cbFun.onSuccessJson(s, code);
                    }
                    cbFun.onFinal();
                }, throwable -> {
                    //请求失败
                    cbFun.onFailure();
                    cbFun.onFinal();
                    Log.e("上传图片失败：:",throwable.getMessage()+"路径："+path);
                });
    }

    public static void uplaodFile(String path, HttpParamsObject o, CommonCallback cbFun)
    {
        uploadFileComm(path,o,cbFun,Constant.BASE_API + Constant.UPLOAD,"image");
    }
    public static void uploadVideo(String path, HttpParamsObject o, CommonCallback cbFun)
    {
        uploadFileComm(path,o,cbFun,Constant.BASE_API + Constant.UPLOADVIDEO,"videoFile");
    }

    public static void uploadFileComm(String path, HttpParamsObject o, CommonCallback cbFun,String Url,String name)
    {
        RxHttp.postForm(Url)
//        RxHttp.postForm( "https://www.coolpaishop.com/api/common/upload")
                .addFile(name,new File(path))

                .setMultiForm()
                .asUpload(progress -> {
                    //上传进度回调,0-100，仅在进度有更新时才会回调,最多回调101次，最后一次回调Http执行结果
                    int currentProgress = progress.getProgress(); //当前进度 0-100
                    long currentSize = progress.getCurrentSize(); //当前已上传的字节大小
                    long totalSize = progress.getTotalSize();     //要上传的总字节大小
                    cbFun.onProgress(currentProgress,currentSize,totalSize);
                }, AndroidSchedulers.mainThread())     //指定回调(进度/成功/失败)线程,不指定,默认在请求所在线程回调
//                .asString()                 //返回字符串数据
//                .timeout(0, TimeUnit.SECONDS)
                .subscribe (s -> {            //这里的s为String类型
                    //请求成功
                    Log.e("上传图片成功：:",s+"");
                    JSONObject json = new JSONObject(s);
                    Integer code = Integer.parseInt(json.getString("code"));
                    String msg = json.getString("msg");
                    Object data = json.get("data");
                    if (!code.equals(cbFun.CODE_SUCCESS)) {
                        cbFun.onError(msg, code);
                    } else {
                        cbFun.onSuccess(data, code);
                        cbFun.onSuccessJson(s, code);
                    }
                    cbFun.onFinal();
                }, throwable -> {
                    //请求失败
                    cbFun.onFailure();
                    cbFun.onFinal();
                    Log.e("上传图片失败：:",throwable.getMessage()+"路径："+path);
                });
    }
//
//    @Override
//    public void onError(String msg,int code) {
//        super.onError(msg,code);
//    }
//
//    @Override
//    public  void onFailure()
//    {
//        super.onFailure();
//    }
//
//
//    @Override
//    public void onSuccess(Object data, int code) {
//        super.onSuccess(data,code);
//
//    }

}

