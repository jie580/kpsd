package com.ming.sjll.Message.item;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ming.sjll.R.id;
import com.ming.sjll.R.layout;
import com.ming.sjll.R.drawable;


import io.rong.imkit.R.string;
import io.rong.imkit.R.bool;
import java.lang.ref.WeakReference;

import io.rong.common.RLog;
import io.rong.eventbus.EventBus;
import io.rong.imkit.destruct.DestructManager;
import io.rong.imkit.manager.AudioPlayManager;
import io.rong.imkit.manager.AudioRecordManager;
import io.rong.imkit.manager.IAudioPlayListener;
import io.rong.imkit.model.Event;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imkit.widget.provider.TextMessageItemProvider;
import io.rong.imkit.widget.provider.VoiceMessageItemProvider;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

@ProviderTag( messageContent = VoiceMessage.class ,showReadState = true)
public class CustomVoiceMessageItemProvider extends IContainerItemProvider.MessageProvider<VoiceMessage> {
    private static final String TAG = "CustomVoiceMessageItemProvider";

    public CustomVoiceMessageItemProvider(Context context) {
    }

    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(layout.rc_item_voice_message, (ViewGroup)null);
        CustomVoiceMessageItemProvider.ViewHolder holder = new CustomVoiceMessageItemProvider.ViewHolder();
        holder.left = (TextView)view.findViewById(id.rc_left);
        holder.right = (TextView)view.findViewById(id.rc_right);
        holder.img = (ImageView)view.findViewById(id.rc_img);
        holder.unread = (ImageView)view.findViewById(id.rc_voice_unread);
        holder.sendFire = (FrameLayout)view.findViewById(id.fl_send_fire);
        holder.receiverFire = (FrameLayout)view.findViewById(id.fl_receiver_fire);
        holder.receiverFireImg = (ImageView)view.findViewById(id.iv_receiver_fire);
        holder.receiverFireText = (TextView)view.findViewById(id.tv_receiver_fire);

        holder.voice_warp = (RelativeLayout)view.findViewById(id.voice_warp);

        view.setTag(holder);
        return view;
    }

    public void bindView(View v, int position, VoiceMessage content, UIMessage message) {
        CustomVoiceMessageItemProvider.ViewHolder holder = (CustomVoiceMessageItemProvider.ViewHolder)v.getTag();
        if (content.isDestruct()) {
            if (message.getMessageDirection() == Message.MessageDirection.SEND) {
                holder.sendFire.setVisibility(View.VISIBLE);
                holder.receiverFire.setVisibility(View.GONE);


            } else {
                holder.sendFire.setVisibility(View.GONE);
                holder.receiverFire.setVisibility(View.VISIBLE);

                DestructManager.getInstance().addListener(message.getUId(), new CustomVoiceMessageItemProvider.DestructListener(holder, message), "CustomVoiceMessageItemProvider");
                if (message.getMessage().getReadTime() > 0L) {
                    holder.receiverFireText.setVisibility(View.VISIBLE);
                    holder.receiverFireImg.setVisibility(View.GONE);
                    String unFinishTime;
                    if (TextUtils.isEmpty(message.getUnDestructTime())) {
                        unFinishTime = DestructManager.getInstance().getUnFinishTime(message.getUId());
                    } else {
                        unFinishTime = message.getUnDestructTime();
                    }

                    holder.receiverFireText.setText(unFinishTime);
                    DestructManager.getInstance().startDestruct(message.getMessage());
                } else {
                    holder.receiverFireText.setVisibility(View.GONE);
                    holder.receiverFireImg.setVisibility(View.VISIBLE);
                }
            }
        } else {
            holder.sendFire.setVisibility(View.GONE);
            holder.receiverFire.setVisibility(View.GONE);
        }

        boolean listened;
        Uri playingUri;
        if (message.continuePlayAudio) {
            playingUri = AudioPlayManager.getInstance().getPlayingUri();
            if (playingUri == null || !playingUri.equals(content.getUri())) {
                listened = message.getMessage().getReceivedStatus().isListened();
                AudioPlayManager.getInstance().startPlay(v.getContext(), content.getUri(), new CustomVoiceMessageItemProvider.VoiceMessagePlayListener(v.getContext(), message, holder, listened));
            }
        } else {
            playingUri = AudioPlayManager.getInstance().getPlayingUri();
            if (playingUri != null && playingUri.equals(content.getUri())) {
                this.setLayout(v.getContext(), holder, message, true);
                listened = message.getMessage().getReceivedStatus().isListened();
                AudioPlayManager.getInstance().setPlayListener(new CustomVoiceMessageItemProvider.VoiceMessagePlayListener(v.getContext(), message, holder, listened));
            } else {
                this.setLayout(v.getContext(), holder, message, false);
            }
        }

    }

    public void onItemClick(View view, int position, VoiceMessage content, UIMessage message) {
        RLog.d("CustomVoiceMessageItemProvider", "Item index:" + position);
        if (content != null) {
            CustomVoiceMessageItemProvider.ViewHolder holder = (CustomVoiceMessageItemProvider.ViewHolder)view.getTag();
            if (AudioPlayManager.getInstance().isPlaying()) {
                if (AudioPlayManager.getInstance().getPlayingUri().equals(content.getUri())) {
                    AudioPlayManager.getInstance().stopPlay();
                    return;
                }

                AudioPlayManager.getInstance().stopPlay();
            }

            if (!AudioPlayManager.getInstance().isInNormalMode(view.getContext()) && AudioPlayManager.getInstance().isInVOIPMode(view.getContext())) {
                Toast.makeText(view.getContext(), view.getContext().getString(string.rc_voip_occupying), Toast.LENGTH_SHORT).show();
            } else {
                holder.unread.setVisibility(View.GONE);
                boolean listened = message.getMessage().getReceivedStatus().isListened();
                AudioPlayManager.getInstance().startPlay(view.getContext(), content.getUri(), new CustomVoiceMessageItemProvider.VoiceMessagePlayListener(view.getContext(), message, holder, listened));
            }
        }
    }


    protected void setGravity(View view, int gravity) {
//        try {
//
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view.getLayoutParams();
//            params.gravity = gravity;
//        }
//        catch (Exception e)
//        {
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

//        }



//
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
//        params.addRule(gravity, gravity);
//        view.setLayoutParams(params);
    }


    private void setLayout(Context context, CustomVoiceMessageItemProvider.ViewHolder holder, UIMessage message, boolean playing) {
        VoiceMessage content = (VoiceMessage)message.getContent();
        int minWidth = 70;
        int maxWidth = 204;
        float scale = context.getResources().getDisplayMetrics().density;
        minWidth = (int)((float)minWidth * scale + 0.5F);
        maxWidth = (int)((float)maxWidth * scale + 0.5F);
        int duration = AudioRecordManager.getInstance().getMaxVoiceDuration();
//        holder.img.getLayoutParams().width = minWidth + (maxWidth - minWidth) / duration * content.getDuration();
        AnimationDrawable animationDrawable;
        if (message.getMessageDirection() == Message.MessageDirection.SEND) {

//            holder.voice_warp.setGravity(Gravity.LEFT);
            setGravity(holder.img,5);
            setGravity(holder.left,5);
            setGravity(holder.right,3);

            holder.left.setText(String.format("%s\"", content.getDuration()));
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
            holder.unread.setVisibility(View.GONE);
            holder.img.setScaleType(ImageView.ScaleType.FIT_END);
            holder.img.setBackgroundResource(drawable.rc_ic_bubble_right);
            animationDrawable = (AnimationDrawable)context.getResources().getDrawable(drawable.rc_an_voice_sent);
            if (playing) {
                holder.img.setImageDrawable(animationDrawable);
                if (animationDrawable != null) {
                    animationDrawable.start();
                }
            } else {
                holder.img.setImageDrawable(holder.img.getResources().getDrawable(drawable.rc_ic_voice_sent));
                if (animationDrawable != null) {
                    animationDrawable.stop();
                }
            }
        } else {
//            holder.voice_warp.setGravity(Gravity.RIGHT);
            setGravity(holder.img,3);
            setGravity(holder.right,3);
            setGravity(holder.left,5);
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)holder.voice_warp.getLayoutParams();
//            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
//            holder.voice_warp.setLayoutParams(params);

            holder.right.setText(String.format("%s\"", content.getDuration()));
            holder.right.setVisibility(View.VISIBLE);
            holder.left.setVisibility(View.GONE);
            if (!message.getReceivedStatus().isListened()) {
                holder.unread.setVisibility(View.VISIBLE);
            } else {
                holder.unread.setVisibility(View.GONE);
            }

            holder.img.setBackgroundResource(drawable.rc_ic_bubble_left);
            animationDrawable = (AnimationDrawable)context.getResources().getDrawable(drawable.rc_an_voice_receive);
            if (playing) {
                holder.img.setImageDrawable(animationDrawable);
                if (animationDrawable != null) {
                    animationDrawable.start();
                }
            } else {
                holder.img.setImageDrawable(holder.img.getResources().getDrawable(drawable.rc_ic_voice_receive));
                if (animationDrawable != null) {
                    animationDrawable.stop();
                }
            }

            holder.img.setScaleType(ImageView.ScaleType.FIT_START);
        }


        if (message.getMessageDirection() == Message.MessageDirection.SEND) {

//            holder.voice_warp.setGravity(Gravity.LEFT);
            setGravity(holder.img, 5);
            setGravity(holder.left, 5);
            setGravity(holder.right, 3);
        }
        else {
//            holder.voice_warp.setGravity(Gravity.RIGHT);
            setGravity(holder.img, 3);
            setGravity(holder.right, 3);
            setGravity(holder.left, 5);
        }
    }

    public Spannable getContentSummary(VoiceMessage data) {
        return null;
    }

    public Spannable getContentSummary(Context context, VoiceMessage data) {
        return data.isDestruct() ? new SpannableString(context.getString(string.rc_message_content_burn)) : new SpannableString(context.getString(string.rc_message_content_voice));
    }

    @TargetApi(8)
    private boolean muteAudioFocus(Context context, boolean bMute) {
        if (context == null) {
            RLog.d("CustomVoiceMessageItemProvider", "muteAudioFocus context is null.");
            return false;
        } else if (Build.VERSION.SDK_INT < 8) {
            RLog.d("CustomVoiceMessageItemProvider", "muteAudioFocus Android 2.1 and below can not stop music");
            return false;
        } else {
            boolean bool = false;
            AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            int result;
            if (bMute) {
                result = am.requestAudioFocus((AudioManager.OnAudioFocusChangeListener)null, 3, 2);
                bool = result == 1;
            } else {
                result = am.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener)null);
                bool = result == 1;
            }

            RLog.d("CustomVoiceMessageItemProvider", "muteAudioFocus pauseMusic bMute=" + bMute + " result=" + bool);
            return bool;
        }
    }

    private static class DestructListener implements RongIMClient.DestructCountDownTimerListener {
        private WeakReference<CustomVoiceMessageItemProvider.ViewHolder> mHolder;
        private UIMessage mUIMessage;

        public DestructListener(CustomVoiceMessageItemProvider.ViewHolder pHolder, UIMessage pUIMessage) {
            this.mHolder = new WeakReference(pHolder);
            this.mUIMessage = pUIMessage;
        }

        public void onTick(long millisUntilFinished, String pMessageId) {
            if (this.mUIMessage.getUId().equals(pMessageId)) {
                CustomVoiceMessageItemProvider.ViewHolder viewHolder = (CustomVoiceMessageItemProvider.ViewHolder)this.mHolder.get();
                if (viewHolder != null) {
                    viewHolder.receiverFireText.setVisibility(View.VISIBLE);
                    viewHolder.receiverFireImg.setVisibility(View.GONE);
                    String unDestructTime = String.valueOf(Math.max(millisUntilFinished, 1L));
                    viewHolder.receiverFireText.setText(unDestructTime);
                    this.mUIMessage.setUnDestructTime(unDestructTime);
                }
            }

        }

        public void onStop(String messageId) {
            if (this.mUIMessage.getUId().equals(messageId)) {
                CustomVoiceMessageItemProvider.ViewHolder viewHolder = (CustomVoiceMessageItemProvider.ViewHolder)this.mHolder.get();
                if (viewHolder != null) {
                    viewHolder.receiverFireText.setVisibility(View.GONE);
                    viewHolder.receiverFireImg.setVisibility(View.VISIBLE);
                    this.mUIMessage.setUnDestructTime((String)null);
                }
            }

        }
    }

    private class VoiceMessagePlayListener implements IAudioPlayListener {
        private Context context;
        private UIMessage message;
        private CustomVoiceMessageItemProvider.ViewHolder holder;
        private boolean listened;

        public VoiceMessagePlayListener(Context context, UIMessage message, CustomVoiceMessageItemProvider.ViewHolder holder, boolean listened) {
            this.context = context;
            this.message = message;
            this.holder = holder;
            this.listened = listened;
        }

        public void onStart(Uri uri) {
            this.message.continuePlayAudio = false;
            this.message.setListening(true);
            this.message.getReceivedStatus().setListened();
            RongIMClient.getInstance().setMessageReceivedStatus(this.message.getMessageId(), this.message.getReceivedStatus(), (RongIMClient.ResultCallback)null);
            CustomVoiceMessageItemProvider.this.setLayout(this.context, this.holder, this.message, true);
            EventBus.getDefault().post(new Event.AudioListenedEvent(this.message.getMessage()));
            if (this.message.getContent().isDestruct() && this.message.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                DestructManager.getInstance().stopDestruct(this.message.getMessage());
            }

        }

        public void onStop(Uri uri) {
            if (this.message.getContent() instanceof VoiceMessage) {
                this.message.setListening(false);
                CustomVoiceMessageItemProvider.this.setLayout(this.context, this.holder, this.message, false);
                if (this.message.getContent().isDestruct() && this.message.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                    DestructManager.getInstance().startDestruct(this.message.getMessage());
                }
            }

        }

        public void onComplete(Uri uri) {
            Event.PlayAudioEvent event = Event.PlayAudioEvent.obtain();
            event.messageId = this.message.getMessageId();
            if (this.message.isListening() && this.message.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                try {
                    event.continuously = this.context.getResources().getBoolean(bool.rc_play_audio_continuous);
                } catch (Resources.NotFoundException var4) {
                    var4.printStackTrace();
                }
            }

            if (event.continuously && !this.message.getContent().isDestruct()) {
                EventBus.getDefault().post(event);
            }

            this.message.setListening(false);
            CustomVoiceMessageItemProvider.this.setLayout(this.context, this.holder, this.message, false);
            if (this.message.getContent().isDestruct() && this.message.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                DestructManager.getInstance().startDestruct(this.message.getMessage());
            }

        }
    }

    private static class ViewHolder {
        ImageView img;
        TextView left;
        TextView right;
        ImageView unread;
        FrameLayout sendFire;
        FrameLayout receiverFire;
        ImageView receiverFireImg;
        TextView receiverFireText;
        RelativeLayout voice_warp;
        private ViewHolder() {
        }
    }
}
