package me.minetsh.imaging.widget;

import android.support.v7.widget.RecyclerView;

//import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by felix on 2018/1/4 下午3:53.
 */

public interface IMGGalleryHolderCallback extends IMGViewHolderCallback {

    void onCheckClick(RecyclerView.ViewHolder holder);
}
