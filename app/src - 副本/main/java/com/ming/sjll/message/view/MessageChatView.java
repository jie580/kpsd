package com.ming.sjll.Message.view;


import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.Message.message.ConfirmCooperationMessageContent;
import com.ming.sjll.Message.message.CooperationApplyMessageContent;
import com.ming.sjll.Message.message.CooperationMessageContent;
import com.ming.sjll.Message.message.ShareGoodsMessageContent;
import com.ming.sjll.Message.message.ShareServiceMessageContent;
import com.ming.sjll.Message.message.ShareUserMessageContent;
import com.ming.sjll.Message.message.ShareWorkMessageContent;
import com.ming.sjll.Message.viewmodel.GetChatProjectBean;
import com.ming.sjll.Message.viewmodel.GroupMemberBean;

import io.rong.imkit.model.UIMessage;

public interface MessageChatView extends MvpView {

    void onShowProjectData(GetChatProjectBean bean);


    void changeProject();

    String getTargetUserId();

    boolean isGroup();

    void hasProject(boolean hasProject);

    void uploadPdf();

    void sendCooperation();

    void sendCooperationApply();

    void complain();

    void shareUserDialog();

    void shareUser(String userId, String userName, String headImage, String occupation);

    void shareWork(String userId, String userName, String headImage, String work_id);

    void jumpToUser(ShareUserMessageContent shareUserMessageContent);

    void jumpToProduct(ShareWorkMessageContent workMessageContent);

    void jumpToService(ShareServiceMessageContent serviceMessageContent);

    void jumpToGoods(ShareGoodsMessageContent goodsMessageContent);

    void jumpToProject(ConfirmCooperationMessageContent cooperationMessageContent);

    void jumpToProject(CooperationMessageContent cooperationMessageContent);

    void jumpToProject(CooperationApplyMessageContent cooperationMessageContent);


    void onCooperationApply(boolean isReceive, CooperationApplyMessageContent content, UIMessage message);

    void onCooperationMessage(boolean isReceive, CooperationMessageContent content, UIMessage message);


    void showGroupMemberData(GroupMemberBean bean);

    void jumpToProject(String projectId);

}
