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

@ProviderTag(messageContent = ShareGoodsMessageContent.class)
public class ShareGoodsMessageItemProvider extends IContainerItemProvider.MessageProvider<ShareGoodsMessageContent> {

    class ViewHolder {
        RelativeLayout message;
        CircleImageView header;
        TextView product;
        TextView tv_address;
        TextView tv_payment;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_custom_share_goods, null);
        ViewHolder holder = new ViewHolder();
        holder.message = view.findViewById(R.id.message);
        holder.header = view.findViewById(R.id.header);
        holder.product = view.findViewById(R.id.product);
        holder.tv_address = view.findViewById(R.id.tv_address);
        holder.tv_payment = view.findViewById(R.id.tv_payment);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, ShareGoodsMessageContent content, UIMessage message) {
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
                    ((MessageChatView) view.getContext()).jumpToGoods(content);
                }

            }
        });
        holder.header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToGoods(content);
                }
            }
        });
        holder.product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToGoods(content);
                }
            }
        });
        holder.tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToGoods(content);
                }
            }
        });
        holder.tv_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getContext() instanceof MessageChatView) {
                    ((MessageChatView) view.getContext()).jumpToGoods(content);
                }
            }
        });
        holder.product.setText(content.getGoodsTitle());
        holder.tv_address.setText(content.getAddress());
        holder.tv_payment.setText(content.getPayment());
    }


    @Override
    public Spannable getContentSummary(ShareGoodsMessageContent data) {
        return new SpannableString("[商品]");
    }

    @Override
    public void onItemClick(View view, int i, ShareGoodsMessageContent pdfMessageContent, UIMessage uiMessage) {

    }


}
