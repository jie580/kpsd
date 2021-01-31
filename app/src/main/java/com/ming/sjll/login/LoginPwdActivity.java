package com.ming.sjll.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;

import butterknife.BindView;

/**
 * x
 *
 * @author luoming
 * created at 2019-10-29 10:45
 * 登录
 */
public class LoginPwdActivity extends BaseActivity {

    @BindView(R.id.tv_goBack)
    ImageView goback;

    @BindView(R.id.tv_nextPage)
    ImageButton nextPage;

    @BindView(R.id.tv_password)
    EditText password;

    private String phone;
    private long mExitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login_pwd_activity);
//        ButterKnife.bind(this);
//        (new SavePreferencesData(this)).getStringData("token");
        initView();
    }


    private void initView() {
        /**
         * 取消
         */
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 登录
         */
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("phone", phone);
                param.setParam("password", password.getText().toString());
                new HttpUtil().sendRequest(Constant.USER_LOGIN, param, new CommonCallback() {

                    @Override
                    public void onSuccess(Object data, int code) {

                        //登录成功
                        String token = ObjeGetValue.GetValu("token", data).toString();
                        String rcToken = ObjeGetValue.GetValu("rcToken", data).toString();
                        String userId = ObjeGetValue.GetValu("userId", data).toString();
                        loginToHome(token, rcToken, userId);
                    }

                });
            }
        });
    }


}
