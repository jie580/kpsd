package com.ming.sjll.Message.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.view.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HanHailong on 15/10/19.
 */
public class MemberOccupationListTagAdapter extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<String> mDataList;

    public MemberOccupationListTagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    public MemberOccupationListTagAdapter(Context context, List<String> mDataList) {
        this.mContext = context;
        this.mDataList = mDataList;
    }


    @Override
    public int getCount() {
        return mDataList.size();
    }

    public List<String> getDataList() {
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

        View view = LayoutInflater.from(mContext).inflate(R.layout.occupation_list_tag_item, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_tag);
        String t = mDataList.get(position);

        textView.setText(t);
//        if (t instanceof String) {
//            textView.setText((String) t);
//        }
        return view;
    }

    public void onlyAddAll(List<String> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<String> datas) {
        mDataList.clear();
        onlyAddAll(datas);
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
