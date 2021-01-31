package com.ming.sjll.project.flow;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectFlowWorkSelectBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.MyUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.flow.adapter.SampleReviewItemAdapter;
import com.ming.sjll.project.flow.adapter.SampleSelectItemAdapter;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;

import me.minetsh.imaging.IMGEditActivity;
import me.minetsh.imaging.IMGGalleryActivity;
import me.minetsh.imaging.MyAppliaction;

/**
 * 样片审视
 */
public class WorkReviewFragment extends BaseFragment {



    String projectId;

    File mImageFile;

    int commPosition;

    SampleReviewItemAdapter sampleUploadItemAdapter;

    private ProjectFlowWorkSelectBean mData;
    public static WorkReviewFragment newInstance(String project_id) {
        WorkReviewFragment fragment = new WorkReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", project_id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.project_flow_review_fragment);
        initView();
    }

    private void initView() {
        projectId = getArguments().getString("projectId");


        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId",projectId);
        param.setParam("flowIdentification","sample_review");
        new HttpUtil().sendRequest(Constant.PROJECT_FLOW_GET_FLOW_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                mData = new Gson().fromJson(data, ProjectFlowWorkSelectBean.class);

                showImage();

            }
        });


    }

    private void showImage()
    {

        RecyclerView listView = (RecyclerView)findViewById(R.id.listView);
//        listView.setLayoutManager(new GridLayoutManager(mContext, 2));\
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        sampleUploadItemAdapter = new SampleReviewItemAdapter(mData.getData(),getActivity(), projectId);
        listView.setAdapter(sampleUploadItemAdapter);
        sampleUploadItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId())
                {
                    case R.id.checkSelect:
                        mData.getData().get(position).setIsOpen(!mData.getData().get(position).getIsOpen());
                        mData.getData().get(position).setNewImg(null);
                        sampleUploadItemAdapter.notifyDataSetChanged();
                        break;
                    case R.id.imageItem:
//                        IMGGalleryActivity.
//                        Uri uri = ((ImageView)view).getClass().getClassLoader();
                        if(!mData.getData().get(position).getIsOpen())
                        {
                            return;
                        }
                        mImageFile = new File((new ContextWrapper(getActivity())).getCacheDir(), UUID.randomUUID().toString() + ".jpg");
//                        Uri uri = getImageUri(getContext(),mData.getData().get(position).getImgBitmap());
//                        Bitmap bitmap = mData.getData().get(position).getImgBitmap();
                        String paht = mImageFile.getPath() !=null? mImageFile.getPath() :mImageFile.getAbsolutePath();

                        MyAppliaction.sendBitmap = mData.getData().get(position).getImgBitmap().copy(mData.getData().get(position).getImgBitmap().getConfig(),true);
                        commPosition = position;
                        startActivityForResult(
                                (new Intent(getActivity(), IMGEditActivity.class))
//                            .putExtra(IMGEditActivity.EXTRA_IMAGE_URI, compressImage(bitmap))
                            .putExtra(IMGEditActivity.EXTRA_IMAGE_SAVE_PATH, paht),
                        1
                        );

                        break;
                    case R.id.save:
                        HttpParamsObject param = new HttpParamsObject();
                        param.setParam("projectId",projectId);
                        param.setParam("imgId",mData.getData().get(position).getImg_id());
                        param.setParam("content",mData.getData().get(position).getImg_rect());
                        param.setParam("editImg",mData.getData().get(position).getNewImg() != null ? mData.getData().get(position).getNewImg() : mData.getData().get(position).getImg());
                        new HttpUtil().sendRequest(Constant.PROJECT_FLOW_SELECT_WORK, param, new CommonCallback() {
                            @Override
                            public void onSuccessJson(String data, int code) {
                                showImage();
                            }
                        });
                        break;
                }

//
//                loadingDailog.show();
//                HttpParamsObject param = new HttpParamsObject();
//                param.setParam("projectId",projectId);
//                param.setParam("imgId",mData.getData().get(position).getImg_id());
//                new HttpUtil().sendRequest(Constant.PROJECT_FLOW_SELECT_WORK, param, new CommonCallback() {
//                    @Override
//                    public void onSuccessJson(String data, int code) {
//                        if(showType.equals("4"))
//                        {
//                            if(mData.getData().get(position).getIs_sample().equals("1"))
//                            {
//                                mData.getData().get(position).setIs_sample("0");
//                            }
//                            else
//                            {
//                                mData.getData().get(position).setIs_sample("1");
//                            }
//                        }
//                        else
//                        {
//                            if(mData.getData().get(position).getIs_select().equals("1"))
//                            {
//                                mData.getData().get(position).setIs_select("0");
//                                mData.getData().get(position).setIs_sample("0");
//                            }
//                            else
//                            {
//                                mData.getData().get(position).setIs_select("1");
//                            }
//                        }
//                        sampleUploadItemAdapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onFinal() {
//                        super.onFinal();
//                        loadingDailog.hide();
//                    }
//                });

//                switch (view.getId())
//                {
//                    case R.id.checkSelect:
//                        RongImUtils.privateChat(getActivity(),mData.getData().get(position).getIm_uid(),mData.getData().get(position).getIm_name());
//                        break;
//                }
            }
        });
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    /**
     * 压缩图片
     * 该方法引用自：http://blog.csdn.net/demonliuhui/article/details/52949203
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == getActivity().RESULT_OK)
        {
            HttpParamsObject param = new HttpParamsObject();
            HttpUtil.uplaodFile(mImageFile.getPath(), param, new CommonCallback() {
                @Override
                public void onSuccessJson(String data, int code) {
                    try {
                        JSONObject json = new JSONObject(data);
                        String path = (String)json.get("data");
//                        MainActivity.saveInfo("default_avatar_rect",path);
                        mData.getData().get(commPosition).setNewImg(path);
                        sampleUploadItemAdapter.notifyDataSetChanged();
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

    }
}
