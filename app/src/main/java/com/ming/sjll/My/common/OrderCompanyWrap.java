package com.ming.sjll.My.common;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.My.adapter.OrderPersonageListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.databinding.MyOrderCompanyWrapBinding;

/**
 * 个人中心-订单
 */
public class OrderCompanyWrap extends BaseActivity {


    ProjectOrderListBean mData;

//    MyOrderCompanyWrapBinding binding;
    private Fragment[] mFragments;
    OrderCompanyOrder orderCompanyOrder;
    OrderCompanyGoods  orderCompanyGoods;
    OrderCompanyService orderCompanyService;

    public static OrderCompanyWrap newInstance() {

        return new OrderCompanyWrap();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_company_wrap);
//        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_order_company_wrap, null, false);
        initView();

        event();
    }

    private void event() {
        ((TextView)findViewById(R.id.guide1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(1);
            }
        });
        ((TextView)findViewById(R.id.guide2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(2);
            }
        });
        ((TextView)findViewById(R.id.guide3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select(3);
            }
        });
    }
 
    private void initView() {

        orderCompanyOrder = OrderCompanyOrder.newInstance();
        orderCompanyGoods = OrderCompanyGoods.newInstance();
        orderCompanyService = OrderCompanyService.newInstance();

        mFragments = new Fragment[]{orderCompanyOrder,orderCompanyGoods,orderCompanyService};
        showFragment(orderCompanyOrder);
        getGuide();
    }

    private void getGuide()
    {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.ORDER_UNREAD, param, new CommonCallback() {
            @Override
            public void onSuccess(Object data, int code) {
                super.onSuccess(data, code);
                String project = ObjeGetValue.GetValu("project", data).toString();
                String goods = ObjeGetValue.GetValu("goods", data).toString();
                String service = ObjeGetValue.GetValu("service", data).toString();
                if( project.equals("0") || project.equals(""))
                {
                    ((TextView)findViewById(R.id.guide1Text)).setVisibility(View.GONE);
                } else {

                    ((TextView)findViewById(R.id.guide1Text)).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.guide1Text)).setText(project);
                }

                if( goods.equals("0") || goods.equals(""))
                {
                    ((TextView)findViewById(R.id.guide2Text)).setVisibility(View.GONE);
                } else {

                    ((TextView)findViewById(R.id.guide2Text)).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.guide2Text)).setText(goods);
                }


                if( service.equals("0") || service.equals(""))
                {
                    ((TextView)findViewById(R.id.guide3Text)).setVisibility(View.GONE);
                } else {

                    ((TextView)findViewById(R.id.guide3Text)).setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.guide3Text)).setText(service);
                }

            }
        });
    }

    private void select(int showMenu)
    {
        ((TextView)findViewById(R.id.guide1)).setBackgroundResource(R.drawable.shape_white_10_corner);
        ((TextView)findViewById(R.id.guide1)).setTextColor(Color.parseColor("#000000"));

        ((TextView)findViewById(R.id.guide2)).setBackgroundResource(R.drawable.shape_white_10_corner);
        ((TextView)findViewById(R.id.guide2)).setTextColor(Color.parseColor("#000000"));

        ((TextView)findViewById(R.id.guide3)).setBackgroundResource(R.drawable.shape_white_10_corner);
        ((TextView)findViewById(R.id.guide3)).setTextColor(Color.parseColor("#000000"));

        switch (showMenu)
        {
            case 1:
                ((TextView)findViewById(R.id.guide1)).setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)findViewById(R.id.guide1)).setTextColor(Color.parseColor("#ffffff"));
                ((TextView)findViewById(R.id.guide1Text)).setVisibility(View.GONE);
                showFragment(orderCompanyOrder);
                break;
            case 2:
                ((TextView)findViewById(R.id.guide2)).setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)findViewById(R.id.guide2)).setTextColor(Color.parseColor("#ffffff"));
                ((TextView)findViewById(R.id.guide2Text)).setVisibility(View.GONE);
                showFragment(orderCompanyGoods);
                break;
            case 3:
                ((TextView)findViewById(R.id.guide3)).setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)findViewById(R.id.guide3)).setTextColor(Color.parseColor("#ffffff"));
                ((TextView)findViewById(R.id.guide3Text)).setVisibility(View.GONE);
                showFragment(orderCompanyService);
                break;
        }
    }




    /**
     * 显示fragment
     */
    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
            for (Fragment f : mFragments) {
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                if (f == fragment) {
                    if (fragment.isHidden()) {
                        transaction2.show(f).commitAllowingStateLoss();
                    }
                } else {
                    if (f.isAdded()) {
                        transaction2.hide(f).commitAllowingStateLoss();
                    }
                }
            }
        } else {
            for (Fragment f : mFragments) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (f == fragment) {
                    if (fragment.isHidden()) {
                        transaction.show(f).commitAllowingStateLoss();
                    }
                } else {
                    if (f.isAdded()) {
                        transaction.hide(f).commitAllowingStateLoss();
                    }
                }
            }

        }
    }


}
