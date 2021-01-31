package com.ming.sjll.My.common;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.webview.WebViewActivity;
import com.ming.sjll.base.webview.WebViewPresenter;
import com.ming.sjll.login.LoginActivity;

import java.util.Set;

/**
 * 个人中心-酷拍商店-设置
 */
public class Setting extends BaseActivity {

    public int point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_setting);

        try {
            ((TextView)findViewById(R.id.ClearCacheTip)).setText(Cache.getTotalCacheSize(Setting.this));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String str1 = Cache.getCache("test");
//        Cache.setCache("test", "test333333");
//        String str2 = Cache.getCache("test");

        event();
    }

    private void event()
    {

        findViewById(R.id.ClearCache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //                提示
                DialogTextTip dialog = new DialogTextTip(Setting.this, "是否确定清除所有缓存记录");
                dialog.show(new CommonCallback() {
                    @Override
                    public void onNext() {
                        super.onNext();
                        Cache.clearAllCache(Setting.this);
                        ((TextView)findViewById(R.id.ClearCacheTip)).setText("0K");
                        dialog.hide();
                    }
                });


            }
        });
        findViewById(R.id.loginOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Tools.jump(Setting.this, LoginActivity.class,  false);
                CommonCallback.onLoginExpired(false);
            }
        });

        /**
         * 隐私协议
         */
        findViewById(R.id.privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewPresenter.KEY_URL, "http://www.baidu.com");
                bundle.putString(WebViewPresenter.KEY_TITLE, "订单支付");
                Tools.jump(Setting.this, WebViewActivity.class, bundle, false);
            }
        });
    }



}
