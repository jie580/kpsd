package com.ming.sjll.Message.message;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.Message.view.MessageChatView;

import org.jetbrains.annotations.NotNull;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

@ProviderTag(messageContent = CooperationMessageContent.class)
public class CooperationMessageItemProvider extends IContainerItemProvider.MessageProvider<CooperationMessageContent> {

    class ViewHolder {
        RelativeLayout message;
        RelativeLayout content;
        TextView tv_apply;
        TextView tv_refuse;
        TextView tv_receive;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_custom_cooperation_apply, null);
        CooperationMessageItemProvider.ViewHolder holder = new CooperationMessageItemProvider.ViewHolder();
        holder.message = view.findViewById(R.id.message);
        holder.tv_apply = view.findViewById(R.id.tv_apply);
        holder.tv_refuse = view.findViewById(R.id.tv_refuse);
        holder.tv_receive = view.findViewById(R.id.tv_receive);
        holder.content = view.findViewById(R.id.content);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, CooperationMessageContent content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();
        String prefix = "您是否接受项目 ";
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            prefix = "您已发送项目合作请求 ";
            holder.message.setBackgroundResource(R.drawable.message_custom_bg_right);
            holder.tv_apply.setVisibility(View.GONE);
            holder.tv_refuse.setVisibility(View.GONE);
        } else {
            holder.message.setBackgroundResource(R.drawable.message_custom_bg_left);
            holder.tv_apply.setVisibility(View.VISIBLE);
            holder.tv_refuse.setVisibility(View.VISIBLE);
        }


        SpannableString spannable = new SpannableString(prefix + content.getDemand() + " ");

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0A3FFF"));
        spannable.setSpan(colorSpan, prefix.length(), (prefix + content.getDemand()).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ImageSpan imageSpan = getImageSpan(v);
        spannable.setSpan(imageSpan, (prefix + content.getDemand()).length(), (prefix + content.getDemand() + " ").length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        holder.tv_apply.setText(spannable);
        holder.tv_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).onCooperationMessage(true, content, message);
                }


//                RongIMUtils.INSTANCE.sendConfirmCooperation(message.getConversationType(), message.getTargetId(),
//                        ConfirmCooperationMessageContent.obtain("", content.getProject_id(), content.getDemand(), content.getApply_id(),
//                                ConfirmCooperationMessageContent.received()));
//
//                RongIMUtils.INSTANCE.deleteMessage(message.getMessageId());
            }
        });

        holder.tv_refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).onCooperationMessage(false, content, message);
                }


//                RongIMUtils.INSTANCE.sendConfirmCooperation(message.getConversationType(), message.getTargetId(),
//                        ConfirmCooperationMessageContent.obtain("", content.getProject_id(), content.getDemand(), content.getApply_id(),
//                                ConfirmCooperationMessageContent.refuseed()));
//
//                RongIMUtils.INSTANCE.deleteMessage(message.getMessageId());
            }
        });
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToProject(content);
                }
            }
        });

        holder.tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToProject(content);
                }
            }
        });


    }

    @NotNull
    private ImageSpan getImageSpan(View v) {
        Drawable drawable = v.getResources().getDrawable(R.mipmap.ic_arrow_h);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
    }

    @Override
    public Spannable getContentSummary(CooperationMessageContent data) {
        return new SpannableString("[合作]");
    }

    @Override
    public void onItemClick(View view, int i, CooperationMessageContent pdfMessageContent, UIMessage uiMessage) {
    }


}
