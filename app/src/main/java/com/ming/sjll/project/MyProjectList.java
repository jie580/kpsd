package com.ming.sjll.project;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogProjectSelect;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.project.manage.HomeActivity;
import com.ming.sjll.search.ProjectProject;
import com.ming.sjll.search.adapter.ProjectListItemAdapter;
import com.ming.sjll.search.bean.ProjecItem;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.ui.PagedListView;
import com.rey.material.app.BottomSheetDialog;

import java.util.List;

/**
 * 搜素的项目列表（综合）
 */

public class MyProjectList extends BaseFragment {

    private View parentContentView;

    private PagedListView listView;
    private ProjectListItemAdapter projectAdapter;
    private ProjectListItem dataList;

    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 5;
    private int type = 1;//0- 未接单 1-已接单

    private boolean isShowlistViewFooter = true;

    public static MyProjectList newInstance() {

        return new MyProjectList();
    }
//
//    @SuppressLint("ValidFragment")
//    public ProjectProject(View parentContentView) {
//        this.parentContentView = parentContentView;
//    }
//
//    public ProjectProject() {
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


    public void setType(int type) {
        this.type = type;
    }

    public void searchKey(String s) {

//        if(this.parentContentView == null)
//        {
//            return;
//        }
//        EditText searchEdit = (EditText) parentContentView.findViewById(R.id.searchEdit);
//        if(searchEdit != null)
//        searchEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                search = s.toString();
//                page = 1;
//                initView();
//            }
//        });
    }


    int lastPos;

    private void initView() {

        listView = (PagedListView) findViewById(R.id.control_main_listview);
        listView.setSelector(new ColorDrawable());//设置默认状态选择器为全透明，不传颜色就是没颜色
        listView.showFooter(isShowlistViewFooter);
//
//
//        setListViewHeightBasedOnChildren(listView);
        //                点击事件

        getList();
    }

    private void nextPage()
    {
        if (isLoad) {
            return;
        }
        isLoad = true;

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("page", page);
        param.setParam("type", type);
        param.setParam("pageSize", pageSize);
        page++;
        new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_MY_PROJECT, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                ProjectListItem dataList2 = new Gson().fromJson(data, ProjectListItem.class);

                for (int i = 0; i < dataList2.getData().getData().size(); i++) {
                    dataList.getData().getData().add(dataList2.getData().getData().get(i));
                }
                projectAdapter.notifyDataSetChanged();
                if (dataList2.getData().getCurrentPage() >= dataList2.getData().getLastPage()) {
                    listView.setNoData();
                }
                isLoad = false;
                listView.onLoadComplete();
                setListViewHeightBasedOnChildren(listView);

            }
        });
    }

    public void getList() {
        if (isLoad) {
            return;
        }
        isLoad = true;

        HttpParamsObject param = new HttpParamsObject();
        param.setParam("page", page);
        param.setParam("type", type);
        param.setParam("pageSize", pageSize);
        page++;
        new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_MY_PROJECT, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                ProjectListItem dataList2 = new Gson().fromJson(data, ProjectListItem.class);

                if (projectAdapter == null) {
                    dataList = dataList2;
                    projectAdapter = new ProjectListItemAdapter(MyProjectList.this.getContext(), dataList.getData().getData(), false);
                    listView.setAdapter(projectAdapter);
                }
                else
                {
                    for (int i = 0; i < dataList2.getData().getData().size(); i++) {
                        dataList.getData().getData().add(dataList2.getData().getData().get(i));
                    }
                }

                projectAdapter.notifyDataSetChanged();
                if (dataList2.getData().getCurrentPage() >= dataList2.getData().getLastPage()) {
                    listView.setNoData();
                }

                isLoad = false;
                listView.onLoadComplete();
                setListViewHeightBasedOnChildren(listView);


                projectAdapter.setOnClickListenerInterface(new ProjectListItemAdapter.setOnClickListener() {

                    @Override
                    public void onClick(int pos, @IdRes int id, View v) {
                        switch (id) {
                            case R.id.projectWrap:
                                Log.e("点击", "点击了rc_wrap" + pos);
                                clickProject(dataList.getData().getData().get(pos),pos);
                                break;
                        }


                    }
                });

                //                分页加载数据
                listView.setListViewLoadInterface(new PagedListView.ListViewLoadListener() {
                    @Override
                    public void loadData() {
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                nextPage();
                            }
                        }, 0);
                    }
                });


            }
        });

    }


    private void clickProject(ProjecItem.DataBean bean,int pos)
    {
        if(type == 1)
        {
            DialogProjectSelect dialogProjectSelect = new DialogProjectSelect();
            dialogProjectSelect.init(getActivity());
            dialogProjectSelect.setMainId(bean.getId());
            dialogProjectSelect.setChatId(bean.getId());
            dialogProjectSelect.setChatName(bean.getDemand());



            dialogProjectSelect.showOpenChat();
            dialogProjectSelect.showOpenChatLine();

            dialogProjectSelect.showProjectManage();
            dialogProjectSelect.showProjectManageLine();

            dialogProjectSelect.showFlow();
            dialogProjectSelect.showFlowLine();

            dialogProjectSelect.setLook(true);
            dialogProjectSelect.showProjectInfo();


            dialogProjectSelect.show();
            return;

        }
        else
        {
            DialogProjectSelect dialogProjectSelect = new DialogProjectSelect();
            dialogProjectSelect.init(getActivity());
            dialogProjectSelect.setMainId(bean.getId());
            dialogProjectSelect.setChatId(bean.getId());
            dialogProjectSelect.setChatName(bean.getDemand());


            dialogProjectSelect.showEdit();
            dialogProjectSelect.showEditLine();

            dialogProjectSelect.showDeleteProject();

            dialogProjectSelect.show();
            dialogProjectSelect.setCommonCallback(new CommonCallback(){
                @Override
                public void onSuccess(Object data, int code) {
                    super.onSuccess(data, code);
                    if(code == DialogProjectSelect.EDIT)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("id",bean.getId());
                        bundle.putBoolean("isLook",false);
                        Tools.jump(getActivity(), ReleaseProject.class, bundle,false);
                        dialogProjectSelect.hide();
                    }
                    else {

                        HttpParamsObject param = new HttpParamsObject();
                        param.setParam("projectId", bean.getId());
                        new HttpUtil().sendRequest(Constant.PROJECT_DEL_PROJECT, param, new CommonCallback() {
                            @Override
                            public void onSuccessJson(String data, int code) {
                                    dataList.getData().getData().remove(pos);
                                    projectAdapter.notifyDataSetChanged();

                            }
                        });
                    }

                }
            });

        }
//        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
//        bottomInterPasswordDialog.setContentView(R.layout.dialog_project_click_select);
//        bottomInterPasswordDialog.inDuration(300);
//        bottomInterPasswordDialog.outDuration(200);
//
//        bottomInterPasswordDialog.heightParam(ViewGroup.LayoutParams.MATCH_PARENT);
//
//
//        bottomInterPasswordDialog.findViewById(R.id.wrap).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bottomInterPasswordDialog.cancel();
//            }
//        });
//
//        bottomInterPasswordDialog.findViewById(R.id.guide1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RongImUtils.groupChat(getActivity(),bean.getId(),bean.getDemand());
//                bottomInterPasswordDialog.cancel();
//            }
//        });
//
//        bottomInterPasswordDialog.findViewById(R.id.guide2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("id",bean.getId());
//                Tools.jump(getActivity(), ReleaseProject.class, bundle,false);
//                bottomInterPasswordDialog.cancel();
//            }
//        });
//
//
//        bottomInterPasswordDialog.findViewById(R.id.guide3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("projectId", bean.getId());
//                Tools.jump(getActivity(), HomeActivity.class, bundle, false);
//                bottomInterPasswordDialog.cancel();
//            }
//        });
//
//        bottomInterPasswordDialog.show();
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
