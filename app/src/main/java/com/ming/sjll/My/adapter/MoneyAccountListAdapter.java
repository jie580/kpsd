package com.ming.sjll.My.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.Bean.WalletBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class MoneyAccountListAdapter extends BaseQuickAdapter<WalletBean.DataBeanX, BaseViewHolder> {


    public MoneyAccountListAdapter(@Nullable List<WalletBean.DataBeanX> data){
        super(R.layout.item_account, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WalletBean.DataBeanX dataBean) {

        baseViewHolder.setText(R.id.companyAccount,dataBean.getHidden_card());
        baseViewHolder.addOnClickListener(R.id.wrap);
    }


}
