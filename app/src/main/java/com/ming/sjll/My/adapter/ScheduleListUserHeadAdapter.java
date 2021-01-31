package com.ming.sjll.My.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.ScheduleItemBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.ui.AntoLineLayoutManager;

import java.util.List;

public class ScheduleListUserHeadAdapter extends BaseQuickAdapter<GetInfoBean.DataBean, BaseViewHolder> {


    public ScheduleListUserHeadAdapter(@Nullable List<GetInfoBean.DataBean> data) {
        super(R.layout.my_schedule_item_user_head_adaper, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean dataBean) {

        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.userHead),
                Constant.BASE_IMAGE + dataBean.getDefault_avatar());

        baseViewHolder.addOnClickListener(R.id.userHead);

    }


}
