package com.ming.sjll.Message.dialog;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.dialog.BaseDialog;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.databinding.DialogInviteAddsShareBinding;
import com.ming.sjll.Message.view.ShareView;
import com.ming.sjll.search.activity.SearchAcitivity;

public class InviteAddsMemberShareDialog extends BaseDialog {

    private ShareView shareView;
    DialogInviteAddsShareBinding binding;

    public static InviteAddsMemberShareDialog newInstance(String title, String identification) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("identification", identification);
        InviteAddsMemberShareDialog fragment = new InviteAddsMemberShareDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_invite_adds_share;
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (DialogInviteAddsShareBinding) dataBinding;

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
                Tools.jump(getActivity(), SearchAcitivity.class, getArguments(), false);
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
                //地图选取
                if (shareView != null) {
                    shareView.share("地图选取");
                }

            }
        });
        String title = getArguments().getString("title");
        if (!TextUtils.isEmpty(title)) {
            if (title.equals("场地添加")) {
                binding.tvColleague.setVisibility(View.VISIBLE);
            } else {
                binding.tvColleague.setVisibility(View.GONE);
            }
            binding.title.setText(title);
        }
    }


    public InviteAddsMemberShareDialog setShareView(ShareView shareView) {
        this.shareView = shareView;
        return this;
    }


    @Override
    public boolean isBottom() {
        return true;
    }
}
