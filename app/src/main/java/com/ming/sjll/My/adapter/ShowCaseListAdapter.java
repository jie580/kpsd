package com.ming.sjll.My.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.ShowCaseLIstBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class ShowCaseListAdapter extends BaseQuickAdapter<ShowCaseLIstBean.DataBean, BaseViewHolder> {

    boolean isShowDelete = false;
    Context context;

    public ShowCaseListAdapter(@Nullable List<ShowCaseLIstBean.DataBean> data){
        super(R.layout.item_show_case, data);
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, ShowCaseLIstBean.DataBean dataBean) {

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.image),
                Constant.BASE_API + dataBean.icon);


//        ((TextView)baseViewHolder.getView(R.id.money)).setText(dataBean.payment);
//        ((TextView)baseViewHolder.getView(R.id.personalized)).setText( dataBean.getPersonalized());
//
//        if(isShowDelete)
//        {
//            baseViewHolder.getView(R.id.delete).setVisibility(View.VISIBLE);
//        }
//        else {
//            baseViewHolder.getView(R.id.delete).setVisibility(View.GONE);
//        }
////
//
//        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.headImage),
//                Constant.BASE_API + dataBean.getDefault_avatar());

//        baseViewHolder.addOnClickListener(R.id.wrap);
        baseViewHolder.addOnClickListener(R.id.wrap);
    }


}
