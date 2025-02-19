package com.ming.sjll.appication;


import com.lzy.okgo.OkGo;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.Message.utils.RongIMUtils;
import com.tencent.bugly.crashreport.CrashReport;


/**
 * @author luoming
 * created at 2019/8/6 10:57 AM
 */
public class SJLLApplication extends BaseApplication {
    private static SJLLApplication mInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //颜色
        AppUtils.init(this);
        //融云初始化
        RongIMUtils.INSTANCE.init(this);
        //初始化上传文件
        OkGo.getInstance().init(this);
        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "9aa86a8873", true);

    }

    public static SJLLApplication getInstance() {
        return mInstance;
    }


}