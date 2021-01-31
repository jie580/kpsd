package com.ming.sjll.project.manage.adapter;


import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;

import java.util.List;

public class TimeUserTiemLineAdapter extends BaseQuickAdapter<GetInfoDateListBean.DataBean.DataBeanY, BaseViewHolder> {


    public boolean canEdit = true;

    Activity mActivity;
    String projectId;
    public TimeUserTiemLineAdapter(@Nullable List<GetInfoDateListBean.DataBean.DataBeanY> data, Activity mActivity, String projectId ) {
        super(R.layout.project_manage_time_line, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoDateListBean.DataBean.DataBeanY dataBean) {

        baseViewHolder.setText(R.id.text, dataBean.getStart_date());
        if(dataBean.getStart_time() == null || dataBean.getStart_time().equals("0"))
        {
            baseViewHolder.setText(R.id.startTime, "开始");
        }
        else
        {
            baseViewHolder.setText(R.id.startTime, Tools.getDateformatHourMinute(Long.parseLong(dataBean.getStart_time())));
        }
        if(dataBean.getEnd_time() == null || dataBean.getEnd_time().equals("0"))
        {
            baseViewHolder.setText(R.id.endTime, "结束");
        }
        else
        {
            baseViewHolder.setText(R.id.endTime, Tools.getDateformatHourMinute(Long.parseLong(dataBean.getEnd_time())));
        }

        baseViewHolder.addOnClickListener(R.id.startTime);
        baseViewHolder.addOnClickListener(R.id.endTime);

    }


}
