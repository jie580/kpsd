package com.ming.sjll.wxapi;


import android.content.Intent;
import android.os.Bundle;

import com.ming.sjll.api.Constant;
import com.ming.sjll.api.EventConstant;
import com.ming.sjll.base.activity.MvpActivity;
import com.ming.sjll.base.bean.EventBusBean;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends MvpActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);

        api = WXAPIFactory.createWXAPI(this, Constant.WxPayAppId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            switch (resp.errCode) {
                case 0://支付成功
                    //Log.d(TAG, "onResp: resp.errCode = 0   支付成功");
                    EventBus.getDefault().post(new EventBusBean(EventConstant.PAYSUCCESSFUL));
                    break;
                case -1://错误，可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等
                    EventBus.getDefault().post(new EventBusBean(EventConstant.PAYFAILURE));
                    //Log.d(TAG, "onResp: resp.errCode = -1  支付错误");
                    break;
                case -2://用户取消，无需处理。发生场景：用户不支付了，点击取消，返回APP。
                    EventBus.getDefault().post(new EventBusBean(EventConstant.PAYCANCEL));
                    // Log.d(TAG, "onResp: resp.errCode = -2  用户取消");
                    break;

            }

            finish();//这里需要关闭该页面
        }
    }

}