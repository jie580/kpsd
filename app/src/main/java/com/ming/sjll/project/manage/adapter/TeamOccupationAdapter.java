package com.ming.sjll.project.manage.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class TeamOccupationAdapter extends BaseQuickAdapter<OccupationList.DataBean, BaseViewHolder> {



    public TeamOccupationAdapter(@Nullable List<OccupationList.DataBean> data){
        super(R.layout.project_team_dialog_occupation, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, OccupationList.DataBean dataBean) {

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.imageItem),
                Constant.BASE_API + dataBean.getIcon());
//
//
        ((TextView)baseViewHolder.getView(R.id.name)).setText(dataBean.getTitle());

        ImageView imageItemWrap = baseViewHolder.getView(R.id.imageItemWrap);
        imageItemWrap.setBackgroundResource(R.mipmap.bg_user2);
        if(dataBean.isVirtualIsSelect())
        {
            imageItemWrap.setBackgroundResource(R.mipmap.bg_user_blue2);
        }

        baseViewHolder.addOnClickListener(R.id.imageItemWrap);
//        baseViewHolder.addOnClickListener(R.id.delete);
    }


}
