package com.ming.sjll.My.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GoodsListItem;
import com.ming.sjll.My.adapter.GoodsListAdapter;
import com.ming.sjll.My.company.ShowcaseInfoActivity;
import com.ming.sjll.My.company.ShowcaseListActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;

/**
 * 个人中心-收藏-商品
 */
public class CollectGoods extends BaseFragment {

    private  static final String type = "goods";
    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private GoodsListItem dataList;
    private GoodsListAdapter listAdapter;
    RecyclerView listView;

    public String userId;
    public static CollectGoods newInstance() {
        return new CollectGoods();
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
        param.setParam("user_id",userId);
        new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECTION, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, GoodsListItem.class);
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
                listView.setLayoutManager(new GridLayoutManager(mContext, 2));
                listAdapter = new GoodsListAdapter(dataList.getData().getData(),getIsShowDelete());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId())
                        {
                            case R.id.delete:
                                HttpParamsObject param2 = new HttpParamsObject();
                                param2.setParam("type",type);
                                param2.setParam("targetId",dataList.getData().getData().get(position).getGoods_id());
                                new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECT, param2, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {
                                        dataList.getData().getData().remove(position);
                                        listAdapter.notifyDataSetChanged();
                                    }
                                });
                                break;
                            case R.id.wrap:
//                                查看详情
                                Intent intent = new Intent(getActivity(), ShowcaseInfoActivity.class);
                                intent.putExtra("goodsId", dataList.getData().getData().get(position).getGoods_id());
                                startActivity(intent);
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

    private boolean getIsShowDelete()
    {
        if(userId == null || userId.equals(""))
        {
            return true;
        }
        return  false;

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
        param.setParam("user_id",userId);
        new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECTION, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                GoodsListItem temgData = new Gson().fromJson(data, GoodsListItem.class);
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
