package com.ming.sjll.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ming.sjll.MainActivity;
import com.ming.sjll.R;

import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.Token;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.SavePreferencesData;
import com.ming.sjll.base.utils.TextUtil;
import com.ming.sjll.base.utils.Tools;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * x
 *
 * @author luoming
 * created at 2019-10-29 10:45
 * 登录
 */
public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.et_pssword)
    EditText etPssword;
    @BindView(R.id.tv_zhuce)
    TextView tvZhuce;
    @BindView(R.id.tv_wangji)
    TextView tvWangji;
    @BindView(R.id.rl_pssword)
    RelativeLayout rlPssword;
    @BindView(R.id.tv_btn1)
    TextView tvBtn1;
    @BindView(R.id.tv_btn2)
    TextView tvBtn2;
    @BindView(R.id.et_phone)
    EditText etPhone;
    private long mExitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_login_activity);
//        ButterKnife.bind(this);
//        (new SavePreferencesData(this)).getStringData("token");
//
//
////        HashMap<String, HttpParamsObject> map = new HashMap<String, HttpParamsObject>();
////        map.put("phone","13046217602");
////        map.put("pwd","123456");
//        HttpParamsObject param = new HttpParamsObject();
//
//        String data[] = new String[3]; /*开辟了一个长度为3的数组*/
//        data[0] = "測試1"; // 第一个元素
//        data[1] = "測試2"; // 第二个元素
//        data[2] = "測試3"; // 第三个元素
//        param.setParam("phone","13046217602");
//        param.setParam("phone",123456);
//        param.setParam("testArray[]",data);
//        new HttpUtil().sendRequest(Constant.LOGIN, param);
//
////        finishActivity(MainActivity.class);
//        if (!TextUtil.isEquals(Token.getUserToken(), "")) {
//            Tools.jump(LoginActivity.this, MainActivity.class, true);
//        }
//        initView();
    }

//    private void initView() {
//        tvBtn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (tvBtn1.getText().toString().equals("快捷登录")) {
//                    tvBtn1.setText("账号登录");
//                    tvBtn2.setText("快捷登录");
//                    rlPssword.setVisibility(View.GONE);
//                } else if (tvBtn1.getText().toString().equals("账号登录")) {
//                    tvBtn1.setText("快捷登录");
//                    tvBtn2.setText("账号登录");
//                    rlPssword.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//        tvBtn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (tvBtn2.getText().toString().equals("快捷登录")) {
//                    //发送验证码
//                    mPresenter.sendcode(etPhone.getText().toString(),"loginCode");
//                } else if (tvBtn2.getText().toString().equals("账号登录")) {
//                    //登录
//                    mPresenter.login(etPhone.getText().toString().trim(), etPssword.getText().toString().trim());
//                }
//
//            }
//        });
//        tvZhuce.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tools.jump(LoginAcitivity.this, RegisteredAcitivity.class, false);
//            }
//        });
//        tvWangji.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Tools.jump(LoginAcitivity.this, ForgotPasswordAcitivity.class, false);
//            }
//        });
//    }
//
//    @Override
//    protected LoginPresenter createPresenter() {
//        return new LoginPresenter();
//    }
//
//
//    @Override
//    public void showError(String msg) {
//        ToastShow.s(msg);
//
//    }
//
//    @Override
//    public void returnLogin(LoginBean baseBean) {
//        mSavePreferencesData.putStringData("token", baseBean.getData().getToken());
//        mSavePreferencesData.putStringData("rcToken", baseBean.getData().getRcToken());
//        mSavePreferencesData.putStringData("userId", baseBean.getData().getUserId());
//        Tools.jump(LoginAcitivity.this, MainActivity.class, true);
//    }
//
//    @Override
//    public void returnCode(BaseBean baseBean) {
//        Intent intent = new Intent(LoginAcitivity.this, VerificationCodeAcitivity.class);
//        intent.putExtra("iphone", etPhone.getText().toString().trim());
//        startActivity(intent);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            // if ((System.currentTimeMillis() - mExitTime) > 2000)
//            // {Toast.makeText(this,
//            // getResources().getString(R.string.exit).toString(),
//            // Toast.LENGTH_SHORT).show();
//            // mExitTime = System.currentTimeMillis();}
//
//            if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                mExitTime = System.currentTimeMillis();
//
//            } else {
//                Intent intent = new Intent(Intent.ACTION_MAIN);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
//                intent.addCategory(Intent.CATEGORY_HOME);
//                this.startActivity(intent);
//                finish();
//            }
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
}
