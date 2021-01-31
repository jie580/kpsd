package com.ming.sjll.project.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.stetho.inspector.protocol.module.Database;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.project.bean.ProjecCoverBg;

import java.util.List;

public class CoverBgAdapter extends BaseQuickAdapter<ProjecCoverBg.DataBean, BaseViewHolder> {



        public CoverBgAdapter(@Nullable List<ProjecCoverBg.DataBean> data) {
            super(R.layout.project_release_project_cover_adapter, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, ProjecCoverBg.DataBean dataBean) {
            if(dataBean.getIsSelect())
            {
                baseViewHolder.getView(R.id.isSelect).setVisibility(View.VISIBLE);
            }
            else
            {
                baseViewHolder.getView(R.id.isSelect).setVisibility(View.GONE);
            }
            Log.e(TAG,dataBean.getImg());
            new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.bg),
                    Constant.BASE_API + dataBean.getImg()+"?"+ System.currentTimeMillis());
//            new ImageHelper().displayCorners( baseViewHolder.getView(R.id.bg), Constant.BASE_API + dataBean.getImg()+"?"+ System.currentTimeMillis());
            baseViewHolder.addOnClickListener(R.id.bg);
        }
}
