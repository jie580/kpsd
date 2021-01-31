package com.ming.sjll.My.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;

/**
 * 个人中心-我的付款
 */
public class OrderPaymentWrap extends BaseActivity {


    ProjectOrderListBean mData;

//    MyOrderCompanyWrapBinding binding;
    private Fragment[] mFragments;
    OrderPaymentGoods orderPaymentGoods;
    OrderPaymentProject orderPaymentProject;
    OrderPaymentService orderPaymentService;

//    OrderCompanyGoods  orderCompanyGoods;
//    OrderCompanyService orderCompanyService;


    private int isPay = 2;
    private String type = "project";

    public static OrderPaymentWrap newInstance() {

        return new OrderPaymentWrap();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_payment_wrap);
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



        ((TextView)findViewById(R.id.topGuide1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(1);
            }
        });
        ((TextView)findViewById(R.id.topGuide2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(2);
            }
        });
        ((TextView)findViewById(R.id.topGuide3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectButton(3);
            }
        });


    }
 
    private void initView() {

        orderPaymentGoods = OrderPaymentGoods.newInstance();
        orderPaymentProject = OrderPaymentProject.newInstance();
        orderPaymentService = OrderPaymentService.newInstance();

//        orderCompanyService = OrderCompanyService.newInstance();

        mFragments = new Fragment[]{orderPaymentProject,orderPaymentGoods,orderPaymentService};
        showFragment();
//        getGuide();
    }


    private void select(int showMenu)
    {
        ((TextView)findViewById(R.id.guide1)).setBackgroundResource(R.drawable.shape_white_10_corner);
        ((TextView)findViewById(R.id.guide1)).setTextColor(Color.parseColor("#000000"));

        ((TextView)findViewById(R.id.guide2)).setBackgroundResource(R.drawable.shape_white_10_corner);
        ((TextView)findViewById(R.id.guide2)).setTextColor(Color.parseColor("#000000"));

        ((TextView)findViewById(R.id.guide3)).setBackgroundResource(R.drawable.shape_white_10_corner);
        ((TextView)findViewById(R.id.guide3)).setTextColor(Color.parseColor("#000000"));

        findViewById(R.id.topGuide1).setVisibility(View.VISIBLE);
        findViewById(R.id.topGuide2).setVisibility(View.VISIBLE);
        findViewById(R.id.topGuide3).setVisibility(View.VISIBLE);

        switch (showMenu)
        {
            case 1:
                ((TextView)findViewById(R.id.guide1)).setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)findViewById(R.id.guide1)).setTextColor(Color.parseColor("#ffffff"));
                type = "project";
                showFragment();
                break;
            case 2:
                ((TextView)findViewById(R.id.guide2)).setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)findViewById(R.id.guide2)).setTextColor(Color.parseColor("#ffffff"));
                type = "goods";
                showFragment();
                break;
            case 3:
                ((TextView)findViewById(R.id.guide3)).setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)findViewById(R.id.guide3)).setTextColor(Color.parseColor("#ffffff"));
                findViewById(R.id.topGuide1).setVisibility(View.GONE);
                findViewById(R.id.topGuide2).setVisibility(View.GONE);
                findViewById(R.id.topGuide3).setVisibility(View.GONE);
                type = "service";
                showFragment();
                break;
        }
    }


    private void selectButton(int showMenu)
    {
        ((TextView)findViewById(R.id.topGuide1)).setBackgroundResource(R.drawable.shape_white_10_corner);
        ((TextView)findViewById(R.id.topGuide1)).setTextColor(Color.parseColor("#000000"));

        ((TextView)findViewById(R.id.topGuide2)).setBackgroundResource(R.drawable.shape_white_10_corner);
        ((TextView)findViewById(R.id.topGuide2)).setTextColor(Color.parseColor("#000000"));

        ((TextView)findViewById(R.id.topGuide3)).setBackgroundResource(R.drawable.shape_white_10_corner);
        ((TextView)findViewById(R.id.topGuide3)).setTextColor(Color.parseColor("#000000"));

        switch (showMenu)
        {
            case 1:
                ((TextView)findViewById(R.id.topGuide1)).setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)findViewById(R.id.topGuide1)).setTextColor(Color.parseColor("#ffffff"));
                isPay = 2;
                showFragment();
                break;
            case 2:
                ((TextView)findViewById(R.id.topGuide2)).setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)findViewById(R.id.topGuide2)).setTextColor(Color.parseColor("#ffffff"));
                isPay = 0;
                showFragment();
                break;
            case 3:
                ((TextView)findViewById(R.id.topGuide3)).setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)findViewById(R.id.topGuide3)).setTextColor(Color.parseColor("#ffffff"));
                isPay = 1;
                showFragment();
                break;

        }
    }


    private void showFragment()
    {
        if(type.equals("goods"))
        {
            showFragment(orderPaymentGoods);
            orderPaymentGoods.setIsPay(isPay);
            orderPaymentGoods.getList();
        }
        else if(type.equals("project"))
        {
            showFragment(orderPaymentProject);
            orderPaymentProject.setIsPay(isPay);
            orderPaymentProject.getList();
        }
        else if(type.equals("service"))
        {
            showFragment(orderPaymentService);
            orderPaymentService.setIsPay(isPay);
            orderPaymentService.getList();

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
