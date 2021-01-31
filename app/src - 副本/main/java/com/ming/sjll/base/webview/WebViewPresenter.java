package com.ming.sjll.base.webview;


import android.os.Bundle;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.wallet.bean.WelletBean;

public class WebViewPresenter extends MvpPresenter<IWebViewView> {
    private String url;
    private String title = "";
    public static String KEY_TITLE = "title";
    public static String KEY_URL = "url";
    public static String ISDRTAIL = "isdetail";

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        url = getBundle().getString(KEY_URL);
        title = getBundle().getString(KEY_TITLE);

    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void getAccountDetail() {
        getNetData(RetrofitManager.get().create(ApiService.class).getAccountDetail(getToken()),
                new ApiObserver<WelletBean>() {
                    @Override
                    public void onSuccess(WelletBean bean) {
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewPresenter.KEY_URL, bean.getData().getUrl());
                        bundle.putString(WebViewPresenter.KEY_TITLE, "钱包明细");
                        Tools.jump(getActivity(), WebViewActivity.class, bundle, false);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }


}
