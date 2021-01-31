package com.ming.sjll.Message.item;

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


import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

@ProviderTag(messageContent = ShareWorkMessageContent.class)
public class ShareWorkMessageItemProvider extends IContainerItemProvider.MessageProvider<ShareWorkMessageContent> {

    class ViewHolder {

//        CircleImageView header;
        TextView title;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_custom_work, null);
        ViewHolder holder = new ViewHolder();
        holder.title = view.findViewById(R.id.title);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View v, int position, ShareWorkMessageContent content, UIMessage message) {
        ViewHolder holder = (ViewHolder) v.getTag();
        holder.title.setText(content.getUserName());

//        holder.title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (view.getContext() instanceof MessageChatView){
//                    ((MessageChatView) view.getContext()).jumpToProduct(content);
//                }
//
//            }
//        }); holder.header.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (view.getContext() instanceof MessageChatView){
//                    ((MessageChatView) view.getContext()).jumpToProduct(content);
//                }
//            }
//        });
//        holder.product.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (view.getContext() instanceof MessageChatView){
//                    ((MessageChatView) view.getContext()).jumpToProduct(content);
//                }
//            }
//        });
        holder.title.setText(content.getUserName() + "的作品");
    }


    @Override
    public Spannable getContentSummary(ShareWorkMessageContent data) {
        return new SpannableString("[作品分享]");
    }

    @Override
    public void onItemClick(View view, int i, ShareWorkMessageContent pdfMessageContent, UIMessage uiMessage) {

    }


}
