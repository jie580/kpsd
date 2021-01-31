package com.ming.sjll.base.tool;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


class WorkThread extends Thread {
    public Handler mHandler;

    public void run() {
        Looper.prepare();

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                // 处理收到的消息
            }
        };

        Looper.loop();
    }
}
