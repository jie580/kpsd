package com.ming.sjll.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.appication.SJLLApplication;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.KpCountTimer;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.LoginInputPhoneActivityBinding;
import com.ming.sjll.databinding.LoginRegPwdActivityBinding;

import java.util.regex.Pattern;

/**
 * x
 *
 * @author created at 2019-10-29 10:45
 * 输入手机号
 */
public class InputPhoneActivity extends BaseActivity {

    LoginInputPhoneActivityBinding binding;
    private String code = "";

    KpCountTimer countTimer;
    private String phone;
    private String toPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.login_input_phone_activity, null, false);
        setContentView(binding.getRoot());
        toPage = getIntent().getStringExtra("toPage");

        initView();
    }


    private void initView() {

        if(toPage.equals("forget"))
        {
            binding.title.setText("忘记密码");
        }
        else if(toPage.equals("reg"))
        {
            binding.title.setText("注册账号");
        }


        binding.nextPage.setOnClickListener(new View.OnClickListener() {
            /**
             * 点击继续
             * @param v
             */
            @Override
            public void onClick(View v) {

                if(!isPhoneNumber(binding.phone.getText().toString().trim()))
                {
                    ToastShow.s("手机号码格式不正确");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("phone", binding.phone.getText().toString().trim());
                bundle.putString("toPage", toPage);
                Tools.jump(getContext(), CodeActivity.class, bundle,true);

            }
        });



    }

    public static boolean isPhoneNumber(String input) {// 判断手机号码是否规则
        String regex = "(1[0-9][0-9]|15[0-9]|18[0-9])\\d{8}";
        Pattern p = Pattern.compile(regex);
        return p.matches(regex, input);//如果不是号码，则返回false，是号码则返回true

    }


}
