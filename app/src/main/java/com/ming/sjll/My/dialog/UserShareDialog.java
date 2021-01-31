package com.ming.sjll.My.dialog;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.dialog.BaseDialog;
import com.ming.sjll.databinding.DialogUserShareBinding;
import com.ming.sjll.toolPage.ShareView;

/**
 * @author luoming
 * created at 2019-11-09 22:49
 * 用户分享
 */

public class UserShareDialog extends BaseDialog {
    private ShareView shareView;


    public static UserShareDialog newInstance() {

        Bundle args = new Bundle();
        UserShareDialog fragment = new UserShareDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_user_share;
    }

    public void setShareView(ShareView shareView) {
        this.shareView = shareView;
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        DialogUserShareBinding binding = (DialogUserShareBinding) dataBinding;

        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareView.share("分享到消息");

            }
        });
        binding.tvWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareView.share("微信好友");
            }
        });
        binding.tvMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareView.share("朋友圈");

            }
        });
    }


    @Override
    public boolean isBottom() {
        return true;
    }
}
