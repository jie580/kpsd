package com.ming.sjll.My.common;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectGoodsListBean;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.Home.bean.AdsBanner;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.OrderCompanyGoodsListAdapter;
import com.ming.sjll.My.adapter.OrderProjectListAdapter;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.Tools;
import com.rey.material.app.BottomSheetDialog;

/**
 * 个人中心-订单
 */
public class Order extends BaseFragment {


    public int point;
    String type;

    public static Order newInstance() {

        return new Order();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_order);
        initView();


    }

    private void event() {

        findViewById(R.id.btnGuide1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("user"))
                {
                    Tools.jump(getActivity(), OrderPersonage.class, false);
                }
                else {
                    Tools.jump(getActivity(), OrderCompanyWrap.class, false);
                }

            }
        });

        findViewById(R.id.btnGuide6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tools.jump(getActivity(), OrderPaymentWrap.class, false);

            }
        });


        findViewById(R.id.btnGuide2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDailog.show();
                HttpParamsObject param = new HttpParamsObject();
                new HttpUtil().sendRequest(Constant.ORDER_COLLECT_PAYMENT, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        super.onSuccess(data, code);
                        ProjectOrderListBean.DataBean dataList = new Gson().fromJson(data, ProjectOrderListBean.DataBean.class);

                        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                        bottomInterPasswordDialog.setContentView(R.layout.dialog_project_list);
                        bottomInterPasswordDialog.inDuration(300);
                        bottomInterPasswordDialog.outDuration(200);
                        bottomInterPasswordDialog.show();

                        if(dataList.getData().size() <= 0)
                        {
                            bottomInterPasswordDialog.findViewById(R.id.dateListView).setVisibility(View.VISIBLE);
                        }
                        else {
                            RecyclerView listView = bottomInterPasswordDialog.findViewById(R.id.dateListView);
                            listView.setLayoutManager( new LinearLayoutManager(getContext()));
                            OrderProjectListAdapter listAdapter = new OrderProjectListAdapter(dataList.getData(),getActivity());
                            listView.setAdapter(listAdapter);
                        }


//                        txt.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
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
    }
 
    private void initView() {
        if (getParentFragment() instanceof Myfragemt) {
            ((Myfragemt) getParentFragment()).setChildObjectForPosition(this, point);
        }


        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.ORDER_MY_ORDER, param, new CommonCallback() {
            @Override
            public void onSuccess(Object data, int code) {
                super.onSuccess(data, code);

                type = ObjeGetValue.GetValu("type", data).toString();
                String project = ObjeGetValue.GetValu("project", data).toString();
                String goods = ObjeGetValue.GetValu("goods", data).toString();
                String service = ObjeGetValue.GetValu("service", data).toString();
                String waitPay = ObjeGetValue.GetValu("waitPay", data).toString();
                String course = ObjeGetValue.GetValu("course", data).toString();
                String myPay = ObjeGetValue.GetValu("myPay", data).toString();


                if(type.equals("user"))
                {
                    findViewById(R.id.hide0).setVisibility(View.VISIBLE);
                    findViewById(R.id.hide1).setVisibility(View.GONE);
                    findViewById(R.id.hide2).setVisibility(View.GONE);
                    findViewById(R.id.hide3).setVisibility(View.GONE);
                }
                ((TextView)findViewById(R.id.text0)).setText(project);
                ((TextView)findViewById(R.id.text1)).setText(project);
                ((TextView)findViewById(R.id.text2)).setText(goods);
                ((TextView)findViewById(R.id.text3)).setText(service);
                ((TextView)findViewById(R.id.text4)).setText(waitPay);
                ((TextView)findViewById(R.id.text5)).setText(course);
                ((TextView)findViewById(R.id.text6)).setText(myPay);



                event();

            }
        });



    }



}
