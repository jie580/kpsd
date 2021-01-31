package com.ming.sjll.project.manage.adapter;


import android.app.Activity;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.R;
import com.ming.sjll.base.utils.Tools;

import java.util.List;

public class TimeUserTiemEditLineAdapter extends BaseQuickAdapter<GetInfoDateListBean.DataBean.DataBeanY, BaseViewHolder> {


    public boolean canEdit = true;

    Activity mActivity;
    String projectId;
    String type="";
    public TimeUserTiemEditLineAdapter(@Nullable List<GetInfoDateListBean.DataBean.DataBeanY> data, Activity mActivity, String projectId,String type) {
        super(R.layout.project_manage_time_line_bg, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
        this.type = type;
    }
    public TimeUserTiemEditLineAdapter(@Nullable List<GetInfoDateListBean.DataBean.DataBeanY> data, Activity mActivity, String projectId ) {
        super(R.layout.project_manage_time_line_bg, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
    }
    public TimeUserTiemEditLineAdapter(@Nullable List<GetInfoDateListBean.DataBean.DataBeanY> data, Activity mActivity) {
        super(R.layout.project_manage_time_line_bg, data);
        this.mActivity = mActivity;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoDateListBean.DataBean.DataBeanY dataBean) {

        if(type.equals("goods"))
        {
            baseViewHolder.setText(R.id.title, "租赁时间");
        }else if(type.equals("service"))
        {
            baseViewHolder.setText(R.id.title, "服务时间");
        }
        else
        {
            baseViewHolder.setText(R.id.title, "时间选择");
        }
        try {
            baseViewHolder.setText(R.id.text, Tools.getDateformatDay(Long.parseLong(Tools.getLongformat(dataBean.getDate_time()))));
        }catch (Exception e)
        {
            baseViewHolder.setText(R.id.text, Tools.getDateformatDay(Long.parseLong(dataBean.getDate_time())));
        }

        if(dataBean.getStart_time() == null || dataBean.getStart_time().equals(""))
        {
            baseViewHolder.setText(R.id.startTime, "开始");
        }
        else
        {
            baseViewHolder.setText(R.id.startTime, Tools.getDateformatHourMinute(Long.parseLong(dataBean.getStart_time())));
        }
        if(dataBean.getEnd_time() == null || dataBean.getEnd_time().equals(""))
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
