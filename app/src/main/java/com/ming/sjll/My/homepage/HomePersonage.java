package com.ming.sjll.My.homepage;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GoodsListItem;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.GoodsListAdapter;
import com.ming.sjll.My.adapter.PersonageOccupationListAdapter;
import com.ming.sjll.My.common.Setting;
import com.ming.sjll.My.personage.Personage;
import com.ming.sjll.My.personage.PersonageOccupation;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.dialog.EditTextDialog;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.toolPage.CropPicture;
import com.ming.sjll.toolPage.CropPictureConfig;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.rey.material.app.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 个人中心-收藏-商品
 */
public class HomePersonage extends BaseFragment {

    public int point;
    public String userId;


    public static HomePersonage newInstance() {
        return new HomePersonage();
    }



    ImageView squareHeadBtn,rectangleHeadBtn;
    CustomRoundAngleImageView squareHead,rectangleHead;
    RelativeLayout name;
    RelativeLayout sex;
    RelativeLayout occupation,personalized;
    ImageView sexTipImage;
    TextView nameTip,personalizedTip,occupationTip;
    private RecyclerView listView;


    //    用户信息
    public GetInfoBean userInfo;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
//        EventBus.getDefault().register(this);
        setContentView(R.layout.my_homepage_personage);

        initView();
        event();
    }


    private void initView() {

        ((HomepageActivity)getActivity()).setChildObjectForPosition(this,point);

        listView = (RecyclerView) findViewById(R.id.listView);
        squareHead = (CustomRoundAngleImageView)findViewById(R.id.squareHead);
        rectangleHead = (CustomRoundAngleImageView)findViewById(R.id.rectangleHead);
//        上传正方形头像
        squareHeadBtn = (ImageView)findViewById(R.id.squareHeadBtn);
        rectangleHeadBtn  = (ImageView) findViewById(R.id.rectangleHeadBtn);

        name = (RelativeLayout)findViewById(R.id.name);
        sex = (RelativeLayout)findViewById(R.id.sex);
        occupation = (RelativeLayout)findViewById(R.id.occupation);
        sexTipImage = (ImageView)findViewById(R.id.sexTipImage);
        nameTip = (TextView)findViewById(R.id.nameTip);
        personalized = (RelativeLayout) findViewById(R.id.personalized);
        personalizedTip = (TextView) findViewById(R.id.personalizedTip);

        occupationTip  = (TextView) findViewById(R.id.occupationTip);
        squareHeadBtn.setVisibility(View.GONE);
        rectangleHeadBtn.setVisibility(View.GONE);
        getUserInfo();
        initUserOccupationList();
    }

    /**
     * 初始化用户职业
     */
    private void initUserOccupationList() {
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        PersonageOccupationListAdapter personageOccupationListAdapter = new PersonageOccupationListAdapter(userInfo.getData().getOccupationInfo());
        personageOccupationListAdapter.canEdit = false;
        listView.setAdapter(personageOccupationListAdapter);

    }

        private void getUserInfo()
    {
//        HttpParamsObject param = new HttpParamsObject();
//
//        new HttpUtil().sendRequest(Constant.MEMBER_USER_CENTER, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {
//                userInfo = new Gson().fromJson(data, GetInfoBean.class);

        updateUserUi("name",userInfo.getData().getName());
        updateUserUi("default_avatar",userInfo.getData().getDefault_avatar());
        updateUserUi("default_avatar_rect",userInfo.getData().getDefault_avatar_rect());
        updateUserUi("sex",userInfo.getData().getSex());
        updateUserUi("payment",userInfo.getData().getOccupationInfo());
        updateUserUi("personalized",userInfo.getData().getPersonalized());
//            }
//        });
    }

    private void event() {
//      姓名
//        name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                EditTextDialog editTextDialog = EditTextDialog.newInstance(nameTip.getText().toString());
//                editTextDialog.setMaxLength(10);
//                editTextDialog.setOnSaveListener(new EditTextDialog.setOnSaveListener(){
//                    @Override
//                    public void save(String s) {
//                        if(s.equals(""))
//                        {
//                            ToastShow.s("数据不能为空");
//                            return;
//                        }
//                        MainActivity.saveInfo("name",s);
//                        editTextDialog.dismiss();
//                    }
//                }).show(getActivity());
//            }
//        });

//      性别
//        sex.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
//                bottomInterPasswordDialog.setContentView(R.layout.dialog_sex_select);
//                bottomInterPasswordDialog.inDuration(300);
//                bottomInterPasswordDialog.outDuration(200);
//                bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
//                bottomInterPasswordDialog.show();
//                bottomInterPasswordDialog.findViewById(R.id.man).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        MainActivity.saveInfo("sex","0");
//                        bottomInterPasswordDialog.hide();
//                    }
//                });
//                bottomInterPasswordDialog.findViewById(R.id.woman).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        MainActivity.saveInfo("sex","1");
//                        bottomInterPasswordDialog.hide();
//                    }
//                });
//            }
//        });

//长方形头像
//        squareHeadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                CropPictureConfig config = new CropPictureConfig();
//                config.setSquareXY();
//                config.context = v.getContext();
//                config.activity = getActivity();
//                config.forResult = PictureConfig.SQUARE_HEAD_REQUEST;
//                new CropPicture(config);
//            }
//        });

        //        上传长方形头像
//        rectangleHeadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                CropPictureConfig config = new CropPictureConfig();
//                config.setRectangleXY();
//                config.context = v.getContext();
//                config.activity = getActivity();
//                config.forResult = PictureConfig.RECTANGLE_HEAD_REQUEST;
//                new CropPicture(config);
//            }
//        });
//        职业
        occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogTextTip dialog = new DialogTextTip(getContext(), occupationTip.getText().toString());
                dialog.hideBtns();
                dialog.show();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("userInfo",userInfo);
//                Tools.jump(HomePersonage.this.getActivity(), PersonageOccupation.class, bundle, false);
            }
        });

//        介绍
        personalized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                DialogTextTip dialog = new DialogTextTip(getContext(), personalizedTip.getText().toString());
                dialog.hideBtns();
                dialog.show();
            }
        });
    }



    /**
     * 更新字符串的UI
     * @param key
     * @param value
     */
    private void updateUserUi(String key ,String value )
    {

        if(key.equals("default_avatar"))
        {
            new ImageHelper().displayBackgroundLoading( squareHead,
                    Constant.BASE_IMAGE + value);
        }
        else if(key.equals("default_avatar_rect"))
        {
            new ImageHelper().displayBackgroundLoading( rectangleHead,
                    Constant.BASE_IMAGE + value);
        }
        else if(key.equals("name"))
        {
            nameTip.setText(value);
        }
        else if(key.equals("sex"))
        {
            if(value.equals("0") )
            {
                sexTipImage.setBackgroundResource(R.mipmap.ic_man);
            }
            else
            {
                sexTipImage.setBackgroundResource(R.mipmap.ic_woman);
            }
        }
        else if(key.equals("personalized"))
        {
            personalizedTip.setText(value);
        }
    }

    private void updateUserUi(String key ,Object value )
    {
        if(key.equals("payment"))
        {
            String str = "";

            List<GetInfoBean.DataBean.occupationInfoBean> dataTemp = (List<GetInfoBean.DataBean.occupationInfoBean>)value;
            for (int i = 0; i < dataTemp.size(); i++) {
                str += dataTemp.get(i).getOccupation()+",";
            }
            if(!str.equals(""))
            {
                str = str.substring(0, str.length()-1);
            }
            ((TextView)findViewById(R.id.occupationTip)).setText(str);

        }
    }



}
