package com.ming.sjll.Message.view;

import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.Message.viewmodel.ShareMessageBean;

public interface ShareMessageView extends MvpView {
    void onShowData(ShareMessageBean dataBeans);
}
