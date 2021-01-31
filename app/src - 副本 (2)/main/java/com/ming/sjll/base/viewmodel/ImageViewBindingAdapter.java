package com.ming.sjll.base.viewmodel;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.ming.sjll.base.utils.ImageLoaderUtil;

public class ImageViewBindingAdapter {

    @BindingAdapter({"url"})
    public static void setImageUrl(ImageView imageView, String url) {

        ImageLoaderUtil.display(imageView, url);
    }
}
