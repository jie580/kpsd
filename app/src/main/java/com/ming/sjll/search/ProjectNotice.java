package com.ming.sjll.search;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.My.company.ShowcaseInfoActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.login.bean.DefaultAvatar;
import com.ming.sjll.project.ReleaseProject;
import com.ming.sjll.project.ReleaseProjectNext;
import com.ming.sjll.search.adapter.ProjectListItemAdapter;
import com.ming.sjll.search.adapter.ProjectNoticeListItemAdapter;
import com.ming.sjll.search.bean.ProjecItem;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.ui.PagedListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 搜素的项目列表（通告）
 */
public class ProjectNotice extends BaseFragment {

    private View parentContentView;

    private PagedListView listView;
    private ProjectNoticeListItemAdapter projectAdapter;
    private ProjectListItem dataList;

    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 5;
    private String search = "";

    private boolean isShowlistViewFooter = true;



    public static ProjectNotice newInstance() {

        return new ProjectNotice();
    }

//    @SuppressLint("ValidFragment")
//    public ProjectNotice() {
//        this.parentContentView = parentContentView;
//    }
//
//    public ProjectNotice() {
//    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.search_project_project);
        initView();
//        event();
    }


//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        view.setTag("projectProject");
//        super.onViewCreated(view, savedInstanceState);
//    }


    public void searchKey(String s) {
//        if(this.parentContentView == null)
//        {
//            return;
//        }
//        EditText searchEdit = (EditText) parentContentView.findViewById(R.id.searchEdit);
//        if(searchEdit != null)
//            searchEdit.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
                    search = s.toString();
                    page = 1;
                    initView();
//                }
//            });
    }

    int lastPos;
    private void initView() {
        listView = (PagedListView) findViewById(R.id.control_main_listview);
        listView.setSelector(new ColorDrawable());//设置默认状态选择器为全透明，不传颜色就是没颜色
        listView.showFooter(isShowlistViewFooter);

        if (isLoad) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("page", page);
        param.setParam("search", search);
        param.setParam("pageSize", pageSize);
        page++;
        new HttpUtil().sendRequest(Constant.PROJECT_NOTICE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                isLoad = false;
                dataList = new Gson().fromJson(data, ProjectListItem.class);
                projectAdapter = new ProjectNoticeListItemAdapter(ProjectNotice.this.getContext(), dataList.getData().getData(), true);
                listView.setAdapter(projectAdapter);
                setListViewHeightBasedOnChildren(listView);
//                分页加载数据
                listView.setListViewLoadInterface(new PagedListView.ListViewLoadListener() {
                    @Override
                    public void loadData() {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                HttpParamsObject param = new HttpParamsObject();
                                if (isLoad) {
                                    return;
                                }
                                isLoad = true;
                                param.setParam("page", page);
                                param.setParam("search", search);
                                param.setParam("pageSize", pageSize);
                                page++;
                                new HttpUtil().sendRequest(Constant.PROJECT_NOTICE, param, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {
                                        isLoad = false;
                                        ProjectListItem dataList2 = new Gson().fromJson(data, ProjectListItem.class);
                                        for (int i = 0; i < dataList2.getData().getData().size(); i++) {
                                            dataList.getData().getData().add(dataList2.getData().getData().get(i));
                                        }
                                        projectAdapter.notifyDataSetChanged();
                                        listView.onLoadComplete();
                                        setListViewHeightBasedOnChildren(listView);
                                    }
                                });
                            }
                        }, 0);
                    }

                });
//                点击事件
                projectAdapter.setOnClickListenerInterface(new ProjectNoticeListItemAdapter.setOnClickListener() {

                    @Override
                    public void onClick(int pos, @IdRes int id, View v) {
                        switch (id) {
                            case R.id.openChat:
                                RongImUtils.privateChat(getActivity(),dataList.getData().getData().get(pos).getIm_uid(),dataList.getData().getData().get(pos).getIm_name());
                                break;
                            case R.id.projectWrap:

                                Bundle bundle = new Bundle();
                                bundle.putString("id",dataList.getData().getData().get(pos).getProject_id());
                                bundle.putBoolean("isLook",true);
                                Tools.jump(getActivity(), ReleaseProject.class, bundle,false);

//                                Log.e("点击", "点击了rc_wrap" + pos);
//                                for (int i = 0; i < dataList.getData().getData().size(); i++) {
//                                    dataList.getData().getData().get(i).setIsNotice(false);
////                                    dataList.getData().getData().get(i).setIsChange(false);
////                                    if(i == lastPos)
////                                    {
////                                        dataList.getData().getData().get(i).setIsChange(true);
////                                    }
//                                }
//                                dataList.getData().getData().get(pos).setIsNotice(true);
//                                dataList.getData().getData().get(pos).setIsChange(true);
//                                lastPos = pos;
//
//                                RelativeLayout projectWrap = (RelativeLayout) v.findViewById(R.id.projectWrap);
//                                RelativeLayout noticeWrap = (RelativeLayout) v.findViewById(R.id.noticeWrap);
//                                projectWrap.setVisibility(View.GONE);
//                                noticeWrap.setVisibility(View.VISIBLE);

//                                if(lastParent != null && lastParent != parent)
//                                {
//                                    RelativeLayout projectWrap2 = (RelativeLayout) lastParent.findViewById(R.id.projectWrap);
//                                    RelativeLayout noticeWrap2 = (RelativeLayout) lastParent.findViewById(R.id.noticeWrap);
//                                    projectWrap2.setVisibility(View.GONE);
//                                    noticeWrap2.setVisibility(View.VISIBLE);
//                                }


                                break;

                            case R.id.lookTimes:
                                Log.e("点击", "noticeWrap---p跳转" + pos);
                                com.rey.material.app.BottomSheetDialog bottomInterPasswordDialog = new com.rey.material.app.BottomSheetDialog(ProjectNotice.this.getContext());
                                bottomInterPasswordDialog.setContentView(R.layout.project_notice_dialog);
                                bottomInterPasswordDialog.inDuration(300);
                                bottomInterPasswordDialog.outDuration(200);
//                                bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                                bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                                bottomInterPasswordDialog.show();

                                RecyclerView mRecyclerView = (RecyclerView) bottomInterPasswordDialog.findViewById(R.id.recyclerview);
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(ProjectNotice.this.getContext()));
                                mRecyclerView.setAdapter(new ProjectNotice.MyRecyclerViewAdapter(dataList.getData().getData().get(pos).getTimeList()));
                                break;
                        }
                        projectAdapter.notifyDataSetChanged();

                    }
                });
            }
        });
    }



//    隐藏显示加载
    public void showFooter(boolean show) {
        isShowlistViewFooter = show;
    }

    private void setListViewHeightBasedOnChildren(PagedListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); //获得Adapter
        if (listAdapter == null) {  //判断是否为空
            return;
        }
        int totalHeight = 0;  //定义总高度
        //根据listAdapter.getCount()获取当前拥有多少个item项，然后进行遍历对每一个item获取高度再相加最终获得总的高度。
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //获取到list的布局属性
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //listview最终高度为item的高度+分隔线的高度，这是重新设置listview的属性
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//将重新设置的params再应用到listview中
        listView.setLayoutParams(params);
    }


    public class MyRecyclerViewAdapter extends BaseQuickAdapter<ProjecItem.DataBean.TimeList, BaseViewHolder> {

        public MyRecyclerViewAdapter(@Nullable List<ProjecItem.DataBean.TimeList> data) {
            super(R.layout.project_notice_dialog_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, ProjecItem.DataBean.TimeList dataBean) {

            String day = Tools.stampToDate(dataBean.getEndTime(), "DD");
            ((TextView) baseViewHolder.getView(R.id.noticeDay)).setText(day);
            String startDate = Tools.stampToDate(dataBean.getStartTime(), "HH:MM");
            ((TextView) baseViewHolder.getView(R.id.startDate)).setText(startDate);
            String endDate = Tools.stampToDate(dataBean.getEndTime(), "HH:MM");
            ((TextView) baseViewHolder.getView(R.id.endDate)).setText(endDate);

        }

    }

}
