package com.ming.sjll.project.flow;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBeans;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.flow.adapter.SampleUploadItemAdapter;

/**
 * 样片处理
 */
public class WorkUploadFragment extends BaseFragment {



    String projectId;

    private GetInfoBeans mData;
    public static WorkUploadFragment newInstance(String project_id) {
        WorkUploadFragment fragment = new WorkUploadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", project_id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.project_flow_upload_fragment);
        initView();
    }

    private void initView() {
        projectId = getArguments().getString("projectId");

        findViewById(R.id.copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("playerId", ((TextView)findViewById(R.id.copyText)).getText());
                clipboardManager.setPrimaryClip(clipData);
                ToastShow.s("复制成功");

            }
        });

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId",projectId);
        param.setParam("flowIdentification","sample_upload");
        new HttpUtil().sendRequest(Constant.PROJECT_FLOW_GET_FLOW_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                mData = new Gson().fromJson(data, GetInfoBeans.class);
                RecyclerView listView = (RecyclerView)findViewById(R.id.listView);
                listView.setLayoutManager(new LinearLayoutManager(getContext()));
                SampleUploadItemAdapter sampleUploadItemAdapter = new SampleUploadItemAdapter(mData.getData(),getActivity(), projectId);
                listView.setAdapter(sampleUploadItemAdapter);
                sampleUploadItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId())
                        {
                            case R.id.openChar:
                                RongImUtils.privateChat(getActivity(),mData.getData().get(position).getIm_uid(),mData.getData().get(position).getIm_name());
                                break;
                        }
                    }
                });


            }
        });

    }


}
