package com.ming.sjll.project.manage.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class TeamOccupationUserDeleteAdapter extends BaseQuickAdapter<GetInfoBean.DataBean, BaseViewHolder> {



    public TeamOccupationUserDeleteAdapter(@Nullable List<GetInfoBean.DataBean> data){
        super(R.layout.item_user_line, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean dataBean) {

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.imageItem),
                Constant.BASE_API + dataBean.getDefault_avatar());
//
//
        ((TextView)baseViewHolder.getView(R.id.name)).setText(dataBean.getName());


//        if(dataBean.isVirtualIsSelect())
//        {
//            imageItemWrap.setBackgroundResource(R.mipmap.bg_user_blue2);
//        }


        baseViewHolder.addOnClickListener(R.id.imageItem);

        baseViewHolder.addOnClickListener(R.id.btnDeleteUser);
//        baseViewHolder.addOnClickListener(R.id.delete);
    }


}
