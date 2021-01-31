package com.ming.sjll.My.common;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.Tools;

/**
 * 个人中心-订单
 */
public class OrderInfo extends BaseFragment {


    public int point;
    String type;

    public static OrderInfo newInstance() {

        return new OrderInfo();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_order);
        initView();



        event();
    }

    private void event() {

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

            }
        });

        findViewById(R.id.btnGuide1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("user"))
                {
                    Tools.jump(getActivity(), OrderPersonage.class, false);
                }

            }
        });

    }



}
