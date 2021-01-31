package com.ming.sjll.toolPage;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ming.sjll.R;
//加载中
public class LoadingDailog extends Dialog{



    private Context context;
    View view;
    TextView msgText;
    LoadingDailog loadingDailog;

    public LoadingDailog(Context context) {
        super(context,R.style.ToolPageLoadingDailog);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
//            View view=inflater.inflate(R.layout.dialog_loading,null);
        view=inflater.inflate(R.layout.tool_page_loading_dialog,null);

//        loadingDailog=new LoadingDailog(context,R.style.ToolPageLoadingDailog);
//             msgText= (TextView) view.findViewById(R.id.tipTextView);
//            if(isShowMessage){
//                msgText.setText(message);
//            }else{
//                msgText.setVisibility(View.GONE);
//            }
        this.setContentView(view);
        msgText= (TextView) view.findViewById(R.id.tipTextView);
    }

    public LoadingDailog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setMsg(String msg)
    {
        msgText.setText(msg);
    }
    public  void showLoading()
    {
        showLoading("加载中");
    }

    public  void showLoading(String msg)
    {
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
        this.show();
    }
//    public static class Builder{
//
//        private Context context;
//        private String message;
//        private boolean isShowMessage=true;
//        private boolean isCancelable=false;
//        private boolean isCancelOutside=false;
//
//
//        public Builder(Context context) {
//            this.context = context;
//        }
//
//        /**
//         * 设置提示信息
//         * @param message
//         * @return
//         */
//
//        public Builder setMessage(String message){
//            this.message=message;
//            if(msgText != null)
//            {
//                msgText.setText(message);
//            }
//            return this;
//        }
//
//        /**
//         * 设置是否显示提示信息
//         * @param isShowMessage
//         * @return
//         */
//        public Builder setShowMessage(boolean isShowMessage){
//            this.isShowMessage=isShowMessage;
//            return this;
//        }
//
//        /**
//         * 设置是否可以按返回键取消
//         * @param isCancelable
//         * @return
//         */
//
//        public Builder setCancelable(boolean isCancelable){
//            this.isCancelable=isCancelable;
//            return this;
//        }
//
//        /**
//         * 设置是否可以取消
//         * @param isCancelOutside
//         * @return
//         */
//        public Builder setCancelOutside(boolean isCancelOutside){
//            this.isCancelOutside=isCancelOutside;
//            return this;
//        }
//        TextView msgText;
//        public LoadingDailog create(){
//
//            LayoutInflater inflater = LayoutInflater.from(context);
////            View view=inflater.inflate(R.layout.dialog_loading,null);
//            View view=inflater.inflate(R.layout.tool_page_loading_dialog,null);
//            LoadingDailog loadingDailog=new LoadingDailog(context,R.style.ToolPageLoadingDailog);
//             msgText= (TextView) view.findViewById(R.id.tipTextView);
//            if(isShowMessage){
//                msgText.setText(message);
//            }else{
//                msgText.setVisibility(View.GONE);
//            }
//            loadingDailog.setContentView(view);
//            loadingDailog.setCancelable(isCancelable);
//            loadingDailog.setCanceledOnTouchOutside(isCancelOutside);
//            return  loadingDailog;
//
//        }
//
//
//    }
}