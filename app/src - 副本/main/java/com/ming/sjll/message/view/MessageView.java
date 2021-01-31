package com.ming.sjll.Message.view;


import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.my.bean.WelcomeBean;

public interface MessageView extends MvpView {
    void ShowData(WelcomeBean bean);


    void showUnreadMessage(int unread_count);
}
