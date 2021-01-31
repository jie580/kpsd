package com.ming.sjll.My.company;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
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
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.personage.PersonageOccupation;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.dialog.EditTextDialog;
import com.ming.sjll.base.event.UserUpdateEvent;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.map.MapMainActivity;
import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.project.ReleaseProjectTabGenral;
import com.ming.sjll.toolPage.CropPicture;
import com.ming.sjll.toolPage.CropPictureConfig;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.rey.material.app.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.List;

/**
 * 个人中心-公司信息
 */
public class CompanyInfo extends BaseActivity {



    HttpParamsObject commParam;


    ImageView backdrop,licenseImg,noneDataLicense;
    ImageView squareHeadBtn, rectangleHeadBtn;
    CustomRoundAngleImageView squareHead, rectangleHead;
    EditText full_company_name;
    EditText short_company_name,street;
    EditText describe;
    LinearLayout address,uploadLicense;
    TextView addressTip;

    TextView title_bar_IvRight;


    //    用户信息
    static GetInfoBean userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        setContentView(R.layout.my_company_info);
        initView();
        event();
    }


    private void initView() {

        title_bar_IvRight = (TextView) findViewById(R.id.submit);
        squareHead = (CustomRoundAngleImageView) findViewById(R.id.squareHead);
        rectangleHead = (CustomRoundAngleImageView) findViewById(R.id.rectangleHead);
//        上传正方形头像
        squareHeadBtn = (ImageView) findViewById(R.id.squareHeadBtn);
        rectangleHeadBtn = (ImageView) findViewById(R.id.rectangleHeadBtn);

        full_company_name = (EditText) findViewById(R.id.full_company_name);
        short_company_name = (EditText) findViewById(R.id.short_company_name);
        describe = (EditText) findViewById(R.id.describe);
        address = (LinearLayout) findViewById(R.id.address);
        uploadLicense = (LinearLayout) findViewById(R.id.uploadLicense);
        addressTip = (TextView) findViewById(R.id.addressTip);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        licenseImg = (ImageView) findViewById(R.id.licenseImg);
        noneDataLicense = (ImageView) findViewById(R.id.noneDataLicense);
        street = (EditText) findViewById(R.id.street);

//        title_bar_IvRight.setVisibility(View.VISIBLE);
//        title_bar_IvRight.setBackground(getResources().getDrawable(R.mipmap.btn_save_top3));
        commParam = new HttpParamsObject();
        getUserInfo();
    }


    private void getUserInfo() {
//        HttpParamsObject param = new HttpParamsObject();
//
//        new HttpUtil().sendRequest(Constant.MEMBER_USER_CENTER, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {
        userInfo = MainActivity.userInfo;

        updateUserUi("cover_img", userInfo.getData().getCover_img());
        updateUserUi("full_company_name", userInfo.getData().getFull_company_name());
        updateUserUi("default_avatar", userInfo.getData().getDefault_avatar());
        updateUserUi("default_avatar_rect", userInfo.getData().getDefault_avatar_rect());
        updateUserUi("short_company_name", userInfo.getData().getShort_company_name());
        updateUserUi("describe", userInfo.getData().getDescribe());
        updateUserUi("address", userInfo.getData().getAddress());
        updateUserUi("business_license", userInfo.getData().getBusiness_license());

        commParam.setParam("default_avatar", userInfo.getData().getDefault_avatar());
        commParam.setParam("default_avatar_rect", userInfo.getData().getDefault_avatar_rect());
//            }
//        });
    }

    private void event() {

//        保存
        title_bar_IvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commParam.setParam("full_company_name",full_company_name.getText().toString());
                commParam.setParam("short_company_name",short_company_name.getText().toString());
                commParam.setParam("describe",describe.getText().toString());
                commParam.setParam("street",street.getText().toString());
//                commParam.setParam("address",addressTip.getText().toString());
                new HttpUtil().sendRequest(Constant.MEMBER_SAVE_USER_INFO, commParam, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {

                        MainActivity.updateUserInfo("full_company_name", commParam.getStringMapParam("full_company_name"));
                        MainActivity.updateUserInfo("default_avatar", commParam.getStringMapParam("default_avatar"));
                        MainActivity.updateUserInfo("default_avatar_rect", commParam.getStringMapParam("default_avatar_rect"));
                        MainActivity.updateUserInfo("short_company_name", commParam.getStringMapParam("short_company_name"));
                        MainActivity.updateUserInfo("describe", commParam.getStringMapParam("describe"));
                        MainActivity.updateUserInfo("address", commParam.getStringMapParam("address"));
                        MainActivity.updateUserInfo("license", commParam.getStringMapParam("license"));
                        MainActivity.updateUserInfo("street", commParam.getStringMapParam("street"));

                        if(!commParam.getStringMapParam("area_code").equals(""))
                        {
                            MainActivity.updateUserInfo("area_code",commParam.getStringMapParam("area_code"));
                            MainActivity.updateUserInfo("provinces",commParam.getStringMapParam("province"));
                            MainActivity.updateUserInfo("city",commParam.getStringMapParam("city"));
                            MainActivity.updateUserInfo("county",commParam.getStringMapParam("district"));
                            MainActivity.updateUserInfo("address_title",commParam.getStringMapParam("title"));
                            MainActivity.updateUserInfo("address",commParam.getStringMapParam("adname"));
                            MainActivity.updateUserInfo("meridian",commParam.getStringMapParam("longitude"));
                            MainActivity.updateUserInfo("weft",commParam.getStringMapParam("latitude"));
                        }


                        ToastShow.s("保存成功");
//                        updateUserInfo(key, value);

//                        if (cb != null) {
//                            cb.onSuccessJson(data, code);
//                        }
                    }
                });

            }
        });


//        地图选择
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.init(getContext());
                Intent intent = new Intent(getContext(), MyTencentMap.class);
                startActivityForResult(intent, PictureConfig.MAP_HEAD_REQUEST);
            }
        });


//        上传正方形头像

        squareHeadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropPictureConfig config = new CropPictureConfig();
                config.setSquareXY();
                config.context = v.getContext();
                config.activity = CompanyInfo.this;
                config.forResult = PictureConfig.SQUARE_HEAD_REQUEST;
                new CropPicture(config);
            }
        });

        //        上传营业执照

        uploadLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropPictureConfig config = new CropPictureConfig();
                config.context = v.getContext();
                config.activity = CompanyInfo.this;
                config.forResult = PictureConfig.LICENSE;
                new CropPicture(config);
            }
        });

        //        上传长方形头像
        rectangleHeadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropPictureConfig config = new CropPictureConfig();
                config.setRectangleXY();
                config.context = v.getContext();
                config.activity = CompanyInfo.this;
                config.forResult = PictureConfig.RECTANGLE_HEAD_REQUEST;
                new CropPicture(config);
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
                            String path = (String) json.get("data");
//                            MainActivity.saveInfo("default_avatar_rect", path);
                            updateUserUi("default_avatar_rect", path);
                            commParam.setParam("default_avatar_rect", path);
//                            updateBgPath(path);
                        } catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }
                    }

                    @Override
                    public void onProgress(int currentProgress, long currentSize, long totalSize) {
                        loadingDailog.setMsg(currentProgress + "%");

                        Log.e("进度：", "currentProgress" + currentProgress + ",,currentSize" + currentSize + ",,totalSize" + totalSize);
                    }

                    @Override
                    public void onFinal() {
                        loadingDailog.hide();
                    }

                });


            } else if (requestCode == PictureConfig.SQUARE_HEAD_REQUEST) {
                loadingDailog.showLoading();
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject param = new HttpParamsObject();
                HttpUtil.uplaodFile(selectList.get(0).getPath(), param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            JSONObject json = new JSONObject(data);
                            String path = (String) json.get("data");
//                            MainActivity.saveInfo("default_avatar", path);
                            updateUserUi("default_avatar", path);
                            commParam.setParam("default_avatar", path);
//                            updateBgPath(path);
                        } catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }
                    }

                    @Override
                    public void onProgress(int currentProgress, long currentSize, long totalSize) {
                        loadingDailog.setMsg(currentProgress + "%");
                        Log.e("进度：", "currentProgress" + currentProgress + ",,currentSize" + currentSize + ",,totalSize" + totalSize);
                    }

                    @Override
                    public void onFinal() {
                        loadingDailog.hide();
                    }

                });
            }
            else if (requestCode == PictureConfig.LICENSE) {
                loadingDailog.showLoading();
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject param = new HttpParamsObject();
                HttpUtil.uplaodFile(selectList.get(0).getPath(), param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            JSONObject json = new JSONObject(data);
                            String path = (String) json.get("data");
//                            MainActivity.saveInfo("default_avatar", path);
                            updateUserUi("business_license", path);
                            commParam.setParam("business_license", path);
//                            updateBgPath(path);
                        } catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }
                    }

                    @Override
                    public void onProgress(int currentProgress, long currentSize, long totalSize) {
                        loadingDailog.setMsg(currentProgress + "%");
                        Log.e("进度：", "currentProgress" + currentProgress + ",,currentSize" + currentSize + ",,totalSize" + totalSize);
                    }

                    @Override
                    public void onFinal() {
                        loadingDailog.hide();
                    }

                });
            }
            else if (requestCode == PictureConfig.MAP_HEAD_REQUEST) {
                double longitude = data.getDoubleExtra("longitude", 0);
                double latitude = data.getDoubleExtra("latitude", 0);
                String area_code = data.getStringExtra("area_code");
                String title = data.getStringExtra("title");
                String adname = data.getStringExtra("address");
                String province = data.getStringExtra("province");
                String city = data.getStringExtra("city");
                String district = data.getStringExtra("district");

                commParam.setParam("area_code",area_code);
                commParam.setParam("provinces",province);
                commParam.setParam("city",city);
                commParam.setParam("county",district);
                commParam.setParam("address_title",title);
                commParam.setParam("address",adname);
                commParam.setParam("meridian",longitude+"");
                commParam.setParam("weft",latitude+"");




                String  s = province+city+district;

                ((TextView)findViewById(R.id.addressTip)).setText(s);


            }

        }
    }

    /**
     * 更新信息到服务器
     *
     * @param key
     * @param value
     */
//    public static void saveInfo(String key, String value, CommonCallback cb) {
//        HttpParamsObject param = new HttpParamsObject();
//        param.setParam(key, value);
//        new HttpUtil().sendRequest(Constant.MEMBER_SAVE_USER_INFO, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {
//                updateUserInfo(key, value);
//                if (cb != null) {
//                    cb.onSuccessJson(data, code);
//                }
//            }
//        });
//    }
//
//    public static void saveInfo(String key, String value) {
//        saveInfo(key, value, null);
//    }


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

        updateUserUi(key, value);

    }


    /**
     * 更新字符串的UI
     *
     * @param key
     * @param value
     */
    private void updateUserUi(String key, String value) {

        if (key.equals("default_avatar")) {
            new ImageHelper().displayBackgroundLoading(squareHead,
                    Constant.BASE_API + value);
        } else if (key.equals("default_avatar_rect")) {
            new ImageHelper().displayBackgroundLoading(rectangleHead,
                    Constant.BASE_API + value);
        } else if (key.equals("full_company_name")) {
            full_company_name.setText(value);
        } else if (key.equals("short_company_name")) {
            short_company_name.setText(value);
        } else if (key.equals("describe")) {
            describe.setText(value);
        } else if (key.equals("address")) {
            addressTip.setText(value);
        }
        else if(key.equals("street"))
        {
            street.setText(value);
        }
        else if(key.equals("business_license"))
        {
            if(value == null || value.equals(""))
            {
                noneDataLicense.setVisibility(View.VISIBLE);
                licenseImg.setVisibility(View.GONE);
            }
            else
            {
                noneDataLicense.setVisibility(View.GONE);
                licenseImg.setVisibility(View.VISIBLE);
                new ImageHelper().displayBackgroundLoading(licenseImg,
                        Constant.BASE_API + value);
            }

        }
        else if (key.equals("cover_img")) {
//            new ImageHelper().displayBackgroundLoading(backdrop,
//                    Constant.BASE_IMAGE + value);
        }
    }


    /**
     * 事件分发
     *
     * @param key
     * @param value
     */
    public static void updateUserInfo(String key, String value) {
        EventBus.getDefault().post(new UserUpdateEvent(key, value));
    }

//    public static void updateUserInfo(String key, List<Object> value)
//    {
//        EventBus.getDefault().post(new UserUpdateEvent(key,value));
//    }


}
