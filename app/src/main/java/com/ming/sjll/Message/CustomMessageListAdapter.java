package com.ming.sjll.Message;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import com.ming.sjll.Message.item.ConfirmCooperationMessageContent;
import com.ming.sjll.Message.item.CooperationLookMessageContent;
import com.ming.sjll.Message.item.CooperationMessageContent;
import com.ming.sjll.Message.item.ShareWorkMessageContent;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import io.rong.common.RLog;
import io.rong.eventbus.EventBus;
import io.rong.imkit.RongContext;
import io.rong.imkit.R.bool;
import io.rong.imkit.R.drawable;
import io.rong.imkit.R.id;
import io.rong.imkit.R.integer;
import io.rong.imkit.R.layout;
import io.rong.imkit.R.string;
import io.rong.imkit.RongIM.ConversationBehaviorListener;
import io.rong.imkit.RongIM.ConversationClickListener;
import io.rong.imkit.destruct.DestructManager;
import io.rong.imkit.dialog.BurnHintDialog;
import io.rong.imkit.mention.RongMentionManager;
import io.rong.imkit.model.ConversationKey;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.model.Event.ShowDurnDialogEvent;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imkit.utilities.RongUtils;
import io.rong.imkit.utils.RongDateUtils;
import io.rong.imkit.widget.AsyncImageView;
import io.rong.imkit.widget.DebouncedOnClickListener;
import io.rong.imkit.widget.ProviderContainerView;
import io.rong.imkit.widget.adapter.BaseAdapter;
import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imkit.widget.provider.IContainerItemProvider.MessageProvider;
import io.rong.imkit.widget.provider.InfoNotificationMsgItemProvider;
import io.rong.imkit.widget.provider.VoiceMessageItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.RongIMClient.ErrorCode;
import io.rong.imlib.RongIMClient.OperationCallback;
import io.rong.imlib.destruct.DestructionTaskManager;
import io.rong.imlib.location.message.RealTimeLocationJoinMessage;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.PublicServiceProfile;
import io.rong.imlib.model.ReadReceiptInfo;
import io.rong.imlib.model.UnknownMessage;
import io.rong.imlib.model.UserInfo;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.Message.MessageDirection;
import io.rong.imlib.model.Message.SentStatus;
import io.rong.message.FileMessage;
import io.rong.message.GroupNotificationMessage;
import io.rong.message.HistoryDividerMessage;
import io.rong.message.ImageMessage;
import io.rong.message.InformationNotificationMessage;
import io.rong.message.LocationMessage;
import io.rong.message.RecallNotificationMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomMessageListAdapter extends MessageListAdapter {
    private static final String TAG = "MessageListAdapter";
    private static long readReceiptRequestInterval = 120L;
    private LayoutInflater mInflater;
    private Context mContext;
    private CustomMessageListAdapter.OnItemHandlerListener mOnItemHandlerListener;
    boolean evaForRobot = false;
    boolean robotMode = true;
    protected boolean timeGone = false;
    private boolean isShowCheckbox;
    private int maxMessageSelectedCount = -1;
    private CustomMessageListAdapter.OnMessageCheckedChanged messageCheckedChanged;
    private CustomMessageListAdapter.OnSelectedCountDidExceed selectedCountDidExceed;

    public CustomMessageListAdapter(Context context) {
        super(context);
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);

        try {
            if (RongContext.getInstance() != null) {
                readReceiptRequestInterval = (long)RongContext.getInstance().getResources().getInteger(integer.rc_read_receipt_request_interval);
            } else {
                RLog.e("MessageListAdapter", "SDK isn't init, use default readReceiptRequestInterval. Please refer to http://support.rongcloud.cn/kb/Mjc2 about how to init.");
            }
        } catch (NotFoundException var3) {
            RLog.e("MessageListAdapter", "MessageListAdapter rc_read_receipt_request_interval not configure in rc_config.xml", var3);
        }

    }

    public void setMaxMessageSelectedCount(int maxMessageSelectedCount) {
        this.maxMessageSelectedCount = maxMessageSelectedCount;
    }

    public void setSelectedCountDidExceed(CustomMessageListAdapter.OnSelectedCountDidExceed selectedCountDidExceed) {
        this.selectedCountDidExceed = selectedCountDidExceed;
    }

    public void setOnItemHandlerListener(CustomMessageListAdapter.OnItemHandlerListener onItemHandlerListener) {
        this.mOnItemHandlerListener = onItemHandlerListener;
    }



    public boolean isShowCheckbox() {
        return this.isShowCheckbox;
    }

    public void setShowCheckbox(boolean showCheckbox) {
        this.isShowCheckbox = showCheckbox;
    }

    public void setMessageCheckedChanged(CustomMessageListAdapter.OnMessageCheckedChanged messageCheckedChanged) {
        this.messageCheckedChanged = messageCheckedChanged;
    }

    public long getItemId(int position) {
        UIMessage message = (UIMessage)this.getItem(position);
        return message == null ? -1L : (long)message.getMessageId();
    }

    public int getPositionBySendTime(long sentTime) {
        for(int i = 0; i < this.getCount(); ++i) {
            UIMessage message = (UIMessage)this.getItem(i);
            if (message.getSentTime() > sentTime) {
                return i;
            }
        }

        return this.getCount();
    }

    protected View newView(Context context, int position, ViewGroup group) {
        View result = this.mInflater.inflate(layout.rc_item_message, (ViewGroup)null);
        CustomMessageListAdapter.ViewHolder holder = new CustomMessageListAdapter.ViewHolder();
        holder.leftIconView = (AsyncImageView)this.findViewById(result, id.rc_left);
        holder.rightIconView = (AsyncImageView)this.findViewById(result, id.rc_right);
        holder.nameView = (TextView)this.findViewById(result, id.rc_title);
        holder.right_nameView = (TextView)this.findViewById(result, R.id.right_rc_title);

        holder.contentView = (ProviderContainerView)this.findViewById(result, id.rc_content);
        holder.layout = (ViewGroup)this.findViewById(result, id.rc_layout);
        holder.progressBar = (ProgressBar)this.findViewById(result, id.rc_progress);
        holder.warning = (ImageView)this.findViewById(result, id.rc_warning);
        holder.readReceipt = (TextView)this.findViewById(result, id.rc_read_receipt);
        holder.readReceiptRequest = (TextView)this.findViewById(result, id.rc_read_receipt_request);
        holder.readReceiptStatus = (TextView)this.findViewById(result, id.rc_read_receipt_status);
        holder.message_check = (CheckBox)this.findViewById(result, id.message_check);
        holder.checkboxLayout = (LinearLayout)this.findViewById(result, id.ll_message_check);
        holder.time = (TextView)this.findViewById(result, id.rc_time);
        holder.sentStatus = (TextView)this.findViewById(result, id.rc_sent_status);
        holder.layoutItem = (RelativeLayout)this.findViewById(result, id.rc_layout_item_message);


        holder.left_item_bg  = (LinearLayout) this.findViewById(result, R.id.left_item_bg);
        holder.left_item_img  = (ImageView) this.findViewById(result, R.id.left_item_img);
        holder.right_item_bg  = (LinearLayout) this.findViewById(result, R.id.right_item_bg );
        holder.right_item_img  = (ImageView) this.findViewById(result, R.id.right_item_img );
        holder.left_shadow  = (ImageView) this.findViewById(result, R.id.left_shadow );
        holder.right_shadow  = (ImageView) this.findViewById(result, R.id.right_shadow );

        holder.rc_content_wrap  = (RelativeLayout) this.findViewById(result, R.id.rc_content_wrap );
        holder.rc_content_bg = (AsyncImageView) result.findViewById( R.id.rc_content_bg );

        if (holder.time.getVisibility() == View.INVISIBLE) {
            this.timeGone = true;
        } else {
            this.timeGone = false;
        }

        result.setTag(holder);
        return result;
    }

    protected boolean getNeedEvaluate(UIMessage data) {
        String extra = "";
        String robotEva = "";
        String sid = "";
        if (data != null && data.getConversationType() != null && data.getConversationType().equals(ConversationType.CUSTOMER_SERVICE)) {
            if (data.getContent() instanceof TextMessage) {
                extra = ((TextMessage)data.getContent()).getExtra();
                if (TextUtils.isEmpty(extra)) {
                    return false;
                }

                try {
                    JSONObject jsonObj = new JSONObject(extra);
                    robotEva = jsonObj.optString("robotEva");
                    sid = jsonObj.optString("sid");
                } catch (JSONException var6) {
                }
            }

            if (data.getMessageDirection() == MessageDirection.RECEIVE && data.getContent() instanceof TextMessage && this.evaForRobot && this.robotMode && !TextUtils.isEmpty(robotEva) && !TextUtils.isEmpty(sid) && !data.getIsHistoryMessage()) {
                return true;
            }
        }

        return false;
    }

    public List<Message> getCheckedMessage() {
        List<Message> checkedMessage = new ArrayList();

        for(int i = 0; i < this.getCount(); ++i) {
            UIMessage uiMessage = (UIMessage)this.getItem(i);
            if (uiMessage.isChecked()) {
                checkedMessage.add(uiMessage.getMessage());
            }
        }

        return checkedMessage;
    }

    private void bindViewClickEvent(View convertView, View contentView, final int position, final UIMessage data) {
        final CustomMessageListAdapter.ViewHolder holder = (CustomMessageListAdapter.ViewHolder)convertView.getTag();
        OnClickListener viewClickListener = new OnClickListener() {
            public void onClick(View v) {
                if (CustomMessageListAdapter.this.isShowCheckbox()) {
                    boolean checked = !data.isChecked();
                    if (CustomMessageListAdapter.this.maxMessageSelectedCount != -1 && CustomMessageListAdapter.this.getCheckedMessage().size() >= CustomMessageListAdapter.this.maxMessageSelectedCount && checked) {
                        if (CustomMessageListAdapter.this.selectedCountDidExceed != null) {
                            CustomMessageListAdapter.this.selectedCountDidExceed.onSelectedCountDidExceed();
                        }

                        return;
                    }

                    data.setChecked(checked);
                    holder.message_check.setChecked(checked);
                    if (CustomMessageListAdapter.this.messageCheckedChanged != null) {
                        CustomMessageListAdapter.this.messageCheckedChanged.onCheckedEnable(CustomMessageListAdapter.this.getCheckedMessage().size() > 0);
                    }
                }

            }
        };
        OnTouchListener viewTouchListener = new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (CustomMessageListAdapter.this.isShowCheckbox() && event.getAction() == 1) {
                    boolean checked = !data.isChecked();
                    if (CustomMessageListAdapter.this.maxMessageSelectedCount != -1 && CustomMessageListAdapter.this.getCheckedMessage().size() >= CustomMessageListAdapter.this.maxMessageSelectedCount && checked) {
                        if (CustomMessageListAdapter.this.selectedCountDidExceed != null) {
                            CustomMessageListAdapter.this.selectedCountDidExceed.onSelectedCountDidExceed();
                        }

                        return true;
                    } else {
                        data.setChecked(checked);
                        holder.message_check.setChecked(checked);
                        if (CustomMessageListAdapter.this.messageCheckedChanged != null) {
                            CustomMessageListAdapter.this.messageCheckedChanged.onCheckedEnable(CustomMessageListAdapter.this.getCheckedMessage().size() > 0);
                        }

                        return true;
                    }
                } else {
                    return false;
                }
            }
        };
        OnClickListener contentClickListener = new CustomMessageListAdapter.NoDoubleClickListener() {
            public void onNoDoubleClick(View v) {
                if (RongContext.getInstance().getConversationBehaviorListener() != null) {
                    if (RongContext.getInstance().getConversationBehaviorListener().onMessageClick(CustomMessageListAdapter.this.mContext, v, data.getMessage())) {
                        return;
                    }
                } else if (RongContext.getInstance().getConversationClickListener() != null && RongContext.getInstance().getConversationClickListener().onMessageClick(CustomMessageListAdapter.this.mContext, v, data.getMessage())) {
                    return;
                }

                if (CustomMessageListAdapter.this.mOnItemHandlerListener != null) {
                    CustomMessageListAdapter.this.mOnItemHandlerListener.onMessageClick(position, data.getMessage(), v);
                }

                Object provider;
                if (CustomMessageListAdapter.this.getNeedEvaluate(data)) {
                    provider = RongContext.getInstance().getEvaluateProvider();
                } else {
                    provider = RongContext.getInstance().getMessageTemplate(data.getContent().getClass());
                }

                if (provider != null) {
                    if (data != null && data.getContent() != null && data.getContent().isDestruct() && data.getMessageDirection().equals(MessageDirection.RECEIVE) && !BurnHintDialog.isFirstClick(v.getContext())) {
                        EventBus.getDefault().post(new ShowDurnDialogEvent());
                        return;
                    }

                    ((MessageProvider)provider).onItemClick(v, position, data != null ? data.getContent() : null, data);
                }

            }

            public void onClick(View v) {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - this.lastClickTime > 500L) {
                    this.lastClickTime = currentTime;
                    this.onNoDoubleClick(v);
                }

            }
        };
        OnLongClickListener contentLongClickListener = new OnLongClickListener() {
            public boolean onLongClick(View v) {
                if (CustomMessageListAdapter.this.isShowCheckbox()) {
                    return true;
                } else {
                    if (RongContext.getInstance().getConversationBehaviorListener() != null) {
                        if (RongContext.getInstance().getConversationBehaviorListener().onMessageLongClick(CustomMessageListAdapter.this.mContext, v, data.getMessage())) {
                            return true;
                        }
                    } else if (RongContext.getInstance().getConversationClickListener() != null && RongContext.getInstance().getConversationClickListener().onMessageLongClick(CustomMessageListAdapter.this.mContext, v, data.getMessage())) {
                        return true;
                    }

                    Object provider;
                    if (CustomMessageListAdapter.this.getNeedEvaluate(data)) {
                        provider = RongContext.getInstance().getEvaluateProvider();
                    } else {
                        provider = RongContext.getInstance().getMessageTemplate(data.getContent().getClass());
                    }

                    if (provider != null) {
                        ((MessageProvider)provider).onItemLongClick(v, position, data.getContent(), data);
                    }

                    return true;
                }
            }
        };
        OnClickListener iconClickListener = new OnClickListener() {
            public void onClick(View v) {
                UserInfo userInfo = data.getUserInfo();
                if (!TextUtils.isEmpty(data.getSenderUserId())) {
                    if (userInfo == null) {
                        userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                    }

                    userInfo = userInfo == null ? new UserInfo(data.getSenderUserId(), (String)null, (Uri)null) : userInfo;
                }

                if (RongContext.getInstance().getConversationBehaviorListener() != null) {
                    RongContext.getInstance().getConversationBehaviorListener().onUserPortraitClick(CustomMessageListAdapter.this.mContext, data.getConversationType(), userInfo);
                } else if (RongContext.getInstance().getConversationClickListener() != null) {
                    RongContext.getInstance().getConversationClickListener().onUserPortraitClick(CustomMessageListAdapter.this.mContext, data.getConversationType(), userInfo, data.getTargetId());
                }

            }
        };
        OnLongClickListener iconLongClickListener = new OnLongClickListener() {
            public boolean onLongClick(View v) {
                UserInfo userInfo = data.getUserInfo();
                if (!TextUtils.isEmpty(data.getSenderUserId())) {
                    if (userInfo == null) {
                        userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                    }

                    userInfo = userInfo == null ? new UserInfo(data.getSenderUserId(), (String)null, (Uri)null) : userInfo;
                }

                if (data.getConversationType().equals(ConversationType.GROUP)) {
                    GroupUserInfo groupUserInfo = RongUserInfoManager.getInstance().getGroupUserInfo(data.getTargetId(), data.getSenderUserId());
                    if (groupUserInfo != null && !TextUtils.isEmpty(groupUserInfo.getNickname()) && userInfo != null) {
                        userInfo.setName(groupUserInfo.getNickname());
                    }
                }

                if (data.getMessageDirection().equals(MessageDirection.SEND)) {
                    if (RongContext.getInstance().getConversationBehaviorListener() != null) {
                        return RongContext.getInstance().getConversationBehaviorListener().onUserPortraitLongClick(CustomMessageListAdapter.this.mContext, data.getConversationType(), userInfo);
                    }

                    if (RongContext.getInstance().getConversationClickListener() != null) {
                        return RongContext.getInstance().getConversationClickListener().onUserPortraitLongClick(CustomMessageListAdapter.this.mContext, data.getConversationType(), userInfo, data.getTargetId());
                    }
                } else {
                    Object conversationListener = RongContext.getInstance().getConversationListener();
                    if (conversationListener != null && (conversationListener instanceof ConversationClickListener && ((ConversationClickListener)conversationListener).onUserPortraitLongClick(CustomMessageListAdapter.this.mContext, data.getConversationType(), userInfo, data.getTargetId()) || conversationListener instanceof ConversationBehaviorListener && ((ConversationBehaviorListener)conversationListener).onUserPortraitLongClick(CustomMessageListAdapter.this.mContext, data.getConversationType(), userInfo))) {
                        return true;
                    }

                    if (RongContext.getInstance().getResources().getBoolean(bool.rc_enable_mentioned_message) && (data.getConversationType().equals(ConversationType.GROUP) || data.getConversationType().equals(ConversationType.DISCUSSION))) {
                        RongMentionManager.getInstance().mentionMember(data.getConversationType(), data.getTargetId(), data.getSenderUserId());
                        return true;
                    }
                }

                return true;
            }
        };
        if (this.isShowCheckbox() && this.allowShowCheckButton(data.getMessage())) {
            convertView.setOnClickListener(viewClickListener);
            contentView.setOnTouchListener(viewTouchListener);
            holder.rightIconView.setOnClickListener(viewClickListener);
            holder.leftIconView.setOnClickListener(viewClickListener);
        } else {
            contentView.setOnClickListener(contentClickListener);
            contentView.setOnLongClickListener(contentLongClickListener);
            holder.rightIconView.setOnClickListener(iconClickListener);
            holder.leftIconView.setOnClickListener(iconClickListener);
            holder.rightIconView.setOnLongClickListener(iconLongClickListener);
            holder.leftIconView.setOnLongClickListener(iconLongClickListener);
        }

        holder.warning.setOnClickListener(new DebouncedOnClickListener() {
            public void onDebouncedClick(View view) {
                if (CustomMessageListAdapter.this.getItemHandlerListener() != null) {
                    CustomMessageListAdapter.this.getItemHandlerListener().onWarningViewClick(position, data.getMessage(), view);
                }

            }
        });
    }

    protected void bindView(View v, int position, final UIMessage data) {
        if (data != null) {
            final CustomMessageListAdapter.ViewHolder holder = (CustomMessageListAdapter.ViewHolder)v.getTag();
            if (holder == null) {
                RLog.e("MessageListAdapter", "view holder is null !");
            } else {
                Object provider;
                ProviderTag tag;
                if (this.getNeedEvaluate(data)) {
                    provider = RongContext.getInstance().getEvaluateProvider();
                    tag = RongContext.getInstance().getMessageProviderTag(data.getContent().getClass());
                } else {
                    if (RongContext.getInstance() == null || data.getContent() == null) {
                        RLog.e("MessageListAdapter", "Message is null !");
                        return;
                    }

                    provider = RongContext.getInstance().getMessageTemplate(data.getContent().getClass());
                    if (provider == null) {
                        provider = RongContext.getInstance().getMessageTemplate(UnknownMessage.class);
                        tag = RongContext.getInstance().getMessageProviderTag(UnknownMessage.class);
                    } else {
                        tag = RongContext.getInstance().getMessageProviderTag(data.getContent().getClass());
                    }

                    if (provider == null) {
                        RLog.e("MessageListAdapter", data.getObjectName() + " message provider not found !");
                        return;
                    }
                }

                if (data.getContent() != null && data.getContent().isDestruct() && data.getMessage() != null && data.getMessage().getReadTime() > 0L && data.getMessage().getContent() != null) {
                    if (data.getMessageDirection() == MessageDirection.SEND) {
                        Log.d("delete", data.getObjectName());
                        DestructManager.getInstance().deleteMessage(data.getMessage());
                        this.remove(position);
                        this.notifyDataSetChanged();
                        return;
                    }

                    long readTime = data.getMessage().getReadTime();
                    long serverTime = System.currentTimeMillis() - RongIMClient.getInstance().getDeltaTime();
                    MessageContent messageContent = data.getMessage().getContent();
                    long delay = messageContent.getDestructTime() - (serverTime - readTime) / 1000L;
                    Log.d("delete delay", delay + "s");
                    if (delay <= 0L) {
                        Log.d("delete", data.getObjectName());
                        DestructionTaskManager.getInstance().deleteMessage(data.getMessage());
                        this.remove(position);
                        this.notifyDataSetChanged();
                        return;
                    }
                }

                 View v1 = null;

                try {
                    v1 = holder.contentView.inflate((IContainerItemProvider)provider);
                } catch (Exception var15) {
                    RLog.e("MessageListAdapter", "bindView contentView inflate error", var15);
                    provider = RongContext.getInstance().getMessageTemplate(UnknownMessage.class);
                    tag = RongContext.getInstance().getMessageProviderTag(UnknownMessage.class);
                    v1 = holder.contentView.inflate((IContainerItemProvider)provider);
                }
                final  View v2 = v1;

                ((IContainerItemProvider)provider).bindView(v1, position, data);
                if (tag == null) {
                    RLog.e("MessageListAdapter", "Can not find ProviderTag for " + data.getObjectName());
                } else {
                    if (tag.hide()) {
                        holder.contentView.setVisibility(View.GONE);
                        holder.time.setVisibility(View.INVISIBLE);
                        holder.nameView.setVisibility(View.GONE);
                        holder.right_nameView.setVisibility(View.GONE);

                        holder.leftIconView.setVisibility(View.GONE);
                        holder.rightIconView.setVisibility(View.GONE);
                        holder.layoutItem.setVisibility(View.GONE);
                        holder.layoutItem.setPadding(0, 0, 0, 0);
                    } else {
                        holder.contentView.setVisibility(View.VISIBLE);
                        holder.layoutItem.setVisibility(View.VISIBLE);
                        holder.layoutItem.setPadding(RongUtils.dip2px(8.0F), RongUtils.dip2px(6.0F), RongUtils.dip2px(8.0F), RongUtils.dip2px(6.0F));
                    }
                    UserInfo userInfo;
                    GroupUserInfo groupUserInfo;

//                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.frameLayout_content.getLayoutParams();
//我是发送人/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    if (data.getMessageDirection() == MessageDirection.SEND) {

//                        params.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.TRUE);
//                        holder.frameLayout_content.setLayoutParams(params);




                        if (tag.showPortrait()) {
                            holder.rightIconView.setVisibility(View.VISIBLE);
                            holder.leftIconView.setVisibility(View.GONE);
                        } else {
                            holder.leftIconView.setVisibility(View.GONE);
                            holder.rightIconView.setVisibility(View.GONE);
                        }

                        if (!tag.centerInHorizontal()) {
                            this.setGravity(holder.layout, 5);
                            holder.contentView.containerViewRight();
//                            LayoutParams contentViewParams = (LayoutParams)holder.contentView.getLayoutParams();
//                            holder.linearLayout_content.setGravity(5);

//                            holder.nameView.setGravity(5);
//                            this.setGravity(holder.nameView,5);
                        } else {
                            this.setGravity(holder.layout, 17);
                            holder.contentView.containerViewCenter();
                            holder.nameView.setGravity(1);
                            holder.contentView.setBackgroundColor(View.VISIBLE);
                        }
                        RelativeLayout.LayoutParams contentViewParams = (RelativeLayout.LayoutParams)holder.contentView.getLayoutParams();
                        contentViewParams.rightMargin = RongUtils.dip2px(51.0F);
                        contentViewParams.leftMargin = RongUtils.dip2px(30.0F);
//                        if(contentViewParams.rightMargin <  contentViewParams.leftMargin)
//                        {
//                            int r = contentViewParams.rightMargin;
//                            contentViewParams.rightMargin = contentViewParams.leftMargin;
//                            contentViewParams.leftMargin = r;
//                            holder.contentView.setLayoutParams(contentViewParams);
//                        }

                        boolean readRec = false;

                        try {
                            readRec = this.mContext.getResources().getBoolean(bool.rc_read_receipt);
                        } catch (NotFoundException var14) {
                            RLog.e("MessageListAdapter", "bindView rc_read_receipt not configure in rc_config.xml", var14);
                        }

                        if (data.getSentStatus() == SentStatus.SENDING) {
                            if (tag.showProgress()) {
                                holder.progressBar.setVisibility(View.VISIBLE);
                            } else {
                                holder.progressBar.setVisibility(View.GONE);
                            }

                            holder.warning.setVisibility(View.GONE);
                            holder.readReceipt.setVisibility(View.GONE);
                        } else if (data.getSentStatus() == SentStatus.FAILED) {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.warning.setVisibility(View.VISIBLE);
                            holder.readReceipt.setVisibility(View.GONE);
                        } else if (data.getSentStatus() == SentStatus.SENT) {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.warning.setVisibility(View.GONE);
                            holder.readReceipt.setVisibility(View.GONE);
                        } else if (readRec && data.getSentStatus() == SentStatus.READ) {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.warning.setVisibility(View.GONE);
                            if (data.getConversationType().equals(ConversationType.PRIVATE) && tag.showReadState()) {
                                holder.readReceipt.setVisibility(View.VISIBLE);
                            } else {
                                holder.readReceipt.setVisibility(View.GONE);
                            }
                        } else {
                            holder.progressBar.setVisibility(View.GONE);
                            holder.warning.setVisibility(View.GONE);
                            holder.readReceipt.setVisibility(View.GONE);
                        }

                        holder.readReceiptRequest.setVisibility(View.GONE);
                        holder.readReceiptStatus.setVisibility(View.GONE);
                        if (readRec && RongContext.getInstance().isReadReceiptConversationType(data.getConversationType()) && (data.getConversationType().equals(ConversationType.GROUP) || data.getConversationType().equals(ConversationType.DISCUSSION))) {
                            if (this.allowReadReceiptRequest(data.getMessage()) && !TextUtils.isEmpty(data.getUId())) {
                                boolean isLastSentMessage = true;

                                for(int i = position + 1; i < this.getCount(); ++i) {
                                    if (((UIMessage)this.getItem(i)).getMessageDirection() == MessageDirection.SEND) {
                                        isLastSentMessage = false;
                                        break;
                                    }
                                }

                                long serverTime = System.currentTimeMillis() - RongIMClient.getInstance().getDeltaTime();
                                if (serverTime - data.getSentTime() < readReceiptRequestInterval * 1000L && isLastSentMessage && (data.getReadReceiptInfo() == null || !data.getReadReceiptInfo().isReadReceiptMessage())) {
                                    holder.readReceiptRequest.setVisibility(View.VISIBLE);
                                }
                            }

                            if (this.allowReadReceiptRequest(data.getMessage()) && data.getReadReceiptInfo() != null && data.getReadReceiptInfo().isReadReceiptMessage()) {
                                if (data.getReadReceiptInfo().getRespondUserIdList() != null) {
                                    holder.readReceiptStatus.setText(String.format(v1.getResources().getString(string.rc_read_receipt_status), data.getReadReceiptInfo().getRespondUserIdList().size()));
                                } else {
                                    holder.readReceiptStatus.setText(String.format(v1.getResources().getString(string.rc_read_receipt_status), 0));
                                }

                                holder.readReceiptStatus.setVisibility(View.VISIBLE);
                            }
                        }

                        holder.nameView.setVisibility(View.VISIBLE);
                        holder.right_nameView.setVisibility(View.VISIBLE);
                        if ( tag.showSummaryWithName() && data.getConversationType() != ConversationType.PUBLIC_SERVICE && data.getConversationType() != ConversationType.APP_PUBLIC_SERVICE) {
                            userInfo = data.getUserInfo();
                            if (data.getConversationType().equals(ConversationType.CUSTOMER_SERVICE) && data.getMessageDirection().equals(MessageDirection.RECEIVE)) {
                                if (userInfo == null && data.getMessage() != null && data.getMessage().getContent() != null) {
                                    userInfo = data.getMessage().getContent().getUserInfo();
                                }

                                if (userInfo != null) {
                                    holder.nameView.setText(userInfo.getName());
                                } else {
                                    holder.nameView.setText(data.getSenderUserId());
                                }
                            } else if (data.getConversationType() == ConversationType.GROUP ) {
                                groupUserInfo = RongUserInfoManager.getInstance().getGroupUserInfo(data.getTargetId(), data.getSenderUserId());
                                if (groupUserInfo != null && !TextUtils.isEmpty(groupUserInfo.getNickname())) {
                                    holder.nameView.setText(groupUserInfo.getNickname());
                                } else {
                                    if (userInfo == null) {
                                        userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                    }

                                    if (userInfo == null) {
                                        holder.nameView.setText(data.getSenderUserId());
                                    } else {
                                        holder.nameView.setText(userInfo.getName());
                                    }
                                }
                            } else {
                                if (userInfo == null) {
                                    userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                }

                                if (userInfo == null) {
                                    holder.nameView.setText(data.getSenderUserId());
                                } else {
                                    holder.nameView.setText(userInfo.getName());
                                }
                            }
                        } else {
                            holder.nameView.setVisibility(View.GONE);
                            holder.right_nameView.setVisibility(View.GONE);
                        }
                        holder.right_nameView.setText( holder.nameView.getText());
                        holder.right_nameView.setVisibility(View.GONE);

                        holder.readReceiptRequest.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                RongIMClient.getInstance().sendReadReceiptRequest(data.getMessage(), new OperationCallback() {
                                    public void onSuccess() {
                                        ReadReceiptInfo readReceiptInfo = data.getReadReceiptInfo();
                                        if (readReceiptInfo == null) {
                                            readReceiptInfo = new ReadReceiptInfo();
                                            data.setReadReceiptInfo(readReceiptInfo);
                                        }

                                        readReceiptInfo.setIsReadReceiptMessage(true);
                                        holder.readReceiptStatus.setText(String.format(v2.getResources().getString(string.rc_read_receipt_status), 0));
                                        holder.readReceiptRequest.setVisibility(View.GONE);
                                        holder.readReceiptStatus.setVisibility(View.VISIBLE);
                                    }

                                    public void onError(ErrorCode errorCode) {
                                        RLog.e("MessageListAdapter", "sendReadReceiptRequest failed, errorCode = " + errorCode);
                                    }
                                });
                            }
                        });
                        holder.readReceiptStatus.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) {
                                if (CustomMessageListAdapter.this.mOnItemHandlerListener != null) {
                                    CustomMessageListAdapter.this.mOnItemHandlerListener.onReadReceiptStateClick(data.getMessage());
                                }

                            }
                        });
                        if (!tag.showWarning()) {
                            holder.warning.setVisibility(View.GONE);
                        }
                    }
//我是接受人/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    else {

//                        params.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.TRUE);
//                        holder.frameLayout_content.setLayoutParams(params);

                        if (tag.showPortrait()) {
                            holder.rightIconView.setVisibility(View.GONE);
                            holder.leftIconView.setVisibility(View.VISIBLE);
                        } else {
                            holder.leftIconView.setVisibility(View.GONE);
                            holder.rightIconView.setVisibility(View.GONE);
                        }

                        if (!tag.centerInHorizontal()) {
                            this.setGravity(holder.layout, 3);
                            holder.contentView.containerViewLeft();
//                            holder.nameView.setGravity(3);
//                            this.setGravity(holder.nameView,3);
                        } else {
                            this.setGravity(holder.layout, 17);
                            holder.contentView.containerViewCenter();
                            holder.nameView.setGravity(1);
                            holder.contentView.setBackgroundColor(View.VISIBLE);
                        }
                        RelativeLayout.LayoutParams contentViewParams = (RelativeLayout.LayoutParams)holder.contentView.getLayoutParams();
                        contentViewParams.leftMargin  = RongUtils.dip2px(51.0F);
                        contentViewParams.rightMargin= RongUtils.dip2px(30.0F);
                        holder.contentView.setLayoutParams(contentViewParams);
//                        if(contentViewParams.rightMargin >  contentViewParams.leftMargin)
//                        {
//                            int r = contentViewParams.rightMargin;
//                            contentViewParams.rightMargin = contentViewParams.leftMargin;
//                            contentViewParams.leftMargin = r;
//                            holder.contentView.setLayoutParams(contentViewParams);
//                        }

                        holder.progressBar.setVisibility(View.GONE);
                        holder.warning.setVisibility(View.GONE);
                        holder.readReceipt.setVisibility(View.GONE);
                        holder.readReceiptRequest.setVisibility(View.GONE);
                        holder.readReceiptStatus.setVisibility(View.GONE);
                        holder.nameView.setVisibility(View.VISIBLE);
                        holder.right_nameView.setVisibility(View.VISIBLE);
                        if ( tag.showSummaryWithName() && data.getConversationType() != ConversationType.PUBLIC_SERVICE && data.getConversationType() != ConversationType.APP_PUBLIC_SERVICE) {
                            userInfo = data.getUserInfo();
                            if (data.getConversationType().equals(ConversationType.CUSTOMER_SERVICE) && data.getMessageDirection().equals(MessageDirection.RECEIVE)) {
                                if (userInfo == null && data.getMessage() != null && data.getMessage().getContent() != null) {
                                    userInfo = data.getMessage().getContent().getUserInfo();
                                }

                                if (userInfo != null) {
                                    holder.nameView.setText(userInfo.getName());
                                } else {
                                    holder.nameView.setText(data.getSenderUserId());
                                }
                            } else if (data.getConversationType() == ConversationType.GROUP) {
                                groupUserInfo = RongUserInfoManager.getInstance().getGroupUserInfo(data.getTargetId(), data.getSenderUserId());
                                if (groupUserInfo != null && !TextUtils.isEmpty(groupUserInfo.getNickname())) {
                                    holder.nameView.setText(groupUserInfo.getNickname());
                                } else {
                                    if (userInfo == null) {
                                        userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                    }

                                    if (userInfo == null) {
                                        holder.nameView.setText(data.getSenderUserId());
                                    } else {
                                        holder.nameView.setText(userInfo.getName());
                                    }
                                }
                            } else {
                                if (userInfo == null) {
                                    userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                                }

                                if (userInfo == null) {
                                    holder.nameView.setText(data.getSenderUserId());
                                } else {
                                    holder.nameView.setText(userInfo.getName());
                                }
                            }
                        } else {
                            holder.nameView.setVisibility(View.GONE);
                            holder.right_nameView.setVisibility(View.GONE);
                        }
                        holder.right_nameView.setText( holder.nameView.getText());
                        holder.nameView.setVisibility(View.GONE);
                    }
//自定义


                    holder.left_item_bg.setVisibility(View.GONE);
                    holder.right_item_bg.setVisibility(View.GONE);
                    holder.rc_content_bg.setVisibility(View.GONE);
                    holder.rc_content_wrap.setBackgroundResource(R.drawable.shape_left_right_60_chat);
//                    holder.rc_content_bg.setLayoutParam(holder.rc_content_wrap.getMeasuredWidthAndState(),holder.rc_content_wrap.getMeasuredHeightAndState());
//                    holder.rc_content_bg.setLayoutParam(holder.rc_content_wrap.getHeight());
//                    LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) holder.rc_content_bg.getLayoutParams();
//                    holder.rc_content_bg.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

                    /**
                     * 是否展示消息类型的图标
                     */
                    boolean showItemBg = false;
                    if(data.getContent() instanceof LocationMessage) {//位置
                        showItemBg = true;
                        holder.right_item_img.setBackgroundResource(R.mipmap.chat_item_location);
                        holder.left_item_img.setBackgroundResource(R.mipmap.chat_item_location);
                    }
                    else if(data.getContent() instanceof ShareWorkMessageContent) {//作品
                        showItemBg = true;
                        holder.right_item_img.setBackgroundResource(R.mipmap.chat_item_work);
                        holder.left_item_img.setBackgroundResource(R.mipmap.chat_item_work);
                    }
                    else if(data.getContent() instanceof InformationNotificationMessage) {
                        holder.rc_content_wrap.setBackground(null);
                    }
                    else if(data.getContent() instanceof VoiceMessage)
                    {
//                        VoiceMessageItemProvider
                    }
                    else if(data.getContent() instanceof CooperationMessageContent )
                    {
                        holder.rc_content_wrap.setBackground(null);
                        CooperationMessageContent myData = (CooperationMessageContent)data.getContent();;
                        holder.rc_content_bg.setAvatar(Constant.BASE_IMAGE +myData.getbgImg(),R.drawable.shape_left_right_60_chat);
                        holder.rc_content_bg.setVisibility(View.VISIBLE);
                    }
                    else if( data.getContent() instanceof CooperationLookMessageContent)
                    {
                        holder.rc_content_wrap.setBackground(null);
                        CooperationLookMessageContent myData = (CooperationLookMessageContent)data.getContent();;
                        holder.rc_content_bg.setAvatar(Constant.BASE_IMAGE +myData.getbgImg(),R.drawable.shape_left_right_60_chat);
                        holder.rc_content_bg.setVisibility(View.VISIBLE);
                    }
                    else if( data.getContent() instanceof ConfirmCooperationMessageContent)
                    {
                        holder.rc_content_wrap.setBackground(null);
                        ConfirmCooperationMessageContent myData = (ConfirmCooperationMessageContent)data.getContent();;
                        holder.rc_content_bg.setAvatar(Constant.BASE_IMAGE +myData.getbgImg(),R.drawable.shape_left_right_60_chat);
                        holder.rc_content_bg.setVisibility(View.VISIBLE);
                    }
                    else if(data.getContent() instanceof FileMessage)//文件
                    {
                        showItemBg = true;
                        holder.right_item_img.setBackgroundResource(R.mipmap.chat_item_file);
                        holder.left_item_img.setBackgroundResource(R.mipmap.chat_item_file);
                    }


                    if(showItemBg)
                    {
                        if(data.getMessageDirection() == MessageDirection.SEND)
                        {
                            holder.right_item_bg.setVisibility(View.VISIBLE);

                        }
                        else
                        {
                            holder.left_item_bg.setVisibility(View.VISIBLE);

                            RelativeLayout.LayoutParams contentViewParams = (RelativeLayout.LayoutParams)holder.contentView.getLayoutParams();
                            contentViewParams.leftMargin  = RongUtils.dip2px(71.0F);
                            contentViewParams.rightMargin= RongUtils.dip2px(30.0F);
                            holder.contentView.setLayoutParams(contentViewParams);
                        }
                    }

                    holder.left_shadow.setVisibility(View.GONE);
                    holder.right_shadow.setVisibility(View.GONE);
                    if(holder.rightIconView.getVisibility() == View.VISIBLE) {
                        holder.right_shadow.setVisibility(View.VISIBLE);
                    }
                    if(holder.leftIconView.getVisibility() == View.VISIBLE) {
                        holder.left_shadow.setVisibility(View.VISIBLE);
                    }



//自定义结束

                    Uri portrait;
                    ConversationKey mKey;
                    PublicServiceProfile publicServiceProfile;
                    if (holder.rightIconView.getVisibility() == View.VISIBLE) {
                        userInfo = data.getUserInfo();
                        portrait = null;
                        if (data.getConversationType().equals(ConversationType.CUSTOMER_SERVICE) && data.getUserInfo() != null && data.getMessageDirection().equals(MessageDirection.RECEIVE)) {
                            if (userInfo != null) {
                                portrait = userInfo.getPortraitUri();
                            }

                            holder.rightIconView.setAvatar(portrait != null ? portrait.toString() : null, 0);
                        } else if ((data.getConversationType().equals(ConversationType.PUBLIC_SERVICE) || data.getConversationType().equals(ConversationType.APP_PUBLIC_SERVICE)) && data.getMessageDirection().equals(MessageDirection.RECEIVE)) {
                            if (userInfo != null) {
                                portrait = userInfo.getPortraitUri();
                                holder.rightIconView.setAvatar(portrait != null ? portrait.toString() : null, 0);
                            } else {
                                mKey = ConversationKey.obtain(data.getTargetId(), data.getConversationType());
                                publicServiceProfile = RongContext.getInstance().getPublicServiceInfoFromCache(mKey.getKey());
                                portrait = publicServiceProfile.getPortraitUri();
                                holder.rightIconView.setAvatar(portrait != null ? portrait.toString() : null, 0);
                            }
                        } else if (!TextUtils.isEmpty(data.getSenderUserId())) {
                            if (userInfo == null) {
                                userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                            }

                            if (userInfo != null && userInfo.getPortraitUri() != null) {
                                holder.rightIconView.setAvatar(userInfo.getPortraitUri().toString(), 0);
                            } else {
                                holder.rightIconView.setAvatar((String)null, 0);
                            }
                        }
                    }
                    else if (holder.leftIconView.getVisibility() == View.VISIBLE) {
                        userInfo = data.getUserInfo();
                        groupUserInfo = null;
                        if (data.getConversationType().equals(ConversationType.CUSTOMER_SERVICE) && data.getMessageDirection().equals(MessageDirection.RECEIVE)) {
                            if (userInfo == null && data.getMessage() != null && data.getMessage().getContent() != null) {
                                userInfo = data.getMessage().getContent().getUserInfo();
                            }

                            if (userInfo != null) {
                                portrait = userInfo.getPortraitUri();
                                holder.leftIconView.setAvatar(portrait != null ? portrait.toString() : null, drawable.rc_cs_default_portrait);
                            }
                        } else if ((data.getConversationType().equals(ConversationType.PUBLIC_SERVICE) || data.getConversationType().equals(ConversationType.APP_PUBLIC_SERVICE)) && data.getMessageDirection().equals(MessageDirection.RECEIVE)) {
                            if (userInfo != null) {
                                portrait = userInfo.getPortraitUri();
                                holder.leftIconView.setAvatar(portrait != null ? portrait.toString() : null, 0);
                            } else {
                                mKey = ConversationKey.obtain(data.getTargetId(), data.getConversationType());
                                publicServiceProfile = RongContext.getInstance().getPublicServiceInfoFromCache(mKey.getKey());
                                if (publicServiceProfile != null && publicServiceProfile.getPortraitUri() != null) {
                                    holder.leftIconView.setAvatar(publicServiceProfile.getPortraitUri().toString(), 0);
                                } else {
                                    holder.leftIconView.setAvatar((String)null, 0);
                                }
                            }
                        } else if (!TextUtils.isEmpty(data.getSenderUserId())) {
                            if (userInfo == null) {
                                userInfo = RongUserInfoManager.getInstance().getUserInfo(data.getSenderUserId());
                            }

                            if (userInfo != null && userInfo.getPortraitUri() != null) {
                                holder.leftIconView.setAvatar(userInfo.getPortraitUri().toString(), 0);
                            } else {
                                holder.leftIconView.setAvatar((String)null, 0);
                            }
                        }
                    }

                    this.bindViewClickEvent(v, v1, position, data);
                    if (tag.hide()) {
                        holder.time.setVisibility(View.INVISIBLE);
                    }
                    else {
                        if (!this.timeGone) {
                            String time = RongDateUtils.getConversationFormatDate(data.getSentTime(), v1.getContext());
                            holder.time.setText(time);
                            if (position == 0) {
                                if (data.getMessage() != null && data.getMessage().getContent() != null && data.getMessage().getContent() instanceof HistoryDividerMessage) {
                                    holder.time.setVisibility(View.INVISIBLE);
                                } else {
                                    holder.time.setVisibility(View.VISIBLE);
                                }
                            } else {
                                UIMessage pre = (UIMessage)this.getItem(position - 1);
                                if (RongDateUtils.isShowChatTime(data.getSentTime(), pre.getSentTime(), 180)) {
                                    holder.time.setVisibility(View.VISIBLE);
                                } else {
                                    holder.time.setVisibility(View.INVISIBLE);
                                }
                            }
                        }

                        if (this.isShowCheckbox() && this.allowShowCheckButton(data.getMessage())) {
                            holder.checkboxLayout.setVisibility(View.VISIBLE);
                            holder.message_check.setFocusable(false);
                            holder.message_check.setClickable(false);
                            holder.message_check.setChecked(data.isChecked());
                        } else {
                            holder.checkboxLayout.setVisibility(View.GONE);
                            data.setChecked(false);
                        }

                        if (this.messageCheckedChanged != null) {
                            this.messageCheckedChanged.onCheckedEnable(this.getCheckedMessage().size() > 0);
                        }

                    }

                    if(holder.rightIconView.getVisibility() == View.VISIBLE)
                    {
//                        holder.defaultLayout.setVisibility(View.GONE);
//                        holder.rightRightIconView = holder.rightIconView;
//                        holder.rightCheckboxLayout = holder.checkboxLayout;
//                        holder.rightMessage_check = holder.message_check;
//                        holder.rightLayout.setVisibility(View.VISIBLE);
                    }
                    else
                    {
//                        holder.rightLayout.setVisibility(View.GONE);
                    }
                }
            }
        }

    }

    protected void setGravity(View view, int gravity) {
            try {

                LayoutParams params = (LayoutParams)view.getLayoutParams();
                params.gravity = gravity;
            }
            catch (Exception e)
            {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
                if(gravity == 3)
                {

                    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
                }
                else if(gravity == 17)
                {
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
                }
                else if(gravity == 5)
                {
                    params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
                }
                view.setLayoutParams(params);
//            view.setLayoutParams();
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
//        params.addRule(, gravity);
//        view.setLayoutParams(params);

            }



//
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
//        params.addRule(gravity, gravity);
//        view.setLayoutParams(params);
    }

    public void setEvaluateForRobot(boolean needEvaluate) {
        this.evaForRobot = needEvaluate;
    }

    public void setRobotMode(boolean robotMode) {
        this.robotMode = robotMode;
    }

    public boolean allowReadReceiptRequest(Message message) {
        return message != null && message.getContent() != null && message.getContent() instanceof TextMessage;
    }

    protected boolean allowShowCheckButton(Message message) {
        if (message != null) {
            MessageContent messageContent = message.getContent();
            if (messageContent != null && (messageContent instanceof InformationNotificationMessage || messageContent instanceof UnknownMessage || messageContent instanceof GroupNotificationMessage || messageContent instanceof RecallNotificationMessage || messageContent instanceof RealTimeLocationJoinMessage)) {
                return false;
            }
        }

        return true;
    }

    private abstract class NoDoubleClickListener implements OnClickListener {
        public static final int MIN_CLICK_DELAY_TIME = 500;
        public long lastClickTime;

        private NoDoubleClickListener() {
            this.lastClickTime = 0L;
        }

        public abstract void onNoDoubleClick(View var1);
    }

    public interface OnItemHandlerListener {
        boolean onWarningViewClick(int var1, Message var2, View var3);

        void onReadReceiptStateClick(Message var1);

        void onMessageClick(int var1, Message var2, View var3);
    }

    public interface OnMessageCheckedChanged {
        void onCheckedEnable(boolean var1);
    }

    public interface OnSelectedCountDidExceed {
        void onSelectedCountDidExceed();
    }

    protected class ViewHolder {
        public AsyncImageView leftIconView;
        public AsyncImageView rightIconView;
        public TextView nameView;
        public TextView right_nameView;
        public ProviderContainerView contentView;
        public ProgressBar progressBar;
        public ImageView warning;
        public TextView readReceipt;
        public TextView readReceiptRequest;
        public TextView readReceiptStatus;
        public ViewGroup layout;
        public TextView time;
        public TextView sentStatus;
        public RelativeLayout layoutItem;
        public CheckBox message_check;
        public LinearLayout checkboxLayout;


        public LinearLayout left_item_bg;
        public ImageView left_item_img;
        public LinearLayout right_item_bg ;
        public ImageView right_item_img ;

        public ImageView right_shadow;
        public ImageView left_shadow;

        public RelativeLayout  rc_content_wrap;


        public AsyncImageView rc_content_bg;
//        public FrameLayout frameLayout_content;
//        public LinearLayout linearLayout_content;

        protected ViewHolder() {
        }
    }
}
