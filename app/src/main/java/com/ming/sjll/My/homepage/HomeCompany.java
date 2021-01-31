package com.ming.sjll.My.homepage;

import android.os.Bundle;
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
import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GetInfoBeans;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.personage.PersonageOccupation;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.dialog.EditTextDialog;
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

import java.util.List;

/**
 * 主页-公司
 */
public class HomeCompany extends BaseFragment {

    public int point;
    public String userId;


    public static HomeCompany newInstance() {
        return new HomeCompany();
    }


    CustomRoundAngleImageView backdrop;
    TextView full_company_name;
    TextView short_company_name;
    EditText describe;
    RelativeLayout address;
    TextView addressTip;


    LinearLayout linearLayout;
    GetInfoBeans userInfoList;

    //    用户信息
    public GetInfoBean userInfo;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
//        EventBus.getDefault().register(this);
        setContentView(R.layout.my_homepage_company);
        initView();
    }



    private void initView() {
        ((HomepageActivity) getActivity()).setChildObjectForPosition(this,point);
        linearLayout  = (LinearLayout) findViewById(R.id.linearLayout);
        full_company_name = (TextView) findViewById(R.id.full_company_name);
        short_company_name = (TextView) findViewById(R.id.short_company_name);
        describe = (EditText) findViewById(R.id.describe);
        addressTip = (TextView) findViewById(R.id.addressTip);
        backdrop = (CustomRoundAngleImageView) findViewById(R.id.backdrop);


        updateUserUi("cover_img", userInfo.getData().getCover_img());
        updateUserUi("full_company_name", userInfo.getData().getFull_company_name());
        updateUserUi("default_avatar", userInfo.getData().getDefault_avatar());
        updateUserUi("default_avatar_rect", userInfo.getData().getDefault_avatar_rect());
        updateUserUi("short_company_name", userInfo.getData().getShort_company_name());
        updateUserUi("describe", userInfo.getData().getDescribe());
        updateUserUi("address", userInfo.getData().getAddress());

        getGui();
    }



    /**
     * 更新字符串的UI
     *
     * @param key
     * @param value
     */
    private void updateUserUi(String key, String value) {

        if (key.equals("default_avatar")) {
//            new ImageHelper().displayBackgroundLoading(squareHead,
//                    Constant.BASE_API + value);
        } else if (key.equals("default_avatar_rect")) {
//            new ImageHelper().displayBackgroundLoading(rectangleHead,
//                    Constant.BASE_API + value);
        } else if (key.equals("full_company_name")) {
            full_company_name.setText(value);
        } else if (key.equals("short_company_name")) {
            short_company_name.setText(value);
        } else if (key.equals("describe")) {
            describe.setText(value);
        } else if (key.equals("address")) {
            addressTip.setText(value);
        }
        else if (key.equals("cover_img")) {
//            new ImageHelper().displayBackgroundLoading(backdrop,
//                    Constant.BASE_IMAGE + value);
        }
    }


    public void getGui() {
        //        获取用戶列表
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.MEMBER_COMPANY_MEMBER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                userInfoList = new Gson().fromJson(data, GetInfoBeans.class);

                for (int j = 0; j < userInfoList.getData().size(); j++) {
                    View userListView = LayoutInflater.from(getActivity()).inflate(R.layout.my_schedule_user_occupation_item, null);
                    userListView.setTag(j);
                    CircleImageView imageItem = (CircleImageView) userListView.findViewById(R.id.imageItem);

                    new ImageHelper().displayBackgroundLoading(imageItem,
                            Constant.BASE_API + userInfoList.getData().get(j).getDefault_avatar());

                    ((TextView)userListView.findViewById(R.id.name)).setText(userInfoList.getData().get(j).getName());
                    ((TextView)userListView.findViewById(R.id.occupation)).setText(userInfoList.getData().get(j).getOccupation());

                    userListView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int indexOf = (int) v.getTag();
                            clickUser(indexOf,v);

                        }
                    });
                    linearLayout.addView(userListView);


                }


            }
        });
    }


    private void clickUser(int indexOf,View v)
    {
        Bundle bundle = new Bundle();
        bundle.putString("userId", userInfoList.getData().get(indexOf).getUser_id());
        Tools.jump(getActivity(), HomepageActivity.class, bundle,false);

    }






}
