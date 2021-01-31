package com.ming.sjll.search.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.StringDataListBean;
import com.ming.sjll.R;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索页面，标签，品牌，皮肤
 */
public class GoodsBrandItemAdapter extends BaseQuickAdapter<StringDataListBean.DataBean.DataBeanX, BaseViewHolder> {

    public GoodsBrandItemAdapter(@Nullable List<StringDataListBean.DataBean.DataBeanX> data) {
        super(R.layout.search_goods_city_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, StringDataListBean.DataBean.DataBeanX dataBean) {

        TextView contentText = ((TextView)baseViewHolder.getView(R.id.contentText));
        if(dataBean.isSelect())
        {
            contentText.setBackgroundResource(R.drawable.shape_blue_50_corner);
            contentText.setTextColor(Color.WHITE);
        }
       else
        {
            contentText.setBackgroundResource(R.drawable.shape_gray_50_corner);
            contentText.setTextColor(Color.parseColor("#FF707070"));
        }
       if(dataBean.getTitle() == null || !dataBean.getTitle().equals(""))
       {
           contentText.setText(dataBean.getTitle());
       }
       else
       {
           contentText.setText(dataBean.getSkin_color() );
       }
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



    public static class City implements Serializable
    {
        public String name;
        public String code;
        public boolean isSelect;
        public boolean realIsSelect = false;
    }
}
