package com.ming.sjll.My.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.ArrayList;

public class SampleListItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SampleListItemAdapter(@Nullable ArrayList<String> data) {
        super(R.layout.my_sample_adapter_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String dataBean) {

//            if(dataBean.getIsSelect())
//            {
//                baseViewHolder.getView(R.id.isSelect).setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                baseViewHolder.getView(R.id.isSelect).setVisibility(View.GONE);
//            }
//            Log.e(TAG,dataBean.getImg());
        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.bg),
                Constant.BASE_IMAGE + dataBean);
        baseViewHolder.addOnClickListener(R.id.bg);

//            new ImageHelper().displayCorners( baseViewHolder.getView(R.id.bg), Constant.BASE_API + dataBean.getImg()+"?"+ System.currentTimeMillis());
//            baseViewHolder.addOnClickListener(R.id.bg);
    }
}
