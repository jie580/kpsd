package com.ming.sjll.project.flow;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBeans;
import com.ming.sjll.Bean.ProjectFlowWorkSelectBean;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.flow.adapter.SampleSelectItemAdapter;
import com.ming.sjll.project.flow.adapter.SampleUploadItemAdapter;

/**
 * 作品选择
 */
public class SampleSelectFragment extends BaseFragment {



    String projectId;
//    是否是样片选线
    String showType = "1";

    private ProjectFlowWorkSelectBean mData;
    public static SampleSelectFragment newInstance(String project_id) {
        SampleSelectFragment fragment = new SampleSelectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", project_id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.project_flow_select_fragment);
        initView();
    }

    private void initView() {
        projectId = getArguments().getString("projectId");


        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId",projectId);
        param.setParam("flowIdentification","work_selected");
        new HttpUtil().sendRequest(Constant.PROJECT_FLOW_GET_FLOW_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                mData = new Gson().fromJson(data, ProjectFlowWorkSelectBean.class);

                showImage();

            }
        });
        ImageView guide1 = (ImageView)findViewById(R.id.guide1);
        ImageView guide2 = (ImageView)findViewById(R.id.guide2);
        ImageView guide3 = (ImageView)findViewById(R.id.guide3);
        ImageView guide4 = (ImageView)findViewById(R.id.guide4);

        guide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide1.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide1);
                guide2.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide2);
                guide3.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide3);
                guide4.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide4);
                guide1.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide1_select);

                showType = "1";
                showImage();
            }
        });
        guide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide1.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide1);
                guide2.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide2);
                guide3.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide3);
                guide4.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide4);
                guide2.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide2_select);
                showType = "2";
                showImage();
            }
        });
        guide3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide1.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide1);
                guide2.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide2);
                guide3.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide3);
                guide4.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide4);
                guide3.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide3_select);
                showType = "3";
                showImage();
            }
        });
        guide4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guide1.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide1);
                guide2.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide2);
                guide3.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide3);
                guide4.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide4);
                guide4.setBackgroundResource(R.mipmap.ic_project_flow_select_work_guide4_select);
                showType = "4";
                showImage();
            }
        });


    }

    private void showImage()
    {

        RecyclerView listView = (RecyclerView)findViewById(R.id.listView);
//        listView.setLayoutManager(new GridLayoutManager(mContext, 2));\
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        SampleSelectItemAdapter sampleUploadItemAdapter = new SampleSelectItemAdapter(mData.getData(),getActivity(), projectId,showType);
        listView.setAdapter(sampleUploadItemAdapter);
        sampleUploadItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                loadingDailog.show();
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("projectId",projectId);
                param.setParam("imgId",mData.getData().get(position).getImg_id());

                String url = Constant.PROJECT_FLOW_SELECT_WORK;
                if(showType.equals("4"))
                {
                    url = Constant.PROJECT_FLOW_SET_SAMPLE;
                }
                new HttpUtil().sendRequest(url, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        if(showType.equals("4"))
                        {
                            if(mData.getData().get(position).getIs_sample().equals("1"))
                            {
                                mData.getData().get(position).setIs_sample("0");
                            }
                            else
                            {
                                mData.getData().get(position).setIs_sample("1");
                            }
                        }
                        else
                        {
                            if(mData.getData().get(position).getIs_select().equals("1"))
                            {
                                mData.getData().get(position).setIs_select("0");
                                mData.getData().get(position).setIs_sample("0");
                            }
                            else
                            {
                                mData.getData().get(position).setIs_select("1");
                            }
                        }
                        sampleUploadItemAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });

//                switch (view.getId())
//                {
//                    case R.id.checkSelect:
//                        RongImUtils.privateChat(getActivity(),mData.getData().get(position).getIm_uid(),mData.getData().get(position).getIm_name());
//                        break;
//                }
            }
        });
    }


}
