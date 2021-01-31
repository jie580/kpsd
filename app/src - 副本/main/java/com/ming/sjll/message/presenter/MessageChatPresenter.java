package com.ming.sjll.Message.presenter;

import android.os.Bundle;
import android.text.TextUtils;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.bean.BaseBean;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.webview.WebViewActivity;
import com.ming.sjll.base.webview.WebViewPresenter;
import com.ming.sjll.Message.activity.ManagerMemberInfoActivity;
import com.ming.sjll.Message.message.CooperationApplyMessageContent;
import com.ming.sjll.Message.message.CooperationMessageContent;
import com.ming.sjll.Message.utils.RongIMUtils;
import com.ming.sjll.Message.view.MessageChatView;
import com.ming.sjll.Message.viewmodel.ApplyPassBean;
import com.ming.sjll.Message.viewmodel.CooperationBean;
import com.ming.sjll.Message.viewmodel.GetChatProjectBean;
import com.ming.sjll.Message.viewmodel.GetInfoBean;
import com.ming.sjll.Message.viewmodel.GroupMemberBean;
import com.ming.sjll.Message.viewmodel.InvitePassBean;
import com.ming.sjll.Message.viewmodel.SetChatProjectBean;
import com.ming.sjll.Home.activity.ProjectDetailsAcitivity;
import com.ming.sjll.wallet.bean.WelletBean;

import io.rong.imkit.model.UIMessage;

public class MessageChatPresenter extends MvpPresenter<MessageChatView> {

    private String projectId;
    private String targetUserId;
    private boolean isSelf;

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
    }


    public void getProjectByUser(String targetUserId) {
        if (getView().isGroup()) {
            projectId = targetUserId;
            getView().hasProject(false);

            //获取群组成员信息
            getNetData(RetrofitManager.get().create(ApiService.class).groupMember(getToken(), projectId),
                    new ApiObserver<GroupMemberBean>() {
                        @Override
                        public void onSuccess(GroupMemberBean bean) {
                            getView().showGroupMemberData(bean);
                        }

                        @Override
                        public void onFailure(int code, String msg) {
//                                            getView().showError(msg);
                        }
                    });
            return;
        }
        getNetData(RetrofitManager.get().create(ApiService.class).getChatProject(getToken(), targetUserId),
                new ApiObserver<GetChatProjectBean>() {
                    @Override
                    public void onSuccess(GetChatProjectBean bean) {
                        if (bean.getData() != null) {
                            projectId = bean.getData().getId() + "";
                            getView().hasProject(true);
                            getView().onShowProjectData(bean);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().hasProject(false);
//                        getView().showError(msg);
                    }
                });
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void getGroupConversationInfo() {
        if (!getView().isGroup()) {
            return;
        }
        String type = "3";
        getNetData(RetrofitManager.get().create(ApiService.class).getInfo(getToken(), type, targetUserId),
                new ApiObserver<GetInfoBean>() {
                    @Override
                    public void onSuccess(GetInfoBean bean) {

                        //是否是发单人和接单人，在群员管理页面是有权限控制的

                        String user_id = bean.getData().getUser_id();
                        String user_id_determine = bean.getData().getUser_id_determine();

                        if (TextUtils.equals(getUserId(), user_id) || TextUtils.equals(getUserId(), user_id_determine)) {
                            isSelf = true;
                        } else {
                            isSelf = false;
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
    }


    public void getProjectByUser() {
        targetUserId = getView().getTargetUserId();
        getProjectByUser(targetUserId);

        saveChat();
    }


    public void saveChat() {
        String type = getView().isGroup() ? "2" : "1";
        getNetData(RetrofitManager.get().create(ApiService.class).saveChat(getToken(), targetUserId, type),
                new ApiObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean bean) {

                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }


    public void setChatProject(int projectId) {
        getNetData(RetrofitManager.get().create(ApiService.class).setChatProject(getToken(), targetUserId, projectId),
                new ApiObserver<SetChatProjectBean>() {
                    @Override
                    public void onSuccess(SetChatProjectBean bean) {
                        getView().hasProject(true);
                        getProjectByUser();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().hasProject(false);
                        getView().showError(msg);
                    }
                });
    }


    public void onCooperationApply(boolean isReceive, CooperationApplyMessageContent content, UIMessage message) {
        getNetData(RetrofitManager.get().create(ApiService.class).applyPass(getToken(), content.getapplyId(), isReceive ? "1" : "0"),
                new ApiObserver<ApplyPassBean>() {
                    @Override
                    public void onSuccess(ApplyPassBean bean) {

                        RongIMUtils.INSTANCE.deleteMessage(message.getMessageId());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }

    public void onCooperationMessage(boolean isReceive, CooperationMessageContent content, UIMessage message) {
        getNetData(RetrofitManager.get().create(ApiService.class).invitePass(getToken(), content.getapplyId(), isReceive ? "1" : "0"),
                new ApiObserver<InvitePassBean>() {
                    @Override
                    public void onSuccess(InvitePassBean bean) {
                        RongIMUtils.INSTANCE.deleteMessage(message.getMessageId());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }


    public void cooperation(String projectId) {
        String userId = targetUserId;
        getNetData(RetrofitManager.get().create(ApiService.class).cooperation(getToken(), userId, projectId),
                new ApiObserver<CooperationBean>() {
                    @Override
                    public void onSuccess(CooperationBean bean) {

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

    public boolean hasProject() {
        return !TextUtils.isEmpty(projectId);
    }

    public void onClickGroupMemberInfo() {
        Bundle bundle = new Bundle();
        if (isSelf()) {//如果自己是接单人和发单人，那么就可以管理项目，具有左滑功能
            bundle.putBoolean(ManagerMemberInfoPresenter.IS_MANAGER, true);
        }
        bundle.putString(ManagerMemberInfoPresenter.PROJECT_ID, projectId);

        Tools.jump(getActivity(), ManagerMemberInfoActivity.class, bundle, false);
    }

    public String getProjectId() {
        return projectId;
    }

    public void onClickAddMemberInfo() {
        //弹框
        getView().shareUserDialog();
    }

    public void onClickProjectManager() {
        if (!TextUtils.isEmpty(targetUserId)) {
            Bundle bundle = new Bundle();
            bundle.putInt("project_id", Integer.valueOf(targetUserId));
            Tools.jump(getActivity(), ProjectDetailsAcitivity.class, bundle, false);
        }
    }

    public void onClickProjectProgress() {
        //TODO 跳转到h5页面地址，项目进程
        getProgress(projectId);

    }

    public void onClickProject() {
        getView().jumpToProject(projectId);
    }
}
