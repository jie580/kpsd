package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;

import java.util.List;

public class MoneyProjectAdapter extends BaseQuickAdapter<ProjectOrderListBean.DataBeanX, BaseViewHolder> {

    Activity context;
    String showType;
    public MoneyProjectAdapter(@Nullable List<ProjectOrderListBean.DataBeanX> data, Activity context, String showType) {
        super(R.layout.my_money_project_item, data);
        this.context = context;
        this.showType = showType;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectOrderListBean.DataBeanX dataBean) {


        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.image),
                Constant.BASE_IMAGE + dataBean.getBackground_image());

        baseViewHolder.setText(R.id.text1, dataBean.getDemand());
        baseViewHolder.setText(R.id.text2, dataBean.getBudget());
        baseViewHolder.setText(R.id.text3, dataBean.getDate_str());


        ImageView openInfo = baseViewHolder.getView(R.id.openInfo);
        ImageView sendInvoice = baseViewHolder.getView(R.id.sendInvoice);
        ImageView sendComplete = baseViewHolder.getView(R.id.sendComplete);
        ImageView makeInvoice = baseViewHolder.getView(R.id.makeInvoice);
        ImageView complete = baseViewHolder.getView(R.id.complete);



        openInfo.setVisibility(View.GONE);
        sendInvoice.setVisibility(View.GONE);
        sendComplete.setVisibility(View.GONE);
        makeInvoice.setVisibility(View.GONE);
        complete.setVisibility(View.GONE);

        Button icon = baseViewHolder.getView(R.id.icon);
        TextView title = baseViewHolder.getView(R.id.title);
        if(showType.equals("need"))
        {
            icon.setBackgroundResource(R.mipmap.ic_angle);
            title.setText("收益");


            if(dataBean.getInvoice_status().equals("1"))
            {
                openInfo.setVisibility(View.VISIBLE);
                sendInvoice.setVisibility(View.VISIBLE);
            }
            else if(dataBean.getInvoice_status().equals("2"))
            {
                openInfo.setVisibility(View.VISIBLE);
                sendComplete.setVisibility(View.VISIBLE);
            }
        }
        else if(showType.equals("moneyDetail"))
        {
            if(dataBean.getMoney_type().equals("1"))
            {
                icon.setBackgroundResource(R.mipmap.ic_angle);
                title.setText("收益");
            }
            else
            {
                icon.setBackgroundResource(R.mipmap.ic_angle2);
                title.setText("支出");
            }
            baseViewHolder.getView(R.id.warp).getLayoutParams().height = Tools.px2dip(context,110);
        }
        else
        {
            icon.setBackgroundResource(R.mipmap.ic_angle2);
            title.setText("支出");

            if(dataBean.getInvoice_status().equals("0"))
            {
                makeInvoice.setVisibility(View.VISIBLE);
            }
            else if(dataBean.getInvoice_status().equals("1"))
            {
                openInfo.setVisibility(View.VISIBLE);
            }
            else if(dataBean.getInvoice_status().equals("2"))
            {
                complete.setVisibility(View.VISIBLE);
            }


        }


        baseViewHolder.addOnClickListener(R.id.openInfo);
        baseViewHolder.addOnClickListener(R.id.sendInvoice);
        baseViewHolder.addOnClickListener(R.id.sendComplete);
        baseViewHolder.addOnClickListener(R.id.makeInvoice);
        baseViewHolder.addOnClickListener(R.id.complete);
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
