package com.ming.sjll.base.webview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Interpolator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.alipay.sdk.app.PayResultActivity;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.luck.picture.lib.permissions.RxPermissions;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.api.EventConstant;
import com.ming.sjll.base.activity.MvpActivity;
import com.ming.sjll.base.bean.EventBusBean;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.base.widget.ToastShow;
//import com.ming.sjll.databinding.WebviewActivityBinding;
//import com.ming.sjll.message.activity.MessageChatActivity;
//import com.ming.sjll.message.utils.RongIMUtils;
//import com.ming.sjll.message.utils.ShareSdkUtils;
//import com.ming.sjll.my.activity.GoodsDetailsAcitivity;
//import com.ming.sjll.my.activity.HomeageActivity;
//import com.ming.sjll.wallet.activity.WalletWebViewActivity;
//import com.ming.sjll.wallet.bean.PayResult;
//import com.ming.sjll.wallet.bean.WebBean;
//import com.ming.sjll.wallet.bean.WxPayBean;
import com.ming.sjll.databinding.WebviewActivityBinding;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;


public class WebViewActivity extends MvpActivity<IWebViewView, WebViewPresenter> implements IWebViewView {
    private WebviewActivityBinding viewDataBinding;

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.webview_activity);
        viewDataBinding.setTitleViewModel(new ToolbarViewModel(mPresenter.getTitle()));

        webViewSetting(viewDataBinding.webView);
        viewDataBinding.webView.addJavascriptInterface(new WebViewActivity.JavaScriptObject(this), "native");
        viewDataBinding.webView.loadUrl(mPresenter.getUrl());

    }

    public void webViewSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true);   //将图片调整到合适的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        String userAgentString = webSettings.getUserAgentString();
        webSettings.setUserAgent(userAgentString);

        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接
                if (h5CallNative(url)) {
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
        //webview加载进度
        webView.setWebChromeClient(new InputFileWebChromeClient());
    }

    /**
     * 与h5交互拦截处理,并且处理相应的逻辑代码
     *
     * @param url
     * @return
     */
    public boolean h5CallNative(String url) {
        if (url.contains("/kp-schema/data/")) {
            return true;
        }
        return false;
    }

    /**
     * 调用js函数
     *
     * @param method     函数
     * @param parameters 参数
     */
    public void nativeCallH5JS(String method, List<String> parameters) {
        viewDataBinding.webView.post(new Runnable() {
            @Override
            public void run() {
                // 注意调用的JS方法名要对应上
                // 调用javascript的callJS()方法
                StringBuilder jsCodeBuilder = new StringBuilder("javascript:");
                jsCodeBuilder.append(method);
                jsCodeBuilder.append("(");

                if (parameters != null && !parameters.isEmpty()) {
                    for (int i = 0; i < parameters.size(); i++) {
                        String p = parameters.get(i);
                        if (i != 0) {
                            jsCodeBuilder.append(",");
                        }
                        jsCodeBuilder.append(p);
                    }
                }
                jsCodeBuilder.append(")");
                viewDataBinding.webView.loadUrl(jsCodeBuilder.toString());
            }
        });
    }

    //点击返回键，返回上一个页面，而不是退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && viewDataBinding.webView.canGoBack()) {
            viewDataBinding.webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //回调支持
        super.onActivityResult(requestCode, resultCode, data);
        helper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清楚webview缓存
        viewDataBinding.webView.clearCache(true);
    }

    public class JavaScriptObject {
        Context mContxt;

        public JavaScriptObject(Context mContxt) {
            this.mContxt = mContxt;
        }


        // 绑定事件
        // 绑定事件
        @JavascriptInterface
        public void transfer(int type, String json) {
            System.out.println("url+home=" + type + "json=" + json);
            WebBean webBean = new Gson().fromJson(json, WebBean.class);
            Bundle bundle = new Bundle();
            switch (type) {
                //url跳转
                case 1:
                    bundle.putString(WebViewPresenter.KEY_URL, webBean.getUrl());
                    bundle.putString(WebViewPresenter.KEY_TITLE, webBean.getTitle());
                    bundle.putBoolean(WebViewPresenter.ISDRTAIL, false);
//                    if (webBean.isFloat()) {
//                        Tools.jump(WebViewActivity.this, WalletWebViewActivity.class, bundle, false);
//                    } else {
                        Tools.jump(WebViewActivity.this, WebViewActivity.class, bundle, false);
//                    }
                    break;
                //返回上一个界面
                case 2:
                    finish();
                    break;
                //用户主页
                case 3:
                    bundle.putString("uid", webBean.getId() + "");
//                    Tools.jump(WebViewActivity.this, HomeageActivity.class, bundle, false);
                    break;
                //项目管理
                case 4:
                    ToastShow.s("项目管理");
                    break;
                //需求页面
                case 5:
                    ToastShow.s("需求页面");
                    break;
                //订单支付页面
                case 6:
                    ToastShow.s("订单支付页");
                    break;
                //改标题
                case 7:
                    viewDataBinding.titleBar.titleBarTvTitle.setText(webBean.getTitle());
                    break;
                //微信支付
                case 8:
                    startWechatPay(webBean.getPayJson());
                    break;
                //返回上上个页面
                case 9:
                    finishActivity(WebViewActivity.class);
                    break;
                //支付宝支付
                case 10:
                    Zhifubao(webBean.getOrderInfo());
                    break;
                //聊天
                case 11:
                    //单聊
                    if (type == 1) {
                        //立即沟通之前关闭聊天框
//                        finishActivity(MessageChatActivity.class);
                        //跳转到聊天页面
//                        RongIMUtils.INSTANCE.privateChat(WebViewActivity.this,
//                                webBean.getId() + "", webBean.getTitle());
                    } else {
                        //群聊
//                        RongIMUtils.INSTANCE.groupChat(WebViewActivity.this, webBean.getId() + "", webBean.getTitle());
                    }
                    break;
                //分享
                case 12:
//                    ShareSdkUtils.userShare("微信好友", webBean.getTypeStr(), webBean.getShareID(), mPresenter);
                    break;
                //商品详情
                case 13:
                    bundle.putString("goods_id", webBean.getId() + "");
//                    Tools.jump(WebViewActivity.this, GoodsDetailsAcitivity.class, bundle, false);
                    break;
                default:
                    break;
            }
        }
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
        payRequest.partnerId = wxPayBean.getPartnerid();
        payRequest.prepayId = wxPayBean.getPrepayid();
        payRequest.packageValue = "Sign=WXPay";//固定值
        payRequest.nonceStr = wxPayBean.getNoncestr();
        payRequest.timeStamp = wxPayBean.getTimestamp();
        payRequest.sign = wxPayBean.getSign();

        //发起请求，调起微信前去支付
        api.sendReq(payRequest);
    }

    private void Zhifubao(String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(WebViewActivity.this);
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

    @Override
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEventReceive(EventBusBean event) {
        if (EventConstant.PAYSUCCESSFUL.equals(event.getEventName())) {
            viewDataBinding.webView.loadUrl("javascript:wxPayBack(0)");
        } else if (EventConstant.PAYFAILURE.equals(event.getEventName())) {
            viewDataBinding.webView.loadUrl("javascript:wxPayBack(-1)");
        } else if (EventConstant.PAYCANCEL.equals(event.getEventName())) {
            viewDataBinding.webView.loadUrl("javascript:wxPayBack(-2)");
        }

    }

    //webview input 特别支持帮助类
    private WebViewUploadFileHelper helper = new WebViewUploadFileHelper(this);

    public class InputFileWebChromeClient extends WebChromeClient {
        //设置 进度条
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            helper.setUploadMessage(uploadMsg);
            permission(() -> {
                helper.openImageActivity();
            });
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            helper.setUploadMessage(uploadMsg);
            permission(() -> {
                helper.openImageActivity(acceptType);
            });
        }

        // For Android  > 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            helper.setUploadMessage(uploadMsg);
            permission(() -> {
                helper.openImageActivity(acceptType, capture);
            });
        }

        // For Android  >= 5.0
        public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                         ValueCallback<Uri[]> filePathCallback,
                                         WebChromeClient.FileChooserParams fileChooserParams) {
            helper.setUploadMessageAboveL(filePathCallback);
            permission(() -> {
                helper.openImageActivity(fileChooserParams.getAcceptTypes(), fileChooserParams.isCaptureEnabled());
            });
            return true;
        }

        //==多窗口的问题
        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }
    }

    @SuppressLint("CheckResult")
    public void permission(CallBack callBack) {
        // 权限支持
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(grant -> {
                    if (grant) {
                        //全部通过
                        try {
                            if (callBack != null) {
                                callBack.onSucess();
                            }
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    } else {
                        ToastShow.s("请同意权限");
                    }
                });
    }

    public interface CallBack {
        void onSucess();
    }

}
