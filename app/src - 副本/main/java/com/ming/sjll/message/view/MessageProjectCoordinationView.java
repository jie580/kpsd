package com.ming.sjll.Message.view;


import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.Message.viewmodel.MessageProjectCoordinationViewModel;

import java.util.List;

public interface MessageProjectCoordinationView extends MvpView {


    void onShowData(List<MessageProjectCoordinationViewModel.DataBeanX.DataEntity> data);

}
