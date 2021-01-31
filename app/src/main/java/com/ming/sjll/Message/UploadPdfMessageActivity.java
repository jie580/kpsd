package com.ming.sjll.Message;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.activity.MvpActivity;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.databinding.ActivityUploadPdfBinding;
import com.orhanobut.logger.Logger;

import io.rong.imkit.RongIM;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class UploadPdfMessageActivity extends BaseActivity {
    private ActivityUploadPdfBinding viewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_upload_pdf);
//        viewDataBinding.setTitleViewModel(new ToolbarViewModel("方案上传"));
//        viewDataBinding.setViewModel(new GetChatProjectBean.DataBean());
//        viewDataBinding.label.setVisibility(View.GONE);
//        viewDataBinding.projectCard.setVisibility(View.GONE);
//        openPdfChatActivity(getIntent());
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        openPdfChatActivity(intent);
//    }
//
//
//    /**
//     * 微信打开APP，发送pdf文件信息
//     */
//    private void openPdfChatActivity(Intent intent) {
//
//        try {
//            if (TextUtils.equals(intent.getAction(), Intent.ACTION_VIEW) && intent.getType().equals("application/pdf")) {
//                //记录上次的用户id
//                Intent newIntent = new Intent(this, MessageChatActivity.class);
//                newIntent.putExtra("fromWechat", true);
//                newIntent.setData(intent.getData());
//                startActivity(newIntent);
//            }
//        } catch (Exception e) {
//        }
//
//    }


//    @Override
//    public void onShowData(GetChatProjectBean viewModel) {
//        GetChatProjectBean.DataBean projectChatViewModel = viewModel.getData();
//        if (projectChatViewModel != null){
//            viewDataBinding.projectCard.setVisibility(View.VISIBLE);
//            viewDataBinding.setViewModel(projectChatViewModel);
//        }
//
//    }
}
