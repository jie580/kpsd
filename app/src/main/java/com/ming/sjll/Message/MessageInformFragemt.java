package com.ming.sjll.Message;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ming.sjll.Bean.InformBean;
import com.ming.sjll.Message.adapter.InformAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.search.bean.ProjectListItem;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * 消息
 */
public class MessageInformFragemt extends BaseFragment {

    public static MessageInformFragemt newInstance() {
        return new MessageInformFragemt();
    }

    InformBean dataList;
    private int page = 1;
    private int pageSize = 5;
    private boolean isLoad = false;
    RecyclerView recyclerList;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_collect_user);
        initView();

    }

    private void initView(){

        recyclerList = (RecyclerView)findViewById(R.id.recyclerList);
        getData();
    }


    private void getData()
    {
        if (isLoad) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("page", page);
        param.setParam("pageSize", pageSize);
        page=0;
        new HttpUtil().sendRequest(Constant.INFORM_SYS_MSG, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataList = new Gson().fromJson(data, InformBean.class);
                InformAdapter informAdapter = new InformAdapter(dataList.getData().getData());
                recyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerList.setAdapter(informAdapter);
            }

            @Override
            public void onFinal() {
                super.onFinal();
                isLoad = false;
            }
        });
    }


}
