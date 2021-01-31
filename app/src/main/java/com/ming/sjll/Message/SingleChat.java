package com.ming.sjll.Message;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.project.MyProjectList;

import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * 消息列表-单聊
 */
public class SingleChat extends BaseFragment {


    public static SingleChat newInstance() {

        return new SingleChat();
    }


    private void getList()
    {
        new RongImUtils().getSingleList( new RongImUtils.onChatEven(){
            @Override
            public void onSuccess(List<Conversation> conversations) {
                if(conversations.size() == 0)
                {
                    Log.e("获取消息成功后：","空");
                    return;
                }
//                conversations.get(0).getLatestMessage();
//                conversations.get(0).getTargetId();
                for (int i = 0; i < conversations.size(); i++) {
                    Log.e("获取到的消息：",conversations.get(i).getLatestMessage()+""+  conversations.get(i).getTargetId());

                }

                Log.e("获取到的消息：",conversations.get(0).getLatestMessage()+""+  conversations.get(0).getTargetId());

            }

        });

    }
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.message_single_chat);



        getList();

        RongIMClient.getInstance().appOnStart();
        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
            @Override
            public boolean onReceived(Message message, int i, boolean b, boolean b1) {
                // 输出消息类型。
                Log.e("融云Receive2:", message.getObjectName());
//                getList();
                // 此处输出判断是否是文字消息，并输出，其他消息同理。
                if (message.getContent() instanceof TextMessage) {
                    final TextMessage textMessage = (TextMessage) message.getContent();
                    Log.e("融云onReceived2", "Text Message: " + textMessage.getContent());
                }

                return false;
            }
        });

//
//        initView();
//        event();
    }


    public ConversationListFragment createConversationListFragment() {
        ConversationListFragment mConversationListFragment = MessageConversationListFragment.newInstance();
        try {
            Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                    .build();
            mConversationListFragment.setUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mConversationListFragment;

    }


}
