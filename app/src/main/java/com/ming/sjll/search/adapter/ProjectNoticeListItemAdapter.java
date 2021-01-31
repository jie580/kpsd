package com.ming.sjll.search.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.search.bean.ProjecItem;
import com.ming.sjll.ui.AntoLineUtil;

import java.util.List;

public class ProjectNoticeListItemAdapter extends BaseAdapter {

    setOnClickListener listener;
    List<ProjecItem.DataBean> mData;
    boolean IsNotice;
    private Context mContext = null; // 上下文环境

    public ProjectNoticeListItemAdapter(Context context, List<ProjecItem.DataBean> arr, boolean IsNotice) {
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

        convertView = LayoutInflater.from(mContext).inflate(R.layout.search_adaper_project_notice_item, parent, false);
//        if(data.getIsChange() == false)
//        {
//            return convertView;
//        }
        //            项目
//        RelativeLayout projectWrap = (RelativeLayout) convertView.findViewById(R.id.projectWrap);
//        TextView projectDemand = (TextView) convertView.findViewById(R.id.projectDemand);
//        TextView projectBrand = (TextView) convertView.findViewById(R.id.projectBrand);
//        ImageView projectBg = (ImageView) convertView.findViewById(R.id.projectBg);
//        TextView projectDate = (TextView) convertView.findViewById(R.id.projectDate);
//        LinearLayout projectDateWarp = (LinearLayout) convertView.findViewById(R.id.projectDateWarp);
//        LinearLayout projectButtonDate = (LinearLayout) convertView.findViewById(R.id.projectButtonDate);

//        projectDemand.setText(data.getBrand());
//        projectBrand.setText(data.getBrand());
        new ImageHelper().displayBackgroundLoading(convertView.findViewById(R.id.noticeBg), Constant.BASE_API + data.getOccupation_background());

        //            标签
        if (mData.get(pos).getTag() != null) {
            AntoLineUtil cameraGroup = (AntoLineUtil) convertView.findViewById(R.id.noticeTag); // 此处是找到父控件LinearLayout
            for (int i = 0; i < mData.get(pos).getTag().length; i++) {
                View view = View.inflate(convertView.getContext(), R.layout.text_gray, null);
                TextView textView = view.findViewById(R.id.text);
                LinearLayout textWrap = view.findViewById(R.id.textWrap);
                textView.setText(mData.get(pos).getTag()[i]);
                cameraGroup.addView(view);
            }
        }

        if (mData.get(pos).getOccupation_string_array() != null) {
            AntoLineUtil cameraGroup = (AntoLineUtil) convertView.findViewById(R.id.occupationTag); // 此处是找到父控件LinearLayout
            for (int i = 0; i < mData.get(pos).getOccupation_string_array().size(); i++) {
                View view = View.inflate(convertView.getContext(), R.layout.text_blue, null);
                TextView textView = view.findViewById(R.id.text);
                LinearLayout textWrap = view.findViewById(R.id.textWrap);
                textView.setText(mData.get(pos).getOccupation_string_array().get(i));
                cameraGroup.addView(view);
            }
        }
        LinearLayout lookTimes = (LinearLayout) convertView.findViewById(R.id.lookTimes);
        TextView noticeDate = (TextView) convertView.findViewById(R.id.noticeDate);
        if (data.getTimeList() != null && data.getTimeList().size() >= 1 ) {


            String startStr = Tools.stampToDate(data.getTimeList().get(0).getStartTime(), "MM月dd日  HH:mm");
            String startEnd = Tools.stampToDate(data.getTimeList().get(0).getEndTime(), "HH:mm");
            noticeDate.setText((startStr + " - " + startEnd));
        }
        else
        {
            noticeDate.setText(("暂无"));
        }


//            通告
//        RelativeLayout noticeWrap = (RelativeLayout) convertView.findViewById(R.id.noticeWrap);
//        ImageView noticeBg = (ImageView) convertView.findViewById(R.id.noticeBg);
//        TextView noticeDate = (TextView) convertView.findViewById(R.id.noticeDate);
        ;

//        if(IsNotice)
//        {
//            if (data.getTimeList() != null && data.getTimeList().size() >= 1 ) {
//
//                    String startStr = Tools.stampToDate(data.getTimeList().get(0).getStartTime(), "MM月dd日  HH:mm");
//                    String startEnd = Tools.stampToDate(data.getTimeList().get(0).getEndTime(), "HH:mm");
//                    projectDate.setText((startStr + " - " + startEnd));
//                    noticeDate.setText((startStr + " - " + startEnd));
//            }
//            else
//            {
//
//                lookTimes.setVisibility(View.GONE);
//            }
//        }
//        else {
//            projectDateWarp.setVisibility(View.GONE);
//        }
//        if (mData.get(pos).getTag() != null) {
//            new ImageHelper().displayCorners(noticeBg, Constant.BASE_API + data.getBackground_image());
////            标签
//            AntoLineUtil cameraGroup = (AntoLineUtil) convertView.findViewById(R.id.noticeTag); // 此处是找到父控件LinearLayout
//            for (int i = 0; i < mData.get(pos).getTag().length; i++) {
//                View view = View.inflate(convertView.getContext(), R.layout.text_gray, null);
//                TextView textView = view.findViewById(R.id.tag);
//                textView.setText(mData.get(pos).getTag()[i]);
//                cameraGroup.addView(view);
//            }
//
//        }
//
//        if (IsNotice) {
//            projectButtonDate.setVisibility(View.VISIBLE);
//            if (data.getIsNotice()) {
//                projectWrap.setVisibility(View.GONE);
//                noticeWrap.setVisibility(View.VISIBLE);
//
//            } else {
//                projectWrap.setVisibility(View.VISIBLE);
//                noticeWrap.setVisibility(View.GONE);
//            }
//        } else {
//            projectButtonDate.setVisibility(View.GONE);
//        }
//
//        projectWrap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (listener != null) {
//                    listener.onClick(pos, R.id.projectWrap, parent);
//                }
//            }
//        });
        lookTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(pos, R.id.lookTimes, v);
                }
            }
        });

        convertView.findViewById(R.id.openChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(pos, R.id.openChat, v);
                }
            }
        });

        convertView.findViewById(R.id.projectWrap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(pos, R.id.projectWrap, v);
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

