package com.ming.sjll.My.dialog;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.dialog.BaseDialog;
import com.ming.sjll.databinding.DialogOtherEditorBinding;

public class OtherEditorDialog extends BaseDialog {

    /**
     * 是否收藏了
     */
    private boolean isCollect = false;

    private OnClickListener onClickListener;

    public static OtherEditorDialog newInstance(boolean isCollect) {
        Bundle args = new Bundle();
        OtherEditorDialog fragment = new OtherEditorDialog();
        fragment.setArguments(args);
        fragment.isCollect = isCollect;
        return fragment;
    }



    public OtherEditorDialog setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }


    @Override
    public int getLayoutId() {
        return R.layout.dialog_other_editor;
    }


    @Override
    public void initView(ViewDataBinding dataBinding) {
        DialogOtherEditorBinding  editorBinding = (DialogOtherEditorBinding) dataBinding;
        if(isCollect)
        {
            editorBinding.tvCollect.setText("收藏");
        }
        else {
            editorBinding.tvCollect.setText("取消收藏");
        }
        editorBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        editorBinding.tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.Collect();
                }

            }
        });
        editorBinding.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.Share();
                }

            }
        });


    }

    @Override
    public boolean isBottom() {
        return true;
    }

    public interface OnClickListener {
        void Collect();

        void Share();
    }
}
