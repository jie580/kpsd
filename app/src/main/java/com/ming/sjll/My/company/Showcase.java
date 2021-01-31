package com.ming.sjll.My.company;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.ShowCaseItemBean;
import com.ming.sjll.Bean.ShowCaseLIstBean;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.ShowCaseListAdapter;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.Show.adapter.ShowAdapter;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.event.GoodsClassEditEvent;
import com.ming.sjll.base.event.GoodsEditEvent;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 个人中心-橱窗
 */
public class Showcase extends BaseFragment {


    RecyclerView listView;
    ShowCaseListAdapter listAdapter;

    public int point;

    public String company_id;

    public static Showcase newInstance() {
        return new Showcase();
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_show_case);
        EventBus.getDefault().register(this);
        initView();
//        event();
    }


    private void initView() {
        if (getParentFragment() instanceof Myfragemt) {
            ((Myfragemt) getParentFragment()).setChildObjectForPosition(this,point);
        }
        else {
            ((HomepageActivity) getActivity()).setChildObjectForPosition(this,point);
        }
        listView = (RecyclerView) findViewById(R.id.recyclerList);
        getList();

    }

    public void getList()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("company_id", company_id);
        new HttpUtil().sendRequest(Constant.SHOW_CASE_COMPANY_SHOW, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                ShowCaseLIstBean showCaseItemBean = new Gson().fromJson(data, ShowCaseLIstBean.class);
                listView.setLayoutManager( new LinearLayoutManager(getContext()));
                listAdapter = new ShowCaseListAdapter(showCaseItemBean.getData());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("class_id", showCaseItemBean.getData().get(position).class_id);
                        bundle.putString("company_id", company_id);
                        bundle.putString("type", showCaseItemBean.getData().get(position).type);
//                        bundle.putString("icon", showCaseItemBean.getData().get(position).icon);
                        Tools.jump(getActivity(), ShowcaseListActivity.class, bundle, false);
//                        ToastShow.s("点击类目："+showCaseItemBean.getData().get(position).class_id);
                    }
                });
            }
        });

    }


    /**
     *
     *
     * @param e
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GoodsClassEditEvent e) {
        getList();
    }
//
//    public void nextPage()
//    {
//        Log.e("Works","nexPagenexPagenexPagenexPagenexPagenexPagenexPagenexPage");
//
//    }


}
