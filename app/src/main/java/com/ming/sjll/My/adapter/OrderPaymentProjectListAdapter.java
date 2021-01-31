package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

import io.rong.imkit.utilities.RongUtils;

public class OrderPaymentProjectListAdapter extends BaseQuickAdapter<ProjectOrderListBean.DataBeanX, BaseViewHolder> {

    Activity context;
    public OrderPaymentProjectListAdapter(@Nullable List<ProjectOrderListBean.DataBeanX> data, Activity context) {
        super(R.layout.my_order_payment_project_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectOrderListBean.DataBeanX dataBean) {


        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.image),
                Constant.BASE_IMAGE + dataBean.getBackground_image());

        baseViewHolder.setText(R.id.text1, dataBean.getDemand());
        baseViewHolder.setText(R.id.text2, dataBean.getBudget());
        baseViewHolder.setText(R.id.text2Tip, "预计支出");
        baseViewHolder.setText(R.id.text3, dataBean.getDate_str());



        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.headImage),
                Constant.BASE_IMAGE + dataBean.getDefault_avatar());
        baseViewHolder.setText(R.id.name, dataBean.getIm_name());
        baseViewHolder.setText(R.id.occupation, dataBean.getIm_city());

        LinearLayout w = baseViewHolder.getView(R.id.warp);
//        Log.e("原本高", w.getLayoutParams().height+"");
//        w.getLayoutParams().height = RongUtils.dip2px(249.0F);
        w.setBackgroundResource(R.mipmap.line_input_bg_big8);
//        Log.d("修改后高", w.getLayoutParams().height+"");



        baseViewHolder.getView(R.id.countDown).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.btns).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.fillWrap).setVisibility(View.GONE);

        if(dataBean.getStatus().equals("1"))
        {
            baseViewHolder.getView(R.id.countDown).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.countDown, "项目进行中");
            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_gray_50_corner);
        }
        else if(dataBean.getStatus().equals("2"))
        {
            baseViewHolder.getView(R.id.btns).setVisibility(View.VISIBLE);
        }
        else if(dataBean.getStatus().equals("3") || dataBean.getStatus().equals("4"))
        {
//            w.getLayoutParams().height = RongUtils.dip2px(386.0F);
            w.setBackgroundResource(R.mipmap.line_input_bg_big7);

            baseViewHolder.getView(R.id.countDown).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.countDown, "项目进行中");
            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_gray_50_corner);

            baseViewHolder.getView(R.id.fillWrap).setVisibility(View.VISIBLE);

            baseViewHolder.setText(R.id.revised_price, dataBean.getRevised_price());
            baseViewHolder.setText(R.id.balance, dataBean.getBalance());

            baseViewHolder.getView(R.id.refundWrap).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.FillingMoney).setVisibility(View.GONE);
            if(dataBean.getStatus().equals("3"))
            {
                baseViewHolder.getView(R.id.FillingMoney).setVisibility(View.VISIBLE);
                baseViewHolder.setText(R.id.balanceTitle, "应补薪酬");
            }
            else
            {
                baseViewHolder.getView(R.id.refundWrap).setVisibility(View.VISIBLE);
                baseViewHolder.setText(R.id.balanceTitle, "应退薪酬");
            }


        }

//        baseViewHolder.getView(R.id.warp).setVisibility(View.VISIBLE);
//        if(dataBean.getStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) > 0)
//        {
//            baseViewHolder.setText(R.id.countDown, "确认加入 | "+calculateTime(Integer.parseInt(dataBean.getCountDown())));
//            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_red_30_corner);
//        }
//        else if(dataBean.getStatus().equals("0") || ( dataBean.getStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) <= 0))
//        {
//            baseViewHolder.getView(R.id.warp).setVisibility(View.GONE);
//        }
//        else if(dataBean.getStatus().equals("2"))
//        {
//            baseViewHolder.setText(R.id.countDown, "确认回仓");
//            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_blue_50_corner_2);
//        }
//        else if(dataBean.getStatus().equals("4"))
//        {
//            baseViewHolder.setText(R.id.countDown, "申诉中");
//            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_red_30_corner);
//        }
//        else
//        {
//            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_gray_50_corner);
//            baseViewHolder.setText(R.id.countDown, "租赁中");
//
//        }


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
