package com.ming.sjll.My.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class GoodsListAdapter2 extends BaseQuickAdapter<GoodsItem.DataBean, BaseViewHolder> {

    boolean isShowDelete = false;
    Context context;
    boolean isShowUser = false;

    public GoodsListAdapter2(@Nullable List<GoodsItem.DataBean> data, boolean isShowDelete){
        super(R.layout.item_goods, data);
        this.isShowDelete = isShowDelete;
    }

    public GoodsListAdapter2(@Nullable List<GoodsItem.DataBean> data, boolean isShowDelete, boolean isShowUser){
        super(R.layout.item_goods, data);
        this.isShowDelete = isShowDelete;
        this.isShowUser = isShowUser;
    }

    public GoodsListAdapter2(@Nullable List<GoodsItem.DataBean> data){
        super(R.layout.item_goods, data);
    }
    public GoodsListAdapter2(@Nullable List<GoodsItem.DataBean> data, Context context) {
        super(R.layout.item_goods, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GoodsItem.DataBean dataBean) {

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.imageItem),
                Constant.BASE_API + dataBean.getCover_img());



        ((TextView)baseViewHolder.getView(R.id.title)).setText(dataBean.getTitle());
        ((TextView)baseViewHolder.getView(R.id.money)).setText(dataBean.getPayment());

//        ((TextView)baseViewHolder.getView(R.id.personalized)).setText( dataBean.getPersonalized());
//
        if(isShowDelete)
        {
            baseViewHolder.getView(R.id.delete).setVisibility(View.VISIBLE);
        }
        else {
            baseViewHolder.getView(R.id.delete).setVisibility(View.GONE);
        }

        if(isShowUser)
        {
            baseViewHolder.getView(R.id.userWrap).setVisibility(View.VISIBLE);
            new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.headImage),
                    Constant.BASE_API + dataBean.getDefault_avatar());
            ((TextView)baseViewHolder.getView(R.id.userName)).setText(dataBean.getName());
            if(dataBean.getIs_collect().equals("true"))
            {
                ((ImageView)baseViewHolder.getView(R.id.collect)).setBackgroundResource(R.mipmap.btn_collected);
            }
            else
            {
                ((ImageView)baseViewHolder.getView(R.id.collect)).setBackgroundResource(R.mipmap.btn_collect);
            }
        }
        else
        {
//            baseViewHolder.getView(R.id.title).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.userWrap).setVisibility(View.GONE);
        }
//
//
//        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.imageItem),
//                Constant.BASE_API + dataBean.getDefault_avatar());

        baseViewHolder.addOnClickListener(R.id.collect);
        baseViewHolder.addOnClickListener(R.id.userWrap);
        baseViewHolder.addOnClickListener(R.id.wrap);
        baseViewHolder.addOnClickListener(R.id.delete);
    }


}
