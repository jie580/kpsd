package com.ming.sjll.login;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.ming.sjll.base.utils.Tools;


public class WelcomeActivity extends Activity {
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_welcome);
//        view = (LinearLayout) findViewById(R.id.welcome_layout);
//        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);// 动画透明度
//        anim.setDuration(500);
//        view.startAnimation(anim);
//        anim.setAnimationListener(new WelcomeAnimation());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        Thread myThread = new Thread() {//创建子线程
            @Override
            public void run() {
                try {
                    sleep(1000);//使程序休眠一秒
                    initPermission();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }


    private class WelcomeAnimation implements AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            initPermission();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

    /**
     * 获取手机信息权限
     */
    private void initPermission() {
        Tools.jump(WelcomeActivity.this, LoginActivity.class, true);

    }


}
