package com.ming.sjll.project.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.Bean.ProjectCost;
import com.ming.sjll.Bean.ProjectSiteListBean;
import com.ming.sjll.My.adapter.SampleItemEditImageAdapter;
import com.ming.sjll.My.common.SampleEdit;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.manage.adapter.CostItemAdapter;
import com.ming.sjll.project.manage.adapter.SiteItemAdapter;
import com.rey.material.app.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * 费用明细
 */
public class CostFragment extends BaseFragment {


    OccupationList commOccList;
    ProjectCost dataList;
    LinearLayout horizontalOccupation;

    String projectId;

    private RecyclerView listView;
    CostItemAdapter timeUserMiddleAdapter;

    public static CostFragment newInstance(String project_id) {
        CostFragment fragment = new CostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", project_id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.project_manage_cost);
        initView();
    }

    List<String> imageList;
    SampleItemEditImageAdapter imageAdapter;
    private void initView() {
        projectId = getArguments().getString("projectId");

        getData();

        horizontalOccupation = (LinearLayout) findViewById(R.id.horizontalOccupation);
        listView = (RecyclerView) findViewById(R.id.listView);


        findViewById(R.id.add_occupation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_add_cost);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);

                 imageList = new LinkedList<>();
                imageAdapter = new SampleItemEditImageAdapter(imageList);
                RecyclerView recyclerview = bottomInterPasswordDialog.findViewById(R.id.recyclerview);
                recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerview.setAdapter(imageAdapter);
                imageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if (view.getId() == R.id.iv_delete) {
                            imageAdapter.removeData(position);
                        } else if (view.getId() == R.id.iv_add) {
                            int max = 7-imageList.size();
                            PictureSelector.create(CostFragment.this)
                                    .openGallery(PictureMimeType.ofImage()).maxSelectNum(max).isCamera(false)
                                    .forResult(PictureConfig.CHOOSE_REQUEST);
                        }
                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HttpParamsObject params = new HttpParamsObject();
                        params.setParam("projectId",projectId);
                        params.setParam("title",((TextView)bottomInterPasswordDialog.findViewById(R.id.title)).getText().toString());
                        params.setParam("price",((TextView)bottomInterPasswordDialog.findViewById(R.id.money)).getText().toString());
                        params.setParam("imgList",imageList);

                        loadingDailog.show();
                        new HttpUtil().sendRequest(Constant.PROJECT_COST_ADD_DETAIL, params, new CommonCallback() {
                            @Override
                            public void onSuccessJson(String data, int code) {
                                bottomInterPasswordDialog.hide();
                                ToastShow.s("添加成功");
                                getData();
                                loadingDailog.hide();

                            }
                        });
                    }
                });

                bottomInterPasswordDialog.show();
            }
        });


//        getOccupation();
//        getOccupationUserList();
    }

    private void getData()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId", projectId);
        new HttpUtil().sendRequest(Constant.PROJECT_COST_COST_DETAIL, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, ProjectCost.class);
                initData();

            }
        });
    }
    private void initData()
    {


        initHorizontal();
        initVertical();
    }

    private void initHorizontal() {
        horizontalOccupation.removeAllViews();
        for (int j = 0; j < dataList.getData().getDetailList().size(); j++) {
//            if (!dataList.getData().get(j).isIs_select()) {
//                continue;
//            }
            View listViewTemp = LayoutInflater.from(getContext()).inflate(R.layout.item_images_circular_50, null);
            listViewTemp.setTag(dataList.getData().getDetailList().get(j).getDetail_id());
            new ImageHelper().displayBackgroundLoading(listViewTemp.findViewById(R.id.image),
                    Constant.BASE_API + dataList.getData().getDetailList().get(j).getImg());

            listViewTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = v.getTag().toString();
                    for (int i = 0; i < dataList.getData().getDetailList().size(); i++) {
                        if (dataList.getData().getDetailList().get(i).getDetail_id().equals(id)) {
                            findViewById(R.id.svscrollouter).scrollTo(0, listView.getChildAt(i).getTop());
                        }
                    }
                }
            });

            horizontalOccupation.addView(listViewTemp);
        }


    }

    private void initVertical()
    {
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        timeUserMiddleAdapter = new CostItemAdapter(dataList.getData().getDetailList(), getActivity(), projectId);
        listView.setAdapter(timeUserMiddleAdapter);
        timeUserMiddleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.add:

                        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                        bottomInterPasswordDialog.setContentView(R.layout.dialog_project_change_cost);
                        bottomInterPasswordDialog.inDuration(300);
                        bottomInterPasswordDialog.outDuration(200);

                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.oldMoney)).setText(dataList.getData().getDetailList().get(position).getTotal_price());

                        bottomInterPasswordDialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HttpParamsObject params = new HttpParamsObject();
                                params.setParam("detailId",dataList.getData().getDetailList().get(position).getDetail_id());
                                params.setParam("revisedPrice",((TextView)bottomInterPasswordDialog.findViewById(R.id.title)).getText().toString());

                                loadingDailog.show();
                                new HttpUtil().sendRequest(Constant.PROJECT_COST_SAVE_PRICE, params, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {
                                        bottomInterPasswordDialog.hide();
                                        ToastShow.s("修改成功");
                                        dataList.getData().getDetailList().get(position).setTotal_price(((TextView)bottomInterPasswordDialog.findViewById(R.id.title)).getText().toString());
                                        timeUserMiddleAdapter.notifyDataSetChanged();
                                        loadingDailog.hide();

                                    }
                                });


                            }
                        });
                        bottomInterPasswordDialog.show();

                     break;
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
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

        }


    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(getActivity(), R.layout.project_manage_team);
////        setContentView(R.layout.project_manage_team);
////        initView();
//    }

}
