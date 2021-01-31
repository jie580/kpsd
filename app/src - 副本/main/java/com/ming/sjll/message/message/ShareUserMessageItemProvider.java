package com.ming.sjll.Message.message;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.base.utils.ImageLoaderUtil;
import com.ming.sjll.Message.view.MessageChatView;
import com.ming.sjll.view.CircleImageView;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

@ProviderTag(messageContent = ShareUserMessageContent.class)
public class ShareUserMessageItemProvider extends IContainerItemProvider.MessageProvider<ShareUserMessageContent> {

    class ViewHolder {
        RelativeLayout message;
        RelativeLayout content;
        CircleImageView header;
        TextView name;
        TextView profession;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_custom_share_user, null);
        ViewHolder holder = new ViewHolder();
        holder.message = view.findViewById(R.id.message);
        holder.header = view.findViewById(R.id.header);
        holder.name = view.findViewById(R.id.name);
        holder.profession = view.findViewById(R.id.profession);
        holder.content = view.findViewById(R.id.content);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, ShareUserMessageContent content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();

        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.message.setBackgroundResource(R.drawable.message_custom_bg_right);
        } else {
            holder.message.setBackgroundResource(R.drawable.message_custom_bg_left);
        }
        holder.name.setText(content.getUserName());
        holder.profession.setText(content.getOccupation());
        ImageLoaderUtil.display(holder.header, content.getHeadImg());

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToUser(content);
                }
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToUser(content);
                }
            }
        });
        holder.profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToUser(content);
                }
            }
        });
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToUser(content);
                }
            }
        });
    }


    @Override
    public Spannable getContentSummary(ShareUserMessageContent data) {
        return new SpannableString("[资料分享]");
    }

    @Override
    public void onItemClick(View view, int position, ShareUserMessageContent pdfMessageContent, UIMessage uiMessage) {
        //跳转到用户信息页面
//        Tools.jump((Activity) view.getContext(), HomeageActivity.class, false);
    }


}
