package com.ming.sjll.Message.item;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ming.sjll.R;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.AutoLinkTextView;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

@ProviderTag( messageContent = TextMessage.class )
public class CustomTextMessageItemProvider extends TextMessageItemProvider {


//    @Override
//    public void bindView(View v, int position, TextMessage content, UIMessage data) {
//        super.bindView(v, position, content, data);
//        TextView textView = (TextView) v;
//        if (data.getMessageDirection() == Message.MessageDirection.SEND) {
//            textView.setTextColor(Color.WHITE);
//        } else {
//            textView.setTextColor(Color.BLACK);
//        }
//    }

//    @Override
//    public void bindView(View v, int position, TextMessage content, UIMessage data) {
//        super.bindView(v, position, content, data);
//        v.findViewById(16908308);
//        textView.setBackgroundResource(R.drawable.rc_ic_bubble_right_transparency);
//
//
//    }

}
