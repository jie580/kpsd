package com.ming.sjll.project;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;

import android.widget.EditText;

import android.widget.TextView;


import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.My.dialog.OtherEditorDialog;
import com.ming.sjll.R;

import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.map.tencent.MyTencentMap;

/**
 * 发布项目-概况
 */
public class ReleaseProjectTabGenral extends BaseFragment {


    HttpParamsObject commParam;
    boolean isLook;
    public static ReleaseProjectTabGenral newInstance(boolean isLook) {
        Bundle args = new Bundle();
        ReleaseProjectTabGenral fragment = new ReleaseProjectTabGenral();
        fragment.setArguments(args);
        fragment.isLook = isLook;
        return fragment;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

        super.onCreateView(savedInstanceState);
        setContentView(R.layout.project_release_project_tab_general);
        initView();
        event();
    }

    public void event()
    {
//        背景选择
        findViewById(R.id.backgroundImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLook)
                {
                    return;
                }
//                跳转上传背景页面
                Bundle bundle = new Bundle();
                Log.e("背景图","背景图"+ commParam.getStringMapParam("backgroundImage"));
                Log.e("backgroundImageURl",",,,,,,"+ commParam.getUrlParam());
//                bundle.putString("backgroundImage", param.getStringMapParam("backgroundImage"));
                Intent intent = new Intent(getActivity(), ReleaseProjectCover.class);
                bundle.putString("backgroundImage", commParam.getStringMapParam("backgroundImage"));
                //1 其实就是 requestCode 请求码 传入了唯一值 1
                intent.putExtras(bundle);
                startActivityForResult(intent,44);
//                startActivityForResult(intent,33);
//                Tools.jump(getActivity(), ReleaseProjectCover.class, bundle, false);
            }
        });
//        地图选择
        findViewById(R.id.address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isLook)
                {
                    return;
                }
                AppUtils.init(getActivity());
                Intent intent = new Intent(getActivity(), MyTencentMap.class);
                startActivityForResult(intent, PictureConfig.MAP_HEAD_REQUEST);

//                AppUtils.init(ReleaseProjectTabGenral.this.getContext());
//                Intent intent = new Intent(getActivity(), MapMainActivity.class);
//                getActivity().startActivityForResult(intent, 5);
            }
        });
    }



    public void setCommParam(HttpParamsObject commParam)
    {
        this.commParam = commParam;

    }
    public boolean getCommParam()
    {

        if(demand.getText().toString().equals("") || brand.getText().toString().equals("") || budget.getText().toString().equals("") || address.getText().toString().equals(""))
        {
            ToastShow.s("信息不能为空");
            return false;
        }
        commParam.setParam("demand",demand.getText().toString());
        commParam.setParam("brand",brand.getText().toString());
        commParam.setParam("budget",budget.getText().toString());
        commParam.setParam("address",address.getText().toString());
        return true;
//        return this.commParam;
    }

    EditText demand;
    EditText brand;
    EditText budget;
    EditText address;
    private void initView() {
//        param.setParam("test3","333333");

        demand = (EditText)findViewById(R.id.demand);
        brand = (EditText)findViewById(R.id.brand);
        budget = (EditText)findViewById(R.id.budget);
        address = (EditText)findViewById(R.id.address);



        address.setText(commParam.getStringMapParam("address_title"));
        demand.setText(commParam.getStringMapParam("demand"));
        brand.setText(commParam.getStringMapParam("brand"));
        budget.setText(commParam.getStringMapParam("budget"));
        if(!commParam.getStringMapParam("backgroundImage").equals(""))
        {
            ((TextView)findViewById(R.id.backgroundImageTip)).setText("已选择");
        }

//        demand.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//                    CropPictureConfig config = new CropPictureConfig();
//                    config.setProjectXY();
//                    config.context = getContext();
//                    config.activity = getActivity();
//                    config.forResult = 1;
//                    new CropPicture(config);
//                }
//
//            });

        demand.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            commParam.setParam("demand",demand.getText().toString());
//                CropPictureConfig config = new CropPictureConfig();
//                config.setProjectXY();

//                config.context = getContext();
//                config.activity = getActivity();
//                config.forResult = 1;
//                new CropPicture(config);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PictureConfig.SHOWGROUND_REQUEST) {
//           if (requestCode == 1) {
            String path = data.getStringExtra("backgroundImage");
            Log.e(TAG,"接收到的背景图"+path);
            ((TextView)findViewById(R.id.backgroundImageTip)).setText("已选择");
            commParam.setParam("backgroundImage",path);

//            }
        }
        else if (requestCode == PictureConfig.MAP_HEAD_REQUEST && resultCode == AppCompatActivity.
        RESULT_OK ) {

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

            address.setText(title);

//            double longitude = data.getDoubleExtra("longitude", 0);
//            double latitude = data.getDoubleExtra("latitude", 0);
//            String adname = data.getStringExtra("adname");
//            param.setParam("address",adname);
//            ((TextView)findViewById(R.id.address)).setText(adname);
        }
    }


}
