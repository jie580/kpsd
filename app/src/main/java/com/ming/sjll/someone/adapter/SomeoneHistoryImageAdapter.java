package com.ming.sjll.someone.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.StringDataListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.LinkedList;
import java.util.List;


//历史记录图片
public class SomeoneHistoryImageAdapter extends BaseQuickAdapter<StringDataListBean.DataBean.DataBeanX, BaseViewHolder> {


    public SomeoneHistoryImageAdapter(@Nullable List<StringDataListBean.DataBean.DataBeanX> data) {
        super(R.layout.someone_history_image, data);
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, StringDataListBean.DataBean.DataBeanX dataBean) {
//        Log.e("图片路径2222",dataBean.toStringDataListBean());

//            baseViewHolder.setGone(R.id.content, true);

            new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.iv_img),
                    Constant.BASE_IMAGE + dataBean.getSearch_img());

        baseViewHolder.addOnClickListener(R.id.iv_delete);
        baseViewHolder.addOnClickListener(R.id.iv_img);

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
