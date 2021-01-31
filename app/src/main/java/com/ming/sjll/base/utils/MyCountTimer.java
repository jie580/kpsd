package com.ming.sjll.base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.List;

public class MyCountTimer extends CountDownTimer {
    public static final int TIME_COUNT = 60000;// 时间可能从60或者59开始
    private TextView btn;
    private String endStrRid;
    private int normalColor, timingColor;// 未计时的文字颜色，计时期间的文字颜色
    private Context mContext;

    /**
     * 参数 millisInFuture 倒计时总时间（如60S，120s等） 参数 countDownInterval 渐变时间（每次倒计1s）
     * <p>
     * 参数 btn 点击的按钮(因为Button是TextView子类，为了通用我的参数设置为TextView）
     * <p>
     * 参数 endStrRid 倒计时结束后，按钮对应显示的文字
     */
    public MyCountTimer(Context context, TextView btn, String endStrRid, int normalColor, int timingColor) {
        super(TIME_COUNT, 1000);
        this.btn = btn;
        this.endStrRid = endStrRid;
        this.mContext = context;
        this.timingColor = timingColor;
    }

    // 计时完毕时触发
    @Override
    public void onFinish() {

        if (normalColor > 0 && isContextExisted(mContext)) {
            btn.setTextColor(mContext.getResources().getColor(normalColor));
        }
        btn.setEnabled(true);
        btn.setText(endStrRid);
    }

    /**
     * Context是否存活
     *
     * @param context
     * @return
     */
    private boolean isContextExisted(Context context) {
        if (context != null) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    return true;
                }
            } else if (context instanceof Service) {
                if (isServiceExisted(context, context.getClass().getName())) {
                    return true;
                }
            } else if (context instanceof Application) {
                return true;
            }
        }
        return false;
    }

    private boolean isServiceExisted(Context context, String className) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
            ComponentName serviceName = serviceInfo.service;

            if (serviceName.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }


    // 计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
        if (timingColor > 0) {
            btn.setTextColor(mContext.getResources().getColor(timingColor));
        }
        btn.setEnabled(false);
        btn.setText("重新 " + millisUntilFinished / 1000 + "s");
    }
}
