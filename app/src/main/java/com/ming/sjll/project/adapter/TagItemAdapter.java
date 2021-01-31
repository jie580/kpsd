package com.ming.sjll.project.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoDateInfoBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class TagItemAdapter extends BaseQuickAdapter<GetInfoDateInfoBean.PaymentBean, BaseViewHolder> {

    public TagItemAdapter(@Nullable List<GetInfoDateInfoBean.PaymentBean> data) {
        super(R.layout.my_tag_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoDateInfoBean.PaymentBean dataBean) {

        TextView contentText = ((TextView)baseViewHolder.getView(R.id.contentText));
        if(dataBean.isIs_select())
        {
            contentText.setBackgroundResource(R.drawable.shape_left_right_select_tag);
        }
        else
        {
            contentText.setBackgroundResource(R.drawable.shape_left_right_normal_tag);
        }
        contentText.setText(dataBean.getPayment_str());
        baseViewHolder.addOnClickListener(R.id.contentText);
//            if(dataBean.getIsSelect())
//            {
//                baseViewHolder.getView(R.id.isSelect).setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                baseViewHolder.getView(R.id.isSelect).setVisibility(View.GONE);
//            }
//            Log.e(TAG,dataBean.getImg());
//        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.bg),
//                Constant.BASE_IMAGE + dataBean);
//        baseViewHolder.addOnClickListener(R.id.bg);

//            new ImageHelper().displayCorners( baseViewHolder.getView(R.id.bg), Constant.BASE_API + dataBean.getImg()+"?"+ System.currentTimeMillis());
//            baseViewHolder.addOnClickListener(R.id.bg);
    }


    public static class tagClass
    {
        public String name;
        public boolean isSelect;
    }

}
