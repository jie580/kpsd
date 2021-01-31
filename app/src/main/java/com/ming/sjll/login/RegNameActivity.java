package com.ming.sjll.login;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.KpCountTimer;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.login.bean.DefaultAvatar;
import com.ming.sjll.ui.LocateCenterHorizontalView;

import java.util.List;

import butterknife.BindView;

/**
 * x
 *
 * @author created at 2019-10-29 10:45
 * 注册页面2
 */
public class RegNameActivity extends BaseActivity {

    @BindView(R.id.tv_login_im_procurer)
    ImageView loginImProcurer;

    @BindView(R.id.tv_login_im_procurer_show)
    TextView loginImProcureShow;

    @BindView(R.id.tv_login_im_supplier)
    ImageView loginImSupplier;

    @BindView(R.id.tv_login_im_supplier_show)
    TextView loginImSupplierShow;


    @BindView(R.id.recyclerview)
    LocateCenterHorizontalView recyclerview;

    ImgRollAdapter headAdpter;
    int circle = 50;


    private int aw = 0;
    private int ah = 0;
    private int bw = 0;
    private int bh = 0;

    private String phone;
    private String password;
    private String photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        phone = getIntent().getStringExtra("phone");
        password = getIntent().getStringExtra("password");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_reg_name_activity);

        aw = loginImProcurer.getLayoutParams().width;
        ah = loginImProcurer.getLayoutParams().height;
        bw = loginImSupplier.getLayoutParams().width;
        bh = loginImSupplier.getLayoutParams().height;

        initView();

    }

    private void initView() {
        getHeadImage();
        loginImSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginImSupplier.isClickable()) {

                    loginImSupplier.setClickable(false);
                    loginImProcurer.setClickable(true);
                    loginImProcurer.setBackground(RegNameActivity.this.getResources().getDrawable(R.mipmap.login_im_procurer_a));
                    loginImProcurer.getLayoutParams().width = bw;
                    loginImProcurer.getLayoutParams().height = bh;
                    loginImSupplier.setBackground(RegNameActivity.this.getResources().getDrawable(R.mipmap.login_im_supplier_b));
                    loginImSupplier.getLayoutParams().width = aw;
                    loginImSupplier.getLayoutParams().height = ah;
                    loginImProcureShow.setVisibility(loginImProcureShow.GONE);
                    loginImSupplierShow.setVisibility(loginImSupplier.VISIBLE);

                }
            }

        });

        loginImProcurer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginImProcurer.isClickable()) {
                    loginImProcurer.setClickable(false);
                    loginImSupplier.setClickable(true);
                    loginImSupplier.setBackground(RegNameActivity.this.getResources().getDrawable(R.mipmap.login_im_supplier_a));
                    loginImSupplier.getLayoutParams().width = bw;
                    loginImSupplier.getLayoutParams().height = bh;
                    loginImProcurer.setBackground(RegNameActivity.this.getResources().getDrawable(R.mipmap.login_im_procurer_b));
                    loginImProcurer.getLayoutParams().width = aw;
                    loginImProcurer.getLayoutParams().height = ah;

                    loginImProcureShow.setVisibility(loginImProcureShow.VISIBLE);
                    loginImSupplierShow.setVisibility(loginImSupplier.GONE);

                }
            }

        });

    }

    private void getHeadImage() {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.USER_GETDEFAULTAVATAR, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                DefaultAvatar da = new Gson().fromJson(data, DefaultAvatar.class);

                recyclerview.setHasFixedSize(true);
                recyclerview.setLayoutManager(new LinearLayoutManager(RegNameActivity.this, LinearLayoutManager.HORIZONTAL, false));
                headAdpter = new ImgRollAdapter(RegNameActivity.this, da.getData(), circle, recyclerview);
                recyclerview.setAdapter(headAdpter);

                recyclerview.setOnSelectedPositionChangedListener(new LocateCenterHorizontalView.OnSelectedPositionChangedListener() {
                    @Override
                    public void selectedPositionChanged(int pos) {
                        Log.e("selectedPositionChanged", "选择后事件" + pos);
                        photo = headAdpter.getData().get(pos % headAdpter.getData().size()).gethoto();
                    }
                });

            }
        });

    }


}
