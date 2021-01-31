package com.ming.sjll.login;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.permission.PermissionHelper;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.toolPage.CustomVideoView;

import butterknife.BindView;


public class WelcomeActivity extends BaseActivity {
//    private View view;

//    @BindView(R.id.vi_next)
    View nextPage;
    private long mExitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_welcome);

//        setContentView(R.layout.welcome_ativity);



        initPermission(true);
        nextPage = (View) this.findViewById(R.id.nextPage);
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPermission();

            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
                intent.addCategory(Intent.CATEGORY_HOME);
                WelcomeActivity.this.startActivity(intent);
                finish();

            }
        });


//        view = (LinearLayout) findViewById(R.id.welcome_layout);
//        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);// 动画透明度
//        anim.setDuration(3000);
//        nextPage.startAnimation(anim);
//        anim.setAnimationListener(new WelcomeAnimation());

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 设置状态栏透明
//            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        Thread myThread = new Thread() {//创建子线程
//            @Override
//            public void run() {
//                try {
//                    sleep(1000);//使程序休眠一秒
//                    initPermission();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        myThread.start();//启动线程

    }

    private void getPermission()
    {
        PermissionHelper.reqCameraAndSDcard(WelcomeActivity.this, new PermissionHelper.PermissionListener() {
            @Override
            public void onSuccess() {
                ToastShow.s("获取权限成功");
            }
            @Override
            public void onFailure() {
                ToastShow.s("获取权限失败");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
                intent.addCategory(Intent.CATEGORY_HOME);
                WelcomeActivity.this.startActivity(intent);
                finish();
//                WelcomeActivity.this.finish();
            }
        });
    }

    private class WelcomeAnimation implements AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
//            initPermission();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

    /**
     * 获取手机信息权限
     */
    private void initPermission() {
        initPermission(false);
    }
    private void initPermission(boolean isInit) {
        PermissionHelper.reqCameraAndSDcard(WelcomeActivity.this, new PermissionHelper.PermissionListener() {
            @Override
            public void onSuccess() {
                    if(Cache.getUserToken() != null && !Cache.getUserToken().equals(""))
                    {
//                        Tools.jump(WelcomeActivity.this, MainActivity.class, true);
                        loginToHome();
                        return;
                    }
                    if(!isInit)
                    {
                        Tools.jump(WelcomeActivity.this, LoginActivity.class, false);
                    }

            }
            @Override
            public void onFailure() {
                ToastShow.s("获取权限失败");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
                intent.addCategory(Intent.CATEGORY_HOME);
                WelcomeActivity.this.startActivity(intent);
                finish();
//                WelcomeActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // if ((System.currentTimeMillis() - mExitTime) > 2000)
            // {Toast.makeText(this,
            // getResources().getString(R.string.exit).toString(),
            // Toast.LENGTH_SHORT).show();
            // mExitTime = System.currentTimeMillis();}

            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
                intent.addCategory(Intent.CATEGORY_HOME);
                this.startActivity(intent);
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
