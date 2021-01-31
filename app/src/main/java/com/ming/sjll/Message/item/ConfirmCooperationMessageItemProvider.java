package com.ming.sjll.Message.item;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ming.sjll.Message.MessageChatActivity;
import com.ming.sjll.R;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.project.ReleaseProject;
//import com.ming.sjll.message.view.MessageChatView;

import org.jetbrains.annotations.NotNull;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

@ProviderTag(messageContent = ConfirmCooperationMessageContent.class)
public class ConfirmCooperationMessageItemProvider extends IContainerItemProvider.MessageProvider<ConfirmCooperationMessageContent> {

    class ViewHolder {
        RelativeLayout message;
        RelativeLayout content;
        TextView tv_apply;
        TextView tv_refuse;
        TextView tv_receive;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
//        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_custom_confirm_cooperation, null);
        View view = LayoutInflater.from(context).inflate(R.layout.message_custom_cooperation_apply, null);
        ViewHolder holder = new ViewHolder();
        holder.message = view.findViewById(R.id.message);
        holder.tv_apply = view.findViewById(R.id.tv_apply);
        holder.tv_refuse = view.findViewById(R.id.tv_refuse);
        holder.tv_receive = view.findViewById(R.id.tv_receive);
        holder.content = view.findViewById(R.id.content);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, ConfirmCooperationMessageContent content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();
        String prefix = "已拒绝项目 ";
        if(content.isReceived())
        {
            prefix = "已接受项目 ";
        }
        holder.tv_receive.setVisibility(View.GONE);
        holder.tv_refuse.setVisibility(View.GONE);
        SpannableString spannable = new SpannableString(prefix +"    "+ content.getDemand() + " ");

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0A3FFF"));
        spannable.setSpan(CharacterStyle.wrap(colorSpan), (prefix  +"    " ).length() , (prefix  +"    " +  content.getDemand()).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        spannable.setSpan(CharacterStyle.wrap(new UnderlineSpan()), (prefix  +"    " ).length() , (prefix  +"    " +  content.getDemand()).length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        spannable.setAntiAlias(true);//抗锯齿

//        ImageSpan imageSpan = getImageSpan(v);
//        spannable.setSpan(imageSpan, (prefix + content.getDemand()).length(), (prefix + content.getDemand() + " ").length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        holder.tv_apply.setText(spannable);

        holder.tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ToastShow.s("跳转项目：" + ((CooperationLookMessageContent)content).getprojectId());
                Bundle bundle = new Bundle();
                bundle.putString("id",((ConfirmCooperationMessageContent)content).getprojectId());
                bundle.putBoolean("isLook",true);
                Tools.jump( ((MessageChatActivity) view.getContext()), ReleaseProject.class, bundle,false);

//                if (view.getContext() instanceof MessageChatView){
//                    ((MessageChatView) view.getContext()).jumpToProject(content);
//                }
            }
        });

    }

//    @NotNull
//    private ImageSpan getImageSpan(View v) {
//        Drawable drawable = v.getResources().getDrawable(R.mipmap.ic_arrow_h);
//        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
//        return new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
//    }

    @Override
    public Spannable getContentSummary(ConfirmCooperationMessageContent data) {
        if (data.isReceived()) {
            return new SpannableString("[确认合作]");
        } else {
            return new SpannableString("[拒绝合作]");
        }
    }

    @Override
    public void onItemClick(View view, int i, ConfirmCooperationMessageContent pdfMessageContent, UIMessage uiMessage) {
    }

}
