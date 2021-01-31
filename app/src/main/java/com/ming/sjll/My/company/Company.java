package com.ming.sjll.My.company;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
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
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.CompanyMemberAdapter;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.My.personage.PersonageOccupation;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.dialog.EditTextDialog;
import com.ming.sjll.base.event.UserUpdateEvent;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
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
 * 个人中心-公司
 */
public class Company extends BaseFragment {


    public int point;
//    LinearLayout linearLayout,editClerk;
    GetInfoBeans userInfoList;
//    RelativeLayout companyInfo,auth;


    RecyclerView listView;
    CompanyMemberAdapter listAdapter;



    public static Company newInstance() {
        return new Company();
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);

        setContentView(R.layout.my_company);
        EventBus.getDefault().register(this);
        initView();
        event();

    }

    private  void event()
    {

        findViewById(R.id.openList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getActivity());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_member_list);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();
                RecyclerView bRecyclerList = (RecyclerView)bottomInterPasswordDialog.findViewById(R.id.recyclerList);
                bRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
                listAdapter = new CompanyMemberAdapter(userInfoList.getData());
                bRecyclerList.setAdapter(listAdapter);

                bottomInterPasswordDialog.findViewById(R.id.openList).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tools.jump(getActivity(), CompanyMember.class,  false);
                        bottomInterPasswordDialog.hide();
                    }
                });



            }
        });

////        职员
//        editClerk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Tools.jump(getActivity(), CompanyMember.class,  false);
//            }
//        });
////        公司信息
        findViewById(R.id.companyInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tools.jump(getActivity(), CompanyInfo.class,  false);
            }
        });
////        人员权限
//        auth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Tools.jump(getActivity(), CompanyAuth.class,  false);
//            }
//        });
    }

    private void initView() {
        if (getParentFragment() instanceof Myfragemt) {
            ((Myfragemt) getParentFragment()).setChildObjectForPosition(this,point);
        }
        listView = (RecyclerView) findViewById(R.id.recyclerList);


//        linearLayout  = (LinearLayout) findViewById(R.id.linearLayout);
//        companyInfo  = (RelativeLayout) findViewById(R.id.companyInfo);
//        editClerk  = (LinearLayout) findViewById(R.id.editClerk);
//        auth  = (RelativeLayout) findViewById(R.id.auth);
        initInfo();
        getGui();
    }

    private void initInfo()
    {
        updateUserUi("","");
    }


    /**
     * 用户信息更新
     *
     * @param e
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserUpdateEvent e) {
        Object objectValue = e.objectValue;
        String value = e.value;
        String key = e.updateKey;
        updateUserUi(key,value);
    }

    /**
     * 更新字符串的UI
     *
     * @param key
     * @param value
     */
    private void updateUserUi(String key, String value) {

        if(MainActivity.userInfo.getData().getFull_company_name().equals("") )
        {
            findViewById(R.id.noneData).setVisibility(View.VISIBLE);
            findViewById(R.id.infoWrap).setVisibility(View.GONE);
        }
        else
        {
            findViewById(R.id.noneData).setVisibility(View.GONE);
            findViewById(R.id.infoWrap).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.company_name)).setText(MainActivity.userInfo.getData().getFull_company_name());
            String s =  MainActivity.userInfo.getData().getProvinces() + "-" + MainActivity.userInfo.getData().getCity() + "-"  + MainActivity.userInfo.getData().getCounty() ;
            ((TextView)findViewById(R.id.address)).setText(s);
            ((TextView)findViewById(R.id.addressTitle)).setText(MainActivity.userInfo.getData().getStreet());
        }



//        if (key.equals("full_company_name")) {
//            full_company_name.setText(value);
//        } else if (key.equals("address")) {
//            addressTip.setText(value);
//        }
//        else if(key.equals("title"))
//        {
//
//        }
//        else
//        {
//
//        }

    }

    public void getGui() {
        //        获取用戶列表
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.MEMBER_COMPANY_MEMBER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                userInfoList = new Gson().fromJson(data, GetInfoBeans.class);

                listView.setLayoutManager(new LinearLayoutManager(getContext()));
                listAdapter = new CompanyMemberAdapter(userInfoList.getData());
                listView.setAdapter(listAdapter);
//
//                for (int j = 0; j < userInfoList.getData().size(); j++) {
//                    View userListView = LayoutInflater.from(getActivity()).inflate(R.layout.my_schedule_user_occupation_item, null);
//                    userListView.setTag(j);
//                    CircleImageView imageItem = (CircleImageView) userListView.findViewById(R.id.imageItem);
//
//                    new ImageHelper().displayBackgroundLoading(imageItem,
//                            Constant.BASE_API + userInfoList.getData().get(j).getDefault_avatar());
//
//                    ((TextView)userListView.findViewById(R.id.name)).setText(userInfoList.getData().get(j).getName());
//                    ((TextView)userListView.findViewById(R.id.occupation)).setText(userInfoList.getData().get(j).getOccupation());
//
//                    userListView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            int indexOf = (int) v.getTag();
//                            clickUser(indexOf,v);
//
//                        }
//                    });
//                    linearLayout.addView(userListView);
//
//
//                }


            }
        });
    }

    private void clickUser(int indexOf)
    {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userInfoList.getData().get(indexOf).getUser_id());
        Tools.jump(getActivity(), HomepageActivity.class, bundle,false);
    }




}
