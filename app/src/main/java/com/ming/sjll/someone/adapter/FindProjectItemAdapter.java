package com.ming.sjll.someone.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.search.bean.ProjecItem;
import com.ming.sjll.ui.AntoLineUtil;

import java.util.List;


//历史记录图片
public class FindProjectItemAdapter extends BaseQuickAdapter<ProjecItem.DataBean, BaseViewHolder> {

    boolean IsNotice;

    public FindProjectItemAdapter(@Nullable List<ProjecItem.DataBean> data,boolean IsNotice) {
        super(R.layout.search_adaper_project_item, data);
        this.IsNotice = IsNotice;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjecItem.DataBean dataBean) {

        //            项目
        RelativeLayout projectWrap = (RelativeLayout) baseViewHolder.getView(R.id.projectWrap);
        TextView projectDemand = (TextView) baseViewHolder.getView(R.id.projectDemand);
        TextView projectBrand = (TextView) baseViewHolder.getView(R.id.projectBrand);
        ImageView projectBg = (ImageView) baseViewHolder.getView(R.id.projectBg);
        TextView projectDate = (TextView) baseViewHolder.getView(R.id.projectDate);
        LinearLayout projectDateWarp = (LinearLayout) baseViewHolder.getView(R.id.projectDateWarp);
        LinearLayout projectButtonDate = (LinearLayout) baseViewHolder.getView(R.id.projectButtonDate);

        projectDemand.setText(dataBean.getBrand());
        projectBrand.setText(dataBean.getBrand());
        new ImageHelper().displayBackgroundLoading( projectBg,
                Constant.BASE_API + dataBean.getBackground_image());

//            通告
        RelativeLayout noticeWrap = (RelativeLayout) baseViewHolder.getView(R.id.noticeWrap);
        ImageView noticeBg = (ImageView) baseViewHolder.getView(R.id.noticeBg);
        TextView noticeDate = (TextView) baseViewHolder.getView(R.id.noticeDate);
        LinearLayout lookTimes = (LinearLayout) baseViewHolder.getView(R.id.lookTimes);

        if(IsNotice)
        {
            if (dataBean.getTimeList() != null && dataBean.getTimeList().size() >= 1 ) {

                String startStr = Tools.stampToDate(dataBean.getTimeList().get(0).getStartTime(), "MM月dd日  HH:mm");
                String startEnd = Tools.stampToDate(dataBean.getTimeList().get(0).getEndTime(), "HH:mm");
                projectDate.setText((startStr + " - " + startEnd));
                noticeDate.setText((startStr + " - " + startEnd));
            }
            else
            {

                lookTimes.setVisibility(View.GONE);
            }
        }
        else {
            projectDateWarp.setVisibility(View.GONE);
        }
        if (dataBean.getTag() != null) {
//            new ImageHelper().displayCorners(noticeBg, Constant.BASE_API + dataBean.getBackground_image());
            new ImageHelper().displayBackgroundLoading( noticeBg,
                    Constant.BASE_API + dataBean.getBackground_image());
//            标签
            AntoLineUtil cameraGroup = (AntoLineUtil) baseViewHolder.getView(R.id.noticeTag); // 此处是找到父控件LinearLayout
            for (int i = 0; i < dataBean.getTag().length; i++) {
                View view = View.inflate(mContext, R.layout.widget_string_tag, null);
                TextView textView = view.findViewById(R.id.tag);
                textView.setText(dataBean.getTag()[i]);
                cameraGroup.addView(view);
            }

        }

        if (IsNotice) {
            projectButtonDate.setVisibility(View.VISIBLE);
            if (dataBean.getIsNotice()) {
                projectWrap.setVisibility(View.GONE);
                noticeWrap.setVisibility(View.VISIBLE);

            } else {
                projectWrap.setVisibility(View.VISIBLE);
                noticeWrap.setVisibility(View.GONE);
            }
        } else {
            projectButtonDate.setVisibility(View.GONE);
        }

        baseViewHolder.addOnClickListener(R.id.projectWrap);
        baseViewHolder.addOnClickListener(R.id.lookTimes);


    }

}
