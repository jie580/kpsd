package com.ming.sjll.someone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.search.CompanyMemberFragment;
import com.ming.sjll.search.adapter.CompanyMemberListItemAdapter;
import com.ming.sjll.someone.adapter.FindPersonItemAdapter;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.ming.sjll.ui.PagedListView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * 图片找人-人员
 */
public class FindListPerson extends BaseFragment {

    RecyclerView listView;

    private String type = "user";
    private String path;
    private boolean isLoad = false;

    private GetInfoBeanList.DataBean dataList;
    private FindPersonItemAdapter listAdapter;

    public static FindListPerson newInstance() {
        return new FindListPerson();
    }

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
        if(loadingDailog != null) {
            loadingDailog.show();
        }
        HttpParamsObject param = new HttpParamsObject();

        param.setParam("type", type);
        param.setParam("imgUrl", path);

        new HttpUtil().sendRequest(Constant.SEARCH_SEARCH_IMG, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, GetInfoBeanList.DataBean.class);
//                for (int i = 0; i < 20; i++) {
//                    dataList.getData().add(dataList.getData().get(0));
//                }
                listAdapter = new FindPersonItemAdapter(dataList.getData());
                listView.setLayoutManager(new LinearLayoutManager(getActivity()));
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", dataList.getData().get(position).getUser_id());
                        Tools.jump(getActivity(), HomepageActivity.class, bundle, false);
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
