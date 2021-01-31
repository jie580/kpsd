package com.ming.sjll.Message.utils;

import android.app.Activity;

import com.ming.sjll.api.ApiService;
import com.ming.sjll.base.exp.RetrofitManager;
import com.ming.sjll.base.http.ApiObserver;
import com.ming.sjll.base.presenter.MvpPresenter;
import com.ming.sjll.Message.viewmodel.SetChatProjectBean;

/**
 * <p>
 * 进入聊天页面
 * </p>
 * <p>
 * 2019-12-11 by gizthon/243537484@qq.com
 * </p>
 */
public class PrivateChatUtils {
    public static void setProjectAndStartChat(String uid, String name, int project_id, MvpPresenter presenter) {
        presenter.getNetData(RetrofitManager.get().create(ApiService.class)
                        .setChatProject(presenter.getToken(), uid + "", project_id),
                new ApiObserver<SetChatProjectBean>() {
                    @Override
                    public void onSuccess(SetChatProjectBean bean) {
                        //跳转到聊天页面
                        RongIMUtils.INSTANCE.privateChat(presenter.getActivity(), uid + "", name);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
    }

    public static void startChat(Activity activity, String uid, String name) {
        RongIMUtils.INSTANCE.privateChat(activity, uid + "", name);
    }

}
