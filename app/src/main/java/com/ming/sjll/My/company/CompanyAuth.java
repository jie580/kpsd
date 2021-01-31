package com.ming.sjll.My.company;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GetInfoBeans;
import com.ming.sjll.Bean.UserTypeBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.event.UserUpdateEvent;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.map.MapMainActivity;
import com.ming.sjll.toolPage.CropPicture;
import com.ming.sjll.toolPage.CropPictureConfig;
import com.ming.sjll.ui.CircleImageView;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.rey.material.app.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.List;

/**
 * 个人中心-权限设置
 */
public class CompanyAuth extends BaseActivity {


    ImageView backdrop;
    GetInfoBeans userInfoList;
    TextView title_bar_TvRight;

    LinearLayout linearLayout;

    private int lastUser = -1;
    String nowUserId;

    ImageView is_serve,is_visible,is_synced,is_manage,is_schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_company_auth);
        initView();
        event();
    }

    private void initView() {
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        is_serve = (ImageView) findViewById(R.id.is_serve);
        is_visible = (ImageView) findViewById(R.id.is_visible);
        is_synced = (ImageView) findViewById(R.id.is_synced);
        is_manage = (ImageView) findViewById(R.id.is_manage);
        is_schedule = (ImageView) findViewById(R.id.is_schedule);

        backdrop = (ImageView) findViewById(R.id.backdrop);
//        new ImageHelper().displayBackgroundLoading(backdrop,
//                Constant.BASE_IMAGE + MainActivity.userInfo.getData().getCover_img());
        title_bar_TvRight = (TextView) findViewById(R.id.title_bar_TvRight);
        title_bar_TvRight.setVisibility(View.VISIBLE);
        title_bar_TvRight.setText("特权说明");


        getGui();
    }

    public void getGui() {
        //        获取用戶列表
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.MEMBER_COMPANY_MEMBER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                userInfoList = new Gson().fromJson(data, GetInfoBeans.class);

                for (int j = 0; j < userInfoList.getData().size(); j++) {
                    View userListView = LayoutInflater.from(CompanyAuth.this).inflate(R.layout.my_schedule_user_item, null);
                    userListView.setTag(j);
                    CircleImageView imageItem = (CircleImageView) userListView.findViewById(R.id.imageItem);

                    new ImageHelper().displayBackgroundLoading(imageItem,
                            Constant.BASE_API + userInfoList.getData().get(j).getDefault_avatar());

                    if (userInfoList.getData().get(j).getUser_id().equals(MainActivity.userInfo.getData().getUser_id())) {
                        ((TextView) userListView.findViewById(R.id.name)).setText("当前账号");
                    } else {
                        ((TextView) userListView.findViewById(R.id.name)).setText(userInfoList.getData().get(j).getName());
                    }


                    userListView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int indexOf = (int) v.getTag();
                            clickUser(indexOf, v);

                        }
                    });
                    linearLayout.addView(userListView);
                    if (j == 0) {
                        clickUser(j, userListView);
                    }

                }


            }
        });
    }

    private void clickUser(int indexOf, View v) {
        if (lastUser == indexOf) {
            return;
        }
        if (lastUser >= 0) {
            View lastView = linearLayout.getChildAt(lastUser);
            lastView.findViewById(R.id.imageItemWrap).setBackgroundResource(R.mipmap.bg_user);
            ((TextView) lastView.findViewById(R.id.name)).setTextColor(Color.BLACK);
            nowUserId = "";
        }
        if (lastUser != indexOf) {
            lastUser = indexOf;
            View view = linearLayout.getChildAt(lastUser);
            view.findViewById(R.id.imageItemWrap).setBackgroundResource(R.mipmap.bg_user_blue);
            ((TextView) view.findViewById(R.id.name)).setTextColor(Color.WHITE);
            nowUserId = userInfoList.getData().get(indexOf).getUser_id();
        } else {
            lastUser = -1;
        }
        getAuth();
    }

    UserTypeBean authData;
    /**
     * 获取权限
     */
    private void getAuth() {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("user_id", nowUserId);
        new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_GET_AUTH, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                authData = new Gson().fromJson(data, UserTypeBean.class);
                updateAuth();
            }
        });
//        ToastShow.s("当前获取权限的用户"+nowUserId);
    }

    private void updateAuth()
    {
        if(authData.getData().getIs_serve().equals("1"))
        {
            is_serve.setBackgroundResource(R.mipmap.btn_switch_y);
        }
        else
        {
            is_serve.setBackgroundResource(R.mipmap.btn_switch_n);
        }
        if(MainActivity.userInfo.getData().getUser_id().equals(nowUserId))
        {
            is_visible.setBackgroundResource(R.mipmap.btn_switch_none);
            is_synced.setBackgroundResource(R.mipmap.btn_switch_none);
            is_manage.setBackgroundResource(R.mipmap.btn_switch_none);
            is_schedule.setBackgroundResource(R.mipmap.btn_switch_none);
            return;
        }

        if(authData.getData().getIs_visible().equals("1"))
        {
            is_visible.setBackgroundResource(R.mipmap.btn_switch_y);
        }
        else
        {
            is_visible.setBackgroundResource(R.mipmap.btn_switch_n);
        }
        if(authData.getData().getIs_synced().equals("1"))
        {
            is_synced.setBackgroundResource(R.mipmap.btn_switch_y);
        }
        else
        {
            is_synced.setBackgroundResource(R.mipmap.btn_switch_n);
        }
        if(authData.getData().getIs_manage().equals("1"))
        {
            is_manage.setBackgroundResource(R.mipmap.btn_switch_y);
        }
        else
        {
            is_manage.setBackgroundResource(R.mipmap.btn_switch_n);
        }
        if(authData.getData().getIs_schedule().equals("1"))
        {
            is_schedule.setBackgroundResource(R.mipmap.btn_switch_y);
        }
        else
        {
            is_schedule.setBackgroundResource(R.mipmap.btn_switch_n);
        }
    }



    private void event() {
//        设置权限
        is_serve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDailog.show();
                String status = authData.getData().getIs_serve().equals("1") ? "0" : "1";
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("user_id", nowUserId);
                param.setParam("is_serve", status);
                new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_SET_AUTH, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        authData.getData().setIs_serve(status);
                        updateAuth();
                    }
                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });
            }

        });

        is_visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDailog.show();
                String status = authData.getData().getIs_visible().equals("1") ? "0" : "1";
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("user_id", nowUserId);
                param.setParam("is_visible", status);
                new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_SET_AUTH, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        authData.getData().setIs_visible(status);
                        updateAuth();
                    }
                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });
            }

        });

        is_synced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDailog.show();
                String status = authData.getData().getIs_synced().equals("1") ? "0" : "1";
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("user_id", nowUserId);
                param.setParam("is_synced", status);
                new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_SET_AUTH, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        authData.getData().setIs_synced(status);
                        updateAuth();
                    }
                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });
            }

        });


        is_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDailog.show();
                String status = authData.getData().getIs_manage().equals("1") ? "0" : "1";
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("user_id", nowUserId);
                param.setParam("is_manage", status);
                new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_SET_AUTH, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        authData.getData().setIs_manage(status);
                        updateAuth();
                    }
                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });
            }

        });

        is_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDailog.show();
                String status = authData.getData().getIs_schedule().equals("1") ? "0" : "1";
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("user_id", nowUserId);
                param.setParam("is_schedule", status);
                new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_SET_AUTH, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        authData.getData().setIs_schedule(status);
                        updateAuth();
                    }
                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });
            }

        });


//        设置菜单
        title_bar_TvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(CompanyAuth.this);
                bottomInterPasswordDialog.setContentView(R.layout.dialog_auth_text);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
//                bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                bottomInterPasswordDialog.show();
                bottomInterPasswordDialog.findViewById(R.id.isPromptWrap).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomInterPasswordDialog.hide();
                    }
                });

            }
        });
    }


}
