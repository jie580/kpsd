package com.ming.sjll.My.homepage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GetInfoBeanList;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.adapter.FansListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;

/**
 * 我的粉丝
 */
public class HomeFans extends BaseFragment {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private GetInfoBeanList dataList;
    private FansListAdapter listAdapter;
    TextView fansCount;
    RecyclerView listView;


    public GetInfoBean userInfo;

    public static HomeFans newInstance() {
        return new HomeFans();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
//        EventBus.getDefault().register(this);
        setContentView(R.layout.my_fans_frame);

        initView();
    }

    private void initView()
    {
        fansCount = (TextView) findViewById(R.id.fansCount);
        listView = (RecyclerView) findViewById(R.id.recyclerList);


        fansCount.setText(userInfo.getData().getCollect_num());
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
        param.setParam("user_id",userInfo.getData().getUser_id());
        new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECT_USER, param, new CommonCallback() {
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
                listAdapter = new FansListAdapter(dataList.getData().getData(), getContext());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", dataList.getData().getData().get(position).getUser_id());
                        Tools.jump(getActivity(), HomepageActivity.class, bundle,false);
//                        ToastShow.s("跳转个人页面");
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
        new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECT_USER, param, new CommonCallback() {
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
