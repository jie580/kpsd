package com.ming.sjll.Message.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.activity.MvpActivity;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.databinding.ActivityUploadPdfBinding;
import com.ming.sjll.Message.presenter.UploadPdfMessagePresenter;
import com.ming.sjll.Message.view.UploadPdfMessageView;
import com.ming.sjll.Message.viewmodel.GetChatProjectBean;

public class UploadPdfMessageActivity extends MvpActivity<UploadPdfMessageView, UploadPdfMessagePresenter> implements UploadPdfMessageView {
    private ActivityUploadPdfBinding viewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload_pdf);
        viewDataBinding.setTitleViewModel(new ToolbarViewModel("方案上传"));
        viewDataBinding.setViewModel(new GetChatProjectBean.DataBean());
        viewDataBinding.label.setVisibility(View.GONE);
        viewDataBinding.projectCard.setVisibility(View.GONE);
    }


    @Override
    public void onShowData(GetChatProjectBean viewModel) {
        GetChatProjectBean.DataBean projectChatViewModel = viewModel.getData();
        if (projectChatViewModel != null) {
            viewDataBinding.projectCard.setVisibility(View.VISIBLE);
            viewDataBinding.setViewModel(projectChatViewModel);
        }

    }
}
