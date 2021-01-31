package com.ming.sjll.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.appication.SJLLApplication;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.KpCountTimer;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.SavePreferencesData;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.LoginCodeBinding;
import com.ming.sjll.databinding.LoginRegPwdActivityBinding;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * x
 *
 * @author created at 2019-10-29 10:45
 * 密码设置
 */
public class RegPwdActivity extends BaseActivity {

    LoginRegPwdActivityBinding binding;
    private String code = "";

    KpCountTimer countTimer;
    private String phone;
    private String toPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.login_reg_pwd_activity, null, false);
        setContentView(binding.getRoot());
        phone = getIntent().getStringExtra("phone");
        toPage = getIntent().getStringExtra("toPage");
        code = getIntent().getStringExtra("code");

        initView();
    }


    private void initView() {

        if(toPage.equals("forget"))
        {
            binding.title.setText("重置密码");
        }
        else
        {
            binding.title.setText("请输入密码");
        }


        binding.nextPage.setOnClickListener(new View.OnClickListener() {
            /**
             * 点击继续
             * @param v
             */
            @Override
            public void onClick(View v) {
                if (binding.password.getText().toString().trim().equals("")) {
                    ToastShow.s("请输入密码");
                    return;
                }
//              判断两次输入密码是否一样
                if (!binding.password.getText().toString().trim().equals(binding.password.getText().toString().trim())) {
                    ToastShow.s("两次输入密码不一样，请重新输入");
                    return;
                }
                if(toPage.equals("reg"))
                {
//                    注册
                    HttpParamsObject param = new HttpParamsObject();
                    param.setParam("phone", phone);
                    param.setParam("password", binding.password.getText().toString().trim());
                    param.setParam("repassword", binding.repassword.getText().toString().trim());
                    param.setParam("code", code);
//                    param.setParam("password", binding.password.getText().toString().trim());
                    new HttpUtil().sendRequest(Constant.REGISTER, param, new CommonCallback() {
                        @Override
                        public void onSuccess(Object data, int code) {
                            String token = ObjeGetValue.GetValu("token", data).toString();
                            String rcToken = ObjeGetValue.GetValu("rcToken", data).toString();
                            String userId = ObjeGetValue.GetValu("userId", data).toString();
                            loginToHome(token, rcToken, userId);
                        }
                    });

                }
                else if(toPage.equals("forget"))
                {
//                    忘记密码
                    HttpParamsObject param = new HttpParamsObject();
                    param.setParam("phone", phone);
                    param.setParam("password", binding.password.getText().toString().trim());
                    param.setParam("repassword", binding.repassword.getText().toString().trim());
                    param.setParam("code", code);
//                    param.setParam("password", binding.password.getText().toString().trim());
                    new HttpUtil().sendRequest(Constant.USER_FORGOT_PWD, param, new CommonCallback() {
                        @Override
                        public void onSuccess(Object data, int code) {
                            ToastShow.s("修改密码成功");
                            Context context = SJLLApplication.getInstance();
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });

                }


            }
        });



    }



}
