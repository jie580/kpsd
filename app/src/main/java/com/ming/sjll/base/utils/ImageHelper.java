package com.ming.sjll.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jpeng.progress.CircleProgress;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
//import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;


import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;


import java.io.File;
import java.security.MessageDigest;


/**
 * Created by Nowy on 2018/5/24.
 * 在app项目中做个代理，解耦点业务和功能
 */

public class ImageHelper {


    public static int BG_DEF = android.R.color.white;
    public static int USER_EEF = R.mipmap.ic_launcher;
    public static int BT_EEF = android.R.color.white;
    public static int BG_DEF_1VS1 = android.R.color.white;
    public static int BG_BEF_4VS3 = android.R.color.white;


    public static GlideRoundTransform glideRoundTransform;

    public static void display(ImageView iv, String url) {
        ImageLoaderUtil.display(iv, url);
    }

    /**
     * 加载圆角图片
     *
     * @param iv
     * @param url
     */
    public void displayCorners(ImageView iv, String url) {
        glideRoundTransform = new GlideRoundTransform(iv.getContext(), 10);

        ImageLoaderUtil.displayBackground(iv, url,
                ImageLoaderUtil.getDefOption()
                        .priority(Priority.HIGH)
                        .centerCrop()
                        .skipMemoryCache(false)
                        .error(USER_EEF)
                        .bitmapTransform(glideRoundTransform)
                        .dontAnimate()
//                .dontTransform()
        );
    }




    /**
     * 带进度的加载
     *
     * @param image
     * @param url
     */
    public void displayBackgroundLoading(ImageView image, String url) {
        displayBackgroundLoading(image,url,null);
    }

    /**
     * 带进度的加载
     *
     * @param image
     * @param url
     */
    public void displayBackgroundLoading(ImageView image, String url, CommonCallback cb) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

//                圆角
//        .displayer(new CircleBitmapDisplayer(Color.TRANSPARENT, 5))
//                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)

//                .imageScaleType(ImageScaleType.NONE)
                .bitmapConfig(Bitmap.Config.RGB_565)
//                .displayer(new RoundedBitmapDisplayer(20))
//                .displayer(new RoundedCenterBitmapDisplayer(20, 2))

                .build();

//        image.setScaleType(ImageView.ScaleType);

//            final ImageView.ScaleType scaleType = image.getScaleType();
//            image.setScaleType(ImageView.ScaleType.CENTER);
//            image.getBackground();
            image.setBackgroundColor(Color.parseColor("#EEEEEE"));

            CircleProgress progress = generateProgress(image);
            progress.inject(image);
            progress.setLevel(0);
            progress.setMaxValue(100);

        ImageLoader.getInstance().displayImage( url,  image, options,
                new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                        //开始加载
                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1,
                                                FailReason arg2) {
                        //加载失败
                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1,
                                                  Bitmap arg2) {
//                        加载成功
//                        image.setScaleType(scaleType);


                        image.setBackgroundColor(Color.alpha(255));
                        if(cb!=null)
                        cb.onSuccessBitmap(arg0,arg1,arg2);

                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        //加载取消
                    }
                }, new ImageLoadingProgressListener() {

                    @Override
                    public void onProgressUpdate(String imageUri, View view,
                                                 int current, int total) {
//                        Log.e("进度2222：",current+",,,,,"+total);
                        //加载进度

                        progress.setLevel(current);
                        progress.setMaxValue(total);
                    }
                });
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
        builder.setProgressColor(Color.parseColor("#0A3FFF"));
        return (CircleProgress) builder.build();
    }


    /**
     * 加载用户头像
     *
     * @param iv
     * @param url
     */
    public static void displayUser(ImageView iv, String url) {
        ImageLoaderUtil.display(iv, url,
                ImageLoaderUtil.getDefOption()
                        .priority(Priority.HIGH)
                        .centerCrop()
                        .error(USER_EEF).dontAnimate());
    }

    /**
     * 加载美容师头像
     *
     * @param iv
     * @param url
     */
    public static void displayBt(ImageView iv, String url) {
        ImageLoaderUtil.display(iv, url,
                ImageLoaderUtil.getDefOption()
                        .priority(Priority.HIGH)
                        .centerCrop()
                        .error(BT_EEF).dontAnimate());
    }

    /**
     * 加载1:1的图片
     *
     * @param iv
     * @param url
     */
    public static void display1Vs1(ImageView iv, String url) {
        ImageLoaderUtil.display(iv, url, ImageLoaderUtil.getDefOption()
                .error(BG_DEF_1VS1).centerCrop());
    }


    /**
     * 加载图片(CenterCrop)
     *
     * @param iv
     * @param url
     */
    public static void displayCenterCrop(ImageView iv, String url, int def) {
        ImageLoaderUtil.display(iv, url,
                ImageLoaderUtil.getDefOption()
                        .centerCrop()
                        .error(def));
    }

    /**
     * 显示背景
     *
     * @param iv
     * @param url
     */
    public static void displayBackground1Vs1(ImageView iv, String url) {
        ImageLoaderUtil.displayBackground(iv, url, BG_DEF_1VS1);
    }

    /**
     * 加载4:3的图片(宽高)
     *
     * @param iv
     * @param url
     */
    public static void display4Vs3(ImageView iv, String url) {
        ImageLoaderUtil.display(iv, url, BG_BEF_4VS3);
    }

    /**
     * 显示背景
     *
     * @param iv
     * @param url
     */
    public static void displayBackground4Vs3(ImageView iv, String url) {
        ImageLoaderUtil.displayBackground(iv, url, BG_BEF_4VS3);
    }

    /**
     * 加载图片，默认图
     *
     * @param iv
     * @param url
     */
    public static void displayDef(ImageView iv, String url, @DrawableRes int defRes) {
        ImageLoaderUtil.display(iv, url, defRes);
    }


    /**
     * 显示图片
     *
     * @param imageView
     * @param file
     * @param imgDef
     */
    public static void display(ImageView imageView, File file, @DrawableRes int imgDef) {
        ImageLoaderUtil.display(imageView, file, imgDef);
    }


    /**
     * 显示背景
     *
     * @param iv
     * @param url
     * @param defRes
     */
    public static void displayBackground(ImageView iv, String url, @DrawableRes int defRes) {
        ImageLoaderUtil.displayBackground(iv, url, defRes);
    }


    /**
     * 下载图片，异步方法，可以做在UI线程中调用
     *
     * @param context
     * @param url
     * @param listener
     */
    public static void downloadAsync(Context context, String url, final DownloadListener listener) {
        ImageLoaderUtil.downloadDrawableAsync(context, url, new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (listener != null) listener.onDownloadSuccess(resource);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                if (listener != null) listener.onDownloadError(errorDrawable);
            }
        });
    }


    public static Bitmap download(Context context, String url, int width, int height) {
        return ImageLoaderUtil.download(context, url, width, height);
    }


    /**
     * 下载监听
     */
    public interface DownloadListener {
        void onDownloadSuccess(Drawable drawable);

        void onDownloadError(Drawable drawable);
    }


    /**
     * 圆角
     */
    public class GlideRoundTransform extends BitmapTransformation {

        private float radius = 0f;

        public GlideRoundTransform(Context context) {
            this(context, 4);
        }

        public GlideRoundTransform(Context context, int dp) {
//        super(context);
            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
            return roundCrop(pool, bitmap);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        public String getId() {
            return getClass().getName() + Math.round(radius);
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }

    }

}


