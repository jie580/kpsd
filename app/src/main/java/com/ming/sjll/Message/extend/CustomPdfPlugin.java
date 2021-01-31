package com.ming.sjll.Message.extend;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.ming.sjll.Message.MessageChatActivity;
import com.ming.sjll.Message.UploadPdfMessageActivity;
import com.ming.sjll.R;
import com.ming.sjll.base.utils.Tools;
//import com.ming.sjll.message.view.MessageChatView;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

public class CustomPdfPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.chat_ext_btn4);
    }

    @Override
    public String obtainTitle(Context context) {
        return "上传PDF";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        Bundle bundle = new Bundle();
        bundle.putString("targetId", rongExtension.getTargetId());
        Tools.jump(fragment.getActivity(), UploadPdfMessageActivity.class, bundle, false);
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
