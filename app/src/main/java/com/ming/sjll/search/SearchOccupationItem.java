package com.ming.sjll.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.GetInfoBeanList;
import com.ming.sjll.Bean.StringDataListBean;
import com.ming.sjll.My.common.CollectGoods;
import com.ming.sjll.My.company.ShowcaseListActivity;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.MyUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.search.CompanyMemberFragment;
import com.ming.sjll.search.adapter.CompanyMemberListItemAdapter;
import com.ming.sjll.search.adapter.SearchOccupationItemAdapter;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.ming.sjll.ui.PagedListView;
import com.ming.sjll.ui.PaginationScrollListener;
import com.rey.material.app.BottomSheetDialog;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * 图片找人-人员
 */
public class SearchOccupationItem extends BaseFragment implements OccupationImplements{

    RecyclerView listView;

    private String type = "user";
    private String search;
    private String occupation_id;
    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 5;


    private GetInfoBeanList dataList;
    private SearchOccupationItemAdapter listAdapter;

    public static SearchOccupationItem newInstance() {
        return new SearchOccupationItem();
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
    HttpParamsObject itemClickParam;
    public void searchKey(HttpParamsObject param, boolean isSearch, CommonCallback cb , HttpParamsObject itemClickParam)
    {
        if(returnParam.getUrlParam().equals(param.getUrlParam()))
        {
            cb.onFinal();
            return;
        }
        returnParam = MyUtil.cloneObject(param);
        searchParam = MyUtil.cloneObject(param);
        this.itemClickParam = itemClickParam;
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

        new HttpUtil().sendRequest(Constant.SEARCH_SEARCHMEMBER, searchParam, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, GetInfoBeanList.class);
                if (dataList.getData().getData().size() <= 0 && !isSearch) {
                    if(cb != null) {
                        cb.onSuccessJson(data, code);
                    }
                    return;
                }
//                for (int i = 0; i < 20; i++) {
//                    dataList.getData().add(dataList.getData().get(0));
//                }

                listAdapter = new SearchOccupationItemAdapter(dataList.getData().getData());
                linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                listView.setLayoutManager(linearLayoutManager);
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if(itemClickParam.getStringMapParam("fromType").equals("projectMember"))
                        {

                            BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getActivity());
                            bottomInterPasswordDialog.setContentView(R.layout.dialog_project_add_member_type);
                            bottomInterPasswordDialog.inDuration(300);
                            bottomInterPasswordDialog.outDuration(200);
                            bottomInterPasswordDialog.findViewById(R.id.guide1menu).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(true);
                                    ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(false);
                                    ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).setChecked(false);
                                }
                            });
                            bottomInterPasswordDialog.findViewById(R.id.guide2menu).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(false);
                                    ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(true);
                                    ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).setChecked(false);
                                }
                            });
                            bottomInterPasswordDialog.findViewById(R.id.guide3menu).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(false);
                                    ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(false);
                                    ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).setChecked(true);
                                }
                            });
                            bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String serviceType = "0";
                                    if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).isChecked()) {
                                        serviceType = "1";
                                    } else if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).isChecked()) {
                                        serviceType = "2";
                                    } else if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).isChecked()) {
                                        serviceType = "3";
                                    } else {
                                        ToastShow.s("请选择服务方式");
                                        return;
                                    }
                                    itemClickParam.setParam("userId",dataList.getData().getData().get(position).getUid());
                                    itemClickParam.setParam("serviceType",serviceType);
                                    new HttpUtil().sendRequest(Constant.RC_COOPERATION, itemClickParam, new CommonCallback() {
                                        @Override
                                        public void onSuccessJson(String data, int code) {
                                            Intent intent = new Intent();
                                            getActivity().setResult(getActivity().RESULT_OK, intent);
                                            getActivity().finish();
                                        }
                                    });

                                }
                            });
                            bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    bottomInterPasswordDialog.hide();
                                }
                            });
                            bottomInterPasswordDialog.show();


                        }
                        else
                        {
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", dataList.getData().getData().get(position).getUid());
                            Tools.jump(getActivity(), HomepageActivity.class, bundle, false);
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

        new HttpUtil().sendRequest(Constant.SEARCH_SEARCHMEMBER, searchParam, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                GetInfoBeanList tempDataList = new Gson().fromJson(data, GetInfoBeanList.class);
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
