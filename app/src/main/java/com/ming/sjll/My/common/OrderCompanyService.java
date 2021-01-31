package com.ming.sjll.My.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.Bean.ProjectGoodsListBean;
import com.ming.sjll.My.adapter.OrderCompanyServiceListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogProjectSelect;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;

/**
 * 个人中心-订单-服务
 */
public class OrderCompanyService extends BaseFragment {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private ProjectGoodsListBean dataList;
    private OrderCompanyServiceListAdapter listAdapter;
    RecyclerView listView;



    public static OrderCompanyService newInstance() {
        return new OrderCompanyService();
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

    private void getList()
    {
        if (isLoad ) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        page = 1;
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        new HttpUtil().sendRequest(Constant.ORDER_SERVICE_ORDER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, ProjectGoodsListBean.class);

                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new OrderCompanyServiceListAdapter(dataList.getData().getData(),getActivity());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        GoodsItem.DataBean dataBean = dataList.getData().getData().get(position);
                        switch (view.getId())
                        {
                            case R.id.openInfo:
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data", dataList.getData().getData().get(position));
                                Tools.jump(getActivity(), OrderCompanyServiceInfo.class, bundle,false);
                                break;
                            case R.id.countDown:

                                if(dataBean.getStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) > 0)
                                {
//                                    倒计时
                                    HttpParamsObject param = new HttpParamsObject();
                                    param.setParam("orderNo",dataList.getData().getData().get(position).getOrder_no());
                                    param.setParam("status",1);
                                    param.setParam("type","service");
                                    new HttpUtil().sendRequest(Constant.ORDER_SAVE_GOODS_STATUS, param, new CommonCallback() {
                                        @Override
                                        public void onSuccessJson(String data, int code) {
                                            getList();
                                        }
                                    });
                                }
                                else if(dataBean.getStatus().equals("1"))
                                {
//                                    回仓
                                    HttpParamsObject param = new HttpParamsObject();
                                    param.setParam("orderNo",dataList.getData().getData().get(position).getOrder_no());
                                    param.setParam("status",3);
                                    param.setParam("type","service");
                                    new HttpUtil().sendRequest(Constant.ORDER_SAVE_GOODS_STATUS, param, new CommonCallback() {
                                        @Override
                                        public void onSuccessJson(String data, int code) {
                                            getList();
                                        }
                                    });
                                }
                                break;
                            case R.id.moneyBtn:
                                DialogProjectSelect dialogProjectSelect = new DialogProjectSelect();
                                dialogProjectSelect.init(getActivity());
                                dialogProjectSelect.setMainId(dataList.getData().getData().get(position).getOrder_no());
                                dialogProjectSelect.setItemType("service");
                                if(dataBean.getStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) > 0)
                                {
//                                    拒绝加入
                                    dialogProjectSelect.showRefusedCooperation();
                                }
                                else if(dataBean.getStatus().equals("4"))
                                {
//                                    申诉中
                                    dialogProjectSelect.showAppealStatus();
                                }
                                else
                                {
//                                    显示申诉
                                    dialogProjectSelect.showOrderAppeal();
                                }
                                dialogProjectSelect.show();
                                dialogProjectSelect.setCommonCallback(new CommonCallback(){
                                    @Override
                                    public void onSuccess(Object data, int code) {
                                        super.onSuccess(data, code);
                                        if(code == DialogProjectSelect.REFUSED_COOPERATION_CODE)
                                        {
                                            getList();
                                        }
                                    }
                                });
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
                ProjectGoodsListBean temgData = new Gson().fromJson(data, ProjectGoodsListBean.class);
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

}
