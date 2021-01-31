package com.ming.sjll.someone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.StringDataListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.someone.adapter.SomeoneHistoryImageAdapter;
import com.ming.sjll.ui.CustomRoundAngleImageView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * 图片找人
 */
public class FindListFragment extends BaseActivity {


    @BindView(R.id.updateBgBtn)
    RelativeLayout updateBgBtn;

    @BindView(R.id.cropImage)
    CustomRoundAngleImageView cropImage;

    @BindView(R.id.guide1)
    ImageView guide1;

    @BindView(R.id.guide2)
    ImageView guide2;

    @BindView(R.id.guide3)
    ImageView guide3;

    @BindView(R.id.guide4)
    ImageView guide4;


    String path;
    StringDataListBean imageList;
    private Fragment[] mFragments;


    FindListPerson findPerson;
    FindListGoods findGoods;
    FindListSample findSample;
    FindListProject findProject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.someone_find);
        path = getIntent().getStringExtra("path");

        findPerson = FindListPerson.newInstance();
        findGoods = FindListGoods.newInstance();
        findSample = FindListSample.newInstance();
        findProject = FindListProject.newInstance();
        mFragments = new Fragment[]{findPerson, findGoods,findSample,findProject};
        event();

        showFragment(findPerson);
        findPerson.showView(path);
    }

    private void showView() {
        new ImageHelper().displayBackgroundLoading(cropImage,
                Constant.BASE_IMAGE + path);
        findPerson.showView(path);
        findGoods.showView(path);
        findSample.showView(path);
        findProject.showView(path);

    }


    private void initView() {

    }

    private void event() {
        guide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide1.setBackgroundResource(R.mipmap.find_gui1_select);
                guide2.setBackgroundResource(R.mipmap.find_gui2);
                guide3.setBackgroundResource(R.mipmap.find_gui3);
                guide4.setBackgroundResource(R.mipmap.find_gui4);
                showFragment(findPerson);
                findPerson.showView(path);

            }
        });
        guide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide1.setBackgroundResource(R.mipmap.find_gui1);
                guide2.setBackgroundResource(R.mipmap.find_gui2_select);
                guide3.setBackgroundResource(R.mipmap.find_gui3);
                guide4.setBackgroundResource(R.mipmap.find_gui4);
                showFragment(findGoods);
                findGoods.showView(path);

            }
        });
        guide3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide1.setBackgroundResource(R.mipmap.find_gui1);
                guide2.setBackgroundResource(R.mipmap.find_gui2);
                guide3.setBackgroundResource(R.mipmap.find_gui3_select);
                guide4.setBackgroundResource(R.mipmap.find_gui4);
                showFragment(findSample);
                findSample.showView(path);

            }
        });

        guide4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide1.setBackgroundResource(R.mipmap.find_gui1);
                guide2.setBackgroundResource(R.mipmap.find_gui2);
                guide3.setBackgroundResource(R.mipmap.find_gui3);
                guide4.setBackgroundResource(R.mipmap.find_gui4_select);
                showFragment(findProject);
                findProject.showView(path);

            }
        });



        updateBgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(FindListFragment.this)
                        .openGallery(PictureMimeType.ofImage()).maxSelectNum(1).isCamera(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });


    }


    /**
     * 显示fragment
     */
    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
            for (Fragment f : mFragments) {
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                if (f == fragment) {
                    if (fragment.isHidden()) {
                        transaction2.show(f).commitAllowingStateLoss();
                    }
                } else {
                    if (f.isAdded()) {
                        transaction2.hide(f).commitAllowingStateLoss();
                    }
                }
            }
        } else {
            for (Fragment f : mFragments) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (f == fragment) {
                    if (fragment.isHidden()) {
                        transaction.show(f).commitAllowingStateLoss();
                    }
                } else {
                    if (f.isAdded()) {
                        transaction.hide(f).commitAllowingStateLoss();
                    }
                }
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                loadingDailog.showLoading();
                //                progressDialog = new ProgressDialog(this);
                loadingDailog.showLoading();
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject param = new HttpParamsObject();
                HttpUtil.uplaodFile(selectList.get(0).getPath(), param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            JSONObject json = new JSONObject(data);
                            path = (String) json.get("data");
                            showView();
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

        }
    }


}
