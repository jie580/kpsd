package com.ming.sjll.search;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ming.sjll.R;

public class ProjectNoticeDialog extends Dialog {

    private Context context;
    private String title;           //dialog标题
    private String content;         //dialog提示内容
    private String positiveBtnText; //dialog确认按钮文字


    private LinearLayout tvTitle;

    public ProjectNoticeDialog(@NonNull Context context) {
        super(context);
        this.context = context;

        initView();
        initSetting();
        initListener();
    }

    public ProjectNoticeDialog(@NonNull Context context, String title, String content, String positiveBtnText) {
        super(context, 0);
        this.context = context;
        this.title = title;
        this.content = content;
        this.positiveBtnText = positiveBtnText;
        initView();
        initSetting();
        initListener();
    }

    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.project_notice_dialog, null);

        setContentView(view);
    }

    private void initSetting() {
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    private void initListener() {
//        tvTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("点击了","initListenerinitListener");
//                if (onDialogClickListener != null) {
//                    onDialogClickListener.onSureClickListener();
//                }
//            }
//        });

    }

    /********************** 回调 ************************/
    private OnDialogClickListener onDialogClickListener;

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public interface OnDialogClickListener {
        void onSureClickListener();     //当点击了确认按钮之后执行

        void onCancelClickListener();   //当点击了取消按钮之后执行（取消操作通常都是弹窗消失）
    }


}
