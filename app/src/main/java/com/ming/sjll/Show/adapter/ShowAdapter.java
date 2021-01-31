package com.ming.sjll.Show.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jpeng.progress.CircleProgress;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.toolPage.DateFrame;


import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;


/**
 * Created by HanHailong on 15/10/19.
 */
public class ShowAdapter extends BaseQuickAdapter<WorkItem.DataBean, BaseViewHolder> {

    public ShowAdapter(@Nullable List<WorkItem.DataBean> data) {
        super(R.layout.show_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WorkItem.DataBean dataBean) {

//        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnLoading(R.mipmap.loading2)
//                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(20)).build();
//
//        ImageView image  = (ImageView) baseViewHolder.getView(R.id.iv_img);
////        image.setScaleType(ImageView.ScaleType);
//        final CircleProgress progress = generateProgress(image);
//        progress.inject(image);
////        progress.setLevel(50);
////        progress.setMaxValue(100);
//        ImageLoader.getInstance().displayImage( Constant.BASE_API +dataBean.getCoverImg()+"?"+ System.currentTimeMillis(), (ImageView) baseViewHolder.getView(R.id.iv_img), options,
//                new ImageLoadingListener() {
//
//                    @Override
//                    public void onLoadingStarted(String arg0, View arg1) {
//                        //开始加载
//                    }
//
//                    @Override
//                    public void onLoadingFailed(String arg0, View arg1,
//                                                FailReason arg2) {
//                        //加载失败
//                    }
//
//                    @Override
//                    public void onLoadingComplete(String arg0, View arg1,
//                                                  Bitmap arg2) {
//                        //加载成功
//                    }
//
//                    @Override
//                    public void onLoadingCancelled(String arg0, View arg1) {
//                        //加载取消
//                    }
//                }, new ImageLoadingProgressListener() {
//
//                    @Override
//                    public void onProgressUpdate(String imageUri, View view,
//                                                 int current, int total) {
//                        Log.e("进度2222：",current+",,,,,"+total);
//                        //加载进度
//                        progress.setLevel(current);
//                        progress.setMaxValue(total);
//                    }
//                });
//        HttpUtil.downloadFile(dataBean.getCoverImg(),null);
        Log.e(TAG,dataBean.getCoverImg());
        new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.iv_img),
                Constant.BASE_API + dataBean.getCoverImg());

//        ImageHelper.displayBackground( baseViewHolder.getView(R.id.iv_img),
//                Constant.BASE_API + dataBean.getCoverImg()+"?"+ System.currentTimeMillis(),R.mipmap.avatars_login);


    }

    /**
     * generate the Progress into the Simpledraweeview, the first,you should new
     * the RectangleProgress.Builder, the seconnd,you can set more properties to
     * the Builder, Finally,you should use build() and inject to DraweeView
     *
     *
     * @param image
     *            the shower
     */
    private CircleProgress generateProgress(ImageView image) {
        CircleProgress.Builder builder = new CircleProgress.Builder();

        return (CircleProgress) builder.build();
    }

}