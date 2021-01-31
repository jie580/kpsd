package com.ming.sjll.pay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.ming.sjll.Bean.PayResultBean;
import com.ming.sjll.Bean.WxPayBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.api.EventConstant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.bean.EventBusBean;
import com.ming.sjll.base.manager.AppManager;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.MyProjectFragment;
import com.ming.sjll.project.ReleaseProject;
import com.ming.sjll.project.ReleaseProjectNext;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Map;

/**
 * 公司转账
 */
public class CompanyPay extends BaseActivity {

    //    订单号
    String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        showTypePos = getIntent().getStringExtra("showType").equals("project") ? 0 : 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_company);
        EventBus.getDefault().register(this);
        orderNo = getIntent().getStringExtra("orderNo");

        initView();
        event();

    }




    private void initView()
    {

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("payType","company");
        param.setParam("orderNo",orderNo);
        new HttpUtil().sendRequest(Constant.PAY_INITIATE_PAY, param, new CommonCallback() {
            @Override
            public void onSuccess(Object data, int code) {
                String order_no = ObjeGetValue.GetValu("order_no", data).toString();
                String money = ObjeGetValue.GetValu("orderInfo.money", data).toString();
                String company_name = ObjeGetValue.GetValu("orderInfo.company_name", data).toString();
                String bank_name = ObjeGetValue.GetValu("orderInfo.bank_name", data).toString();
                String account = ObjeGetValue.GetValu("orderInfo.account", data).toString();
                ((TextView)findViewById(R.id.order_no)).setText(order_no);
                ((TextView)findViewById(R.id.money)).setText(money);
                ((TextView)findViewById(R.id.company_name)).setText(company_name);
                ((TextView)findViewById(R.id.bank_name)).setText(bank_name);
                ((TextView)findViewById(R.id.account)).setText(account);

            }
        });


    }
    private void event()
    {
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("orderNo",orderNo);
                new HttpUtil().sendRequest(Constant.PAY_PAY_COMPLETED, param, new CommonCallback() {
                    @Override
                    public void onSuccess(Object data, int code) {
                        AppManager.getAppManager().finishActivity(HomePay.class);
//                        finishActivity(200);
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });
    }

}
