package com.ming.sjll.Message.presenter;

import android.text.TextUtils;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.Message.utils.RongIMUtils;
import com.ming.sjll.Message.view.ShareMessageView;
import com.ming.sjll.Message.viewmodel.ShareMessageBean;

public class ShareMessagePresenter extends MvpPresenter<ShareMessageView> {

    public static final String SHARE_TYPE = "shareType";
    public static final String SHARE_ID = "shareId";

    @Override
    protected void onViewAttached() {
        super.onViewAttached();
        getData("");
    }

    public void getData(String keyword) {
        String page = "1";
        String pageSize = "100";
        getNetData(RetrofitManager.get().create(ApiService.class).getChatList(getToken(), keyword, page, pageSize),
                new ApiObserver<ShareMessageBean>() {
                    @Override
                    public void onSuccess(ShareMessageBean bean) {
                        getView().onShowData(bean);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });
    }


    public void sendMessage(ShareMessageBean.DataBean dataBean) {
//        调用接口发送消息
        String share_type = getBundle().getString(SHARE_TYPE);
        String share_id = getBundle().getString(SHARE_ID);

        String target_id = dataBean.getTarget_id();
        String msg_type = "";
        boolean privateChat = TextUtils.equals(dataBean.getType(), "1");
        if (privateChat) {
            msg_type = "1";
        } else {
            msg_type = "3";
        }
//        消息类型：1=单聊，3=群聊
        getNetData(RetrofitManager.get().create(ApiService.class).shareAppMsg(getToken(), share_type, share_id, target_id, msg_type),
                new ApiObserver<ShareMessageBean>() {
                    @Override
                    public void onSuccess(ShareMessageBean bean) {
                        if (privateChat) {
                            RongIMUtils.INSTANCE.privateChat(getActivity(), dataBean.getTarget_id(), dataBean.getTitle());
                        } else {
                            RongIMUtils.INSTANCE.groupChat(getActivity(), dataBean.getTarget_id(), dataBean.getTitle());
                        }

                        ToastShow.showLong("分享成功");
                        //分享成功，关闭当前页面
                        getActivity().finish();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        getView().showError(msg);
                    }
                });


    }

}
