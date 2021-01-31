package com.ming.sjll.My.common;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.Bean.InvoiceBean;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.My.adapter.MoneyInvoiceAdapter;
import com.ming.sjll.My.adapter.MoneyProjectAdapter;
import com.ming.sjll.My.company.ShowcaseAddTwoActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyMoneyEditAccountBinding;
import com.ming.sjll.databinding.MyMoneyInvoiceListBinding;
import com.ming.sjll.map.tencent.MyTencentMap;

import java.io.Serializable;

/**
 * 个人中心-发票列表
 */
public class MoneyInvoiceList extends BaseActivity {



    MyMoneyInvoiceListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_money_edit_account);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_money_invoice_list, null, false);
        setContentView(binding.getRoot());
        event();
        initView();
    }

    private void event() {
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoneyInvoiceEdit.class);
                startActivityForResult(intent, 1);
            }
        });

    }


    private void initView() {
        getList();
    }



    MoneyInvoiceAdapter listAdapter;
    private void getList()
    {
        RecyclerView listView = binding.recyclerList;

        HttpParamsObject param = new HttpParamsObject();

        new HttpUtil().sendRequest(Constant.INVOICE_INVOICE_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                InvoiceBean.DataBeanList dataList = new Gson().fromJson(data, InvoiceBean.DataBeanList.class);

                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new MoneyInvoiceAdapter(dataList.getData(),getActivity());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                        Intent intent = new Intent(getActivity(), MoneyInvoiceEdit.class);
                        intent.putExtra("data",(Serializable) dataList.getData().get(position));
                        startActivityForResult(intent, 1);


                    }
                });

            }


        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            getList();
        }
    }


}
