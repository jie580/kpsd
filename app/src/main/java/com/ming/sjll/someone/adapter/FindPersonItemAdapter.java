package com.ming.sjll.someone.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.StringDataListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;


//图片找人-找到的人
public class FindPersonItemAdapter extends BaseQuickAdapter<GetInfoBean.DataBean, BaseViewHolder> {


    public FindPersonItemAdapter(@Nullable List<GetInfoBean.DataBean> data) {
        super(R.layout.search_adaper_company_member_item, data);
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean dataBean) {

        ((TextView)baseViewHolder.getView(R.id.companyName)).setText(dataBean.getAddress());
        ((TextView)baseViewHolder.getView(R.id.payment)).setText(dataBean.getPayment_str());

        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.bg),
                Constant.BASE_IMAGE + dataBean.getDefault_avatar_rect());

        if(dataBean.getIs_approve().equals("0"))
        {
            baseViewHolder.getView(R.id.approve).setVisibility(View.GONE);
        }
        else
        {
            baseViewHolder.getView(R.id.approve).setVisibility(View.VISIBLE);
        }

        baseViewHolder.addOnClickListener(R.id.bg);



//            if(dataBean.getIsSelect())
//            {
//                baseViewHolder.getView(R.id.isSelect).setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                baseViewHolder.getView(R.id.isSelect).setVisibility(View.GONE);
//            }
//            Log.e(TAG,dataBean.getImg());



//            new ImageHelper().displayCorners( baseViewHolder.getView(R.id.bg), Constant.BASE_API + dataBean.getImg()+"?"+ System.currentTimeMillis());
//            baseViewHolder.addOnClickListener(R.id.bg);
    }

//    public void addData(String data) {
//        mData.add(data);
//    }
//
    public void removeData(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }
}
