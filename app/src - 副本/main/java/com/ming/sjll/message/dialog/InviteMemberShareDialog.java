package com.ming.sjll.Message.dialog;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.dialog.BaseDialog;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.databinding.DialogInviteShareBinding;
import com.ming.sjll.Message.view.ShareView;
import com.ming.sjll.search.activity.MyColleagueActivity;
import com.ming.sjll.search.activity.SearchAcitivity;
import com.ming.sjll.search.presenter.MyColleaguePresenter;

public class InviteMemberShareDialog extends BaseDialog {

    public static String PROJECT_ID = "PROJECT_ID";
    private ShareView shareView;

    public static InviteMemberShareDialog newInstance(String projectId) {

        Bundle args = new Bundle();
        args.putString(PROJECT_ID, projectId);
        InviteMemberShareDialog fragment = new InviteMemberShareDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_invite_share;
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        DialogInviteShareBinding binding = (DialogInviteShareBinding) dataBinding;

        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                //搜索页面
                Tools.jump(getActivity(), SearchAcitivity.class, false);
            }
        });
        binding.tvWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                //微信分享
                if (shareView != null) {
                    shareView.share("微信好友");
                }
            }
        });
        binding.tvMoment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                //朋友圈分享
                if (shareView != null) {
                    shareView.share("朋友圈");
                }
            }
        });
        binding.tvColleague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                Bundle bundle = new Bundle();
                if (getArguments() != null) {
                    int projectId = getArguments().getInt(PROJECT_ID);
                    bundle.putInt(MyColleaguePresenter.PROJECT_ID, projectId);
                }
                //我的同事页面
                Tools.jump(getActivity(), MyColleagueActivity.class, bundle, false);


            }
        });
    }

    public InviteMemberShareDialog setShareView(ShareView shareView) {
        this.shareView = shareView;
        return this;
    }


    @Override
    public boolean isBottom() {
        return true;
    }
}
