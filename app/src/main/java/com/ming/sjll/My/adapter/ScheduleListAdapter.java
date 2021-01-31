package com.ming.sjll.My.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.ScheduleItemBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;

import java.util.List;

public class ScheduleListAdapter extends BaseQuickAdapter<ScheduleItemBean.DataBean, BaseViewHolder> {

    public ScheduleListAdapter(@Nullable List<ScheduleItemBean.DataBean> data) {
        super(R.layout.my_schedule_item_adaper, data);

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ScheduleItemBean.DataBean dataBean) {

        TextView projectDemand = (TextView)baseViewHolder.getView(R.id.projectDemand);
        projectDemand.setText(dataBean.getDemand());

        TextView projectBrand = (TextView)baseViewHolder.getView(R.id.projectBrand);
        projectBrand.setText(dataBean.getDemand());


        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.projectBg),
                Constant.BASE_IMAGE + dataBean.getBackground_image());

        RecyclerView recyclerList = (RecyclerView)baseViewHolder.getView(R.id.recyclerList);
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(mContext,true);
//        AntoLineLayoutManager layout = new AntoLineLayoutManager();
//        recyclerList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerList.setLayoutManager(layout);
//        for (int i = 0; i < 10; i++) {
//            dataBean.getMember().add(dataBean.getMember().get(0));
//        }
        if(dataBean.getMember() != null && dataBean.getMember().size() > 0 )
        {
            ScheduleListUserHeadAdapter adapter = new ScheduleListUserHeadAdapter(dataBean.getMember());
            recyclerList.setAdapter(adapter);
            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.userHead:
                            ToastShow.s("点击了用户头像"+dataBean.getMember().get(position).getUser_id());
                    }
                };
            });
        }
        else if(dataBean.getDateListString() != null && dataBean.getDateListString().size() > 0)
        {
             ScheduleListUserDateAdapter adapter = new  ScheduleListUserDateAdapter(dataBean.getDateListString());
            recyclerList.setAdapter(adapter);
        }





        baseViewHolder.addOnClickListener(R.id.projectBg);

    }


}
