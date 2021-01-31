package com.ming.sjll.My.common;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.Bean.WalletAccountBean;
import com.ming.sjll.Bean.WalletBean;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.MoneyAccountListAdapter;
import com.ming.sjll.My.adapter.MoneyProjectAdapter;
import com.ming.sjll.My.adapter.MoneyProjectMoneyAdapter;
import com.ming.sjll.My.adapter.MoneyProjectMoneyFreezeAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.rey.material.app.BottomSheetDialog;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

/**
 * 个人中心-资金
 */
public class Money extends BaseFragment {

    public int point;
    WalletAccountBean accountListData;
    MoneyAccountListAdapter adapter;
    WalletBean mData;

    public static Money newInstance() {

        return new Money();
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_money);
        initView();
        event();
    }


    private void initView() {
        if (getParentFragment() instanceof Myfragemt) {
            ((Myfragemt) getParentFragment()).setChildObjectForPosition(this,point);
        }


        getData();

    }

    private void getData()
    {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.WALLET_MY_WALLET, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                mData = new Gson().fromJson(data, WalletBean.class);

                ((TextView)findViewById(R.id.money)).setText(mData.getData().getMoney());
                ((TextView)findViewById(R.id.projectAmount)).setText(mData.getData().getProjectAmount());
                ((TextView)findViewById(R.id.pledge)).setText(mData.getData().getPledge());
                ((TextView)findViewById(R.id.freeze_money)).setText(mData.getData().getFreeze_money());
                ((TextView)findViewById(R.id.invoiceNumber)).setText(mData.getData().getInvoiceNumber());

                if(mData.getData().getCompanyAccount() == null || mData.getData().getCompanyAccount().getCard() == null)
                {
                    ((TextView)findViewById(R.id.companyAccount)).setVisibility(View.GONE);
                    findViewById(R.id.companyAccountNull).setVisibility(View.VISIBLE);
                }
                else {
                    ((TextView)findViewById(R.id.companyAccount)).setText(mData.getData().getCompanyAccount().getHidden_card());
                    findViewById(R.id.companyAccountNull).setVisibility(View.GONE);
                }

                if(mData.getData().getAliPay() == null || mData.getData().getAliPay().getCard() == null)
                {
                    ((TextView)findViewById(R.id.aliPay)).setVisibility(View.GONE);
                    findViewById(R.id.aliPayNull).setVisibility(View.VISIBLE);
                }
                else {
                    ((TextView)findViewById(R.id.aliPay)).setText(mData.getData().getAliPay().getHidden_card());
                    findViewById(R.id.aliPayNull).setVisibility(View.GONE);
                }

            }
        });
    }


    private void event()
    {
//        冻结金额
        findViewById(R.id.freezeFunds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDailog.show();
                HttpParamsObject param = new HttpParamsObject();
                new HttpUtil().sendRequest(Constant.WALLET_FREEZE_FUNDS, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {

                        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                        bottomInterPasswordDialog.setContentView(R.layout.dialog_money_project_list);
                        bottomInterPasswordDialog.inDuration(300);
                        bottomInterPasswordDialog.outDuration(200);
                        bottomInterPasswordDialog.show();

                        bottomInterPasswordDialog.findViewById(R.id.tipTextView).setVisibility(View.GONE);
                        RecyclerView listView = bottomInterPasswordDialog.findViewById(R.id.recyclerList);
                        ProjectOrderListBean.DataBean dataList = new Gson().fromJson(data, ProjectOrderListBean.DataBean.class);

                        listView.setLayoutManager( new LinearLayoutManager(getContext()));
                        MoneyProjectMoneyFreezeAdapter listAdapter = new MoneyProjectMoneyFreezeAdapter(dataList.getData(),getActivity());
                        listView.setAdapter(listAdapter);
//                        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//                            @Override
//                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                                switch (view.getId()) {
//                                    case R.id.openInfo:
//                                        break;
//                                    case R.id.makeInvoice:
//                                        Intent intent = new Intent(getActivity(), MoneyInvoiceSelectProject.class);
//                                        intent.putExtra("projectId",dataList.getData().getData().get(position).getProject_id());
//                                        startActivityForResult(intent, 1);
//                                        break;
//
//                                }
//                            }
//                        });

                    }

                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });

            }
        });

//        退保证金
        findViewById(R.id.refund).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDailog.show();
                HttpParamsObject param = new HttpParamsObject();
                new HttpUtil().sendRequest(Constant.WALLET_CHECK_REFUND, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {

                        ProjectOrderListBean.DataBean dataList = new Gson().fromJson(data, ProjectOrderListBean.DataBean.class);
                        if(dataList.getData().size()  > 0)
                        {
                            BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                            bottomInterPasswordDialog.setContentView(R.layout.dialog_money_project_list);
                            bottomInterPasswordDialog.inDuration(300);
                            bottomInterPasswordDialog.outDuration(200);
                            bottomInterPasswordDialog.show();

                            bottomInterPasswordDialog.findViewById(R.id.tipTextView).setVisibility(View.GONE);
                            RecyclerView listView = bottomInterPasswordDialog.findViewById(R.id.recyclerList);


                            listView.setLayoutManager( new LinearLayoutManager(getContext()));
                            MoneyProjectMoneyAdapter listAdapter = new MoneyProjectMoneyAdapter(dataList.getData(),getActivity());
                            listView.setAdapter(listAdapter);
                        }
                        else
                        {
                            ToastShow.s("系统错误");
                        }

                    }

                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });



            }
        });
//        项目资金查看
        findViewById(R.id.projectMoney).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDailog.show();
                HttpParamsObject param = new HttpParamsObject();
                new HttpUtil().sendRequest(Constant.WALLET_PROJECT_MONEY, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {

                        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                        bottomInterPasswordDialog.setContentView(R.layout.dialog_money_project_list);
                        bottomInterPasswordDialog.inDuration(300);
                        bottomInterPasswordDialog.outDuration(200);
                        bottomInterPasswordDialog.show();

                        bottomInterPasswordDialog.findViewById(R.id.tipTextView).setVisibility(View.GONE);
                        RecyclerView listView = bottomInterPasswordDialog.findViewById(R.id.recyclerList);
                        ProjectOrderListBean.DataBean dataList = new Gson().fromJson(data, ProjectOrderListBean.DataBean.class);

                        listView.setLayoutManager( new LinearLayoutManager(getContext()));
                        MoneyProjectMoneyAdapter listAdapter = new MoneyProjectMoneyAdapter(dataList.getData(),getActivity());
                        listView.setAdapter(listAdapter);
//                        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//                            @Override
//                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                                switch (view.getId()) {
//                                    case R.id.openInfo:
//                                        break;
//                                    case R.id.makeInvoice:
//                                        Intent intent = new Intent(getActivity(), MoneyInvoiceSelectProject.class);
//                                        intent.putExtra("projectId",dataList.getData().getData().get(position).getProject_id());
//                                        startActivityForResult(intent, 1);
//                                        break;
//
//                                }
//                            }
//                        });

                    }

                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });

            }
        });

//        提现
        findViewById(R.id.withdrawApply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(
//                        (mData.getData().getCompanyAccount() == null || mData.getData().getCompanyAccount().getCard() == null ||  mData.getData().getCompanyAccount().getCard().equals(""))
//                        && (mData.getData().getAliPay() == null || mData.getData().getAliPay().getCard() == null || mData.getData().getAliPay().getCard().equals(""))
//
//                )
//                {
//                    Tools.jump(getActivity(), MoneyEditAccount.class, false);
//                    return;
//                }

                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_withdraw_apply);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();

                ((TextView)bottomInterPasswordDialog.findViewById(R.id.withdrawMoney)).setText(mData.getData().getMoney());
                bottomInterPasswordDialog.findViewById(R.id.btnAll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ((TextView)bottomInterPasswordDialog.findViewById(R.id.money)).setText(mData.getData().getMoney());

                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String money =  ((TextView)bottomInterPasswordDialog.findViewById(R.id.money)).getText().toString();
                        HttpParamsObject param = new HttpParamsObject();
                        new HttpUtil().sendRequest(Constant.WALLET_ACCOUNT_LIST, param, new CommonCallback() {
                            @Override
                            public void onSuccessJson(String data, int code) {
                                bottomInterPasswordDialog.hide();
                                accountListData = new Gson().fromJson(data, WalletAccountBean.class);


                                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                                bottomInterPasswordDialog.setContentView(R.layout.dialog_money_list);
                                bottomInterPasswordDialog.inDuration(300);
                                bottomInterPasswordDialog.outDuration(200);
                                bottomInterPasswordDialog.show();
                                SwipeRecyclerView listView = bottomInterPasswordDialog.findViewById(R.id.dateListView);
                                listView.setLayoutManager(new LinearLayoutManager(getContext()));
//                                listView.setSwipeMenuCreator(swipeMenuCreator);
//                                listView.setOnItemMenuClickListener(mMenuItemClickListener);

                                MoneyAccountListAdapter adapterTemp = new MoneyAccountListAdapter(accountListData.getData());
                                listView.setAdapter(adapterTemp);
                                adapterTemp.notifyDataSetChanged();
                                adapterTemp.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                    @Override
                                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                                        HttpParamsObject param = new HttpParamsObject();
                                        param.setParam("money",money);
                                        param.setParam("accountId",accountListData.getData().get(position).getId());
                                        new HttpUtil().sendRequest(Constant.WALLET_APPLY, param, new CommonCallback() {
                                            @Override
                                            public void onSuccessJson(String data, int code) {
                                              ToastShow.s("申请提现成功，请耐心等候");
                                                bottomInterPasswordDialog.hide();
                                                getData();
                                            }
                                        });
                                    }
                                });

                                bottomInterPasswordDialog.findViewById(R.id.editMoney).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        bottomInterPasswordDialog.hide();
                                        Tools.jump(getActivity(), MoneyEditAccount.class, false);
                                    }
                                });




                            }
                        });

                    }
                });




            }
        });

//        账号管理
        findViewById(R.id.accountManage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                new HttpUtil().sendRequest(Constant.WALLET_ACCOUNT_LIST, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        accountListData = new Gson().fromJson(data, WalletAccountBean.class);


                        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                        bottomInterPasswordDialog.setContentView(R.layout.dialog_money_list);
                        bottomInterPasswordDialog.inDuration(300);
                        bottomInterPasswordDialog.outDuration(200);
                        bottomInterPasswordDialog.show();
                        SwipeRecyclerView listView = bottomInterPasswordDialog.findViewById(R.id.dateListView);
                        listView.setLayoutManager(new LinearLayoutManager(getContext()));
                        listView.setSwipeMenuCreator(swipeMenuCreator);
                        listView.setOnItemMenuClickListener(mMenuItemClickListener);

                        adapter = new MoneyAccountListAdapter(accountListData.getData());
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        bottomInterPasswordDialog.findViewById(R.id.editMoney).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomInterPasswordDialog.hide();
                                Tools.jump(getActivity(), MoneyEditAccount.class, false);
                            }
                        });




                    }
                });

            }
        });


        findViewById(R.id.invoice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(getActivity(), MoneyInvoice.class, false);
            }
        });

        findViewById(R.id.detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(getActivity(), MoneyDetail.class, false);
            }
        });

    }




    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {

                HttpParamsObject param = new HttpParamsObject();
                param.setParam("accountId" , accountListData.getData().get(position).getId());
                new HttpUtil().sendRequest(Constant.WALLET_DEL_ACCOUNT, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("删除成功");
                        accountListData.getData().remove(position);
                        adapter.notifyDataSetChanged();
                    }
                    });
            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {

            }
        }
    };

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = Tools.dip2px(getContext(), 77);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;


            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {


                SwipeMenuItem addItem = new SwipeMenuItem(getContext())
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setTextSize(16)
//                        .setBackgroundColor(Color.parseColor("#0A3FFF"))
                        .setBackground(R.mipmap.btn_right_delete)
//                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。


            }
        }
    };





}
