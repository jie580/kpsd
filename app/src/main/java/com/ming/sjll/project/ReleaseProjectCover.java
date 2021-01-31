package com.ming.sjll.project;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.databinding.ProjectReleaseProjectCoverBinding;
import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.adapter.CoverBgAdapter;
import com.ming.sjll.project.bean.ProjecCoverBg;
import com.ming.sjll.toolPage.CropPicture;
import com.ming.sjll.toolPage.CropPictureConfig;
import com.ming.sjll.ui.AntoLineLayoutManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import rxhttp.RxHttpPlugins;
//import rxhttp.wrapper.param.RxHttp;

/**
 * 选择封面
 */
public class ReleaseProjectCover extends BaseActivity {

    public ProjectReleaseProjectCoverBinding binding;
    RecyclerView recyclerList;
    ImageView cropImage;
    TextView updateBtnTxt;
    RelativeLayout updateBgBtn;
//    背景封面图
    String backgroundImage;



    int showTypePos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        showTypePos = getIntent().getStringExtra("showType").equals("project") ? 0 : 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_release_project_cover);
        Log.e("创建","111111111111111111111111111111111");
        initView();

        String path = getIntent().getStringExtra("backgroundImage");
        updateBgPath(path);

    }


    private void initView() {
//        loadingDailog.show();
        binding = DataBindingUtil.setContentView(this, R.layout.project_release_project_cover);
        binding.setTitleViewModel(new ToolbarViewModel());
        binding.titleBar.titleBarIvRight.setVisibility(View.VISIBLE);
        binding.titleBar.titleBarIvRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_save_top));
        binding.titleBar.titleBarIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(backgroundImage.equals(""))
                {
                    ToastShow.s("请上传图片");
                    return;
                }

                //保存

                Intent intent= getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("backgroundImage",backgroundImage);
                intent.putExtras(bundle);

                setResult(PictureConfig.SHOWGROUND_REQUEST,intent);

                finish();
                Log.e("点击保存:",backgroundImage);
            }
        });

        cropImage = (ImageView)findViewById(R.id.cropImage);
        updateBgBtn = (RelativeLayout)findViewById(R.id.updateBgBtn);
        updateBtnTxt = (TextView)findViewById(R.id.updateBtnTxt);


        getImageList();
        uploadingBg();

    }


    ProjecCoverBg dataList;
//    获取背景图片列表
    private void getImageList()
    {
        recyclerList  = findViewById(R.id.recyclerList);

        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.PROJECT_GET_BG_IMG, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, ProjecCoverBg.class);
                AntoLineLayoutManager layout = new AntoLineLayoutManager();
                recyclerList.setLayoutManager(layout);
                CoverBgAdapter coverBgAdapter = new CoverBgAdapter(dataList.getData());
                recyclerList.setAdapter(coverBgAdapter);
                coverBgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        Log.e("点击了封面选择",R.id.bg+"&&&&&&&&&"+view.getId());
                        switch (view.getId()) {
                            case R.id.bg:
                                for (int i = 0; i < dataList.getData().size(); i++) {
                                    dataList.getData().get(i).setIsSelect(false);
                                }
                                dataList.getData().get(position).setIsSelect(true);
                                coverBgAdapter.notifyDataSetChanged();
                                String path = dataList.getData().get(position).getImg();
                                updateBgPath(path);
                                break;
                        }
                     };
                });
            }
        });
    }


//    ProgressDialog progressDialog;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG,"收到回到");
        Log.e("接受","111111111111111111111111111111111");
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.SHOWGROUND_REQUEST) {
                loadingDailog.showLoading();
//                progressDialog = new ProgressDialog(this);
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject param = new HttpParamsObject();
                HttpUtil.uplaodFile(selectList.get(0).getPath(), param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            JSONObject json = new JSONObject(data);
                            String path = (String)json.get("data");
                            updateBgPath(path);
                        }catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }

//                        progressDialog.dismiss();
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
        }
    }


    /**
     * 设置背景图片
     * @param path
     */
    private void updateBgPath(String path)
    {
        if(path == null || path.equals(""))
        {
            backgroundImage = "";
            updateBtnTxt.setText("GO");
        }
        else
        {
            updateBtnTxt.setText("替换");
            backgroundImage = path;
            new ImageHelper().displayCorners( cropImage, Constant.BASE_API + backgroundImage);
        }

    }
    private void updateBgPath()
    {
        updateBgPath("");
    }

    /**
     * 剪裁图片
     */
    public void uploadingBg() {

        cropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                CropPictureConfig config = new CropPictureConfig();
                config.setProjectXY();
                config.context = v.getContext();
                config.activity = ReleaseProjectCover.this;
                config.forResult = PictureConfig.SHOWGROUND_REQUEST;
//                new CropPicture(config);


                PictureSelector.create(config.activity)
                        .openGallery(PictureMimeType.ofImage()).enableCrop(config.enableCrop).withAspectRatio(config.aspect_ratio_x, config.aspect_ratio_y)
                        .isDragFrame(true).rotateEnabled(false).freeStyleCropEnabled(false).scaleEnabled(true).hideBottomControls(true).maxSelectNum(config.maxSelectNum).minSelectNum(1).forResult(config.forResult);
            }
        });
    }


}
