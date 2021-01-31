package com.ming.sjll.Message.extend;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.ming.sjll.R;

import io.rong.imkit.plugin.DefaultLocationPlugin;

public class CustomLocationPlugin extends DefaultLocationPlugin {
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.plugin_location);
    }

}
