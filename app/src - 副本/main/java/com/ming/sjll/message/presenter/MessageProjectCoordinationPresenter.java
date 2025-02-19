package com.ming.sjll.Message.presenter;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.Message.view.MessageProjectCoordinationView;
import com.ming.sjll.Message.viewmodel.ApplyPassBean;
import com.ming.sjll.Message.viewmodel.MessageProjectCoordinationViewModel;

public class MessageProjectCoordinationPresenter extends MvpPresenter<MessageProjectCoordinationView> {

    private int pageIndex = 1;
    private int pageSize = 100;

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        getData();
    }

    public void getData() {
        getNetData(RetrofitManager.get().create(ApiService.class).projectApply(getToken(), pageIndex + "", pageSize + ""),
                new ApiObserver<MessageProjectCoordinationViewModel>() {
                    @Override
                    public void onSuccess(MessageProjectCoordinationViewModel bean) {
                        getView().onShowData(bean.getData().getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }

    public void applyPass(String applyId, boolean isReceive) {
        String status = isReceive ? "1" : "0";
        getNetData(RetrofitManager.get().create(ApiService.class).applyPass(getToken(), applyId + "", status + ""),
                new ApiObserver<ApplyPassBean>() {
                    @Override
                    public void onSuccess(ApplyPassBean bean) {
                        getData();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }
}
