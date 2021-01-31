package com.ming.sjll.Message.extend;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.ming.sjll.R;

import io.rong.imkit.widget.provider.FilePlugin;

public class CustomFilePlugin extends FilePlugin {

    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.mipmap.chat_ext_btn2);
    }

    @Override
    public String obtainTitle(Context context) {
        return "文件";
    }


}
