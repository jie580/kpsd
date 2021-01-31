package com.ming.sjll.base.tool.http;


import com.ming.sjll.base.widget.ToastShow;

/**
 *
 */
public class CommonJsonCallback {


    /**
     * 自定义了一些我们常见的一些异常类型
     */
    protected final int NETWORK_ERROR = -1;//网络错误
    protected final int JSON_ERROR = -2;//json解析错误
    protected final int OTHER_ERROR = -3;//其他错误


    /**
     * 验证失败（token过期）
     */
    protected final int NONT_LOGIN = 402;//其他错误

    /**
     * 失败
     */
    protected final int CODE_SUCCESS = 1;

    /**
     * 成功
     */
    protected final int WXCODE_SUCCESS = 0;


    public void onSuccess(Object data, int code) {

    }

    /**
     * 请求错误
     */
    public void onFailure() {
        ToastShow.s("网络错误，请稍后重试");
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
                onLoginExpired(code, msg);
            default:
                ToastShow.s(msg);
        }
    }

    public void onLoginExpired(int code, String msg) {
        ToastShow.s("登录过期或者账号在别的设备登录，请重新登录");

//        Context context = SJLLApplication.getInstance();
//        SavePreferencesData mSavePreferencesData = new SavePreferencesData(context);
//        mSavePreferencesData.putStringData("token", null);
//        Intent intent = new Intent(context, LoginAcitivity.class);
//        context.startActivity(intent);
    }


}