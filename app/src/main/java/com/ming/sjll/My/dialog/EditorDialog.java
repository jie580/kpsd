package com.ming.sjll.My.dialog;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.ming.sjll.R;
import com.ming.sjll.base.dialog.BaseDialog;
import com.ming.sjll.databinding.DialogMyEditorBinding;

public class EditorDialog extends BaseDialog {

    private OnClickListener onClickListener;

    public static EditorDialog newInstance() {

        Bundle args = new Bundle();
        EditorDialog fragment = new EditorDialog();
        fragment.setArguments(args);
        return fragment;
    }

//    其他人查看时只显示收藏和分享
    public EditorDialog  otherLook()
    {
        editorBinding.line2.setVisibility(View.GONE);
        editorBinding.line3.setVisibility(View.GONE);
        editorBinding.tvEditor.setVisibility(View.GONE);
        editorBinding.tvDelete.setVisibility(View.GONE);
        return this;
    }


    public EditorDialog setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }


    @Override
    public int getLayoutId() {
        return R.layout.dialog_my_editor;
    }

    DialogMyEditorBinding editorBinding;
    @Override
    public void initView(ViewDataBinding dataBinding) {
        editorBinding = (DialogMyEditorBinding) dataBinding;
        editorBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        editorBinding.tvEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.Editor();
                }

            }
        });
        editorBinding.tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.Editor();
                }

            }
        });
        editorBinding.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onClickListener != null) {
                    onClickListener.Delete();
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
        void Editor();

        void Delete();

        void Share();
    }
}
