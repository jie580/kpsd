package com.ming.sjll.project.manage.adapter;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import java.util.List;

public class TimeUserAdapter extends BaseQuickAdapter<GetInfoDateListBean.DataBean.DataBeanX, BaseViewHolder> {


    public boolean canEdit = true;

    Activity mActivity;
    String projectId;
    public TimeUserAdapter(@Nullable List<GetInfoDateListBean.DataBean.DataBeanX> data, Activity mActivity, String projectId ) {
        super(R.layout.project_manage_time_item, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoDateListBean.DataBean.DataBeanX dataBean) {

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.icon),
                Constant.BASE_IMAGE + dataBean.getDefault_avatar());

        if (dataBean.getIs_conflict().equals("true")) {
            baseViewHolder.getView(R.id.isConflict).setVisibility(View.VISIBLE);
        } else {
            baseViewHolder.getView(R.id.isConflict).setVisibility(View.GONE);
        }
        baseViewHolder.setText(R.id.name, dataBean.getName());
        baseViewHolder.setText(R.id.occupation, dataBean.getOccupation());
        baseViewHolder.setText(R.id.money, dataBean.getTotal());


        baseViewHolder.getView(R.id.noneData).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.listView).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.listViewHorizontal).setVisibility(View.GONE);

        if (dataBean.getDateTime().size() == 0) {
//            没有数据
            baseViewHolder.getView(R.id.noneData).setVisibility(View.VISIBLE);
        } else {
            if (dataBean.getType().equals("1")) {
//                显示时间
                baseViewHolder.getView(R.id.listView).setVisibility(View.VISIBLE);
                RecyclerView listView = baseViewHolder.getView(R.id.listView);
                listView.setLayoutManager(new LinearLayoutManager(mContext));
                TimeUserTiemLineAdapter timeUserTiemLineAdapter = new TimeUserTiemLineAdapter(dataBean.getDateTime(), mActivity, projectId);
                listView.setAdapter(timeUserTiemLineAdapter);


            } else {
//                显示日期
                baseViewHolder.getView(R.id.listViewHorizontal).setVisibility(View.VISIBLE);
                for (int i = 0; i < dataBean.getDateTime().size(); i++) {

                    View listViewTemp = LayoutInflater.from(mContext).inflate(R.layout.project_manage_time_text, null);
                    ((TextView) listViewTemp.findViewById(R.id.text)).setText(dataBean.getDateTime().get(i).getStart_date());
                    ((LinearLayout) baseViewHolder.getView(R.id.userLayout)).addView(listViewTemp);
                }

            }
        }


        baseViewHolder.addOnClickListener(R.id.add);
    }


}
