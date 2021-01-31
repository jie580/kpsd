package com.ming.sjll.Message.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.Message.viewmodel.GetMemberInfoBean;
import com.ming.sjll.view.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class MemberOccupationTagAdapter extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<GetMemberInfoBean.DataBean.OccupationBean> mDataList;

    public MemberOccupationTagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    public List<GetMemberInfoBean.DataBean.OccupationBean> getDataList() {
        return mDataList;
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.occupation_tag_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_label2);
        GetMemberInfoBean.DataBean.OccupationBean t = mDataList.get(position);

        textView.setText(t.getTitle());
//        if (t instanceof String) {
//            textView.setText((String) t);
//        }
        return view;
    }

    public void onlyAddAll(List<GetMemberInfoBean.DataBean.OccupationBean> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<GetMemberInfoBean.DataBean.OccupationBean> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    public void add(GetMemberInfoBean.DataBean.OccupationBean bean) {

        //如果存在相同的职业id，就不用添加
        for (int i = 0; i < mDataList.size(); i++) {
            if (TextUtils.equals(mDataList.get(i).getOccupation_id(), bean.getOccupation_id())) {
                return;
            }
        }

        mDataList.add(bean);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mDataList.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public boolean isSelectedPosition(int position) {

        return false;
    }
}
