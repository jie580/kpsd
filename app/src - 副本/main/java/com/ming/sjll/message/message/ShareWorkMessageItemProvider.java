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

@ProviderTag(messageContent = ShareWorkMessageContent.class)
public class ShareWorkMessageItemProvider extends IContainerItemProvider.MessageProvider<ShareWorkMessageContent> {

    class ViewHolder {
        RelativeLayout message;
        CircleImageView header;
        TextView product;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_custom_share_work, null);
        ViewHolder holder = new ViewHolder();
        holder.message = view.findViewById(R.id.message);
        holder.header = view.findViewById(R.id.header);
        holder.product = view.findViewById(R.id.product);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, ShareWorkMessageContent content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();

        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.message.setBackgroundResource(R.drawable.message_custom_bg_right);
        } else {
            holder.message.setBackgroundResource(R.drawable.message_custom_bg_left);
        }

        ImageLoaderUtil.display(holder.header, content.getHeadImg());

        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToProduct(content);
                }

            }
        });
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToProduct(content);
                }
            }
        });
        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToProduct(content);
                }
            }
        });
        holder.product.setText(content.getUserName() + "的作品");
    }


    @Override
    public Spannable getContentSummary(ShareWorkMessageContent data) {
        return new SpannableString("[作品分享]");
    }

    @Override
    public void onItemClick(View view, int i, ShareWorkMessageContent pdfMessageContent, UIMessage uiMessage) {

    }


}
