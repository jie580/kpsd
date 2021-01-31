package com.ming.sjll.My.homepage;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GoodsListItem;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.CollectSampleListAdapter;
import com.ming.sjll.My.adapter.GoodsListAdapter;
import com.ming.sjll.My.adapter.SampleListAdapter;
import com.ming.sjll.My.dialog.EditorDialog;
import com.ming.sjll.My.dialog.OtherEditorDialog;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.Share.ShareMessageActivity;
import com.ming.sjll.Share.ShareWx;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.rey.material.app.BottomSheetDialog;

/**
 * 个人中心-收藏-商品
 */
public class HomeSample extends BaseFragment {

    private  static final String type = "goods";
    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private WorkListItem dataList;
    private SampleListAdapter listAdapter;
    RecyclerView listView;

    public static HomeSample newInstance() {
        return new HomeSample();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_collect_user);
        initView();
    }

    public int point;
    public String  userId;
    private void initView() {
        ((HomepageActivity) getActivity()).setChildObjectForPosition(this,point);

        listView = (RecyclerView) findViewById(R.id.recyclerList);
        getList();
    }

    private void getList()
    {
        if (isLoad ) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        page = 1;
        param.setParam("user_id", userId);
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        new HttpUtil().sendRequest(Constant.USER_WORK_GET_WORKS_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, WorkListItem.class);
//                LinearLayoutManager linearmanager = new LinearLayoutManager(getContext());
//                listView.setLayoutManager(new LinearLayoutManager(getContext()){
//
//                    @Override
//
//                    public boolean canScrollVertically() {
//
//                        return false;
//                    }
//                });
                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new SampleListAdapter(dataList.getData().getData(),getContext());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId())
                        {
                            case R.id.iv_menu:
//                                ToastShow.s("收藏"+dataList.getData().getData().get(position).getWorkId());
                                OtherEditorDialog.newInstance(true).setOnClickListener(new OtherEditorDialog.OnClickListener() {
                                    @Override
                                    public void Collect() {
                                        ToastShow.s("收藏"+dataList.getData().getData().get(position).getWorkId());
                                    }
                                    @Override
                                    public void Share() {

                                        ToastShow.s("分享"+dataList.getData().getData().get(position).getWorkId());
                                        Log.e("分享", "ShareShareShareShareShare");

                                        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                                        bottomInterPasswordDialog.setContentView(R.layout.dialog_share);
                                        bottomInterPasswordDialog.inDuration(300);
                                        bottomInterPasswordDialog.outDuration(200);
//                                        bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                                        bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                                        bottomInterPasswordDialog.show();
                                        View message = bottomInterPasswordDialog.findViewById(R.id.message);
                                        View friend = bottomInterPasswordDialog.findViewById(R.id.friend);
                                        View circle = bottomInterPasswordDialog.findViewById(R.id.circle);
                                        message.setOnClickListener(new View.OnClickListener() {//分享到消息
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
                                        friend.setOnClickListener(new View.OnClickListener() {//分享到朋友圈
                                            @Override
                                            public void onClick(View v) {
                                                bottomInterPasswordDialog.hide();
                                                ShareWx.userShare(ShareWx.TYPE_SAMPLE, ShareWx.WX_FRIEND, dataList.getData().getData().get(position).getId(), getContext());
                                            }
                                        });
                                        circle.setOnClickListener(new View.OnClickListener() {//分享到微信
                                            @Override
                                            public void onClick(View v) {
                                                bottomInterPasswordDialog.hide();
                                                ShareWx.userShare(ShareWx.TYPE_SAMPLE, ShareWx.WX_CIRCLE, dataList.getData().getData().get(position).getId(), getContext());
                                            }
                                        });

                                    }
                                }).show(getActivity());


                                break;
                            case R.id.wrap:
//                                ToastShow.s("点击外层"+dataList.getData().getData().get(position).getWorkId());
                                break;
                        }

                    }
                });

            }
            @Override
            public void onFinal() {
                super.onFinal();
                isLoad = false;
            }

        });
    }

    public void  nextPage() {
        if (dataList == null || dataList.getData().getData().size() == 0) {
            getList();
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
        param.setParam("type", type);
        new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECTION, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                WorkListItem temgData = new Gson().fromJson(data, WorkListItem.class);
                for (int i = 0; i < temgData.getData().getData().size(); i++) {
                    dataList.getData().getData().add(temgData.getData().getData().get(i));
                }
                listAdapter.notifyDataSetChanged();

            }
            @Override
            public void onFinal() {
                super.onFinal();
                isLoad = false;
            }
        });

    }

}
