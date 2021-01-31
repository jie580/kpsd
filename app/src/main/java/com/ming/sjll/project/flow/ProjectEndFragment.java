package com.ming.sjll.project.flow;

import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectFlowWorkSelectBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.flow.adapter.SampleSelectItemAdapter;

import java.io.File;

/**
 * 项目结束
 */
public class ProjectEndFragment extends BaseFragment {



    String projectId;

    private ProjectFlowWorkSelectBean mData;
    public static ProjectEndFragment newInstance(String project_id) {
        ProjectEndFragment fragment = new ProjectEndFragment();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", project_id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.project_flow_project_end_fragment);
        initView();
    }

    private void initView() {
        projectId = getArguments().getString("projectId");

        new ContextWrapper(getContext()).getCacheDir();

        findViewById(R.id.toEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDailog.show();
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("projectId",projectId);
                param.setParam("toEmail", ((TextView)findViewById(R.id.toEmailText)).getText().toString());
                new HttpUtil().sendRequest(Constant.PROJECT_FLOW_TO_EMAIL, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("发送成");
                    }

                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });
            }
        });


    }


}
