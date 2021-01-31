package com.ming.sjll.My.personage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.Config;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyPersonageFaceBinding;
import com.ming.sjll.databinding.MyPersonageFigureBinding;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 身材信息
 */
public class PersonageFigure extends BaseActivity {

    private MyPersonageFigureBinding binding;

    public  GetInfoBean userInfo;

    private String skin_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_personage_figure, null, false);
        setContentView(binding.getRoot());

        getInfo();
        event();
    }

    private void getInfo()
    {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.USER_APPROVE_GET_MODEL_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                userInfo = new Gson().fromJson(data, GetInfoBean.class);
                initData();
            }
        });
    }

    private void event()
    {

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("height",binding.height.getText().toString());
                param.setParam("chest",binding.chest.getText().toString());
                param.setParam("waistline",binding.waistline.getText().toString());
                param.setParam("hipline",binding.hipline.getText().toString());
                param.setParam("shoe_size",binding.shoeSize.getText().toString());
                param.setParam("bra_size",binding.braSize.getText().toString());
                param.setParam("skin_color",skin_color);
                new HttpUtil().sendRequest(Constant.USER_APPROVE_SAVE_FACE_INFO, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("保存成功");
                        finish();
                    }
                });
            }
        });

        binding.figure1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skin_color = "1";
                initFigure();
            }
        });
        binding.figure2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skin_color = "2";
                initFigure();
            }
        });
        binding.figure3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skin_color = "3";
                initFigure();
            }
        });
        binding.figure4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skin_color = "4";
                initFigure();
            }
        });
    }

    private void initData()
    {
        binding.height.setText(userInfo.getData().getHeight());
        binding.chest.setText(userInfo.getData().getChest());
        binding.waistline.setText(userInfo.getData().getWaistline());
        binding.hipline.setText(userInfo.getData().getHipline());
        binding.shoeSize.setText(userInfo.getData().getShoe_size());
        binding.braSize.setText(userInfo.getData().getBra_size());
        skin_color = userInfo.getData().getSkin_color();
        initFigure();
    }

    /**
     * 肤色初始化
     */
    private void initFigure()
    {
        switch (skin_color)
        {
            case "1":
                binding.figure1.setBackgroundResource(R.mipmap.bg_figure_skin1_select);
                binding.figure2.setBackgroundResource(R.mipmap.bg_figure_skin2);
                binding.figure3.setBackgroundResource(R.mipmap.bg_figure_skin3);
                binding.figure4.setBackgroundResource(R.mipmap.bg_figure_skin4);
                break;
            case "2":
                binding.figure1.setBackgroundResource(R.mipmap.bg_figure_skin1);
                binding.figure2.setBackgroundResource(R.mipmap.bg_figure_skin2_select);
                binding.figure3.setBackgroundResource(R.mipmap.bg_figure_skin3);
                binding.figure4.setBackgroundResource(R.mipmap.bg_figure_skin4);
                break;
            case "3":
                binding.figure1.setBackgroundResource(R.mipmap.bg_figure_skin1);
                binding.figure2.setBackgroundResource(R.mipmap.bg_figure_skin2);
                binding.figure3.setBackgroundResource(R.mipmap.bg_figure_skin3_select);
                binding.figure4.setBackgroundResource(R.mipmap.bg_figure_skin4);
                break;
            case "4":
                binding.figure1.setBackgroundResource(R.mipmap.bg_figure_skin1);
                binding.figure2.setBackgroundResource(R.mipmap.bg_figure_skin2);
                binding.figure3.setBackgroundResource(R.mipmap.bg_figure_skin3);
                binding.figure4.setBackgroundResource(R.mipmap.bg_figure_skin4_select);
                break;
        }
    }


}
