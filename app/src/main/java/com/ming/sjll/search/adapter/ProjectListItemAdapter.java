package com.ming.sjll.search.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.login.bean.DefaultAvatar;
import com.ming.sjll.search.bean.ProjecItem;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.ui.AntoLineUtil;
import com.ming.sjll.ui.CustomRoundAngleImageView;

import java.util.List;

import io.rong.imageloader.core.DisplayImageOptions;

public class ProjectListItemAdapter extends BaseAdapter {

    setOnClickListener listener;
    List<ProjecItem.DataBean> mData;
    boolean IsNotice;
    private Context mContext = null; // 上下文环境

    public ProjectListItemAdapter(Context context, List<ProjecItem.DataBean> arr, boolean IsNotice) {
        mData = arr;
        mContext = context;
        this.IsNotice = IsNotice;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {


        ProjecItem.DataBean data = mData.get(pos);

        convertView = LayoutInflater.from(mContext).inflate(R.layout.search_adaper_project_item, parent, false);
//        if(data.getIsChange() == false)
//        {
//            return convertView;
//        }
        //            项目
        RelativeLayout projectWrap = (RelativeLayout) convertView.findViewById(R.id.projectWrap);
        TextView projectDemand = (TextView) convertView.findViewById(R.id.projectDemand);
        TextView projectBrand = (TextView) convertView.findViewById(R.id.projectBrand);
//        ImageView projectBg = (ImageView) convertView.findViewById(R.id.projectBg);
        TextView projectDate = (TextView) convertView.findViewById(R.id.projectDate);
        LinearLayout projectDateWarp = (LinearLayout) convertView.findViewById(R.id.projectDateWarp);
        LinearLayout projectButtonDate = (LinearLayout) convertView.findViewById(R.id.projectButtonDate);

        projectDemand.setText(data.getDemand());
        projectBrand.setText(data.getBrand());
        new ImageHelper().displayBackgroundLoading(convertView.findViewById(R.id.projectBg), Constant.BASE_API + data.getBackground_image());
        if(data.isSelect())
        {
            convertView.findViewById(R.id.selectWrap).setVisibility(View.VISIBLE);
        }
        else
        {
            convertView.findViewById(R.id.selectWrap).setVisibility(View.GONE);
        }

//            通告
        RelativeLayout noticeWrap = (RelativeLayout) convertView.findViewById(R.id.noticeWrap);
        ImageView noticeBg = (ImageView) convertView.findViewById(R.id.noticeBg);
        TextView noticeDate = (TextView) convertView.findViewById(R.id.noticeDate);
        LinearLayout lookTimes = (LinearLayout) convertView.findViewById(R.id.lookTimes);

        if(IsNotice)
        {
            if (data.getTimeList() != null && data.getTimeList().size() >= 1 ) {

                    String startStr = Tools.stampToDate(data.getTimeList().get(0).getStartTime(), "MM月dd日  HH:mm");
                    String startEnd = Tools.stampToDate(data.getTimeList().get(0).getEndTime(), "HH:mm");
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
        if (mData.get(pos).getTag() != null) {
            new ImageHelper().displayCorners(noticeBg, Constant.BASE_API + data.getBackground_image());
//            标签
            AntoLineUtil cameraGroup = (AntoLineUtil) convertView.findViewById(R.id.noticeTag); // 此处是找到父控件LinearLayout
            for (int i = 0; i < mData.get(pos).getTag().length; i++) {
                View view = View.inflate(convertView.getContext(), R.layout.widget_string_tag, null);
                TextView textView = view.findViewById(R.id.tag);
                textView.setText(mData.get(pos).getTag()[i]);
                cameraGroup.addView(view);
            }

        }

        if (IsNotice) {
            projectButtonDate.setVisibility(View.VISIBLE);
            if (data.getIsNotice()) {
                projectWrap.setVisibility(View.GONE);
                noticeWrap.setVisibility(View.VISIBLE);

            } else {
                projectWrap.setVisibility(View.VISIBLE);
                noticeWrap.setVisibility(View.GONE);
            }
        } else {
            projectButtonDate.setVisibility(View.GONE);
        }

        projectWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(pos, R.id.projectWrap, parent);
                }
            }
        });
        lookTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(pos, R.id.lookTimes, v);
                }
            }
        });


        return convertView;
    }

    /***
     * 事件列表
     */
    public interface setOnClickListener {
        /**
         * 点击
         *
         * @param pos
         */
        void onClick(int pos, @IdRes int id, View parent);

    }

    public void setOnClickListenerInterface(setOnClickListener listener) {
        this.listener = listener;

    }


}

