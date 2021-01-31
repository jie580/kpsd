package com.ming.sjll.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.KpCountTimer;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.LoginCodeBinding;
import com.ming.sjll.databinding.LoginLoginActivityBinding;
import com.ming.sjll.toolPage.CustomVideoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 验证码输入
 */
public class CodeActivity extends BaseActivity {

    LoginCodeBinding binding;
    private List<String> codes = new ArrayList<>();

    KpCountTimer countTimer;
    private String phone;
    private String toPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.login_code, null, false);
        setContentView(binding.getRoot());
        phone = getIntent().getStringExtra("phone");
        toPage = getIntent().getStringExtra("toPage");
        binding.phone.setText("+" + phone);
        sendCode();
        initView();
    }


    private void initView() {


        //验证码输入
        binding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0) {
                    binding.etCode.setText("");
                    if (codes.size() < 6) {
                        String data = editable.toString().trim();
                        if (data.length() >= 6) {
                            //将string转换成List
                            List<String> list = Arrays.asList(data.split(""));
                            //Arrays.asList没有实现add和remove方法，继承的AbstractList，需要将list放进java.util.ArrayList中
                            codes = new ArrayList<>(list);
                            if (codes.size() > 0 && codes.size() > 6) {
                                //使用data.split("")方法会将""放进第一下标里需要去掉
                                codes.remove(0);
                            }
                        } else {
                            codes.add(data);
                        }
                        showCode();
                    }
                }
            }
        });
        // 监听验证码删除按键
        binding.etCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (codes.size() > 0) {
                    if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && codes.size() > 0) {
                        codes.remove(codes.size() - 1);
                        showCode();
                        return true;
                    }
                }
                return false;
            }
        });


        binding.nextPage.setOnClickListener(new View.OnClickListener() {
            /**
             * 点击继续
             * @param v
             */
            @Override
            public void onClick(View v) {

                StringBuilder sb = new StringBuilder();
                for (String s : codes) {
                    sb.append(s);
                }
               String codeString = sb.toString();

                if (toPage.equals("home")) {
                    HttpParamsObject param = new HttpParamsObject();
                    param.setParam("phone", phone);
                    param.setParam("code",   codeString);
//                    param.setParam("password", binding.password.getText().toString().trim());
                    new HttpUtil().sendRequest(Constant.QUICKLOGIN, param, new CommonCallback() {
                        @Override
                        public void onSuccess(Object data, int code) {
                            String token = ObjeGetValue.GetValu("token", data).toString();
                            String rcToken = ObjeGetValue.GetValu("rcToken", data).toString();
                            String userId = ObjeGetValue.GetValu("userId", data).toString();
                            loginToHome(token, rcToken, userId);
                        }
                    });

                } else {
                    HttpParamsObject param = new HttpParamsObject();
                    param.setParam("phone", phone);
                    param.setParam("code", codeString);
                    if (toPage.equals("reg")) {
                        param.setParam("type", "registerCode");//注册
                    } else if (toPage.equals("forget")) {
                        param.setParam("type", "ChangeLoginPwd");//忘记密码
                    } else if (toPage.equals("SaveLoginPwd")) {
                        param.setParam("type", "SaveLoginPwd");//修改密码
                    }


//                    param.setParam("password", binding.password.getText().toString().trim());
                    new HttpUtil().sendRequest(Constant.USER_CHECK_CODE, param, new CommonCallback() {
                        @Override
                        public void onSuccess(Object data, int code) {
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", phone);
                            bundle.putString("code", codeString);
                            bundle.putString("toPage", toPage);
                            Tools.jump(getContext(), RegPwdActivity.class, bundle, true);
                        }
                    });
                }


            }
        });


        binding.sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendCode();


            }
        });

        /**
         * 取消
         */
//        goback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tools.jump( LoginActivity.this, WelcomeActivity.class, false);
////                finish();
//            }
//        });


    }


    private void sendCode() {

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("phone", phone);
        if (toPage.equals("home")) {
            param.setParam("type", "loginCode");
        } else if (toPage.equals("reg")) {
            param.setParam("type", "registerCode");
        } else if (toPage.equals("forget")) {
            param.setParam("type", "ChangeLoginPwd");
        } else if (toPage.equals("SaveLoginPwd")) {
            param.setParam("type", "SaveLoginPwd");
        }
//                    param.setParam("password", binding.password.getText().toString().trim());
        new HttpUtil().sendRequest(Constant.USER_SENDCODE, param, new CommonCallback() {
            @Override
            public void onSuccess(Object data, int code) {

                ToastShow.s("发送验证码成功");

                countTimer = new KpCountTimer(getActivity(), binding.sendCode,
                        "重新获取", binding.sendCode.getBackground(), binding.sendCode.getBackground());
                countTimer.start();
            }
        });

    }

    /**
     * 显示输入的验证码
     */
    private void showCode() {
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        String code5 = "";
        String code6 = "";
        if (codes.size() >= 1) {
            code1 = codes.get(0);
        }
        if (codes.size() >= 2) {
            code2 = codes.get(1);
        }
        if (codes.size() >= 3) {
            code3 = codes.get(2);
        }
        if (codes.size() >= 4) {
            code4 = codes.get(3);
        }
        if (codes.size() >= 5) {
            code5 = codes.get(4);
        }
        if (codes.size() >= 6) {
            code6 = codes.get(5);
        }
        binding.tvCode1.setText(code1);
        binding.tvCode2.setText(code2);
        binding.tvCode3.setText(code3);
        binding.tvCode4.setText(code4);
        binding.tvCode5.setText(code5);
        binding.tvCode6.setText(code6);
        //setColor();//设置高亮颜色
    }


}
