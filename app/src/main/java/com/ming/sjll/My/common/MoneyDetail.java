package com.ming.sjll.My.common;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.My.adapter.MoneyProjectAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.databinding.MyMoneyDetailBinding;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * 个人中心-资金-明细
 */
public class MoneyDetail extends BaseActivity {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 20;

    MyMoneyDetailBinding binding;
    String type = "month";
    String dateTimeMonth_year;
    String dateTimeYear_year;
    String dateTimeMonth_month;



    public static MoneyDetail newInstance() {
        return new MoneyDetail();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_money_detail, null, false);
        setContentView(binding.getRoot());
        initView();
        event();

    }

    private void event()
    {
        binding.monthType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "month";
                getData();
                binding.monthType.setTextColor(Color.parseColor("#FFFFFF"));
                binding.yearType.setTextColor(Color.parseColor("#C4D9F5"));
            }
        });


        binding.yearType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "year";
                getData();
                binding.yearType.setTextColor(Color.parseColor("#FFFFFF"));
                binding.monthType.setTextColor(Color.parseColor("#C4D9F5"));
            }
        });

        List<String> date1 = new LinkedList<>();
        for (int i = 2020; i < 2030; i++) {
            date1.add(i+"年");
        }

        List<String> date2 = new LinkedList<>();
        for (int i = 1; i < 13; i++) {
            date2.add(i+"月");
        }

        binding.dateTimeWarp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int optionIndex1 = 0;
                int optionIndex2 = 0;
                if(type.equals("month"))
                {
                    for (int i = 0; i < date1.size(); i++) {
                        if(date1.get(i).equals(dateTimeMonth_year+"年"))
                        {
                            optionIndex1 = i;
                        }
                    }
                    for (int i = 0; i < date2.size(); i++) {
                        if(date2.get(i).equals(dateTimeMonth_month+"月"))
                        {
                            optionIndex2 = i;
                        }
                    }
                }
                else
                {
                    for (int i = 0; i < date1.size(); i++) {
                        if(date1.get(i).equals(dateTimeYear_year+"年"))
                        {
                            optionIndex1 = i;
                        }
                    }
                }
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        if(type.equals("month"))
                        {
                            dateTimeMonth_year = date1.get(options1).replace("年","");
                            dateTimeMonth_month = date2.get(option2).replace("月","");
                        }
                        else
                        {
                            dateTimeYear_year = date1.get(options1).replace("年","");
                        }
                        getData();
                    }
                })
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .setSelectOptions(optionIndex1,optionIndex2)
                        .build();
                if(type.equals("month"))
                {
                    pvOptions.setNPicker(date1, date2,null);
                }
                else
                {
                    pvOptions.setNPicker(date1, null,null);
                }
                pvOptions.show();
            }
        });


    }

    private void initView() {
        Calendar cd = Calendar.getInstance();
        dateTimeYear_year = cd.get(Calendar.YEAR)+"";
        dateTimeMonth_year = dateTimeYear_year;
        dateTimeMonth_month = cd.get(Calendar.MONTH) + 1 + "";
        getData();
    }

    private void getData(){

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("type",type);
        String s = "";
        if(type.equals("month"))
        {
           s = dateTimeMonth_year + "年" + dateTimeMonth_month +"月";
            binding.title.setText("当月发生项目");
        }
        else
        {
            s = dateTimeYear_year + "年";
            binding.title.setText("当年发生项目");
        }
        binding.dateTime.setText(s);
        param.setParam("dateTime", s);

        new HttpUtil().sendRequest(Constant.INVOICE_DETAIL_INFO, param, new CommonCallback() {

            @Override
            public void onSuccess(Object data, int code) {
                super.onSuccess(data, code);
                binding.collectMoney.setText(ObjeGetValue.GetValu("collectMoney", data).toString());
                binding.payMoney.setText(ObjeGetValue.GetValu("payMoney", data).toString());
                binding.countMoney.setText("共发生" + ObjeGetValue.GetValu("countMoney", data).toString()+"个项目，合计");
                binding.projectCount.setText(ObjeGetValue.GetValu("projectCount", data).toString());
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
        String s = "";
        if(type.equals("month"))
        {
            s = dateTimeMonth_year + "年" + dateTimeMonth_month +"月";
        }
        else
        {
            s = dateTimeYear_year + "年";
        }
        param.setParam("dateTime", s);
        page = 1;
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        new HttpUtil().sendRequest(Constant.INVOICE_MONEY_DETAIL, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                ProjectOrderListBean dataList = new Gson().fromJson(data, ProjectOrderListBean.class);

                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new MoneyProjectAdapter(dataList.getData().getData(),getActivity(),"moneyDetail");
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.openInfo:
                                break;
                            case R.id.makeInvoice:
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


}
