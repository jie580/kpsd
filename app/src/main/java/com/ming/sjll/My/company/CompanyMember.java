package com.ming.sjll.My.company;

import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBeans;
import com.ming.sjll.Bean.UserTypeBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.CompanyClerkListAdapter;
import com.ming.sjll.My.adapter.CompanyMemberAdapter;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.search.CompanyMemberFragment;
import com.ming.sjll.ui.CircleImageView;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.rey.material.app.BottomSheetDialog;

import java.util.LinkedList;

/**
 * 个人中心-公司-成员管理
 */
public class CompanyMember extends BaseActivity {


    public int point;
    CustomRoundAngleImageView backdrop;
    RecyclerView listView;
    LinearLayout searchWrap;
    GetInfoBeans dataList;
    CompanyMemberAdapter listAdapter;

    public static CompanyMember newInstance() {
        return new CompanyMember();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_company_member);

        initView();
        event();

    }


    private void event() {
//        跳转搜索
        searchWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CompanyMemberFragment.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("comeType",2);
                intent.putExtra("comeType", 2);
                startActivityForResult(intent, 1); // 这里请求码唯一就可以，我这里是
//                Tools.jump(CompanyClerk.this, CompanyMemberFragment.class,bundle, false);
            }
        });
    }
    private void initView() {
        backdrop = (CustomRoundAngleImageView) findViewById(R.id.backdrop);
        new ImageHelper().displayBackgroundLoading(backdrop,
                Constant.BASE_IMAGE + MainActivity.userInfo.getData().getCover_img());

        listView = (RecyclerView) findViewById(R.id.recyclerList);

        searchWrap = (LinearLayout) findViewById(R.id.searchWrap);

        getList();
    }


    private void getList() {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_MEMBER_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, GetInfoBeans.class);
//                for (int i = 0; i < 20; i++) {
//                    dataList.getData().add(dataList.getData().get(0));
//                }
                listView.setLayoutManager(new LinearLayoutManager(getContext()));
                listAdapter = new CompanyMemberAdapter(dataList.getData());
                listView.setAdapter(listAdapter);
//                selectData = new LinkedList<>();

                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId())
                        {
                            case R.id.headImage:
                                Bundle bundle = new Bundle();
                                bundle.putString("userId", dataList.getData().get(position).getUser_id());
                                Tools.jump(getActivity(), HomepageActivity.class, bundle,false);
                                break;
                            case R.id.wrap:

                                if(!dataList.getData().get(position).getStatus().equals("1"))
                                {
                                    BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getActivity());
                                    bottomInterPasswordDialog.setContentView(R.layout.dialog_member_auth);
                                    bottomInterPasswordDialog.inDuration(300);
                                    bottomInterPasswordDialog.outDuration(200);
                                    bottomInterPasswordDialog.show();
                                    bottomInterPasswordDialog.findViewById(R.id.initHide).setVisibility(View.GONE);


                                    bottomInterPasswordDialog.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            DialogTextTip dialog = new DialogTextTip(getContext(), "是否确定移除改用户");
                                            dialog.show(new CommonCallback() {
                                                @Override
                                                public void onNext() {
                                                    super.onNext();
                                                    loadingDailog.show();
                                                    HttpParamsObject param = new HttpParamsObject();
                                                    param.setParam("apply_id", dataList.getData().get(position).getUser_id());
                                                    new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_DEL_MEMBER, param, new CommonCallback() {
                                                        @Override
                                                        public void onSuccessJson(String data, int code) {
                                                            bottomInterPasswordDialog.hide();
                                                            getList();
                                                        }

                                                        @Override
                                                        public void onFinal() {
                                                            super.onFinal();
                                                            loadingDailog.show();
                                                        }
                                                    });

                                                }
                                            });


                                        }
                                    });

                                    bottomInterPasswordDialog.findViewById(R.id.question).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            BottomSheetDialog bottomInterPasswordDialog2 = new BottomSheetDialog(getContext());
                                            bottomInterPasswordDialog2.setContentView(R.layout.dialog_auth_text);
                                            bottomInterPasswordDialog2.inDuration(300);
                                            bottomInterPasswordDialog2.outDuration(200);
//                bottomInterPasswordDialog2.inInterpolator(new BounceInterpolator());
//                bottomInterPasswordDialog2.outInterpolator(new AnticipateInterpolator());
                                            bottomInterPasswordDialog2.show();
                                            bottomInterPasswordDialog2.findViewById(R.id.isPromptWrap).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    bottomInterPasswordDialog2.hide();
                                                }
                                            });

                                        }
                                    });

                                }
                                else
                                {
                                    getAuth(dataList.getData().get(position).getUser_id());
                                }


                                break;
                        }
                    }
                });
            }
        });
    }


    /**
     * 获取权限
     */
    private void getAuth(String id) {
        loadingDailog.show();
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("user_id", id);
        new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_GET_AUTH, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                UserTypeBean dataTemp = new Gson().fromJson(data, UserTypeBean.class);
                nowUserId = id;
                authData = dataTemp;
                openAuth();
            }

            @Override
            public void onFinal() {
                super.onFinal();
                loadingDailog.hide();
            }
        });


//        ToastShow.s("当前获取权限的用户"+nowUserId);
    }

    ImageView is_serve ;
    ImageView is_visible ;
    ImageView is_synced ;
    ImageView is_manage ;
    ImageView is_schedule ;
    String nowUserId;
    UserTypeBean authData;

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
    private void openAuth()
    {
        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getActivity());
        bottomInterPasswordDialog.setContentView(R.layout.dialog_member_auth);
        bottomInterPasswordDialog.inDuration(300);
        bottomInterPasswordDialog.outDuration(200);
        bottomInterPasswordDialog.show();

        is_serve = (ImageView)bottomInterPasswordDialog.findViewById(R.id.is_serve);
        is_visible = (ImageView)bottomInterPasswordDialog.findViewById(R.id.is_visible);
        is_synced = (ImageView)bottomInterPasswordDialog.findViewById(R.id.is_synced);
        is_manage = (ImageView)bottomInterPasswordDialog.findViewById(R.id.is_manage);
        is_schedule = (ImageView)bottomInterPasswordDialog.findViewById(R.id.is_schedule);

        TextView delete = (TextView)bottomInterPasswordDialog.findViewById(R.id.delete);
        updateAuth();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DialogTextTip dialog = new DialogTextTip(getContext(), "是否确定移除改用户");
                dialog.show(new CommonCallback() {
                    @Override
                    public void onNext() {
                        super.onNext();
                        loadingDailog.show();
                        HttpParamsObject param = new HttpParamsObject();
                        param.setParam("apply_id", nowUserId);
                        new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_DEL_MEMBER, param, new CommonCallback() {
                            @Override
                            public void onSuccessJson(String data, int code) {
                                bottomInterPasswordDialog.hide();
                                getList();
                            }

                            @Override
                            public void onFinal() {
                                super.onFinal();
                                loadingDailog.show();
                            }
                        });


                    }
                });


            }
        });


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

    }






}
