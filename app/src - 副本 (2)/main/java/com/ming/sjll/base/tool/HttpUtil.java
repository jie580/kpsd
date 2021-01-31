package com.ming.sjll.base.tool;

import com.ming.sjll.base.tool.http.CommonJsonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.HttpPostGet;


/**
 * 网络工具类
 *
 * @author wuwf
 */
public class HttpUtil extends CommonJsonCallback {

    public HttpPostGet http;

    public HttpUtil() {
        http = new HttpPostGet();
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

    /**
     * 发送网络请求的方法
     */
    public void sendRequest(String url, HttpParamsObject o) {
        String result = http.requestPost(url, o);
        if (result.equals("")) {
            onFailure();
        }
    }

    @Override
    public void onError(String msg, int code) {
        super.onSuccess(msg, code);
    }

    @Override
    public void onFailure() {
        super.onFailure();
    }


    @Override
    public void onSuccess(Object data, int code) {
        super.onSuccess(data, code);

    }

}

