package com.ming.sjll.Message.presenter;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.Message.view.MessageSystemNotificationView;
import com.ming.sjll.Message.viewmodel.MessageSystemNotificationBean;

public class ManagerSystemNotificationPresenter extends MvpPresenter<MessageSystemNotificationView> {
    private String pageIndex = "1";
    private String pageSize = "100";

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        getData();
    }

    public void getData() {
        getNetData(RetrofitManager.get().create(ApiService.class).sysmsg(getToken(), pageIndex, pageSize),
                new ApiObserver<MessageSystemNotificationBean>() {
                    @Override
                    public void onSuccess(MessageSystemNotificationBean bean) {
                        getView().onShowData(bean.getData().getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }

}
