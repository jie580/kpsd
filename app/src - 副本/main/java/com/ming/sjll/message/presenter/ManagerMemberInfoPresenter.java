package com.ming.sjll.Message.presenter;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.Message.view.ManagerMemberView;
import com.ming.sjll.Message.viewmodel.GetMemberInfoBean;
import com.ming.sjll.Message.viewmodel.GroupManagerBean;
import com.ming.sjll.Message.viewmodel.GroupMemberBean;
import com.ming.sjll.Home.bean.OccupationBean;

import java.util.List;

public class ManagerMemberInfoPresenter extends MvpPresenter<ManagerMemberView> {


    public static final String PROJECT_ID = "project_id";
    public static final String IS_MANAGER = "is_manager";
    public String projectId;
    private boolean isManager;

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        projectId = getBundle().getString(PROJECT_ID);
        isManager = getBundle().getBoolean(IS_MANAGER);
    }

    public boolean isManager() {
        return isManager;
    }

    public void getData() {

        getNetData(RetrofitManager.get().create(ApiService.class).groupMember(getToken(), projectId),
                new ApiObserver<GroupMemberBean>() {
                    @Override
                    public void onSuccess(GroupMemberBean bean) {
                        getView().onShowData(bean.getData());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });

        getOccpationList();
    }


    public void delMember(String uid) {
        getNetData(RetrofitManager.get().create(ApiService.class).delMember(getToken(), projectId, uid),
                new ApiObserver<GroupManagerBean>() {
                    @Override
                    public void onSuccess(GroupManagerBean bean) {
                        getView().onDelMember();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }

    public void groupManage(String uid, String remark, List<String> occupationId) {
        getNetData(RetrofitManager.get().create(ApiService.class).groupManage(getToken(), projectId, uid, remark, occupationId),
                new ApiObserver<GroupManagerBean>() {
                    @Override
                    public void onSuccess(GroupManagerBean bean) {
                        getView().onGroupMemberSuccess();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }

    public void getMemberInfo(String uid) {
        getNetData(RetrofitManager.get().create(ApiService.class).getMemberInfo(getToken(), projectId, uid),
                new ApiObserver<GetMemberInfoBean>() {
                    @Override
                    public void onSuccess(GetMemberInfoBean bean) {
                        getView().onShowGroupMemberInfo(bean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }

    public void getOccpationList() {
        getNetData(RetrofitManager.get().create(ApiService.class).getspOccupation(getToken()),
                new ApiObserver<OccupationBean>() {
                    @Override
                    public void onSuccess(OccupationBean bean) {
                        getView().getspOccupation(bean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }
}
