package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heartfor.heartvideo.video.HeartVideo;
import com.heartfor.heartvideo.video.HeartVideoInfo;
import com.heartfor.heartvideo.video.VideoControl;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class OrderPersonageListAdapter extends BaseQuickAdapter<ProjectOrderListBean.DataBeanX, BaseViewHolder> {

    Context context;
    public OrderPersonageListAdapter(@Nullable List<ProjectOrderListBean.DataBeanX> data, Context context) {
        super(R.layout.my_order_personage_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectOrderListBean.DataBeanX dataBean) {


        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.image),
                Constant.BASE_IMAGE + dataBean.getBackground_image());

        baseViewHolder.setText(R.id.text1, dataBean.getDemand());
        baseViewHolder.setText(R.id.text2, dataBean.getTitle());
        baseViewHolder.setText(R.id.text3, dataBean.getDate_str());



        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.headImage),
                Constant.BASE_IMAGE + dataBean.getDefault_avatar());
        baseViewHolder.setText(R.id.name, dataBean.getIm_name());
        baseViewHolder.setText(R.id.occupation, dataBean.getIm_city());



        baseViewHolder.getView(R.id.warp).setVisibility(View.VISIBLE);
        if(dataBean.getStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) > 0)
        {
            baseViewHolder.setText(R.id.countDown, "确认加入 | "+calculateTime(Integer.parseInt(dataBean.getCountDown())));
            baseViewHolder.getView(R.id.countDown).setBackgroundResource(R.drawable.shape_red_30_corner);
        }
        else if(dataBean.getStatus().equals("2") || ( dataBean.getStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) <= 0))
        {
            baseViewHolder.getView(R.id.warp).setVisibility(View.GONE);
        }
        else if(dataBean.getStatus().equals("4"))
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


        baseViewHolder.addOnClickListener(R.id.countDown);
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
