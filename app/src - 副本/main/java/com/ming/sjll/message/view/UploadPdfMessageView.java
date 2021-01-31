package com.ming.sjll.Message.view;


import com.ming.sjll.base.view.MvpView;
import com.ming.sjll.Message.viewmodel.GetChatProjectBean;

public interface UploadPdfMessageView extends MvpView {
    void onShowData(GetChatProjectBean viewModel);
}
