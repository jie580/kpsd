package com.ming.sjll.Message.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.my.activity.HomeageActivity;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.PublicServiceProfile;
import io.rong.imlib.model.UserInfo;

public class MessageConversationFragment extends ConversationFragment {

    public MessageListAdapter onResolveAdapter(Context context) {
        return new MessageConversationAdapter(context);
    }

    public class MessageConversationAdapter extends MessageListAdapter {
        public MessageConversationAdapter(Context context) {
            super(context);

            RongContext.getInstance().setConversationClickListener(new RongIM.ConversationClickListener() {
                @Override
                public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {

                    //跳转到个人中心页面
                    String userId = userInfo.getUserId();
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", userId + "");
                    Tools.jump(getActivity(), HomeageActivity.class, bundle, false);
                    return true;
                }

                @Override
                public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {
                    return false;
                }

                @Override
                public boolean onMessageClick(Context context, View view, Message message) {
                    return false;
                }

                @Override
                public boolean onMessageLinkClick(Context context, String s, Message message) {
                    return false;
                }

                @Override
                public boolean onMessageLongClick(Context context, View view, Message message) {
                    return false;
                }
            });
        }

        @Override
        protected void bindView(View v, int position, UIMessage data) {
            super.bindView(v, position, data);
            if (data != null) {
                final MessageListAdapter.ViewHolder holder = (MessageListAdapter.ViewHolder) v.getTag();
                TextView viewById = holder.contentView.findViewById(android.R.id.text1);
                if (viewById != null) {
                    if (data.getMessageDirection() == Message.MessageDirection.SEND) {
                        viewById.setTextColor(Color.parseColor("#FFFFFF"));
                    } else {
                        viewById.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        }
    }

    @Override
    protected void updatePublicServiceMenu(PublicServiceProfile publicServiceProfile) {
//        super.updatePublicServiceMenu(publicServiceProfile);
    }
}
