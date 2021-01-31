package com.ming.sjll.My.common;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.InvoiceBean;
import com.ming.sjll.Bean.ProjectCost;
import com.ming.sjll.My.adapter.MoneyInvoiceAdapter;
import com.ming.sjll.My.adapter.MoneyInvoiceProjectAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyMoneyInvoiceListBinding;
import com.ming.sjll.databinding.MyMoneyInvoiceSelectProjectBinding;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 个人中心-发票-选择项目
 */
public class MoneyInvoiceSelectProject extends BaseActivity {



    MyMoneyInvoiceSelectProjectBinding binding;

    public String type = "1";//0-未开 2-已开 其他-全部（默认）
    String projectId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_money_edit_account);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_money_invoice_select_project, null, false);
        setContentView(binding.getRoot());
        binding.setTitleViewModel(new ToolbarViewModel("选择需开票交易"));
        binding.titleBar.titleBarTvRight.setText("确定");
        binding.titleBar.titleBarTvRight.setTextColor(Color.parseColor("#80B5FF"));
        binding.titleBar.titleBarTvRight.setVisibility(View.VISIBLE);
        initView();
        event();

    }

    private void event() {
        binding.titleBar.titleBarTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MoneyInvoiceSelectInvoice.class);
                List<String> list = new LinkedList<>();
                for (int i = 0; i < dataList.getData().size(); i++) {
                    if(dataList.getData().get(i).isSelect())
                    {
                        list.add(dataList.getData().get(i).getTarget_id());
                    }
                }
                if(list.size() == 0)
                {
                    ToastShow.s("请选择交易项");
                    return;
                }
                intent.putExtra("targetIds",(Serializable)list);
                intent.putExtra("projectId",projectId);
                startActivityForResult(intent, 1);
            }
        });
       
        binding.type0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "0";
                binding.type0.setTextColor(Color.parseColor("#000000"));
                binding.type1.setTextColor(Color.parseColor("#D7DAE5"));
                binding.type2.setTextColor(Color.parseColor("#D7DAE5"));
                listAdapter.notifyDataSetChanged();
            }
        });
        binding.type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "1";
                binding.type1.setTextColor(Color.parseColor("#000000"));
                binding.type0.setTextColor(Color.parseColor("#D7DAE5"));
                binding.type2.setTextColor(Color.parseColor("#D7DAE5"));
                listAdapter.notifyDataSetChanged();
            }
        });
        binding.type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "2";
                binding.type2.setTextColor(Color.parseColor("#000000"));
                binding.type0.setTextColor(Color.parseColor("#D7DAE5"));
                binding.type1.setTextColor(Color.parseColor("#D7DAE5"));
                listAdapter.notifyDataSetChanged();
            }
        });
        binding.checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int position = 0; position < dataList.getData().size(); position++) {

                    if(dataList.getData().get(position).getInvoice_status().equals("0"))
                    {
                        dataList.getData().get(position).setSelect(true);
                    }
                    listAdapter.notifyDataSetChanged();

                }
            }
        });

    }


    private void initView() {
        projectId = getIntent().getStringExtra("projectId");
        getList();
    }



    MoneyInvoiceProjectAdapter listAdapter;
    ProjectCost.DataListBean dataList;
    private void getList()
    {
        RecyclerView listView = binding.recyclerList;

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId",projectId);
        param.setParam("type",type);
        new HttpUtil().sendRequest(Constant.INVOICE_NEED_INVOICE_SUPPLIERS, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, ProjectCost.DataListBean.class);

                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new MoneyInvoiceProjectAdapter(dataList.getData(),getActivity(),type);
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if(dataList.getData().get(position).getInvoice_status().equals("0"))
                        {
                            dataList.getData().get(position).setSelect(! dataList.getData().get(position).isSelect());
                        }
                        listAdapter.notifyDataSetChanged();
//                        Intent intent = new Intent(getActivity(), MoneyInvoiceEdit.class);
//                        intent.putExtra("data",(Serializable) dataList.getData().get(position));
//                        startActivityForResult(intent, 1);
//

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
