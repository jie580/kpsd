package com.ming.sjll.project.flow;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.ming.sjll.Bean.GetInfoBeans;
import com.ming.sjll.Bean.ProjectCost;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.My.adapter.SampleItemEditImageAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.flow.adapter.SampleUploadItemAdapter;
import com.ming.sjll.project.manage.CostFragment;
import com.ming.sjll.project.manage.adapter.CostItemAdapter;
import com.rey.material.app.BottomSheetDialog;

import java.util.LinkedList;
import java.util.List;

/**
 * 费用调整
 */
public class MoneyAdjustFragment extends BaseFragment {



    String projectId;
    private RecyclerView listView;
    CostItemAdapter timeUserMiddleAdapter;
    List<String> imageList;
    private ProjectCost mData;
    SampleItemEditImageAdapter imageAdapter;

    public static MoneyAdjustFragment newInstance(String project_id) {
        MoneyAdjustFragment fragment = new MoneyAdjustFragment();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", project_id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.project_flow_money_adjust_fragment);
        initView();
    }

    private void initView() {
        projectId = getArguments().getString("projectId");
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
                            PictureSelector.create(getActivity())
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


        findViewById(R.id.sendProjectMoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("projectId",projectId);
                new HttpUtil().sendRequest(Constant.PROJECT_FLOW_SEND_ORDER, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("发送成功");
                    }
                });
            }
        });

        getData();

    }

    private void getData()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId",projectId);
        param.setParam("flowIdentification","personnel_time");
        new HttpUtil().sendRequest(Constant.PROJECT_FLOW_GET_FLOW_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                mData = new Gson().fromJson(data, ProjectCost.class);
                ((TextView)findViewById(R.id.money1)).setText(mData.getData().getPayMoney());
                ((TextView)findViewById(R.id.money2)).setText(mData.getData().getTotalPrice());
                ((TextView)findViewById(R.id.money3)).setText(mData.getData().getUnpaid());
                initVertical();

            }
        });
    }


    private void initVertical()
    {
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        timeUserMiddleAdapter = new CostItemAdapter(mData.getData().getDetailList(), getActivity(), projectId);
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

                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.oldMoney)).setText(mData.getData().getDetailList().get(position).getTotal_price());

                        bottomInterPasswordDialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                HttpParamsObject params = new HttpParamsObject();
                                params.setParam("detailId",mData.getData().getDetailList().get(position).getDetail_id());
                                params.setParam("revisedPrice",((TextView)bottomInterPasswordDialog.findViewById(R.id.title)).getText().toString());

                                loadingDailog.show();
                                new HttpUtil().sendRequest(Constant.PROJECT_COST_SAVE_PRICE, params, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {
                                        bottomInterPasswordDialog.hide();
                                        ToastShow.s("修改成功");
                                        getData();
//                                        mData.getData().getDetailList().get(position).setTotal_price(((TextView)bottomInterPasswordDialog.findViewById(R.id.title)).getText().toString());
//                                        timeUserMiddleAdapter.notifyDataSetChanged();
//                                        loadingDailog.hide();

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


}
