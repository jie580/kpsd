package com.ming.sjll.Message.view;


import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.Message.viewmodel.MessageNotifyBean;

import java.util.ArrayList;

public interface MessageNotificationListView extends MvpView {
    void onShowData(ArrayList<MessageNotifyBean> viewModel);
}
