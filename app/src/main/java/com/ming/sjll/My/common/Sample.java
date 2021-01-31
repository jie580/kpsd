package com.ming.sjll.My.common;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;


import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.view.View.GONE;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import com.heartfor.heartvideo.video.HeartVideo;
import com.heartfor.heartvideo.video.HeartVideoManager;
import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.Bean.WorkItem;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.SampleListAdapter;
import com.ming.sjll.My.company.CompanyAuth;
import com.ming.sjll.My.dialog.EditorDialog;
import com.ming.sjll.R;
import com.ming.sjll.Share.ShareMessageActivity;
import com.ming.sjll.Share.ShareWx;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.event.SampleEditEvent;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.rey.material.app.BottomSheetDialog;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

/**
 * 个人中心-作品
 */
public class Sample extends BaseFragment {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private WorkListItem dataList;
    private boolean isInit = true;


    private int firstVisibleItem;
    private int lastVisibleItem;
    private int visibleCount;
    private int scrollState = 0;
    LinearLayoutManager linearmanager;


    private SampleListAdapter sampleListAdapter;

    //    public static Sample This;
    public int point;
    RecyclerView listView;

    public static Sample newInstance() {
//        if (This != null) {
//            return This;
//        }
//        This = new Sample();
        return new Sample();

    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
//        if(isInit) {
        super.onCreateView(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        setContentView(R.layout.my_sample);

        initView();
//            if(!isInit)
//            {
//                return;
//            }
//            isInit = false;
        bindView();

//        }
//        event();
    }

    private void initView() {
        listView = (RecyclerView) findViewById(R.id.listView);
//        listView.setSelector(new ColorDrawable());

        if (getParentFragment() instanceof Myfragemt) {
            ((Myfragemt) getParentFragment()).setChildObjectForPosition(this, point);
        }


    }
//
//    public static void uploadData()
//    {
//        This.page = 1;
//        This.bindView();
////        Log.e("加载数据","加载数据加载数据加载数据加载数据加载数据加载数据加载数据加载数据加载数据加载数据");
//    }

    /**
     * 作品更新
     *
     * @param e
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SampleEditEvent e) {

        if (dataList == null) {
            bindView();
            return;
        }

        WorkItem.DataBean param = e.param;
//        WorkItem.DataBean work = new WorkItem.DataBean();
//        work.setWorkId( Integer.parseInt(param.getStringMapParam("workId") ));
//        work.settype( Integer.parseInt(param.getStringMapParam("type") ));
//        work.setTitle( param.getStringMapParam("title"));
//        work.setDescribe( param.getStringMapParam("describe") );
//        work.setCoverImg(param.getStringMapParam("coverImg"));
//
//        List<String> listTemp = param.getStringListParam("img");
//        ArrayList<String> list = new ArrayList<>(listTemp);

//        list.addAll(listTemp);
//        for (String s :
//                listTemp) {
//
//        }
//        work.setimgList( list);
//        work.setCoverImg(param.getStringMapParam("videoUrl"));
        if (e.oldPoint >= 0) {
            dataList.getData().getData().remove(e.oldPoint);
            dataList.getData().getData().add(e.oldPoint, param);
        } else {
            dataList.getData().getData().add(0, param);
        }
        sampleListAdapter.notifyDataSetChanged();
//        ToastShow.s(param.getUrlParam());
//        zhouText.moveToPosition(e.position);
    }


    public void nextPage() {
        Log.e("Sample", "nexPagenexPagenexPagenexPagenexPagenexPagenexPagenexPage");
        nextPageData();
    }


    private void nextPageData() {
        if (dataList == null || dataList.getData().getData().size() == 0) {
            bindView();
            return;
        }
        if (isLoad) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        page++;
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);


        new HttpUtil().sendRequest(Constant.USER_WORK_GET_WORKS_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                WorkListItem temgData = new Gson().fromJson(data, WorkListItem.class);
                for (int i = 0; i < temgData.getData().getData().size(); i++) {
                    dataList.getData().getData().add(temgData.getData().getData().get(i));
                }
                sampleListAdapter.notifyDataSetChanged();
                isLoad = false;
            }

            @Override
            public void onFinal() {
                super.onFinal();
                isLoad = false;
            }
        });

    }


    public void bindView() {

        if (isLoad) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        page = 1;
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);


        new HttpUtil().sendRequest(Constant.USER_WORK_GET_WORKS_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, WorkListItem.class);

//                adapterList = new SampleListAdapter(Sample.this.getContext(), dataList.getData().getData());
//                listView.setAdapter(adapterList);
                linearmanager = new LinearLayoutManager(getContext());
                listView.setLayoutManager(linearmanager);
                sampleListAdapter = new SampleListAdapter(dataList.getData().getData(), getContext());
                listView.setAdapter(sampleListAdapter);


                //重复使用监听
                listView.setRecyclerListener(new RecyclerView.RecyclerListener() {
                    @Override
                    public void onViewRecycled(RecyclerView.ViewHolder holder) {
                        HeartVideo heartVideo = (HeartVideo) holder.itemView.findViewById(R.id.video_view);
                        if (heartVideo == HeartVideoManager.getInstance().getCurrPlayVideo()) {
                            HeartVideoManager.getInstance().release();
                        }
                    }
                });
                //滑动监听
                listView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        switch (newState) {
                            case SCROLL_STATE_IDLE: //滚动停止
                                autoPlayVideo(recyclerView);
                                break;
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        firstVisibleItem = linearmanager.findFirstVisibleItemPosition();
                        lastVisibleItem = linearmanager.findLastVisibleItemPosition();
                        visibleCount = lastVisibleItem - firstVisibleItem;
                    }
                });


                sampleListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if (view.getId() == R.id.iv_menu) {

                            BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getActivity());
                            bottomInterPasswordDialog.setContentView(R.layout.dialog_sample_more);
                            bottomInterPasswordDialog.inDuration(300);
                            bottomInterPasswordDialog.outDuration(200);
//                bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                            bottomInterPasswordDialog.show();
                            bottomInterPasswordDialog.findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (bottomInterPasswordDialog.findViewById(R.id.shareWrap).getVisibility() == GONE) {

                                        View view = bottomInterPasswordDialog.findViewById(R.id.shareWrap);
//                                        TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
//                                                Animation.RELATIVE_TO_SELF, 0.0f,
//                                                Animation.RELATIVE_TO_SELF, 0.0f,
//                                                Animation.RELATIVE_TO_SELF, 0.0f);
//                                        showAnim.setDuration(500);
//                                        view.startAnimation(showAnim);
                                        view.setVisibility(View.VISIBLE);
                                    } else {
                                        View view = bottomInterPasswordDialog.findViewById(R.id.shareWrap);
//
//                                        TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//
//                                                Animation.RELATIVE_TO_SELF, 1.0f,
//
//                                                Animation.RELATIVE_TO_SELF, 0.0f,
//
//                                                Animation.RELATIVE_TO_SELF, 0.0f);

//                                        hideAnim.setDuration(500);
//
//                                        view.startAnimation(hideAnim);
                                        view.setVisibility(GONE);
                                    }

                                }
                            });
                            bottomInterPasswordDialog.findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bottomInterPasswordDialog.hide();
                                    Bundle b = new Bundle();
                                    b.putSerializable("content", (Serializable) dataList.getData().getData().get(position));
                                    b.putInt("oldPoint", position);
                                    Tools.jump(getActivity(), SampleEdit.class, b, false);
                                }
                            });
                            bottomInterPasswordDialog.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bottomInterPasswordDialog.hide();
                                    DialogTextTip dialog = new DialogTextTip(getContext(), "是否确定删除");
                                    dialog.show(new CommonCallback() {
                                        @Override
                                        public void onNext() {
                                            super.onNext();
                                            loadingDailog.show();

                                            HttpParamsObject param = new HttpParamsObject();
                                            param.setParam("workId", dataList.getData().getData().get(position).getId());
                                            new HttpUtil().sendRequest(Constant.USER_WORK_DEL_WORK, param, new CommonCallback() {
                                                @Override
                                                public void onSuccessJson(String data, int code) {
                                                    dataList.getData().getData().remove(position);
                                                    sampleListAdapter.notifyDataSetChanged();
                                                }
                                            });

                                        }
                                    });

                                }
                            });
//分享
                            bottomInterPasswordDialog.findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {//分享到消息
                                @Override
                                public void onClick(View v) {
//                                            分享到消息
                                    bottomInterPasswordDialog.hide();
                                    Bundle bundle = new Bundle();
                                    bundle.putString(ShareMessageActivity.SHARE_ID, dataList.getData().getData().get(position).getId() + "");
                                    //0-服务 1-人员 2-商品,3-作品
                                    bundle.putString(ShareMessageActivity.SHARE_TYPE, ShareMessageActivity.TYPE_SAMPLE);
                                    Tools.jump(getActivity(), ShareMessageActivity.class, bundle, false);
                                }
                            });
                            bottomInterPasswordDialog.findViewById(R.id.friend).setOnClickListener(new View.OnClickListener() {//分享到朋友圈
                                @Override
                                public void onClick(View v) {
                                    bottomInterPasswordDialog.hide();
                                    ShareWx.userShare(ShareWx.TYPE_SAMPLE, ShareWx.WX_FRIEND, dataList.getData().getData().get(position).getId(), getContext());
                                }
                            });
                            bottomInterPasswordDialog.findViewById(R.id.circle).setOnClickListener(new View.OnClickListener() {//分享到微信
                                @Override
                                public void onClick(View v) {
                                    bottomInterPasswordDialog.hide();
                                    ShareWx.userShare(ShareWx.TYPE_SAMPLE, ShareWx.WX_CIRCLE, dataList.getData().getData().get(position).getId(), getContext());
                                }
                            });


                        }
//                        else if(view.getId() == R.id.playBtn)
//                        {
//
//                        }
                    }
                });


//                MyUtil.setListViewHeightBasedOnChildren(listView);

                Log.e("加载完成", "..............................");
                //                分页加载数据
//                listView.setListViewLoadInterface(new PagedListView.ListViewLoadListener() {
//                    @Override
//                    public void loadData() {
//                        Log.e("触发分页","..............................");
//                    }
//                });
                isLoad = false;

            }
        });


    }

    private void autoPlayVideo(RecyclerView recyclerview) {
        RecyclerView.LayoutManager layoutManager = recyclerview.getLayoutManager();
        for (int i = 0; i < visibleCount; i++) {
            if (layoutManager != null && layoutManager.getChildAt(i) != null && layoutManager.getChildAt(i).findViewById(R.id.video_view) != null) {
                HeartVideo heartVideo = (HeartVideo) layoutManager.getChildAt(i).findViewById(R.id.video_view);
                Rect rect = new Rect();
                heartVideo.getLocalVisibleRect(rect);
                int videoheight = heartVideo.getHeight();
                if (rect.top == 0 && rect.bottom == videoheight) {
                    heartVideo.start();
//                    heartVideo.setStreamMute(true);
                    return;
                }

            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PictureConfig.SAMPLE_REQUEST) {

        }
    }


}
