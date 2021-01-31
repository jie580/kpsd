package com.ming.sjll.appication;


import android.content.Context;

//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.lzy.okgo.OkGo;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.utils.AppUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.bugly.crashreport.CrashReport;
//
//import org.xutils.x;


/**
 * @author luoming
 * created at 2019/8/6 10:57 AM
 */
public class SJLLApplication extends BaseApplication {
    private static SJLLApplication mInstance = null;

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//		//颜色
		AppUtils.init(this);
//		//融云初始化
		new RongImUtils().init(this);
        //初始化上传文件
//        OkGo.getInstance().init(this);
        //bugly
//		CrashReport.initCrashReport(getApplicationContext(), "9aa86a8873", true);


        context = this;
//        x.Ext.init(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY + 2)
                .denyCacheImageMultipleSizesInMemory()
//.diskCacheSize(1)
                .tasksProcessingOrder(QueueProcessingType.FIFO)// 图片加载任务顺序
                .threadPoolSize(1)
//                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // 打印debug log
                .build();

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
//        ImagePipelineConfig config1 = ImagePipelineConfig.newBuilder(this).setResizeAndRotateEnabledForNetwork(true)
//                .setDownsampleEnabled(true)
//                .build();
//
//        Fresco.initialize(this, config1);
        Cache.initCache(context);
    }

    public static SJLLApplication getInstance() {
        return mInstance;
    }


}