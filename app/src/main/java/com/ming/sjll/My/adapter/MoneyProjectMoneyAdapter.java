package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;

import java.util.List;

public class MoneyProjectMoneyAdapter extends BaseQuickAdapter<ProjectOrderListBean.DataBeanX, BaseViewHolder> {

    Activity context;
    String showType;
    public MoneyProjectMoneyAdapter(@Nullable List<ProjectOrderListBean.DataBeanX> data, Activity context) {
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


    private String calculateTime(int diff)
    {
        long hours = diff /  60 ;
        long minutes = diff - hours*60;
//        long days = diff / ( 60 * 60 * 24);
//        long hours = (diff-days*( 60 * 60 * 24))/( 60 * 60);
//        long minutes = (diff-hours*( 60 * 60 * 24)-hours*( 60 * 60))/( 60);
        return hours + "m" + minutes + "s";

    }

}
