package com.ming.sjll.Message;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ming.sjll.base.widget.ToastShow;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.model.UIMessage;

import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.PublicServiceProfile;
import io.rong.imlib.model.UserInfo;

public class MessageConversationFragment extends ConversationFragment {


    public listenerExtension listener;

    public CustomMessageListAdapter onResolveAdapter(Context context) {
        return new MessageConversationAdapter(context);
    }




    /**
     * 输入区关闭监听
     */
    @Override
    public void onExtensionCollapsed() {
        super.onExtensionCollapsed();
        if(listener != null)
        {
            listener.onExtensionCollapsed();
        }
        Log.e("onExtensionCollapsed","关闭");
    }

    /**
     * 输入区展开监听
     * @param h 输入区高度
     */
    @Override
    public void onExtensionExpanded(int h) {
        super.onExtensionExpanded(h);
        if(listener != null)
        {
            listener.onExtensionExpanded();
        }
        Log.e("onExtensionExpanded","展开"+h);
    }



//    /**
//     * '+' 扩展按钮事件监听
//     * @param pluginModule 扩展项按钮对象
//     * @param position 扩展项的位置
//     */
//    @Override
//    public void onPluginClicked(IPluginModule pluginModule, int position) {
//        super.onPluginClicked(pluginModule, position);
//        Log.e("onPluginToggleClick",v.getVisibility()+",,,,"+position);
//    }


    public class MessageConversationAdapter extends CustomMessageListAdapter {
        public MessageConversationAdapter(Context context) {
            super(context);

            RongContext.getInstance().setConversationClickListener(new RongIM.ConversationClickListener() {
                @Override
                public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String s) {

                    //跳转到个人中心页面
                    String userId = userInfo.getUserId();
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", userId + "");
                    ToastShow.s("跳转个人主页"+userId);
//                    Tools.jump(getActivity(), HomeageActivity.class, bundle, false);
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
                final CustomMessageListAdapter.ViewHolder holder = (CustomMessageListAdapter.ViewHolder)v.getTag();
                TextView viewById = holder.contentView.findViewById(android.R.id.text1);
                if (viewById != null){
                    if (data.getMessageDirection() == Message.MessageDirection.SEND) {
                        viewById.setTextColor(Color.parseColor("#000000"));
                    }else {
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


    public interface listenerExtension
    {
        public void onExtensionCollapsed();
        public void onExtensionExpanded();
    }
}
