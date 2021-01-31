package com.ming.sjll.Message.view;


import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.my.bean.UserScheduleBean;

public interface AddSchdulingView extends MvpView {

    void setLongTimer(int value);

    void setUserScheduleBean(UserScheduleBean bean);
}
