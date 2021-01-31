package com.ming.sjll.Message.extend;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.ming.sjll.R;

import io.rong.imkit.plugin.ImagePlugin;

public class CustomImagePlugin extends ImagePlugin {

    @Override
    public String obtainTitle(Context context) {
        return "发送图片";
    }

    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.plugin_image);
    }
}
