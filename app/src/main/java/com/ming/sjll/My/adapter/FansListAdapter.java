package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heartfor.heartvideo.video.HeartVideo;
import com.heartfor.heartvideo.video.HeartVideoInfo;
import com.heartfor.heartvideo.video.VideoControl;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.ui.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class FansListAdapter extends BaseQuickAdapter<GetInfoBean.DataBean, BaseViewHolder> {

//    是否显示删除按钮
    boolean isShowDelete = false;

    Context context;
    public FansListAdapter(@Nullable List<GetInfoBean.DataBean> data, boolean isShowDelete){
        super(R.layout.my_fans_item, data);
        this.isShowDelete = isShowDelete;
    }

    public FansListAdapter(@Nullable List<GetInfoBean.DataBean> data){
        super(R.layout.my_fans_item, data);
    }
    public FansListAdapter(@Nullable List<GetInfoBean.DataBean> data, Context context) {
        super(R.layout.my_fans_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean dataBean) {


        ((TextView)baseViewHolder.getView(R.id.name)).setText(dataBean.getName());
//        ((TextView)baseViewHolder.getView(R.id.personalized)).setText( dataBean.getPersonalized());

        if(isShowDelete)
        {
            baseViewHolder.getView(R.id.delete).setVisibility(View.VISIBLE);
        }
        else {
            baseViewHolder.getView(R.id.delete).setVisibility(View.GONE);
        }

        if (dataBean.getOccupation_string_array() != null)
        {


            LinearLayout linearLayout = ((LinearLayout)baseViewHolder.getView(R.id.linearLayout));

            for (int j = 0; j < dataBean.getOccupation_string_array().size(); j++) {
                View userListView = LayoutInflater.from(mContext).inflate(R.layout.text_gray2, null);
                userListView.setTag(j);

                ((TextView) userListView.findViewById(R.id.text)).setText(dataBean.getOccupation_string_array().get(j));

    //            userListView.setOnClickListener(new View.OnClickListener() {
    //                @Override
    //                public void onClick(View v) {
    //                    int indexOf = (int) v.getTag();
    //                    clickUser(indexOf,v);
    //
    //                }
    //            });
                linearLayout.addView(userListView);
            }

        }


        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.headImage),
                Constant.BASE_API + dataBean.getDefault_avatar());

        baseViewHolder.addOnClickListener(R.id.wrap);
        baseViewHolder.addOnClickListener(R.id.delete);
    }


}
