package com.ming.sjll.My.personage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;

import com.ming.sjll.My.company.ShowcaseAddTwoActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.dialog.EditTextDialog;
import com.ming.sjll.base.event.SampleEditEvent;
import com.ming.sjll.base.event.UserUpdateEvent;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.project.ReleaseProjectCover;
import com.ming.sjll.toolPage.CropPicture;
import com.ming.sjll.toolPage.CropPictureConfig;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.rey.material.app.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 个人中心-个人
 */
public class Personage extends BaseFragment {

    public int point;

    public static Personage newInstance() {
        return new Personage();
    }



    ImageView squareHeadBtn,rectangleHeadBtn;
    CustomRoundAngleImageView squareHead,rectangleHead;
    RelativeLayout name;
    RelativeLayout sex;
    RelativeLayout occupation,personalized,street;
    ImageView sexTipImage;
    TextView nameTip,personalizedTip,streetTip;

//    用户信息
     GetInfoBean userInfo;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.my_personage);

        initView();
        event();
    }


    private void initView() {

        if (getParentFragment() instanceof Myfragemt) {
            ((Myfragemt) getParentFragment()).setChildObjectForPosition(this,point);
        }

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
        street = (RelativeLayout) findViewById(R.id.street);
        streetTip = (TextView) findViewById(R.id.streetTip);

        getUserInfo();
    }


    private void getUserInfo()
    {
//        HttpParamsObject param = new HttpParamsObject();
//
//        new HttpUtil().sendRequest(Constant.MEMBER_USER_CENTER, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {
//                userInfo = new Gson().fromJson(data, GetInfoBean.class);

                userInfo = MainActivity.userInfo;
                updateUserUi("name",userInfo.getData().getName());
                updateUserUi("default_avatar",userInfo.getData().getDefault_avatar());
                updateUserUi("default_avatar_rect",userInfo.getData().getDefault_avatar_rect());
                updateUserUi("sex",userInfo.getData().getSex());
                updateUserUi("payment",userInfo.getData().getOccupationInfo());
                updateUserUi("personalized",userInfo.getData().getPersonalized());
                updateUserUi("address",userInfo.getData().getProvinces()+userInfo.getData().getCity()+userInfo.getData().getCounty());
                updateUserUi("street",userInfo.getData().getStreet());
//            }
//        });
    }

    private void event() {
//        选择地址
        findViewById(R.id.address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.init(getContext());
                Intent intent = new Intent(getContext(), MyTencentMap.class);
                startActivityForResult(intent, PictureConfig.MAP_HEAD_REQUEST);
            }
        });
//      姓名
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditTextDialog editTextDialog = EditTextDialog.newInstance(nameTip.getText().toString());
                editTextDialog.setMaxLength(10);
                editTextDialog.setOnSaveListener(new EditTextDialog.setOnSaveListener(){
                    @Override
                    public void save(String s) {
                        if(s.equals(""))
                        {
                            ToastShow.s("数据不能为空");
                            return;
                        }
                        MainActivity.saveInfo("name",s);
                        editTextDialog.dismiss();
                    }
                }).show(getActivity());
            }
        });

//      性别
        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_sex_select);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
//                bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                bottomInterPasswordDialog.show();
                bottomInterPasswordDialog.findViewById(R.id.man).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.saveInfo("sex","0");
                        bottomInterPasswordDialog.hide();
                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.woman).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity.saveInfo("sex","1");
                        bottomInterPasswordDialog.hide();
                    }
                });
            }
        });

//长方形头像
        squareHeadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CropPictureConfig config = new CropPictureConfig();
                config.setSquareXY();
                config.context = v.getContext();
                config.activity = getActivity();
                config.forResult = PictureConfig.SQUARE_HEAD_REQUEST;
                new CropPicture(config);
            }
        });

        //        上传长方形头像
        rectangleHeadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CropPictureConfig config = new CropPictureConfig();
                config.setRectangleXY();
                config.context = v.getContext();
                config.activity = getActivity();
                config.forResult = PictureConfig.RECTANGLE_HEAD_REQUEST;
                new CropPicture(config);
            }
        });
//        职业
        occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                发送职业信息
                Bundle bundle = new Bundle();
                bundle.putSerializable("userInfo",userInfo);
                Tools.jump(Personage.this.getActivity(), PersonageOccupation.class, bundle, false);
            }
        });

        //        介绍
        personalized.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditTextDialog editTextDialog = EditTextDialog.newInstance(personalizedTip.getText().toString());
                editTextDialog.setMaxLength(200);
                editTextDialog.setOnSaveListener(new EditTextDialog.setOnSaveListener(){
                    @Override
                    public void save(String s) {
                        if(s.equals(""))
                        {
                            ToastShow.s("数据不能为空");
                            return;
                        }
                        if(s.length() > 25)
                        {
                            ToastShow.s("最多只能输入25个文字");
                            return;
                        }

                        MainActivity.saveInfo("personalized",s);
                        editTextDialog.dismiss();
                    }
                }).show(getActivity());
            }
        });

//        地址
        streetTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EditTextDialog editTextDialog = EditTextDialog.newInstance(streetTip.getText().toString());
                editTextDialog.setMaxLength(200);
                editTextDialog.setOnSaveListener(new EditTextDialog.setOnSaveListener(){
                    @Override
                    public void save(String s) {
                        if(s.equals(""))
                        {
                            ToastShow.s("数据不能为空");
                            return;
                        }
                        MainActivity.saveInfo("street",s);
                        editTextDialog.dismiss();
                    }
                }).show(getActivity());
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "收到回到");
        Log.e("接受", "111111111111111111111111111111111");
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.RECTANGLE_HEAD_REQUEST) {
                loadingDailog.showLoading();
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject param = new HttpParamsObject();
                HttpUtil.uplaodFile(selectList.get(0).getPath(), param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            JSONObject json = new JSONObject(data);
                            String path = (String)json.get("data");
                            MainActivity.saveInfo("default_avatar_rect",path);
//                            updateBgPath(path);
                        }catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }
                    }
                    @Override
                    public void onProgress(int currentProgress,long currentSize,long totalSize)
                    {
                        loadingDailog.setMsg(currentProgress+"%");

                        Log.e("进度：","currentProgress"+currentProgress+",,currentSize"+currentSize+",,totalSize"+totalSize);
                    }
                    @Override
                    public void onFinal()
                    {
                        loadingDailog.hide();
                    }

                });


            }
            else if(requestCode == PictureConfig.SQUARE_HEAD_REQUEST){
                loadingDailog.showLoading();
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject param = new HttpParamsObject();
                HttpUtil.uplaodFile(selectList.get(0).getPath(), param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            JSONObject json = new JSONObject(data);
                            String path = (String)json.get("data");
                            MainActivity.saveInfo("default_avatar",path);
//                            updateBgPath(path);
                        }catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }
                    }
                    @Override
                    public void onProgress(int currentProgress,long currentSize,long totalSize)
                    {
                        loadingDailog.setMsg(currentProgress+"%");
                        Log.e("进度：","currentProgress"+currentProgress+",,currentSize"+currentSize+",,totalSize"+totalSize);
                    }
                    @Override
                    public void onFinal()
                    {
                        loadingDailog.hide();
                    }

                });
            }
            if (requestCode == PictureConfig.MAP_HEAD_REQUEST) {
                double longitude = data.getDoubleExtra("longitude", 0);
                double latitude = data.getDoubleExtra("latitude", 0);
                String area_code = data.getStringExtra("area_code");
                String title = data.getStringExtra("title");
                String adname = data.getStringExtra("address");
                String province = data.getStringExtra("province");
                String city = data.getStringExtra("city");
                String district = data.getStringExtra("district");

                MainActivity.saveInfo("area_code",area_code);
                MainActivity.saveInfo("provinces",province);
                MainActivity.saveInfo("city",city);
                MainActivity.saveInfo("county",district);
                MainActivity.saveInfo("address_title",title);
                MainActivity.saveInfo("address",adname);
                MainActivity.saveInfo("meridian",longitude+"");
                MainActivity.saveInfo("weft",latitude+"");

                String  s = province+city+district;

                ((TextView)findViewById(R.id.addressTip)).setText(s);


            }


        }
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
        updateUserUi(key,objectValue);
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
            String ss = value.length() + "/25";
            ((TextView)findViewById(R.id.personalizedCount)).setText(ss);
            personalizedTip.setText(value);
        }
        else if(key.equals("address"))
        {
            ((TextView)findViewById(R.id.addressTip)).setText(value);
        }
        else if(key.equals("street")) {
            streetTip.setText(value);
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
