package com.ming.sjll.My.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.LinkedList;
import java.util.List;


public class SampleItemEditImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public SampleItemEditImageAdapter(@Nullable List<String> data) {
        super(R.layout.my_sample_edit_image, data);

//        for (int i = 0; i < mData.size(); i++) {
//            String e = mData.get(i);
//            if (!e.equals("footerview")) {
//                mData.add(e);
//            }
//        }
        mData.add(0,"footerview");
    }

    public List<String> getRealList() {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < mData.size(); i++) {
            String e = mData.get(i);
            if (!e.equals("footerview")) {
                list.add(e);
            }
        }
        return list;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String dataBean) {
        Log.e("图片路径2222",dataBean.toString());
        if (dataBean.equals("footerview")) {
            baseViewHolder.setGone(R.id.content, false);
            baseViewHolder.setGone(R.id.iv_add, true);

        } else {
            baseViewHolder.setGone(R.id.content, true);
            baseViewHolder.setGone(R.id.iv_add, false);
//            ImageHelper.display((ImageView) baseViewHolder.getView(R.id.iv_img), dataBean);

            new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.iv_img),
                    Constant.BASE_IMAGE + dataBean);

        }

        baseViewHolder.addOnClickListener(R.id.iv_delete);
        baseViewHolder.addOnClickListener(R.id.iv_add);
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
