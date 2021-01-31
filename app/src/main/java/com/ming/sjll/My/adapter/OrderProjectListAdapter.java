package com.ming.sjll.My.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class OrderProjectListAdapter extends BaseQuickAdapter<ProjectOrderListBean.DataBeanX, BaseViewHolder> {

    Context context;
    public OrderProjectListAdapter(@Nullable List<ProjectOrderListBean.DataBeanX> data, Context context) {
        super(R.layout.item_project_payment, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectOrderListBean.DataBeanX dataBean) {


        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.projectBg),
                Constant.BASE_IMAGE + dataBean.getBackground_image());

        baseViewHolder.setText(R.id.projectDemand, dataBean.getDemand());
        baseViewHolder.setText(R.id.projectBrand, dataBean.getBrand());


        baseViewHolder.addOnClickListener(R.id.openInfo);
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
