package com.ming.sjll.My.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectGoodsListBean;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.My.adapter.OrderCompanyGoodsListAdapter;
import com.ming.sjll.My.adapter.OrderPaymentProjectListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;

/**
 * 个人中心-订单-项目
 */
public class OrderPaymentProject extends BaseFragment {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;

    private int isPay = 2;
    private String type = "project";

    private ProjectOrderListBean dataList;
    private OrderPaymentProjectListAdapter listAdapter;
    RecyclerView listView;



    public static OrderPaymentProject newInstance() {
        return new OrderPaymentProject();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_collect_user);

        initView();
    }



    private void initView() {
        listView = (RecyclerView) findViewById(R.id.recyclerList);

        getList();
    }

    public void getList()
    {
        if (isLoad ) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        page = 1;
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        param.setParam("type", type);
        param.setParam("isPay", isPay);
        new HttpUtil().sendRequest(Constant.ORDER_PAID_ORDER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, ProjectOrderListBean.class);

                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new OrderPaymentProjectListAdapter(dataList.getData().getData(),getActivity());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId())
                        {
                            case R.id.openInfo:
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data", dataList.getData().getData().get(position));
                                Tools.jump(getActivity(), OrderCompanyGoodsInfo.class, bundle,false);
//                                ToastShow.s("点击用户");
                                break;
//                                HttpParamsObject param2 = new HttpParamsObject();
//                                param2.setParam("type",type);
//                                param2.setParam("targetId",dataList.getData().getData().get(position).getUser_id());
//                                new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECT, param2, new CommonCallback() {
//                                    @Override
//                                    public void onSuccessJson(String data, int code) {
//                                        dataList.getData().getData().remove(position);
//                                        listAdapter.notifyDataSetChanged();
//                                    }
//                                });
//                                break;
//                            case R.id.wrap:
//                                Bundle bundle = new Bundle();
//                                bundle.putString("userId", dataList.getData().getData().get(position).getUser_id());
//                                Tools.jump(getActivity(), HomepageActivity.class, bundle,false);
////                                ToastShow.s("点击用户");
//                                break;
                        }
//
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


    public void  nextPage() {
        if (dataList == null || dataList.getData().getData().size() == 0) {
            getList();
            return;
        }
        if (isLoad) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        page++;
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        new HttpUtil().sendRequest(Constant.ORDER_COMPANY_PROJECT_ORDER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                ProjectOrderListBean temgData = new Gson().fromJson(data, ProjectOrderListBean.class);
                for (int i = 0; i < temgData.getData().getData().size(); i++) {
                    dataList.getData().getData().add(temgData.getData().getData().get(i));
                }
                listAdapter.notifyDataSetChanged();

            }
            @Override
            public void onFinal() {
                super.onFinal();
                isLoad = false;
            }
        });

    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
