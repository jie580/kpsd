package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
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
import com.ming.sjll.project.ReleaseProjectNext;

import java.util.List;

public class OrderCompanyListAdapter extends BaseQuickAdapter<ProjectOrderListBean.DataBeanX, BaseViewHolder> {

    Activity context;
    public OrderCompanyListAdapter(@Nullable List<ProjectOrderListBean.DataBeanX> data, Activity context) {
        super(R.layout.my_order_company_order_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectOrderListBean.DataBeanX dataBean) {


        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.image),
                Constant.BASE_IMAGE + dataBean.getBackground_image());

        baseViewHolder.setText(R.id.text1, dataBean.getDemand());
        baseViewHolder.setText(R.id.text2, dataBean.getBudget());
        baseViewHolder.setText(R.id.text3, dataBean.getDate_str());



        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.headImage),
                Constant.BASE_IMAGE + dataBean.getDefault_avatar());
        baseViewHolder.setText(R.id.name, dataBean.getIm_name());
        baseViewHolder.setText(R.id.occupation, dataBean.getIm_city());



        baseViewHolder.getView(R.id.warp).setVisibility(View.VISIBLE);
        if(dataBean.getMemberStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) > 0)
        {
            baseViewHolder.setText(R.id.countDown, "确认加入 | "+calculateTime(Integer.parseInt(dataBean.getCountDown())));
            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_red_30_corner);
        }
        else if(dataBean.getMemberStatus().equals("2") || ( dataBean.getMemberStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) <= 0))
        {
            baseViewHolder.getView(R.id.warp).setVisibility(View.GONE);
        }
        else if(dataBean.getMemberStatus().equals("4"))
        {
            baseViewHolder.setText(R.id.countDown, "申诉中");
            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_red_30_corner);
        }
        else
        {
            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_gray_50_corner);
            if(dataBean.getIs_pay().equals("1"))
            {
                baseViewHolder.setText(R.id.countDown, "项目执行中");
            }
            else
            {
                baseViewHolder.setText(R.id.countDown, "项目统筹中");
            }

        }

        LinearLayout linearLayout = baseViewHolder.getView(R.id.linearLayout);
        for (int i = 0; i < dataBean.getMember().size(); i++) {
            LayoutInflater l_Inflater = LayoutInflater.from(context);
            View view = l_Inflater.inflate(R.layout.item_user, null);
            view.setTag(dataBean.getMember().get(i).getUid());
            new ImageHelper().displayBackgroundLoading(view.findViewById(R.id.image),
                    Constant.BASE_IMAGE + dataBean.getMember().get(i).getDefault_avatar());
            ((TextView)view.findViewById(R.id.name)).setText(dataBean.getMember().get(i).getName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String indexOf = v.getTag().toString();
                    for (int j = 0; j < dataBean.getMember().size(); j++) {
                        if (dataBean.getMember().get(j).getUid().equals(indexOf)) {
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", dataBean.getMember().get(j).getUid());
                            Tools.jump(context, HomepageActivity.class, bundle,false);
                        }
                    }

                }
            });

            linearLayout.addView(view);
        }


        baseViewHolder.addOnClickListener(R.id.countDown);
        baseViewHolder.addOnClickListener(R.id.moneyBtn);
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
