package com.ming.sjll.My.common;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBeanList;
import com.ming.sjll.My.adapter.FansListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;

/**
 * 个人中心-收藏-课程
 */
public class CollectCourse extends BaseFragment {

    private  static final String type = "user";
    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private GetInfoBeanList dataList;
    private FansListAdapter listAdapter;
    RecyclerView listView;

    public static CollectCourse newInstance() {
        return new CollectCourse();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_collect_user);

        initView();
    }

    private void initView() {
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
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        param.setParam("type", type);
        new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECTION, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, GetInfoBeanList.class);
//                LinearLayoutManager linearmanager = new LinearLayoutManager(getContext());
                listView.setLayoutManager(new LinearLayoutManager(getContext()){

                    @Override

                    public boolean canScrollVertically() {

                        return false;
                    }
                });
                listAdapter = new FansListAdapter(dataList.getData().getData(),true);
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId())
                        {
                            case R.id.delete:
                                ToastShow.s("删除");
                                break;
                            case R.id.wrap:
                                ToastShow.s("点击用户");
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
                GetInfoBeanList temgData = new Gson().fromJson(data, GetInfoBeanList.class);
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
