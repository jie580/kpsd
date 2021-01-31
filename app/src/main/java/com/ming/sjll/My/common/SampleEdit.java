package com.ming.sjll.My.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.My.adapter.SampleItemEditImageAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.event.SampleEditEvent;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.ReleaseProjectCover;
import com.ming.sjll.ui.CustomRoundAngleImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

import org.greenrobot.eventbus.EventBus;
/**
 * 个人中心-作品
 */
public class SampleEdit extends BaseActivity {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.title_bar_IvRight)
    ImageView title_bar_IvRight;

    @BindView(R.id.title)
    EditText title;

    @BindView(R.id.describe)
    EditText describe;

    @BindView(R.id.wrapPicture)
    LinearLayout wrapPicture;

    @BindView(R.id.wrapVideo)
    LinearLayout wrapVideo;

    @BindView(R.id.replaceVideo)
    ImageView replaceVideo;

    @BindView(R.id.videoImg)
    CustomRoundAngleImageView videoImg;





    int oldPoint = -1;

    String type ;//类型 1 -图片作品 2 - 视频作品

    SampleItemEditImageAdapter imageAdapter;

    //    总数据
    HttpParamsObject param;

//    传过来的数据
    WorkItem.DataBean dataBean;

    List<String> imageList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_sample_edit);
        title_bar_IvRight.setVisibility(View.VISIBLE);
        title_bar_IvRight.setBackground(getResources().getDrawable(R.drawable.btn_save_top2));

        type = getIntent().getStringExtra("type");
        dataBean = (WorkItem.DataBean) getIntent().getSerializableExtra("content");
        oldPoint = getIntent().getIntExtra("oldPoint",-1);
        param = new HttpParamsObject();
        init();
        if(type.equals("1"))
        {
            initPictureView();
        }

        event();
    }

    public void init()
    {

        int oldId = -1;
        if(dataBean != null)
        {
            oldId = dataBean.getId();
            param.setParam("workId",dataBean.getId());
            param.setParam("describe",dataBean.getDescribe());
            param.setParam("title",dataBean.getTitle());
            param.setParam("type",dataBean.gettype());
            param.setParam("coverImg",dataBean.getCoverImg());
            param.setParam("img",dataBean.getimgList());
            param.setParam("videoUrl",dataBean.getvideo_url());
            type = dataBean.gettype()+"";
        }
        title.setText(param.getStringMapParam("title"));
        describe.setText(param.getStringMapParam("describe"));
        if( type.equals( "1") )
        {
            wrapPicture.setVisibility(View.VISIBLE);
            wrapVideo.setVisibility(View.GONE);
            imageList = param.getStringListParam("img");
            if(oldId < 0)
            {
                int max = 6;
                PictureSelector.create(SampleEdit.this)
                        .openGallery(PictureMimeType.ofImage()).maxSelectNum(max).isCamera(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        }
        else
        {
            wrapPicture.setVisibility(View.GONE);
            wrapVideo.setVisibility(View.VISIBLE);
            if(oldId < 0)
            {
                int max = 6;
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofVideo()).maxSelectNum(1).minSelectNum(1).forResult(PictureConfig.VIDEO_REQUEST);
            }
            else
            {
//                视频略缩图
                new ImageHelper().displayBackgroundLoading( videoImg,
                        Constant.BASE_IMAGE + param.getStringMapParam("coverImg"));
            }
        }


    }
    public void event()
    {

        title_bar_IvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type.equals("1") && param.getStringMapParam("coverImg").equals("") )
                {
                    ToastShow.s("请选择展示封面");
                    return;
                }
                else if(type.equals("1") && imageAdapter.getRealList().size() < 1)
                {
                    ToastShow.s("请上传作品图片");
                    return;
                }
                else if(type.equals("2") && (param.getStringMapParam("coverImg").equals("") ||  param.getStringMapParam("videoUrl").equals("")))
                {
                    ToastShow.s("请上传作品视频");
                    return;
                }
                else if(title.getText().toString().equals(""))
                {
                    ToastShow.s("请输入作品名称");
                    return;
                }
                else if(describe.getText().toString().equals(""))
                {
                    ToastShow.s("请输入作品介绍");
                    return;
                }
//
                param.setParam("title",title.getText().toString());
                param.setParam("describe",describe.getText().toString());
                if(type.equals("1"))
                {
                    param.setParam("img",imageAdapter.getRealList());
                }
                param.setParam("type",type);
//
                //保存
                Log.e("提交参数作品" ,"提交参数作品"+param.getUrlParam());
                Log.e("实际作品" ,"实际作品"+imageList.toString());

                new HttpUtil().sendRequest(Constant.USER_WORK_SAVE_WORK, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        WorkItem DataBean =  new Gson().fromJson(data, WorkItem.class);
                        EventBus.getDefault().post(new SampleEditEvent(DataBean.getData(),oldPoint));
                        finish();
                    }
//
                });

            }
        });


        //        背景选择
        findViewById(R.id.backgroundImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                跳转上传背景页面
//                Bundle bundle = new Bundle();
//                Log.e("背景图","背景图"+param.getStringMapParam("coverImg"));
//                Log.e("backgroundImageURl",",,,,,,"+param.getUrlParam());
////                bundle.putString("backgroundImage", param.getStringMapParam("backgroundImage"));
//                Intent intent = new Intent(SampleEdit.this, ReleaseProjectCover.class);
//                bundle.putString("backgroundImage",param.getStringMapParam("coverImg"));
//                //1 其实就是 requestCode 请求码 传入了唯一值 1
//                intent.putExtras(bundle);
//                startActivityForResult(intent,44);
//                startActivityForResult(intent,33);
//                Tools.jump(getActivity(), ReleaseProjectCover.class, bundle, false);


                PictureSelector.create(getContext())
                        .openGallery(PictureMimeType.ofImage()).enableCrop(true).withAspectRatio(173, 255)
                        .isDragFrame(true).rotateEnabled(false).freeStyleCropEnabled(false).scaleEnabled(true).hideBottomControls(true).maxSelectNum(1).minSelectNum(1).forResult(44);

            }
        });

        /**
         * 视频替换
         */
        replaceVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(SampleEdit.this)
                        .openGallery(PictureMimeType.ofVideo()).maxSelectNum(1).minSelectNum(1).forResult(PictureConfig.VIDEO_REQUEST);
            }
        });
    }

    private void initPictureView() {
        Log.e("图片路径","dddd"+imageList.toString());
        imageAdapter = new SampleItemEditImageAdapter(imageList);
        recyclerview.setLayoutManager(new GridLayoutManager(SampleEdit.this, 3));
        recyclerview.setAdapter(imageAdapter);

        imageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_delete) {
                    imageAdapter.removeData(position);
                } else if (view.getId() == R.id.iv_add) {
                    int max = 7-imageList.size();
                    PictureSelector.create(SampleEdit.this)
                            .openGallery(PictureMimeType.ofImage()).maxSelectNum(max).isCamera(false)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            }
        });

    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                loadingDailog.showLoading();
                //                progressDialog = new ProgressDialog(this);
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject paramTemp = new HttpParamsObject();
                List<String> fileList = new LinkedList<>();
                for (LocalMedia localMedia : selectList) {
                    fileList.add(localMedia.getPath());
                }
                Log.e(TAG, "回调参数：" + paramTemp.getUrlParam());
                HttpUtil.uplaodFiles(fileList, paramTemp, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            //                        JSONObject json = new JSONObject(data);
                            //                        String path = (String)json.get("data");
                            //                        updateBgPath(path);

                            JSONObject jsonObject = new JSONObject(data);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
//                            List<String> imageLists = imageAdapter.getRealList();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                imageList.add((String) jsonArray.get(i));
                            }
//                            Log.e(TAG, "上传返回：" + imageLists.toString());
//                            param.setParam("img", imageLists);
//                            initView();

                            imageAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }

                        //                        progressDialog.dismiss();
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
            else if(requestCode == PictureConfig.VIDEO_REQUEST)
            {
                loadingDailog.showLoading();
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject paramTemp = new HttpParamsObject();

                HttpUtil.uploadVideo(selectList.get(0).getPath(), paramTemp, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {

                            JSONObject jsonObject = new JSONObject(data);
                            Object dataObject = jsonObject.get("data");
//                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            String coverImg  = ObjeGetValue.GetValu("img",dataObject).toString();
                            param.setParam("coverImg",coverImg);
                            param.setParam("videoUrl",ObjeGetValue.GetValu("path",dataObject).toString());
                            //                视频略缩图
                            new ImageHelper().displayBackgroundLoading( videoImg,
                                    Constant.BASE_IMAGE + coverImg);
////                            List<String> imageLists = imageAdapter.getRealList();
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                imageList.add((String) jsonArray.get(i));
//                            }
//                            Log.e(TAG, "上传返回：" + imageLists.toString());
//                            param.setParam("img", imageLists);
//                            initView();

//                            imageAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }

                        //                        progressDialog.dismiss();
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
            else if (requestCode == 44 ) {


                loadingDailog.showLoading();
//                progressDialog = new ProgressDialog(this);
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject paramTemp = new HttpParamsObject();
                HttpUtil.uplaodFile(selectList.get(0).getPath(), paramTemp, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            JSONObject json = new JSONObject(data);
                            String path = (String)json.get("data");

                            //           if (requestCode == 1) {
//                        String path = data.getStringExtra("backgroundImage");
                            Log.e(TAG,"接收到的背景图"+path);
                            ((TextView)findViewById(R.id.backgroundImageTip)).setText("已选择");
                            param.setParam("coverImg",path);

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





                //            }
            }


        }
//        else if (resultCode == PictureConfig.SHOWGROUND_REQUEST) {
////           if (requestCode == 1) {
//            String path = data.getStringExtra("backgroundImage");
//            Log.e(TAG,"接收到的背景图"+path);
//            ((TextView)findViewById(R.id.backgroundImageTip)).setText("已选择");
//            param.setParam("coverImg",path);
//
////            }
//        }
    }








}
