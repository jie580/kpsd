package com.ming.sjll.pay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.PayResultBean;
import com.ming.sjll.Bean.UserTypeBean;
import com.ming.sjll.Bean.WxPayBean;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.api.EventConstant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.bean.EventBusBean;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.webview.WebViewActivity;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.ReleaseProjectNext;
import com.ming.sjll.project.ReleaseProjectTabGenral;
import com.ming.sjll.project.ReleaseProjectTabRequire;
import com.ming.sjll.search.bean.ProjecItem;
import com.ming.sjll.ui.WrapContentHeightViewPager;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 支付页面
 */
public class HomePay extends BaseActivity {

    //    订单号
    String orderNo;
    int time = 15 * 60;
    String type = "alipay";
    private static final int SDK_PAY_FLAG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        showTypePos = getIntent().getStringExtra("showType").equals("project") ? 0 : 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_home);
        EventBus.getDefault().register(this);
        orderNo = getIntent().getStringExtra("orderNo");

        initView();
        event();

    }




    private void initView()
    {

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("payType","alipay");
        param.setParam("orderNo",orderNo);
        new HttpUtil().sendRequest(Constant.PAY_ORDER_INFO, param, new CommonCallback() {
            @Override
            public void onSuccess(Object data, int code) {
                String order_no = ObjeGetValue.GetValu("order_no", data).toString();
                String order_info = ObjeGetValue.GetValu("order_info", data).toString();
                String money = ObjeGetValue.GetValu("money", data).toString();
                ((TextView)findViewById(R.id.order_no)).setText(order_no);
                ((TextView)findViewById(R.id.order_info)).setText(order_info);
                ((TextView)findViewById(R.id.money)).setText(money);

            }
        });
        countDown();

    }
    private void event()
    {

        ((CheckBox)findViewById(R.id.guide1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "alipay";
                ((CheckBox)findViewById(R.id.guide1)).setChecked(true);
                ((CheckBox)findViewById(R.id.guide2)).setChecked(false);
                ((CheckBox)findViewById(R.id.guide3)).setChecked(false);
            }
        });

        ((CheckBox)findViewById(R.id.guide2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "wx";
                ((CheckBox)findViewById(R.id.guide1)).setChecked(false);
                ((CheckBox)findViewById(R.id.guide2)).setChecked(true);
                ((CheckBox)findViewById(R.id.guide3)).setChecked(false);
            }
        });

        ((CheckBox)findViewById(R.id.guide3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "company";
                ((CheckBox)findViewById(R.id.guide1)).setChecked(false);
                ((CheckBox)findViewById(R.id.guide2)).setChecked(false);
                ((CheckBox)findViewById(R.id.guide3)).setChecked(true);
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("wx"))
                {
                    //        获取账号类型
                    HttpParamsObject param = new HttpParamsObject();
                    param.setParam("payType","wx");
                    param.setParam("orderNo",orderNo);
                    new HttpUtil().sendRequest(Constant.PAY_INITIATE_PAY, param, new CommonCallback() {
                        @Override
                        public void onSuccessJson(String data, int code) {
                            WxPayBean orderInfo = new Gson().fromJson(data, WxPayBean.class);
                            startWechatPay(orderInfo);
                        }
                    });
                }
                else if(type.equals("alipay"))
                {
                    HttpParamsObject param = new HttpParamsObject();
                    param.setParam("payType","alipay");
                    param.setParam("orderNo",orderNo);
                    new HttpUtil().sendRequest(Constant.PAY_INITIATE_PAY, param, new CommonCallback() {
                        @Override
                        public void onSuccess(Object data, int code) {
                            String orderInfo = ObjeGetValue.GetValu("orderInfo", data).toString();
                            Zhifubao(orderInfo);
                        }
                    });
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("orderNo", orderNo);
                    Tools.jump(getActivity(), CompanyPay.class, bundle, false);
                }

            }
        });

    }



    /**
     * 调支付的方法
     * <p>
     * 注意： 每次调用微信支付的时候都会校验 appid 、包名 和 应用签名的。 这三个必须保持一致才能够成功调起微信
     *
     * @param wxPayBean
     */
    private void startWechatPay(WxPayBean wxPayBean) {

        //这里的appid，替换成自己的即可
        IWXAPI api = WXAPIFactory.createWXAPI(this, Constant.WxPayAppId);
        api.registerApp(Constant.WxPayAppId);

        //这里的bean，是服务器返回的json生成的bean
        PayReq payRequest = new PayReq();
        payRequest.appId = Constant.WxPayAppId;
        payRequest.partnerId = wxPayBean.getData().getPartnerid();
        payRequest.prepayId = wxPayBean.getData().getPrepayid();
        payRequest.packageValue = "Sign=WXPay";//固定值
        payRequest.nonceStr = wxPayBean.getData().getNoncestr();
        payRequest.timeStamp = wxPayBean.getData().getTimestamp();
        payRequest.sign = wxPayBean.getData().getSign();

        //发起请求，调起微信前去支付
        api.sendReq(payRequest);
    }



    private void Zhifubao(String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResultBean payResult = new PayResultBean((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        EventBus.getDefault().post(new EventBusBean(EventConstant.PAYSUCCESSFUL));
                    } else {
                        EventBus.getDefault().post(new EventBusBean(EventConstant.PAYFAILURE));
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    @Subscribe
    public void onEventReceive(EventBusBean event) {
        if (EventConstant.PAYSUCCESSFUL.equals(event.getEventName())) {
            ToastShow.s("支付成功");
        } else if (EventConstant.PAYFAILURE.equals(event.getEventName())) {
            ToastShow.s("支付失败");
        } else if (EventConstant.PAYCANCEL.equals(event.getEventName())) {
            ToastShow.s("支取消");
        }

    }


    private void countDown()
    {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(time == 0)
                {
                    ((TextView)findViewById(R.id.submit)).setClickable(false);
                    return;
                }
                time--;
                ((TextView)findViewById(R.id.submit)).setText("立即付款 "+calculateTime(time));
                countDown();
            }
        }, 1000);
    }


    private String calculateTime(int diff)
    {
        long hours = diff /  60 ;
        long minutes = diff - hours*60;
//        long days = diff / ( 60 * 60 * 24);
//        long hours = (diff-days*( 60 * 60 * 24))/( 60 * 60);
//        long minutes = (diff-hours*( 60 * 60 * 24)-hours*( 60 * 60))/( 60);
        return hours + "：" + minutes ;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}
