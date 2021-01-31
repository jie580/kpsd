package com.ming.sjll.Message;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.R.id;

import org.jetbrains.annotations.NotNull;

import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.provider.GroupConversationProvider;
import io.rong.imkit.widget.provider.PrivateConversationProvider;
import io.rong.imlib.model.MessageContent;


/**
 * 群消息内容分解
 */
@ConversationProviderTag(conversationType = "group", portraitPosition = 1)
public class GroupIContainerItemProvider extends GroupConversationProvider {

    public class ViewHolder extends PrivateConversationProvider.ViewHolder{

        protected ViewHolder() {
        }
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {

        View result = LayoutInflater.from(context).inflate(R.layout.rc_group_item_base_conversation ,null);
        PrivateConversationProvider.ViewHolder holder =  new GroupIContainerItemProvider.ViewHolder();
        holder.title = (TextView)result.findViewById(id.rc_conversation_title);
        holder.time = (TextView)result.findViewById(id.rc_conversation_time);
        holder.content = (TextView)result.findViewById(id.rc_conversation_content);
        holder.notificationBlockImage = (ImageView)result.findViewById(id.rc_conversation_msg_block);
        holder.readStatus = (ImageView)result.findViewById(id.rc_conversation_status);
        result.setTag(holder);
        return result;
    }

    @Override
    public void bindView(View view, int position, UIConversation data) {
//        deleteNameIfContains(data);



        GroupIContainerItemProvider.ViewHolder holder = (GroupIContainerItemProvider.ViewHolder)view.getTag();


//        data.send

//
//        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0A3FFF"));
//        spannable.setSpan(colorSpan, prefix.length(), (prefix + content.getDemand()).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//

        super.bindView(view, position, data);

//        if( data.getMessageContent().getUserInfo() != null)
//        {
            String content = holder.content.getText().toString();
            SpannableString spannable = new SpannableString(content);
            ImageSpan imageSpan = getImageSpan(view);
            spannable.setSpan(imageSpan,0,0, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            SpannableStringBuilder builder = new SpannableStringBuilder(content);
            holder.content.setText(spannable);
//            data.setConversationContent(builder);
//            Log.e("fasongxingxi","dddd"+data.getMessageContent().getUserInfo().getPortraitUri());
//        }


        MessageContent mesg = data.getMessageContent();
        mesg.getUserInfo();
////        spannable.setSpan(imageSpan, (prefix + content.getDemand()).length(), (prefix + content.getDemand() + " ").length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//        holder.title.setText();

    }

    @NotNull
    private ImageSpan getImageSpan(View v) {
        Drawable drawable = v.getResources().getDrawable(R.mipmap.chat_ext_btn4);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
    }

    /**
     * If conversation data contains user's name, delete the name from the data
     *
     * @param data conversation ui data
     */
    private void deleteNameIfContains(UIConversation data) {
        Spannable content = data.getConversationContent();

        String separator = ": ";
        int indexOf = content.toString().indexOf(separator);
        if (indexOf == -1) {
            return;
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.delete(0, indexOf + separator.length());

        data.setConversationContent(builder);
    }
}
