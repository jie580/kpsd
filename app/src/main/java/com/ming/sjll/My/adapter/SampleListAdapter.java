package com.ming.sjll.My.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.heartfor.heartvideo.video.HeartVideo;
import com.heartfor.heartvideo.video.HeartVideoInfo;
import com.heartfor.heartvideo.video.VideoControl;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.project.adapter.CoverBgAdapter;
import com.ming.sjll.project.bean.ProjecCoverBg;
import com.ming.sjll.ui.AntoLineLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class SampleListAdapter extends BaseQuickAdapter<WorkItem.DataBean, BaseViewHolder> {

    Context context;
    public SampleListAdapter(@Nullable List<WorkItem.DataBean> data,Context context) {
        super(R.layout.my_sample_adapter_list, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WorkItem.DataBean dataBean) {


        baseViewHolder.setText(R.id.tv_day, Tools.getDateformat3(Integer.parseInt(dataBean.getcreated_time()), "dd"));
        baseViewHolder.setText(R.id.tv_moth, Tools.getDateformat3(Integer.parseInt(dataBean.getcreated_time()), "MM") + "月");
        baseViewHolder.setText(R.id.tv_describe, dataBean.getDescribe());
        baseViewHolder.setText(R.id.tv_title, dataBean.getTitle());

        baseViewHolder.getView(R.id.voidWrap).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.recyclerview).setVisibility(View.GONE);
//        作品图文
        if (dataBean.gettype() == 1) {
            baseViewHolder.getView(R.id.recyclerview).setVisibility(View.VISIBLE);
            //        作品列表
            RecyclerView recyclerView = baseViewHolder.getView(R.id.recyclerview);
//        AntoLineLayoutManager layout = new AntoLineLayoutManager();
//        recyclerView.setLayoutManager(layout);
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
            SampleListItemAdapter sampleListItemAdapter = new SampleListItemAdapter(dataBean.getimgList());
            List<LocalMedia> selectList = new ArrayList<>();
            for (String img : dataBean.getimgList()) {
                LocalMedia localMedia = new LocalMedia(Constant.BASE_API + img, 0, 1, "image/jpeg");
                selectList.add(localMedia);
            }
            sampleListItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.bg) {
                        PictureSelector.create((Activity) mContext).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
                    }
                }
            });
            recyclerView.setAdapter(sampleListItemAdapter);
        } else {
            baseViewHolder.getView(R.id.voidWrap).setVisibility(View.VISIBLE);
//            baseViewHolder.getView(R.id.playBg).setVisibility(View.VISIBLE);
//            new ImageHelper().displayBackgroundLoading( baseViewHolder.getView(R.id.playBg),
//                    Constant.BASE_IMAGE + dataBean.getCoverImg()+"?"+ System.currentTimeMillis());


            HeartVideoInfo info = HeartVideoInfo.Builder().setTitle("").setPath(Constant.BASE_IMAGE + dataBean.getvideo_url()).setImagePath(Constant.BASE_IMAGE + dataBean.getCoverImg()).setSaveProgress(true).builder();
            VideoControl control = new VideoControl(context);
            control.setInfo(info);
//            ((HeartVideo) baseViewHolder.getView(R.id.video_view)).bringToFront();
            HeartVideo heartVideo = ((HeartVideo) baseViewHolder.getView(R.id.video_view));
            heartVideo.setHeartVideoContent(control);
            heartVideo.setStreamMute(true);
//            control.listen

//            VideoDetailInfo voidInfo = new VideoDetailInfo(dataBean.getTitle(),Constant.BASE_IMAGE + dataBean.getvideo_url());
//            BDVideoView videoView = baseViewHolder.getView(R.id.video_view);
//            baseViewHolder.addOnClickListener(R.id.playBtn);
//            baseViewHolder.getView(R.id.playBtn).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //预览视频
//                    PictureSelector.create((Activity) mContext).externalPictureVideo(Constant.BASE_IMAGE + dataBean.getvideo_url());
////                    baseViewHolder.getView(R.id.playBg).setVisibility(View.GONE);
////                    baseViewHolder.getView(R.id.playBtn).setVisibility(View.GONE);
////                    videoView.startPlayVideo(voidInfo);
//                }
//            });
//            VideoDetailInfo info = (VideoDetailInfo) getIntent().getSerializableExtra("info");
        }

        baseViewHolder.addOnClickListener(R.id.iv_menu);
    }


}
