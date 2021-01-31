package com.ming.sjll.Message.dialog;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.dialog.BaseDialog;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.databinding.DialogAddShareBinding;
import com.ming.sjll.search.activity.SearchAcitivity;
import com.ming.sjll.supplier.activity.AddGroupMembersAcitivity;

/**
 * 添加成员弹框
 */
public class AddMemberShareDialog extends BaseDialog {


    public static AddMemberShareDialog newInstance(String projectId, String occupation_id) {

        Bundle args = new Bundle();
        args.putString("projectId", projectId);
        args.putString("occupationId", occupation_id);
        AddMemberShareDialog fragment = new AddMemberShareDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_add_share;
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
        DialogAddShareBinding binding = (DialogAddShareBinding) dataBinding;

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
        binding.tvQun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                ///群
                getArguments().putInt("type", 1);
                Tools.jump(getActivity(), AddGroupMembersAcitivity.class, getArguments(), false);

            }
        });
        binding.tvColleague.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                //我的同事
                getArguments().putInt("type", 2);
                Tools.jump(getActivity(), AddGroupMembersAcitivity.class, getArguments(), false);

            }
        });
    }


    @Override
    public boolean isBottom() {
        return true;
    }
}
