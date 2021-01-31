package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.InvoiceBean;
import com.ming.sjll.Bean.ProjectCost;
import com.ming.sjll.My.common.MoneyInvoiceSelectProject;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class MoneyInvoiceProjectAdapter extends BaseQuickAdapter<ProjectCost.DataBeanX, BaseViewHolder> {

    Activity context;
    String type;
    public MoneyInvoiceProjectAdapter(@Nullable List<ProjectCost.DataBeanX> data, Activity context, String type) {
        super(R.layout.my_money_invoice_select_project_item, data);
        this.context = context;
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectCost.DataBeanX dataBean) {


        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.imageItem),
                Constant.BASE_IMAGE + dataBean.getImg());

        baseViewHolder.setText(R.id.target_name, dataBean.getTarget_name());
        baseViewHolder.setText(R.id.total_price, dataBean.getTotal_price());
        ImageView is_read = baseViewHolder.getView(R.id.is_read);
        if(dataBean.getInvoice_status().equals("0"))
        {
            baseViewHolder.setText(R.id.status, "未开票");
            if(dataBean.isSelect())
            {
                is_read.setBackgroundResource(R.mipmap.btn_checked);
            }
            else
            {
                is_read.setBackgroundResource(R.mipmap.btn_checked_un);
            }
        }
        else if (dataBean.getInvoice_status().equals("1"))
        {
            baseViewHolder.getView(R.id.is_read);
            baseViewHolder.setText(R.id.status, "开票中");
            is_read.setBackgroundResource(R.mipmap.btn_checked_ed);
        }
        else
        {
            baseViewHolder.setText(R.id.status, "已开票");
            is_read.setBackgroundResource(R.mipmap.btn_checked_ed);
        }

        MoneyInvoiceSelectProject m = (MoneyInvoiceSelectProject)context;
        type = m.type;
        baseViewHolder.getView(R.id.itemWrap).setVisibility(View.VISIBLE);
        if(type.equals("0") && !dataBean.getInvoice_status().equals("0"))
        {
            baseViewHolder.getView(R.id.itemWrap).setVisibility(View.GONE);
        }
        else if(type.equals("2") && !dataBean.getInvoice_status().equals("2"))
        {
            baseViewHolder.getView(R.id.itemWrap).setVisibility(View.GONE);
        }

        baseViewHolder.addOnClickListener(R.id.itemWrap);
    }



}
