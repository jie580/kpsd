package com.ming.sjll.My.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class ScheduleListUserDateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ScheduleListUserDateAdapter(@Nullable List<String> data) {
        super(R.layout.my_schedule_item_user_date_adaper, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String dataBean) {

//        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.userHead),
//                Constant.BASE_IMAGE + dataBean.getDefault_avatar());
        ((TextView)baseViewHolder.getView(R.id.dateText)).setText(dataBean);
        baseViewHolder.addOnClickListener(R.id.userHead);

    }


}
