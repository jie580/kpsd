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

import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.search.bean.ProjecItem;
import com.ming.sjll.ui.AntoLineUtil;

import java.util.List;

public class CompanyListItemAdapter extends BaseAdapter {

    setOnClickListener listener;
    List<GetInfoBean.DataBean> mData;
    boolean IsNotice;
    private Context mContext = null; // 上下文环境

    public CompanyListItemAdapter(Context context, List<GetInfoBean.DataBean> arr, boolean IsNotice) {
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

        GetInfoBean.DataBean data = mData.get(pos);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.search_adaper_company_item, parent, false);
        ((TextView)convertView.findViewById(R.id.companyName)).setText(data.getAddress());

        new ImageHelper().displayBackgroundLoading( convertView.findViewById(R.id.bg),
                Constant.BASE_IMAGE + data.getDefault_avatar_rect());

        convertView.findViewById(R.id.bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(pos, R.id.bg, parent);
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

