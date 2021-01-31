package com.ming.sjll.My.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.My.adapter.OrderCompanyListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogProjectSelect;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;

/**
 * 个人中心-订单
 */
public class OrderCompanyOrder extends BaseFragment {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private ProjectOrderListBean dataList;
    private OrderCompanyListAdapter listAdapter;
    RecyclerView listView;



    public static OrderCompanyOrder newInstance() {
        return new OrderCompanyOrder();
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
        new HttpUtil().sendRequest(Constant.ORDER_COMPANY_PROJECT_ORDER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, ProjectOrderListBean.class);

                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new OrderCompanyListAdapter(dataList.getData().getData(),getActivity());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                        ProjectOrderListBean.DataBeanX dataBean = dataList.getData().getData().get(position);

                        switch (view.getId()) {
                            case R.id.openInfo:
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data", dataList.getData().getData().get(position));
                                Tools.jump(getActivity(), OrderCompanyOrderInfo.class, bundle, false);
//                                ToastShow.s("点击用户");
                                break;
                            case R.id.countDown:

                                if(dataBean.getMemberStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) > 0) {
                                    /**
                                     * 同意加入
                                     */
                                    RongImUtils.sendConfirmCompanyCooperation(dataList.getData().getData().get(position).getApplyIds(), false, new RongImUtils.onChatEven() {
                                        @Override
                                        public void onSuccess() {
                                            getList();
                                        }

                                    });
                                }
                            case R.id.moneyBtn:
                                DialogProjectSelect dialogProjectSelect = new DialogProjectSelect();
                                dialogProjectSelect.init(getActivity());
                                dialogProjectSelect.setMainId(dataList.getData().getData().get(position).getProject_id());
                                dialogProjectSelect.setApplyId(dataList.getData().getData().get(position).getApply_id());
                                dialogProjectSelect.setChatId(dataList.getData().getData().get(position).getProject_id());
                                dialogProjectSelect.setChatName(dataList.getData().getData().get(position).getDemand());
                                dialogProjectSelect.setUserType("user");
                                dialogProjectSelect.setApplyIdList(dataList.getData().getData().get(position).getApplyIds());


                                if(dataBean.getMemberStatus().equals("0") && Integer.parseInt(dataBean.getCountDown()) > 0)
                                {
//                                    申请中
                                    dialogProjectSelect.showRefusedJoin();
                                }
                                else if(dataBean.getMemberStatus().equals("4"))
                                {
//                                    申诉中
                                    dialogProjectSelect.showAppealStatus();
                                }
                                else
                                {
                                    dialogProjectSelect.showOpenChat();
                                    dialogProjectSelect.showOpenChatLine();

                                    dialogProjectSelect.showProjectManage();
                                    dialogProjectSelect.showProjectManageLine();

                                    dialogProjectSelect.showFlow();
                                    dialogProjectSelect.showFlowLine();

                                    dialogProjectSelect.showOrderAppeal();
                                }
                                dialogProjectSelect.show();
                                dialogProjectSelect.setCommonCallback(new CommonCallback(){
                                    @Override
                                    public void onSuccess(Object data, int code) {
                                        super.onSuccess(data, code);
                                        if(code == DialogProjectSelect.REFUSED_JOIN_CODE)
                                        {
                                            getList();
                                        }
                                    }
                                });
                                break;
                        }

                    }
                });
//                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//                    @Override
//                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                        switch (view.getId())
//                        {
//                            case R.id.delete:
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
//                        }
//
//                    }
//                });

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

}
