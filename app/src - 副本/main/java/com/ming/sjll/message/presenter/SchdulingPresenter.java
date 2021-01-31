package com.ming.sjll.Message.presenter;

import android.os.Bundle;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.webview.WebViewActivity;
import com.ming.sjll.base.webview.WebViewPresenter;
import com.ming.sjll.Message.fragment.ProjectListFragment;
import com.ming.sjll.Message.view.SchdulingView;
import com.ming.sjll.Message.viewmodel.MessageChangeProjectBean;
import com.ming.sjll.my.bean.UserListBean;
import com.ming.sjll.wallet.bean.WelletBean;

public class SchdulingPresenter extends MvpPresenter<SchdulingView> {
    private String date_time;
    private String userId;

    @Override
    public void attachView(SchdulingView view) {
        super.attachView(view);
        date_time = getBundle().getString("date_time");
        userId = getBundle().getString("userId");
        if (userId.equals("-1")) {
            companyMember();
        } else if (userId.equals("0")) {
            memberScheduleInfo(getUserId());
        } else {
            memberScheduleInfo(userId);
        }

    }

    public String getType() {
        return getBundle().getString(ProjectListFragment.TYPE);
    }

    public void memberScheduleInfo(String user_id) {
        getNetData(RetrofitManager.get().create(ApiService.class).memberScheduleInfo(getToken(), user_id, date_time),
                new ApiObserver<MessageChangeProjectBean>() {
                    @Override
                    public void onSuccess(MessageChangeProjectBean bean) {
                        getView().getMemberScheduleInfo(bean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });

    }

    public void companyMember() {
        getNetData(RetrofitManager.get().create(ApiService.class).companyMember(getToken(), date_time),
                new ApiObserver<UserListBean>() {
                    @Override
                    public void onSuccess(UserListBean bean) {
                        getView().getCompanyMember(bean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });

    }

    public void getProgress(String projectId) {
        Integer projectId1 = null;
        try {
            projectId1 = Integer.valueOf(projectId);
        } catch (NumberFormatException e) {
            projectId1 = 0;
        }
        getNetData(RetrofitManager.get().create(ApiService.class).getProgress(getToken(), projectId1),
                new ApiObserver<WelletBean>() {
                    @Override
                    public void onSuccess(WelletBean data) {
                        Bundle bundle = new Bundle();
                        bundle.putString(WebViewPresenter.KEY_URL, data.getData().getUrl());
                        bundle.putString(WebViewPresenter.KEY_TITLE, "项目进程");
                        Tools.jump(getActivity(), WebViewActivity.class, bundle, false);
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

}
