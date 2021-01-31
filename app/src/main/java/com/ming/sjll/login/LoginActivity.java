package com.ming.sjll.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.common.Setting;
import com.ming.sjll.R;

import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;

import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.SavePreferencesData;
import com.ming.sjll.base.utils.TextUtil;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.webview.WebViewActivity;
import com.ming.sjll.base.webview.WebViewPresenter;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.LoginLoginActivityBinding;
import com.ming.sjll.login.bean.DefaultAvatar;
import com.ming.sjll.toolPage.CustomVideoView;
import com.ming.sjll.ui.HeadImgRollEvent;
import com.ming.sjll.ui.LocateCenterHorizontalView;
import com.ming.sjll.ui.LooperLayoutManager;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * x
 *
 * @author luoming
 * created at 2019-10-29 10:45
 * 登录
 */
public class LoginActivity extends BaseActivity {

    LoginLoginActivityBinding binding;
    boolean isPwdLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.login_login_activity, null, false);
        setContentView(binding.getRoot());
        initView();
        play();
//        testView();
    }

    private void play() {
        /*主要代码起始位置*/
        final CustomVideoView vv = binding.videoView;
        final String uri = "android.resource://" + getPackageName() + "/" + R.raw.welcome;
        vv.setVideoURI(Uri.parse(uri));
        vv.start();
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);


            }
        });

        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
//              播放完成后
//              vv.start();
//        		Intent intent = new Intent(inst, second.class);
//        		startActivity(intent);
//        		inst.finish();
            }
        });
    }


//
//
//    private void testView()
//    {
//        HttpParamsObject param = new HttpParamsObject();
//        new HttpUtil().sendRequest(Constant.USER_GETDEFAULTAVATAR, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {
//                DefaultAvatar da = new Gson().fromJson(data, DefaultAvatar.class);
//
//                recyclerview.setHasFixedSize(true);
//                recyclerview.setLayoutManager(new LinearLayoutManager(LoginActivity.this, LinearLayoutManager.HORIZONTAL, false));
//                headAdpter = new ImgRollAdapter(LoginActivity.this, da.getData(), circle,recyclerview);
//                recyclerview.setAdapter(headAdpter);
//
//                recyclerview.setOnSelectedPositionChangedListener(new LocateCenterHorizontalView.OnSelectedPositionChangedListener() {
//                    @Override
//                    public void selectedPositionChanged(int pos) {
//                        Log.e("selectedPositionChanged" ,"选择后事件"+pos);
//
//                    }
//
////
//                });
//
//            }
//        });
//    }


//    private void testView()
//    {
//        final HorizontalWheelView wv = (HorizontalWheelView) findViewById(R.id.wv);
//        hh = (HorizontalScrollView) findViewById(R.id.wv);
//        final LinearLayout viewById = (LinearLayout) findViewById(R.id.ll_camera_aperture);
//        hh.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("hh","hh");
//                hh.scrollTo(100,0);
//            }
//        });
//
//
//        wv.setAdapter(new WheelViewAdapter() {
//            @Override
//            public int getItemCount() {
//
//                return 17;
//            }
//
//            @Override
//            public int getShowItemCount() {
//
//                return 5;
//            }
//
//            @Override
//            public View getItemView(int i) {
//                TextView tv = new TextView(LoginActivity.this);
//                tv.setText("hello" + i);
//                tv.setGravity(Gravity.CENTER);
//                tv.setBackgroundColor(Color.BLUE);
//                return tv;
//            }
//
//            @Override
//            public void onItemSelected(int lst, int now, View itemLst, View itemNow) {
//
//                itemLst.setBackgroundColor(Color.BLUE);
//                itemNow.setBackgroundColor(Color.RED);
//            }
//
//            @Override
//            public View getRootView() {
//
//                return viewById;
//            }
//        });
//        //设置选中的item
//        wv.setSelectedPosition(4);
//
//
//        wv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e("rest",scrollX+"");
//            }
//        });
//
//    }

    private void loginInit() {
        if (isPwdLogin) {
            binding.pwdWrap.setVisibility(View.GONE);
            binding.pwdWrap2.setVisibility(View.VISIBLE);
            binding.pwdWrap3.setVisibility(View.VISIBLE);
            binding.title.setText("密码登录");
        } else {
            binding.pwdWrap.setVisibility(View.VISIBLE);
            binding.pwdWrap2.setVisibility(View.GONE);
            binding.pwdWrap3.setVisibility(View.GONE);
            binding.title.setText("手机号登录/注册");

        }
    }

    private void initView() {
//

        loginInit();
//        protocol.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString(WebViewPresenter.KEY_URL, Constant.BASE_API + Constant.USER_PROTOCOL + "?id=2");
//                bundle.putString(WebViewPresenter.KEY_TITLE, "酷拍商店服务条款");
//                Tools.jump(getContext(), WebViewActivity.class, bundle, false);
//            }
//        });

        binding.changeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPwdLogin = !isPwdLogin;
                loginInit();
            }
        });

        binding.reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("toPage", "reg");
                Tools.jump(getContext(), InputPhoneActivity.class, bundle,false);
            }
        });

        binding.forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("toPage", "forget");
                Tools.jump(getContext(), InputPhoneActivity.class, bundle,false);
            }
        });

        binding.nextPage.setOnClickListener(new View.OnClickListener() {
            /**
             * 点击继续
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (isPwdLogin) {

                    HttpParamsObject param = new HttpParamsObject();
                    param.setParam("phone", binding.phone.getText().toString().trim());
                    param.setParam("password", binding.password.getText().toString().trim());
                    new HttpUtil().sendRequest(Constant.USER_LOGIN, param, new CommonCallback() {
                        @Override
                        public void onSuccess(Object data, int code) {
                            //登录成功
                            String token = ObjeGetValue.GetValu("token", data).toString();
                            String rcToken = ObjeGetValue.GetValu("rcToken", data).toString();
                            String userId = ObjeGetValue.GetValu("userId", data).toString();
                            loginToHome(token, rcToken, userId);
//                            Intent intent;
//                            if ((Boolean) ObjeGetValue.GetValu("isRegister", data) == true) {
//////                  跳转已注册页面
//                                intent = new Intent(LoginActivity.this, LoginPwdActivity.class);
//                            } else {
//                                //跳转没注册页面
//                                intent = new Intent(LoginActivity.this, RegPwdActivity.class);
//                            }
//                            intent.putExtra("phone", phone);
//                            startActivity(intent);
                        }
                    });
                }
                else
                {
//                    判断手机号是否注册
                    HttpParamsObject param = new HttpParamsObject();
                    param.setParam("phone", binding.phone.getText().toString().trim());
//                    param.setParam("password", binding.password.getText().toString().trim());
                    new HttpUtil().sendRequest(Constant.USER_CHECK_PHONE, param, new CommonCallback() {
                        @Override
                        public void onSuccess(Object data, int code) {
                            Intent intent = new Intent(LoginActivity.this, CodeActivity.class);
                            if ((Boolean) ObjeGetValue.GetValu("isRegister", data) == true) {

                                intent.putExtra("toPage", "home");
                            }
                            else
                            {
                                intent.putExtra("toPage", "reg");
                            }
                            intent.putExtra("phone", binding.phone.getText().toString().trim());
                            startActivity(intent);

                        }
                    });

                }



            }
        });


    }



}
