package com.ming.sjll.Message.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.Message.extend.CustomExtensionModule;
import com.ming.sjll.Message.item.ConfirmCooperationMessageContent;
import com.ming.sjll.Message.item.ConfirmCooperationMessageItemProvider;
import com.ming.sjll.Message.item.CooperationApplyMessageContent;
import com.ming.sjll.Message.item.CooperationApplyMessageItemProvider;
import com.ming.sjll.Message.item.CooperationLookMessageContent;
import com.ming.sjll.Message.item.CooperationLookMessageItemProvider;
import com.ming.sjll.Message.item.CooperationMessageContent;
import com.ming.sjll.Message.item.CooperationMessageItemProvider;
import com.ming.sjll.Message.item.CustomLocationMessageItemProvider;
import com.ming.sjll.Message.item.CustomTextMessageItemProvider;
import com.ming.sjll.Message.item.CustomVoiceMessageItemProvider;
import com.ming.sjll.Message.item.ShareWorkMessageContent;
import com.ming.sjll.Message.item.ShareWorkMessageItemProvider;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;

import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.Event;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imkit.widget.provider.LocationMessageItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

public class RongImUtils {

    private static boolean icConnect = false;
    private onChatEven Listener;

    public void init(Application context)
    {
//        RongIMClient.init(context);
        RongIM.init(context);
        connect();
        setMyExtensionModule();

        RongIM.registerMessageType(TextMessage.class);
        RongIM.registerMessageTemplate(new CustomTextMessageItemProvider());

        RongIM.registerMessageType(VoiceMessage.class);
        RongIM.registerMessageTemplate(new CustomVoiceMessageItemProvider(context));

        RongIM.registerMessageType(ConfirmCooperationMessageContent.class);
        RongIM.registerMessageTemplate(new ConfirmCooperationMessageItemProvider());

        RongIM.registerMessageType(CooperationApplyMessageContent.class);
        RongIM.registerMessageTemplate(new CooperationApplyMessageItemProvider());

        RongIM.registerMessageType(CooperationLookMessageContent.class);
        RongIM.registerMessageTemplate(new CooperationLookMessageItemProvider());

        RongIM.registerMessageType(CooperationMessageContent.class);
        RongIM.registerMessageTemplate(new CooperationMessageItemProvider());

        RongIM.registerMessageType(ShareWorkMessageContent.class);
        RongIM.registerMessageTemplate(new ShareWorkMessageItemProvider());

        RongIM.registerMessageTemplate(new CustomLocationMessageItemProvider());
//        setOnReceiveMessageListener();
    }

    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new CustomExtensionModule());
            }
        }
    }

    public void setOnReceiveMessageListener()
    {

        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
            @Override
            public boolean onReceived(Message message, int i, boolean b, boolean b1) {
                // 输出消息类型。
                Log.e("融云Receive:", message.getObjectName());

                // 此处输出判断是否是文字消息，并输出，其他消息同理。
                if (message.getContent() instanceof TextMessage) {
                    final TextMessage textMessage = (TextMessage) message.getContent();
                    Log.e("融云onReceived", "Text Message: " + textMessage.getContent());
                }
                return false;
            }
        });
    }
    public static void connect()
    {
        String token = Cache.CacheValue("rcToken");
        RongIM.connect(token, new RongIMClient.ConnectCallbackEx() {
            /**
             * 数据库回调.
             * @param code 数据库打开状态. DATABASE_OPEN_SUCCESS 数据库打开成功; DATABASE_OPEN_ERROR 数据库打开失败
             */
            @Override
            public void OnDatabaseOpened(RongIMClient.DatabaseOpenStatus code) {
                Log.e("融云数据库回调",code.name()+",,,"+code.toString());
            }
            /**
             * token 无效
             */
            @Override
            public void onTokenIncorrect() {
                Log.e("融云初始化失败","token 无效");
            }
            /**
             * 成功回调
             * @param userId 当前用户 ID
             */
            @Override
            public void onSuccess(String userId) {
                Log.e("融云初始化成功","ID:"+userId);
                icConnect = true;

                RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public UserInfo getUserInfo(String s) {
                        userUpdate(s, false);
                        return null;
                    }
                }, false);

                RongIM.setGroupUserInfoProvider(new RongIM.GroupUserInfoProvider() {
                    @Override
                    public GroupUserInfo getGroupUserInfo(String s, String s1) {
                        userUpdate(s, true);
                        return null;
                    }
                }, false);

            }
            /**
             * 错误回调
             * @param errorCode 错误码
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("融云数据库错误",errorCode.getMessage()+",,,"+errorCode.toString());


            }
        });
    }


    /**
     * 打开单聊
     * @param activity
     * @param targetUserId
     * @param title
     */
    public static void privateChat(Context activity, String targetUserId, String title)
    {
        RongIM.getInstance().startPrivateChat(activity, targetUserId, title);
    }

    /**
     * 打开群聊
     * @param activity
     * @param targetUserId
     * @param title
     */
    public static void groupChat(Context activity,String targetUserId,String title)
    {
        RongIM.getInstance().startGroupChat(activity, targetUserId, title);
    }


    /**
     * 更新用戶信息
     * @param s
     * @param isGroup
     */
    public static void userUpdate(String s, boolean isGroup) {
        String type = isGroup ? "3" : "1";
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("conversationType",type);
        param.setParam("targetId",s);
        new HttpUtil().sendRequest(Constant.RC_GET_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                GetInfoBean bean = new Gson().fromJson(data, GetInfoBean.class);
                if (isGroup) {
                    RongIM.getInstance().refreshGroupInfoCache(new Group(s, bean.getData().getName(), Uri.parse(Constant.BASE_API + bean.getData().getDefault_avatar())));
                } else {
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(s, bean.getData().getName(), Uri.parse(Constant.BASE_API + bean.getData().getDefault_avatar())));
                }
            }

        });
    }

    /**
     * 更新项目信息
     * @param s
     */
    public static void projectUpdate(String s ,TextView progress, TextView project) {
        String type = "3" ;
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("conversationType",type);
        param.setParam("targetId",s);
        progress.setText("");
        project.setText("");
        new HttpUtil().sendRequest(Constant.RC_GET_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                GetInfoBean bean = new Gson().fromJson(data, GetInfoBean.class);
                progress.setText(bean.getData().getStatus());
                project.setText(bean.getData().getDemand());
//                if (isGroup) {
//                    RongIM.getInstance().refreshGroupInfoCache(new Group(s, bean.getData().getName(), Uri.parse(Constant.BASE_API + bean.getData().getDefault_avatar())));
//                } else {
//                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(s, bean.getData().getName(), Uri.parse(Constant.BASE_API + bean.getData().getDefault_avatar())));
//                }
            }

        });
    }

    /**
     * 个人项目邀请：同意，取消   (邀请加入)
     */
    public static void sendConfirmCooperation(String id,boolean status,onChatEven chatListCallBack) {

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("status",status ? "1" : "0");
        param.setParam("apply_id",id);
        new HttpUtil().sendRequest(Constant.INVITEPASS, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                if(chatListCallBack != null)
                {
                    chatListCallBack.onSuccess();
                }
            }

        });
    }

    /**
     * 公司项目邀请：同意，取消   (邀请加入)
     */
    public static void sendConfirmCompanyCooperation(List<String> id,boolean status,onChatEven chatListCallBack) {

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("status",status ? "1" : "0");
        param.setParam("apply_id",id);
        new HttpUtil().sendRequest(Constant.INVITEPASS, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                if(chatListCallBack != null)
                {
                    chatListCallBack.onSuccess();
                }
            }

        });
    }

    /**
     * 刪除消息
     */
    public static void  deleteMessage(int messageId){
        int[] array = new int[] {messageId };
        RongIM.getInstance().deleteMessages(array, new RongIMClient.ResultCallback<Boolean>() {
            public void onSuccess(Boolean bool) {

            }

            public void onError(RongIMClient.ErrorCode e) {

            }
        });
    }





    /**
     *  获取单聊消息列表
     */
    public void getSingleList(onChatEven chatListCallBack)
    {

        // 分页获取会话列表
        // timeStamp 首次传 0, 后续使用真实时间戳
        long timeStamp = 0;
        // 每页数量, 最大为 10 条
        int count = 10;

        RongIMClient.getInstance().getConversationListByPage(new RongIMClient.
                ResultCallback<List<Conversation>>() {
            /**
             * 成功回调
             * @param conversations 获取的会话列表
             */
            @Override
            public void onSuccess(List<Conversation> conversations) {
                Log.e("融云获取消息成功","获取消息个数"+conversations.size());
                if(chatListCallBack != null)
                {
                    conversations.get(0).getUnreadMessageCount();
                    chatListCallBack.onSuccess(conversations);
                }
            }


            /**
             * 错误回调
             * @param ErrorCode 错误码
             */
            @Override
            public void onError(RongIMClient.ErrorCode ErrorCode) {
                Log.e("融云获取单聊列表错误码",ErrorCode.toString()+"："+ErrorCode.getMessage());
                if(Listener != null)
                {
                    Listener.onError(ErrorCode);
                }

            }
        },timeStamp, count, Conversation.ConversationType.PRIVATE);
    }

    public static class onChatEven
    {

        public void onSuccess() {
        }
        public void onSuccess(List<Conversation> conversations) {
        }

        public void onError(RongIMClient.ErrorCode ErrorCode) {

        }
    }


}
