package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.InvoiceBean;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class MoneyInvoiceAdapter extends BaseQuickAdapter<InvoiceBean.DataBeanX, BaseViewHolder> {

    Activity context;

    public MoneyInvoiceAdapter(@Nullable List<InvoiceBean.DataBeanX> data, Activity context) {
        super(R.layout.my_money_invoice_item, data);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, InvoiceBean.DataBeanX dataBean) {


        baseViewHolder.setText(R.id.name, dataBean.getName());
        baseViewHolder.setText(R.id.duty_paragraph, dataBean.getDuty_paragraph());

        baseViewHolder.addOnClickListener(R.id.wrap);
    }


    private String calculateTime(int diff)
    {
        long hours = diff /  60 ;
        long minutes = diff - hours*60;
//        long days = diff / ( 60 * 60 * 24);
//        long hours = (diff-days*( 60 * 60 * 24))/( 60 * 60);
//        long minutes = (diff-hours*( 60 * 60 * 24)-hours*( 60 * 60))/( 60);
        return hours + "m" + minutes + "s";

    }

}
