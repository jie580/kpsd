package com.ming.sjll.base.tool;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.rey.material.app.BottomSheetDialog;

import javax.security.auth.callback.Callback;

import io.rong.imkit.utilities.RongUtils;

public class DialogTextTip {

    public enum Prompt
    {
        /**
         * 清除缓存
         */
//        CLEAR_CACHE,
        /**
         * 转换个人
         */
        CHANGE_PERSONAGE,
        /**
         * 商品加入项目
         */
        GOODS_JOIN_PROJECT,
        /**
         * 购买商品提示
         */
        GOODS_BUY,
    }

    private String text;
    private Context context;


    // 是否显示窗体
    private boolean isShowPrompt;

    private Prompt key;

    private View isPromptWrap;
    public BottomSheetDialog bottomInterPasswordDialog;
    private CheckBox isPromptBox;
    private TextView tvTipText;
    LinearLayout btns;

    /**
     * 显示弹窗提示，根据key+用户id来判断
     * @param context
     * @param text
     * @param key
     */
    public DialogTextTip(Context context, String text, Prompt key)
    {
        this.key = key;
        isShowPrompt = !Cache.getCache(key+""+Cache.getUserId()).equals("false");

        init( context,  text,  true);
    }

    /**
     * 不显示不再提示
     * @param context
     * @param text
     */
    public DialogTextTip(Context context, String text)
    {
        isShowPrompt = true;
        init( context,  text,  false);
    }

    public void hideBtns()
    {
        btns.setVisibility(View.GONE);
        tvTipText.setGravity(Gravity.CENTER);
        tvTipText.setPadding(0, RongUtils.dip2px(40.0F),0,RongUtils.dip2px(40.0F));
    }

     public void init(Context context, String text, boolean isShowPrompt)
     {
          this.text = text;
          this.context = context;

         bottomInterPasswordDialog = new BottomSheetDialog(context);
         bottomInterPasswordDialog.setContentView(R.layout.dialog_text_tip);
         bottomInterPasswordDialog.inDuration(300);
         bottomInterPasswordDialog.outDuration(200);
//         bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//         bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
         isPromptBox = bottomInterPasswordDialog.findViewById(R.id.isPrompt);
         tvTipText = bottomInterPasswordDialog.findViewById(R.id.tvTipText);
         btns = bottomInterPasswordDialog.findViewById(R.id.btns);

         tvTipText.setText(text);

         /**
          * 是否显示不再提示弹窗
          */
         isPromptWrap = bottomInterPasswordDialog.findViewById(R.id.isPromptWrap);
         if(isShowPrompt)
         {
             isPromptWrap.setVisibility(View.VISIBLE);
         }
         else{
             isPromptWrap.setVisibility(View.GONE);
         }

         event();

     }

    SpannableStringBuilder builder;
    /**
     * 设置某段文字颜色
     */
     public   void setTextColor(int start, int end)
     {
         if(builder == null )
         {
             builder = new SpannableStringBuilder(text);
         }
         ForegroundColorSpan buleSpan1 = new ForegroundColorSpan(Color.parseColor("#0A3FFF"));
         builder.setSpan(buleSpan1, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
         tvTipText.setText(builder);
     }

     private void event()
     {

//         isPromptBox.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
////                 isShowPrompt = !isShowPrompt;
//                 Cache.CacheValue(key+""+Cache.getUserId(),isPromptBox.isChecked()   +"");
////                 isPromptBox.setChecked(isShowPrompt);
//             }
//         });

     }

    public void show()
    {
        show(null);
    }
     public void show(CommonCallback cb)
     {
         /**
          * 直接成功
          */
         if( isShowPrompt == true)
         {
             if(bottomInterPasswordDialog != null)
             bottomInterPasswordDialog.show();
         }
         else
         {
             if(cb != null)
             {
                cb.onNext();
             }
         }

         bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(cb != null)
                 {
                     bottomInterPasswordDialog.hide();
                     cb.onCancel();
                 }
             }
         });
         bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Cache.setCache(key+""+Cache.getUserId(),!isPromptBox.isChecked()   +"");
                 if(cb != null)
                 {
                     cb.onNext();
                 }
                 else
                 {
                     bottomInterPasswordDialog.hide();
                 }
             }
         });
     }

     public void hide()
     {
         if(bottomInterPasswordDialog != null)
         bottomInterPasswordDialog.hide();
     }

}
