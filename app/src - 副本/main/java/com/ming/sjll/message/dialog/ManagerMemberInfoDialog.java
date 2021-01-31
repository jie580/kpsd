package com.ming.sjll.Message.dialog;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.dialog.BaseDialog;
import com.ming.sjll.databinding.DialogManagerMemberBinding;
import com.ming.sjll.Message.viewmodel.GetMemberInfoBean;
import com.ming.sjll.Home.bean.OccupationBean;
import com.ming.sjll.view.FlowTagLayout;
import com.ming.sjll.view.OnTagClickListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerMemberInfoDialog extends BaseDialog {

    private DialogManagerMemberBinding binding;
    private GetMemberInfoBean.DataBean viewModel;
    private ArrayList<OccupationBean.DataBean> occpationList;
    private MemberOccupationTagAdapter tagAdapter;

    public static ManagerMemberInfoDialog newInstance(GetMemberInfoBean.DataBean viewModel, ArrayList<OccupationBean.DataBean> occupationBeans) {

        Bundle args = new Bundle();
        args.putParcelable("info", viewModel);
        args.putParcelableArrayList("occpation", occupationBeans);
        ManagerMemberInfoDialog fragment = new ManagerMemberInfoDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_manager_member;
    }

    private void initFlowtag() {
        tagAdapter = new MemberOccupationTagAdapter(getActivity());
        binding.flowtag.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);//支持多选
        binding.flowtag.setAdapter(tagAdapter);
        if (viewModel.getOccupation() == null || viewModel.getOccupation().isEmpty()) {
            binding.flowtag.setVisibility(View.GONE);
        }
        tagAdapter.clearAndAddAll(viewModel.getOccupation());

        binding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spanner string
                int index = binding.nsPosition.getSelectedIndex();
                OccupationBean.DataBean dataBean = occpationList.get(index);
                GetMemberInfoBean.DataBean.OccupationBean bean = new GetMemberInfoBean.DataBean.OccupationBean();
                bean.setOccupation_id(dataBean.getId() + "");
                bean.setTitle(dataBean.getTitle());
                tagAdapter.add(bean);

            }
        });
        binding.flowtag.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                //点击就是删除
                viewModel.getOccupation().remove(position);
                tagAdapter.remove(position);
            }
        });


    }


    @Override
    public void initView(ViewDataBinding dataBinding) {
        binding = (DialogManagerMemberBinding) dataBinding;

        if (getArguments() != null) {
            viewModel = getArguments().getParcelable("info");
            occpationList = getArguments().getParcelableArrayList("occpation");
            if (viewModel != null) {
                initFlowtag();
                if (!TextUtils.isEmpty(viewModel.getRemark())) {
                    binding.tvRemark.setText(viewModel.getRemark());
                    binding.tvRemark.setSelection(viewModel.getRemark().length());
                }
            }

            List<String> strings = new ArrayList<>();
            for (int i = 0; i < occpationList.size(); i++) {
                OccupationBean.DataBean dataBean = occpationList.get(i);
                strings.add(dataBean.getTitle());
            }
            binding.nsPosition.attachDataSource(strings);

        }


        binding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存
                if (onSaveListener != null) {
                    List<GetMemberInfoBean.DataBean.OccupationBean> occupation = tagAdapter.getDataList();
                    List<String> occupationId = new ArrayList<>();
                    for (int i = 0; i < occupation.size(); i++) {
                        occupationId.add(occupation.get(i).getOccupation_id());
                    }
                    onSaveListener.onSave(viewModel.getUser_id(), binding.tvRemark.getText().toString(), occupationId);
                }
                dismissAllowingStateLoss();

            }
        });
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    private OnSaveListener onSaveListener;

    public void setOnSaveListener(OnSaveListener onSaveListener) {
        this.onSaveListener = onSaveListener;
    }

    public interface OnSaveListener {
        void onSave(String uid, String remark, List<String> occupationId);
    }

}
