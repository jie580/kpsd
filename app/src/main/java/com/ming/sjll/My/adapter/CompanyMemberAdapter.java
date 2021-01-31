package com.ming.sjll.My.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class CompanyMemberAdapter extends BaseQuickAdapter<GetInfoBean.DataBean, BaseViewHolder> {

    public CompanyMemberAdapter(@Nullable List<GetInfoBean.DataBean> data){
        super(R.layout.user_item_company_menber, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean dataBean) {


        ((TextView)baseViewHolder.getView(R.id.name)).setText(dataBean.getName());
        ((TextView)baseViewHolder.getView(R.id.personalized)).setText( dataBean.getOccupation());
        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.headImage),
                Constant.BASE_API + dataBean.getDefault_avatar());

        RelativeLayout wrap = baseViewHolder.getView(R.id.wrap);
        TextView status = baseViewHolder.getView(R.id.status);
        wrap.setBackgroundResource(R.drawable.shape_white_10_corner);
        status.setVisibility(View.GONE);

        if(dataBean.getStatus() == null)
        {

        }
        else if(dataBean.getStatus().equals("0"))
        {
            wrap.setBackgroundResource(R.drawable.dotted_gray_2);
            status.setVisibility(View.VISIBLE);
            status.setText("等待对方确认");
            status.setTextColor(Color.parseColor("#9BA6BC"));
        }
        else if(dataBean.getStatus().equals("2"))
        {
            status.setVisibility(View.VISIBLE);
            wrap.setBackgroundResource(R.drawable.dotted_red_2);
            status.setText("对方未同意加入");
            status.setTextColor(Color.parseColor("#FF3A33"));

        }

        baseViewHolder.addOnClickListener(R.id.headImage);
        baseViewHolder.addOnClickListener(R.id.wrap);
    }


}
