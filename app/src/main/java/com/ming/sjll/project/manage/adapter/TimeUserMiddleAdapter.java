package com.ming.sjll.project.manage.adapter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.project.manage.TimeEditActivity;

import java.util.List;

public class TimeUserMiddleAdapter extends BaseQuickAdapter<GetInfoDateListBean.DataBean, BaseViewHolder> {


    public boolean canEdit = true;

    Activity mActivity;
    String projectId;
    public TimeUserMiddleAdapter(@Nullable List<GetInfoDateListBean.DataBean> data, Activity mActivity, String projectId ) {
        super(R.layout.my_collect_user, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoDateListBean.DataBean dataBean) {

        RecyclerView listView = baseViewHolder.getView(R.id.recyclerList);
        listView.setLayoutManager(new LinearLayoutManager(mContext));
        TimeUserAdapter teamOccupationUserDeleteAdapter = new TimeUserAdapter(dataBean.getOccupationList(), mActivity, projectId);
        listView.setAdapter(teamOccupationUserDeleteAdapter);

        teamOccupationUserDeleteAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("projectId", projectId);
                bundle.putString("occupationId", dataBean.getOccupationList().get(position).getOccupation_id());
                bundle.putString("userId", dataBean.getOccupationList().get(position).getUid());
                Intent intent = new Intent(mActivity,TimeEditActivity.class);
                intent.putExtras(bundle);
                mActivity.startActivityForResult(intent, 46);
//                Tools.jump(mActivity, TimeEditActivity.class, bundle, false);
            }
        });

    }


}
