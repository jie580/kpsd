package com.ming.sjll.base.dialog;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.ming.sjll.R;
import com.ming.sjll.databinding.DialogEditTextBinding;

import java.util.Timer;
import java.util.TimerTask;

public class EditTextDialog extends BaseDialog {

    private setOnSaveListener onClickListener;

    public static EditTextDialog newInstance(String defaultString) {
        Bundle args = new Bundle();
        args.putString("defaultString",defaultString);
        EditTextDialog fragment = new EditTextDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public EditTextDialog setOnSaveListener(setOnSaveListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public void setMaxLength(int length)
    {
        this.textLength = length;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_edit_text;
    }

    String defaultString;
    DialogEditTextBinding editorBinding;
    int textLength = -1;
    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager,tag);

    }
    @Override
    public void initView(ViewDataBinding dataBinding) {
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
         defaultString = getArguments().getString("defaultString","");
         editorBinding = (DialogEditTextBinding) dataBinding;

        editorBinding.editText.setText(defaultString);
        editorBinding.editText.setFocusable(true);
        editorBinding.editText.setFocusableInTouchMode(true);
        editorBinding.editText.requestFocus();
        if(textLength > 0)
        {
            editorBinding.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(textLength)});
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) editorBinding.editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editorBinding.editText, 0);
            }

        }, 500);


        editorBinding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.save(editorBinding.editText.getText().toString());
                }
            }
        });
//        editorBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        editorBinding.tvEditor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                if (onClickListener != null) {
//                    onClickListener.Editor();
//                }
//
//            }
//        });
//        editorBinding.tvDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                if (onClickListener != null) {
//                    onClickListener.Delete();
//                }
//
//            }
//        });
//        editorBinding.tvShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//                if (onClickListener != null) {
//                    onClickListener.Share();
//                }
//
//            }
//        });


    }

    @Override
    public boolean isBottom() {
        return true;
    }

    public interface setOnSaveListener {
      void save(String s);
    }
}
