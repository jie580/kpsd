package com.ming.sjll.My.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoDateInfoBean;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.R;

import java.util.List;

public class ShowcaseTagItemAdapter extends BaseQuickAdapter<GoodsItem.DataBean.DataBeanX, BaseViewHolder> {

    public ShowcaseTagItemAdapter(@Nullable List<GoodsItem.DataBean.DataBeanX> data) {
        super(R.layout.my_tag_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GoodsItem.DataBean.DataBeanX dataBean) {

        TextView contentText = ((TextView)baseViewHolder.getView(R.id.contentText));
        if(dataBean.isIs_select())
        {
            contentText.setTextColor(Color.parseColor("#FFFFFF"));
            contentText.setBackgroundResource(R.drawable.shape_left_right_select_tag);
        }
        else
        {
            contentText.setTextColor(Color.parseColor("#9BA6BC"));
            contentText.setBackgroundResource(R.drawable.shape_left_right_normal_tag2);
        }
        contentText.setText(dataBean.getPrice_str());
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
