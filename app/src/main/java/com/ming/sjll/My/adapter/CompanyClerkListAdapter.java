package com.ming.sjll.My.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class CompanyClerkListAdapter extends BaseQuickAdapter<GetInfoBean.DataBean, BaseViewHolder> {



    public CompanyClerkListAdapter(@Nullable List<GetInfoBean.DataBean> data){
        super(R.layout.my_schedule_user_occupation_item, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean dataBean) {

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.imageItem),
                Constant.BASE_API + dataBean.getDefault_avatar());
//
//
        ((TextView)baseViewHolder.getView(R.id.name)).setText(dataBean.getName());
        ((TextView)baseViewHolder.getView(R.id.occupation)).setText( dataBean.getOccupation());

        ImageView bgWait = baseViewHolder.getView(R.id.bgWait);
        ImageView bgNo = baseViewHolder.getView(R.id.bgNo);
        ImageView imageItemWrap = baseViewHolder.getView(R.id.imageItemWrap);


        bgWait.setVisibility(View.GONE);
        bgNo.setVisibility(View.GONE);
        imageItemWrap.setBackgroundResource(R.mipmap.bg_user2);

        if(dataBean.getStatus().equals("0"))
        {
            imageItemWrap.setBackgroundResource(R.mipmap.bg_user_gray2);
            bgWait.setVisibility(View.VISIBLE);
        }
        else if(dataBean.getStatus().equals("2"))
        {
            bgNo.setVisibility(View.VISIBLE);
        }

        if(dataBean.isSelect())
        {
            imageItemWrap.setBackgroundResource(R.mipmap.bg_user_blue2);
        }

//        if(isShowDelete)
//        {
//            baseViewHolder.getView(R.id.delete).setVisibility(View.VISIBLE);
//        }
//        else {
//            baseViewHolder.getView(R.id.delete).setVisibility(View.GONE);
//        }
////
////
////        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.headImage),
////                Constant.BASE_API + dataBean.getDefault_avatar());
//
        baseViewHolder.addOnClickListener(R.id.imageItemWrap);
//        baseViewHolder.addOnClickListener(R.id.delete);
    }


}
