package com.ming.sjll.project.manage.adapter;


import android.app.Activity;
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
import com.ming.sjll.Bean.ProjectCost;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class CostItemAdapter extends BaseQuickAdapter<ProjectCost.DataBeanX, BaseViewHolder> {


    public boolean canEdit = true;

    Activity mActivity;
    String projectId;
    public CostItemAdapter(@Nullable List<ProjectCost.DataBeanX> data, Activity mActivity, String projectId ) {
        super(R.layout.project_manage_cost_item, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectCost.DataBeanX dataBean) {

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.icon),
                Constant.BASE_IMAGE + dataBean.getImg());
        baseViewHolder.setText(R.id.name, dataBean.getTarget_name());
        baseViewHolder.setText(R.id.occupation, dataBean.getClass_name());


        baseViewHolder.getView(R.id.noneData).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.moneyWrap).setVisibility(View.GONE);
        if(dataBean.getTotal_price() == null || dataBean.getTotal_price().equals(""))
        {
            baseViewHolder.getView(R.id.noneData).setVisibility(View.VISIBLE);
        }
        else
        {
            baseViewHolder.getView(R.id.moneyWrap).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.money, dataBean.getTotal_price());
        }


        baseViewHolder.addOnClickListener(R.id.add);
    }


}
