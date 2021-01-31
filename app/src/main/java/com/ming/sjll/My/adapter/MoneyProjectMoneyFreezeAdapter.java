package com.ming.sjll.My.adapter;

import android.app.Activity;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

import javax.annotation.Nullable;

public class MoneyProjectMoneyFreezeAdapter extends BaseQuickAdapter<ProjectOrderListBean.DataBeanX, BaseViewHolder> {

    Activity context;
    String showType;
    public MoneyProjectMoneyFreezeAdapter(@Nullable List<ProjectOrderListBean.DataBeanX> data, Activity context) {
        super(R.layout.money_predict_project_item, data);
        this.context = context;
        this.showType = showType;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectOrderListBean.DataBeanX dataBean) {


        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.projectBg),
                Constant.BASE_IMAGE + dataBean.getBackground_image());

        baseViewHolder.setText(R.id.projectDemand, dataBean.getDemand());
        baseViewHolder.setText(R.id.projectBrand, dataBean.getBrand());
        baseViewHolder.setText(R.id.projectBudget, dataBean.getBudget());
        baseViewHolder.getView(R.id.freezeBtn).setVisibility(View.VISIBLE);

        baseViewHolder.setText(R.id.projectTitle, "冻结资金");

        ImageView flow = baseViewHolder.getView(R.id.flow);
        if(dataBean.getStatus().equals("1"))
        {
            flow.setBackgroundResource(R.mipmap.money_project_flow2);
        }
        else
        {
            flow.setBackgroundResource(R.mipmap.money_project_flow1);
        }


    }

}
