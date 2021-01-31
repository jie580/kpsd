package com.ming.sjll.someone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.someone.adapter.SomeoneHistoryImageAdapter;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * 图片找人-历史记录
 */
public class FindFragment extends BaseActivity {


    @BindView(R.id.recyclerList)
    RecyclerView recyclerview;

    @BindView(R.id.updateBgBtn)
    RelativeLayout updateBgBtn;

    StringDataListBean imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.someone_fragment);
        getList();
        event();
    }

    private void event()
    {
        updateBgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(FindFragment.this)
                        .openGallery(PictureMimeType.ofImage()).maxSelectNum(1).isCamera(false)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });


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
                            String path = (String) json.get("data");
                            Bundle bundle = new Bundle();
                            bundle.putString("path",path);
                            Tools.jump(FindFragment.this, FindListFragment.class, bundle, false);
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

    private void getList()
    {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.SEARCH_SEARCH_LOG, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                imageList = new Gson().fromJson(data, StringDataListBean.class);
                SomeoneHistoryImageAdapter imageAdapter = new SomeoneHistoryImageAdapter(imageList.getData().getData());
                recyclerview.setLayoutManager(new GridLayoutManager(FindFragment.this, 3));
                recyclerview.setAdapter(imageAdapter);
                imageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if (view.getId() == R.id.iv_delete) {
                            HttpParamsObject param = new HttpParamsObject();
                            param.setParam("logId",imageList.getData().getData().get(position).getId());
                            new HttpUtil().sendRequest(Constant.SEARCH_DEL_LOG, param, new CommonCallback() {
                                @Override
                                public void onSuccessJson(String data, int code) {
                                    imageAdapter.removeData(position);
                                }
                            });

                        } else if (view.getId() == R.id.iv_img) {
                            Bundle bundle = new Bundle();
                            bundle.putString("path",imageList.getData().getData().get(position).getSearch_img());
                            Tools.jump(FindFragment.this, FindListFragment.class, bundle, false);

                        }
                    }
                });
            }
        });
    }



}
