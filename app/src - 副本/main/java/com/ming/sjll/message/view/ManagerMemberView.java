package com.ming.sjll.Message.view;


import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.Message.viewmodel.GetMemberInfoBean;
import com.ming.sjll.Message.viewmodel.GroupMemberBean;
import com.ming.sjll.Home.bean.OccupationBean;

import java.util.List;

public interface ManagerMemberView extends MvpView {

    void onShowData(List<GroupMemberBean.DataBean> dataBeans);

    void onDelMember();

    void onGroupMemberSuccess();

    void onShowGroupMemberInfo(GetMemberInfoBean groupMemberBean);

    void getspOccupation(OccupationBean bean);
}
