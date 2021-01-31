package com.ming.sjll.Message.presenter;

import android.os.Bundle;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.base.utils.SavePreferencesData;
import com.ming.sjll.Message.view.MessageView;
import com.ming.sjll.Message.viewmodel.MessageSystemUnreadBean;
import com.ming.sjll.my.bean.WelcomeBean;

public class MessagePresenter extends MvpPresenter<MessageView> {
    @Override
    public void attachView(MessageView view, Bundle bundle) {
        super.attachView(view, bundle);
        welcome();
        getUnReadData();
    }

    public void welcome() {
        getNetData(RetrofitManager.get().create(ApiService.class).welcome(getToken()),
                new ApiObserver<WelcomeBean>() {
                    @Override
                    public void onSuccess(WelcomeBean bean) {
                        new SavePreferencesData(getActivity()).putStringData("userId", bean.getData().getUserId());
                        getView().ShowData(bean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });


    }

    public void getUnReadData() {
        getNetData(RetrofitManager.get().create(ApiService.class).sysUnread(getToken()),
                new ApiObserver<MessageSystemUnreadBean>() {
                    @Override
                    public void onSuccess(MessageSystemUnreadBean bean) {
                        try {
                            MessageSystemUnreadBean.DataBean data = bean.getData();
                            getView().showUnreadMessage(data.getUnread_count());
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }


}
