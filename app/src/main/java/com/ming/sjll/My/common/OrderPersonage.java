package com.ming.sjll.My.common;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.OrderPersonageListAdapter;
import com.ming.sjll.My.adapter.SampleListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.Tools;

/**
 * 个人中心-订单
 */
public class OrderPersonage extends BaseActivity {


    public int point;
    String type;
    ProjectOrderListBean mData;
    OrderPersonageListAdapter orderPersonageListAdapter;

    public static OrderPersonage newInstance() {

        return new OrderPersonage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_personage);
        initView();



        event();
    }

    private void event() {

    }
 
    private void initView() {

        RecyclerView recyclerList = (RecyclerView)findViewById(R.id.recyclerList);
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.ORDER_PROJECT_ORDER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String  data, int code) {
                 mData = new Gson().fromJson(data, ProjectOrderListBean.class);

                LinearLayoutManager linearmanager = new LinearLayoutManager(getContext());
                recyclerList.setLayoutManager(linearmanager);
                orderPersonageListAdapter = new OrderPersonageListAdapter(mData.getData().getData(), getContext());
                recyclerList.setAdapter(orderPersonageListAdapter);

                orderPersonageListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.openInfo:
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data", mData.getData().getData().get(position));
                                Tools.jump(getActivity(), OrderCompanyOrderInfo.class, bundle, false);
//                                ToastShow.s("点击用户");
                                break;
                        }
                    }
                });

                countDown();

            }
        });


    }


    private void countDown()
    {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //要执行的任务
                for (int i = 0; i < mData.getData().getData().size(); i++) {
                    int time = Integer.parseInt(mData.getData().getData().get(i).getCountDown()) - 1;
                    if(time  < 0)
                    {
                        continue;
                    }
                    else
                    {
                        mData.getData().getData().get(i).setCountDown(time+"");
                    }

                }
                orderPersonageListAdapter.notifyDataSetChanged();
                countDown();
            }
        }, 1000);
    }




}
