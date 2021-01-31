package com.ming.sjll.Message.view;


import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.Message.viewmodel.MessageChangeProjectBean;
import com.ming.sjll.my.bean.UserListBean;

public interface SchdulingView extends MvpView {
    void getCompanyMember(UserListBean bean);

    void getMemberScheduleInfo(MessageChangeProjectBean bean);


}
