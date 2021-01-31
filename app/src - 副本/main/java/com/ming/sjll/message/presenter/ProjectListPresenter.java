package com.ming.sjll.Message.presenter;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.Message.fragment.ProjectListFragment;
import com.ming.sjll.Message.view.MessageChangeProjectView;
import com.ming.sjll.Message.viewmodel.MessageChangeProjectBean;

public class ProjectListPresenter extends MvpPresenter<MessageChangeProjectView> {

    @Override
    public void attachView(MessageChangeProjectView view) {
        super.attachView(view);
        getProjectList();
    }

    public String getType() {
        return getBundle().getString(ProjectListFragment.TYPE);
    }

    public void getProjectList() {
        String type = getBundle().getString(ProjectListFragment.TYPE);
        //1 我发起的 2 我接单的 3 项目合伙
        int pageIndex = 1;
        String pageSize = "100";
        getNetData(RetrofitManager.get().create(ApiService.class).myProject(getToken(), type, pageIndex + "", pageSize),
                new ApiObserver<MessageChangeProjectBean>() {
                    @Override
                    public void onSuccess(MessageChangeProjectBean bean) {
                        getView().onShowData(bean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });

    }

}
