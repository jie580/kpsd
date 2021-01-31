package com.ming.sjll.Message.view;


import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.Message.viewmodel.MessageSystemNotificationBean;

import java.util.List;

public interface MessageSystemNotificationView extends MvpView {

    void onShowData(List<MessageSystemNotificationBean.DataBean.DataBeanX> dataBeans);

}
