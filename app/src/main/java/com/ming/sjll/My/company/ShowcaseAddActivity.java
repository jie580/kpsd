package com.ming.sjll.My.company;

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
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.My.adapter.SampleItemEditImageAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.ReleaseProjectCover;
import com.ming.sjll.toolPage.CropPicture;
import com.ming.sjll.toolPage.CropPictureConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * 个人中心-橱窗-商品添加
 */
public class ShowcaseAddActivity extends BaseActivity {


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

    SampleItemEditImageAdapter imageAdapter;

    List<String> imageList = new LinkedList<>();


    int oldPoint = -1;

    private String class_id, goods_id,type;


    //    总数据
    HttpParamsObject commParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_show_case_add);
        class_id = getIntent().getStringExtra("class_id");
        goods_id = getIntent().getStringExtra("goods_id");//编辑
        type = getIntent().getStringExtra("type");
        oldPoint = getIntent().getIntExtra("oldPoint",-1);
        commParam = new HttpParamsObject();
        commParam.setParam("class_id",class_id);
        commParam.setParam("type",type);
        commParam.setParam("goods_id",goods_id);
        commParam.setParam("oldPoint",oldPoint);

        if(goods_id == null || goods_id.equals(""))
        {
            initView();
        }
        else
        {
            getInfo();
        }

        event();
    }
    GoodsItem MyData;
    private void getInfo()
    {

        loadingDailog.show();
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("goods_id",goods_id);
        new HttpUtil().sendRequest(Constant.GOODS_GOODS_INFO, commParam, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                 MyData =  new Gson().fromJson(data, GoodsItem.class);
                commParam.setParam("title",MyData.getData().getTitle());
                commParam.setParam("describe",MyData.getData().getDescribe());
                commParam.setParam("cover_img",MyData.getData().getCover_img());
                commParam.setParam("imgList",MyData.getData().getImgList());
//                commParam.setParam("price",MyData.getData().getPrice());
//                commParam.setParam("units",MyData.getData().getUnits());
                commParam.setParam("deposit",MyData.getData().getDeposit());


                commParam.setParam("area_code", MyData.getData().getArea_code());
                commParam.setParam("provinces", MyData.getData().getProvinces());
                commParam.setParam("city", MyData.getData().getCity());
                commParam.setParam("county", MyData.getData().getCounty());
                commParam.setParam("address_title", MyData.getData().getAddress_title());
                commParam.setParam("address", MyData.getData().getAddress());
                commParam.setParam("meridian", MyData.getData().getMeridian());
                commParam.setParam("weft", MyData.getData().getWeft());
                commParam.setParam("street",MyData.getData().getStreet());
                commParam.setParam("num",MyData.getData().getNum() );
                commParam.setParam("sendType",MyData.getData().getSendType() );


                commParam.setParam("serviceType",MyData.getData().getServiceType() );
                commParam.setParam("email",MyData.getData().getEmail() );
                commParam.setParam("is_relevancy",MyData.getData().getIs_relevancy() );
                commParam.setParam("userIds",MyData.getData().getUserIds() );

//                commParam.setParam("is_inventory",MyData.getData().getIs_inventory());
//                commParam.setParam("is_mail",MyData.getData().getIs_mail());

                initView();
            }

            @Override
            public void onFinal() {
                super.onFinal();
                loadingDailog.hide();
            }
        });
    }

    private void initView() {


        title_bar_IvRight.setVisibility(View.VISIBLE);
        title_bar_IvRight.setBackgroundResource(R.mipmap.btn_save_top4);


        if(!commParam.getStringMapParam("cover_img").equals(""))
        {
            ((TextView) findViewById(R.id.backgroundImageTip)).setText("已选择");
        }
        title.setText(commParam.getStringMapParam("title"));
        describe.setText(commParam.getStringMapParam("describe"));
        imageList = commParam.getStringListParam("imgList");

        initPictureView();
    }

    private void initPictureView() {
        Log.e("图片路径", "dddd" + imageList.toString());
        imageAdapter = new SampleItemEditImageAdapter(imageList);
        recyclerview.setLayoutManager(new GridLayoutManager(ShowcaseAddActivity.this, 3));
        recyclerview.setAdapter(imageAdapter);

        imageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_delete) {
                    imageAdapter.removeData(position);
                } else if (view.getId() == R.id.iv_add) {
                    int max = 7 - imageList.size();
                    PictureSelector.create(ShowcaseAddActivity.this)
                            .openGallery(PictureMimeType.ofImage()).maxSelectNum(max).isCamera(false)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            }
        });


    }


    private void event() {

        title_bar_IvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().equals("")) {
                    ToastShow.s("请输入商品名称");
                    return;
                } else if (describe.getText().toString().equals("")) {
                    ToastShow.s("请输入商品介绍");
                    return;
                } else if (commParam.getStringMapParam("cover_img").equals("")) {
                    ToastShow.s("请选择展示封面");
                    return;
                } else if (imageAdapter.getRealList().size() < 1) {
                    ToastShow.s("请上传商品图片");
                    return;
                }
                commParam.setParam("title", title.getText().toString());
                commParam.setParam("describe", describe.getText().toString());
                commParam.setParam("imgList", imageAdapter.getRealList());

                Intent intent = new Intent(ShowcaseAddActivity.this, ShowcaseAddTwoActivity.class);
                intent.putExtra("commParam", commParam);
                intent.putExtra("MyData", MyData);
                Log.e("提交", "推送" + commParam.getUrlParam());
                startActivity(intent);

            }
        });


        //        背景选择
        findViewById(R.id.backgroundImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
////                跳转上传背景页面
//                Bundle bundle = new Bundle();
//                Log.e("背景图", "背景图" + commParam.getStringMapParam("cover_img"));
//                Log.e("backgroundImageURl", ",,,,,," + commParam.getUrlParam());
////                bundle.putString("backgroundImage", commParam.getStringMapParam("backgroundImage"));
//                Intent intent = new Intent(ShowcaseAddActivity.this, ReleaseProjectCover.class);
//                bundle.putString("backgroundImage", commParam.getStringMapParam("cover_img"));
//                //1 其实就是 requestCode 请求码 传入了唯一值 1
//                intent.putExtras(bundle);
//                startActivityForResult(intent, 44);



                PictureSelector.create(getContext())
                        .openGallery(PictureMimeType.ofImage()).enableCrop(true).withAspectRatio(173, 255)
                        .isDragFrame(true).rotateEnabled(false).freeStyleCropEnabled(false).scaleEnabled(true).hideBottomControls(true).maxSelectNum(1).minSelectNum(1).forResult(44);

                //                startActivityForResult(intent,33);
//                Tools.jump(getActivity(), ReleaseProjectCover.class, bundle, false);
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);

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
        } else if (requestCode == 44 && resultCode == RESULT_OK) {


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

                        //           if (requestCode == 1) {
//                        String path = data.getStringExtra("backgroundImage");
                        Log.e(TAG, "接收到的背景图" + path);
                        ((TextView) findViewById(R.id.backgroundImageTip)).setText("已选择");
                        commParam.setParam("cover_img", path);

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


}
