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
import com.ming.sjll.Bean.InvoiceBean;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.My.adapter.MoneyProjectAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.databinding.MyMoneyInvoiceBinding;

/**
 * 个人中心-资金-发票
 */
public class MoneyProjectMoney extends BaseActivity {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 20;
    MyMoneyInvoiceBinding binding;
    String type = "need";



    public static MoneyProjectMoney newInstance() {
        return new MoneyProjectMoney();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_money_invoice, null, false);
        setContentView(binding.getRoot());
        initView();
        event();

    }

    private void event()
    {
        binding.invoiceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(getActivity(), MoneyInvoiceList.class, false);
            }
        });


        binding.need.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "need";
                binding.title.setText("需开票订单");
                getList();
            }
        });

        binding.issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "issue";
                binding.title.setText("可开票订单");
                getList();
            }
        });
    }

    private void initView() {
        getData();
    }

    private void getData(){
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.INVOICE_INVOICE_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                InvoiceBean mData = new Gson().fromJson(data, InvoiceBean.class);
//                if(mData.getData().getInvoiceList().size() > 0)
//                {
//                    for (int i = 0; i < mData.getData().getInvoiceList().size(); i++) {
//                        if(mData.getData().getInvoiceList().get(i).getIs_default().equals("1"))
//                        {
//                            binding.name.setText(mData.getData().getInvoiceList().get(i).getName());
//                            binding.dutyParagraph.setText(mData.getData().getInvoiceList().get(i).getDuty_paragraph());
//                        }
//                    }
//                }
                binding.name.setText(mData.getData().getBank());
                binding.dutyParagraph.setText(mData.getData().getBank_card());
                binding.issueInvoice.setText(mData.getData().getIssueInvoice());
                binding.needInvoice.setText(mData.getData().getNeedInvoice());

            }
        });

        getList();
    }

    MoneyProjectAdapter  listAdapter;
    private void getList()
    {
        RecyclerView listView = binding.recyclerList;

        if (isLoad ) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("type",type);
        page = 1;
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        new HttpUtil().sendRequest(Constant.INVOICE_NEED_INVOICE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                ProjectOrderListBean dataList = new Gson().fromJson(data, ProjectOrderListBean.class);

                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new MoneyProjectAdapter(dataList.getData().getData(),getActivity(),type);
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.openInfo:
                                break;
                            case R.id.makeInvoice:
                                Intent intent = new Intent(getActivity(), MoneyInvoiceSelectProject.class);
                                intent.putExtra("projectId",dataList.getData().getData().get(position).getProject_id());
                                startActivityForResult(intent, 1);
                                break;

                        }
                    }
                });

            }

            @Override
            public void onFinal() {
                super.onFinal();
                isLoad = false;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            getData();
        }
    }

}
