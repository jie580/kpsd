package com.ming.sjll.Message.presenter;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.Message.view.UploadPdfMessageView;
import com.ming.sjll.Message.viewmodel.GetChatProjectBean;

public class UploadPdfMessagePresenter extends MvpPresenter<UploadPdfMessageView> {
    public static final String TARGETUSERID = "targetUserId";

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        getProjectInfo();
    }

    public void getProjectInfo() {
        String targetUserId = getBundle().getString(TARGETUSERID);
        getNetData(RetrofitManager.get().create(ApiService.class).getChatProject(getToken(), targetUserId),
                new ApiObserver<GetChatProjectBean>() {
                    @Override
                    public void onSuccess(GetChatProjectBean bean) {
                        getView().onShowData(bean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }
}
