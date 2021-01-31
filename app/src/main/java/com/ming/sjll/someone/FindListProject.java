package com.ming.sjll.someone;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.My.adapter.CollectSampleListAdapter;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.someone.adapter.FindProjectItemAdapter;

/**
 * 图片找人-项目
 */
public class FindListProject extends BaseFragment {

    RecyclerView listView;

    private String type = "project";
    private String path;
    private boolean isLoad = false;

    private ProjectListItem.DataBeanX dataList;
    private FindProjectItemAdapter listAdapter;

    public static FindListProject newInstance() {
        return new FindListProject();
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_collect_user);
////        path  = getIntent().getStringExtra("path");
//
//        listView = (RecyclerView)findViewById(R.id.recyclerList);
//    }
//
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_collect_user);
//        path  = getIntent().getStringExtra("path");
        listView = (RecyclerView)findViewById(R.id.recyclerList);
    }

    public void showView(String path)
    {
        if(path.equals(this.path))
        {
            return;
        }
        this.path = path;
        initGetList();
    }


    private void initGetList() {
        if (isLoad) {
            return;
        }

        isLoad = true;
        if(loadingDailog != null)
        {
            loadingDailog.show();
        }

        HttpParamsObject param = new HttpParamsObject();

        param.setParam("type", type);
        param.setParam("imgUrl", path);

        new HttpUtil().sendRequest(Constant.SEARCH_SEARCH_IMG, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {


                dataList = new Gson().fromJson(data, ProjectListItem.DataBeanX.class);
//                for (int i = 0; i < 20; i++) {
//                    dataList.getData().add(dataList.getData().get(0));
//                }
                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new FindProjectItemAdapter(dataList.getData(),false);
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                        switch (view.getId())
                        {
                            case R.id.projectWrap:
                                Bundle bundle = new Bundle();
                                bundle.putString("projectId", dataList.getData().get(position).getId());
                                Tools.jump(getActivity(), FindListProjectInfo.class, bundle, false);
                                break;
                        }

                    }
                });
                if(loadingDailog != null) {
                    loadingDailog.hide();
                }
                isLoad = false;
            }
        });
    }



    private void initView()
    {

    }







}
