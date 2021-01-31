package com.ming.sjll.Message.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.InformBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.project.bean.ProjecCoverBg;

import java.util.List;

public class InformAdapter extends BaseQuickAdapter<InformBean.DataBeanX, BaseViewHolder> {


        public InformAdapter(@Nullable List<InformBean.DataBeanX> data) {
            super(R.layout.message_inform_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, InformBean.DataBeanX dataBean) {
            baseViewHolder.setText(R.id.text, dataBean.getContent());
        }
}
