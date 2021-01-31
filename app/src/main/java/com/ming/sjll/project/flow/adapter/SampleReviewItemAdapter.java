package com.ming.sjll.project.flow.adapter;


import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.ProjectFlowWorkSelectBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class SampleReviewItemAdapter extends BaseQuickAdapter<ProjectFlowWorkSelectBean.DataBean, BaseViewHolder> {



    Activity mActivity;
    String projectId;

    public SampleReviewItemAdapter(@Nullable List<ProjectFlowWorkSelectBean.DataBean> data, Activity mActivity, String projectId ) {
        super(R.layout.project_flow_review_sample_item, data);
        this.mActivity = mActivity;
        this.projectId = projectId;

    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectFlowWorkSelectBean.DataBean dataBean) {

        baseViewHolder.getView(R.id.wrap).setVisibility(View.VISIBLE);

        if(dataBean.getNewImg() != null)
        {
            new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.imageItem),
                    Constant.BASE_IMAGE + dataBean.getNewImg(),new CommonCallback(){
                        @Override
                        public void onSuccessBitmap(String arg0, View arg1, Bitmap arg2) {
                            super.onSuccessBitmap(arg0, arg1, arg2);
                            dataBean.setImgBitmap(arg2);
                        }
                    });
        }
        else
        {
            new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.imageItem),
                    Constant.BASE_IMAGE + dataBean.getImg(),new CommonCallback(){
                        @Override
                        public void onSuccessBitmap(String arg0, View arg1, Bitmap arg2) {
                            super.onSuccessBitmap(arg0, arg1, arg2);
                            dataBean.setImgBitmap(arg2);
                        }
                    });
        }



        if(dataBean.getIsOpen())
        {
            ((ImageView)baseViewHolder.getView(R.id.checkSelect)).setBackgroundResource(R.mipmap.btn_return);
            baseViewHolder.getView(R.id.inputWrap).setVisibility(View.VISIBLE);
//            baseViewHolder.setText(R.id.editText,dataBean.getImg_rect());
        }
        else
        {
            ((ImageView)baseViewHolder.getView(R.id.checkSelect)).setBackgroundResource(R.mipmap.btn_chat3);
            baseViewHolder.getView(R.id.inputWrap).setVisibility(View.GONE);
        }


        EditText price = (EditText)baseViewHolder.getView(R.id.editText);
//        price.setFocusable(false);
//        price.setFocusableInTouchMode(false);
        //        通过tag判断当前editText是否已经设置监听，有监听的话，移除监听再给editText赋值
        if (price.getTag() instanceof TextWatcher){
            price.removeTextChangedListener((TextWatcher) price.getTag());
        }

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
//                Log.e("Tag", "FirstAdapter afterTextChanged name---" + item.getName() + ",---" + editable);
//                if (!TextUtils.isEmpty(editable)) {
                dataBean.setImg_rect(editable.toString());
//                }
            }
        };
        price.addTextChangedListener(watcher);
//        给editText设置tag，以便于判断当前editText是否已经设置监听
        price.setTag(watcher);


        baseViewHolder.addOnClickListener(R.id.save);
        baseViewHolder.addOnClickListener(R.id.imageItem);
        baseViewHolder.addOnClickListener(R.id.checkSelect);

    }

}
