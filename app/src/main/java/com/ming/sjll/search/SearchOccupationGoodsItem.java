package com.ming.sjll.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GoodsListItem;
import com.ming.sjll.My.adapter.GoodsListAdapter;
import com.ming.sjll.My.company.ShowcaseInfoActivity;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.MyUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.ui.PaginationScrollListener;

/**
 * 图片找人-人员
 */
public class SearchOccupationGoodsItem extends BaseFragment implements OccupationImplements{

    RecyclerView listView;

    private String type = "user";
    private String search;
    private String occupation_id;
    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 5;


    private GoodsListItem dataList;
    private GoodsListAdapter listAdapter;

    public static SearchOccupationGoodsItem newInstance() {
        return new SearchOccupationGoodsItem();
    }

//    View rootView;
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (null != rootView) {
//            ViewGroup parent = (ViewGroup) rootView.getParent();
//            if (null != parent) {
//                parent.removeView(rootView);
//            }
//        } else {
//           View rootView = inflater.inflate(R.layout.my_collect_user, null);
////            setupView(rootView);// 控件初始化
//            listView = (RecyclerView)rootView.findViewById(R.id.recyclerList);
//        }
//        return rootView;
//    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_collect_user);
        listView = (RecyclerView) findViewById(R.id.recyclerList);

    }

    HttpParamsObject returnParam = new HttpParamsObject();
    HttpParamsObject searchParam = new HttpParamsObject();
    public void searchKey(HttpParamsObject param, boolean isSearch, CommonCallback cb , HttpParamsObject itemClickParam)
    {
        if(returnParam.getUrlParam().equals(param.getUrlParam()))
        {
            cb.onFinal();
            return;
        }
        returnParam = MyUtil.cloneObject(param);
        searchParam = MyUtil.cloneObject(param);
        initGetList(searchParam,isSearch,cb);
    }
//    public void searchKey(String search, String occupation_id, String type) {
//
//        if (occupation_id.equals(this.occupation_id) && search.equals(this.search) && type.equals(this.type)) {
//            return;
//        }
//        this.occupation_id = occupation_id;
//        this.search = search;
//        this.type = type;
//        initGetList();
//    }


    LinearLayoutManager linearLayoutManager;

    private void initGetList(HttpParamsObject param, boolean isSearch, CommonCallback cb) {
        if (isLoad) {
            return;
        }
        isLoad = true;
//        if (loadingDailog != null) {
//            loadingDailog.show();
//        }
//        searchParam = param;
//        HttpParamsObject param = new HttpParamsObject();
        page = 1;
//        if (type.equals("all")) {
//            param.setParam("search", search);
//        }
//        param.setParam("type", type);
//        param.setParam("occupation_id", occupation_id);
        searchParam.setParam("page", page);
        searchParam.setParam("pageSize", pageSize);

        new HttpUtil().sendRequest(Constant.SEARCH_GOODS, searchParam, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, GoodsListItem.class);
                if (dataList.getData().getData().size() <= 0 && !isSearch) {
                    if(cb != null) {
                        cb.onSuccessJson(data, code);
                    }
                    return;
                }
//                for (int i = 0; i < 20; i++) {
//                    dataList.getData().add(dataList.getData().get(0));
//                }

                listAdapter  = new GoodsListAdapter(dataList.getData().getData(), false, true);
                linearLayoutManager = new GridLayoutManager( getActivity(), 2);
                listView.setLayoutManager(linearLayoutManager);
                listView.setAdapter(listAdapter);

                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        switch (view.getId()) {
                            case R.id.wrap:
//                               查看详情
                                Intent intent = new Intent(getActivity(), ShowcaseInfoActivity.class);
                                intent.putExtra("goodsId", dataList.getData().getData().get(position).getGoods_id());
                                startActivity(intent);
                                break;
                            case R.id.userWrap:
                                Bundle bundle = new Bundle();
                                bundle.putString("userId", dataList.getData().getData().get(position).getUser_id());
                                Tools.jump(getActivity(), HomepageActivity.class, bundle,false);
                                break;
                            case R.id.collect:
                                loadingDailog.show();
                                HttpParamsObject param = new HttpParamsObject();
                                param.setParam("targetId",dataList.getData().getData().get(position).getGoods_id());
                                param.setParam("type","goods");
                                new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECT, param, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {
                                        GetInfoBean temp = new Gson().fromJson(data, GetInfoBean.class);
                                        dataList.getData().getData().get(position).setIs_collect(temp.getData().getIs_collect());
                                        listAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFinal() {
                                        super.onFinal();
                                        loadingDailog.hide();
                                    }
                                });
                                break;
                        }
                    }
                });



                listView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
                    @Override
                    protected void loadMoreItems() {
                        nextPage();
                    }

                    @Override
                    public int getTotalPageCount() {
                        return dataList.getData().getTotal();
                    }

                    @Override
                    public boolean isLastPage() {
                        return page == dataList.getData().getLastPage();
                    }

                    @Override
                    public boolean isLoading() {
                        return isLoad;
                    }
                });

                if(cb != null) {
                    cb.onSuccessJson(data, code);
                }
            }

            @Override
            public void onFinal() {
                super.onFinal();

//                if (loadingDailog != null) {
//                    loadingDailog.hide();
//                }
                isLoad = false;
                if(cb != null)
                {
                    cb.onFinal();
                }

            }
        });


    }

    private void nextPage() {
        if (isLoad) {
            return;
        }
        if (loadingDailog != null) {
            loadingDailog.show();
        }
        HttpParamsObject param = new HttpParamsObject();
        page++;
//        if (type.equals("all")) {
//            param.setParam("search", search);
//        }
//        param.setParam("type", type);
//        param.setParam("occupation_id", occupation_id);
        searchParam.setParam("page", page);
        searchParam.setParam("pageSize", pageSize);

        new HttpUtil().sendRequest(Constant.SEARCH_GOODS, searchParam, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                GoodsListItem tempDataList = new Gson().fromJson(data, GoodsListItem.class);
                for (int i = 0; i < tempDataList.getData().getData().size(); i++) {
                    dataList.getData().getData().add(tempDataList.getData().getData().get(i));
                }
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFinal() {
                super.onFinal();

                if (loadingDailog != null) {
                    loadingDailog.hide();
                }
                isLoad = false;

            }
        });

    }


}
