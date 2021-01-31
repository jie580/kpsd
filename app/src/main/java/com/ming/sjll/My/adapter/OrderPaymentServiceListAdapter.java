package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class OrderPaymentServiceListAdapter extends BaseQuickAdapter<GoodsItem.DataBean, BaseViewHolder> {

    Activity context;
    public OrderPaymentServiceListAdapter(@Nullable List<GoodsItem.DataBean> data, Activity context) {
        super(R.layout.my_order_payment_goods_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GoodsItem.DataBean dataBean) {


        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.image),
                Constant.BASE_IMAGE + dataBean.getCover_img());

        baseViewHolder.setText(R.id.text1, dataBean.getName());
        baseViewHolder.setText(R.id.text2, dataBean.getTotal_price());
        baseViewHolder.setText(R.id.text2Tip, "交易金额");
        baseViewHolder.setText(R.id.text3, dataBean.getDate_str());



        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.headImage),
                Constant.BASE_IMAGE + dataBean.getDefault_avatar());
        baseViewHolder.setText(R.id.name, dataBean.getIm_name());
        baseViewHolder.setText(R.id.occupation, dataBean.getIm_city());



        baseViewHolder.getView(R.id.warp).setVisibility(View.VISIBLE);
        baseViewHolder.getView(R.id.btns).setVisibility(View.GONE);


         if(dataBean.getStatus().equals("0"))
        {

            baseViewHolder.setText(R.id.countDown, "接单中");
            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_gray_50_corner);
        }
        else if(dataBean.getStatus().equals("1"))
        {
            baseViewHolder.setText(R.id.countDown, "项目进行中");
            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_gray_50_corner);
        }
         else if(dataBean.getStatus().equals("2"))
         {
             baseViewHolder.setText(R.id.countDown, "确认完成");
             baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_blue_50_corner_2);
         }
         else if(dataBean.getStatus().equals("3"))
         {
             baseViewHolder.setText(R.id.countDown, "已完成");
             baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_gray_50_corner);
         }
        else if(dataBean.getStatus().equals("4"))
        {
            baseViewHolder.getView(R.id.countDown).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.countDown, "申诉中");
            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_red_30_corner);
        }



        baseViewHolder.addOnClickListener(R.id.openInfo);
        baseViewHolder.addOnClickListener(R.id.contentText);
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
