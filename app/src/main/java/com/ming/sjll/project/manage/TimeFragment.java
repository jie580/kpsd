package com.ming.sjll.project.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.project.manage.adapter.TeamOccupationUserAdapter;
import com.ming.sjll.project.manage.adapter.TimeUserMiddleAdapter;
import com.ming.sjll.search.SearchMemberProjectMultipleActivity;
import com.ming.sjll.search.SearchOccupationFragment;

/**
 * 时间安排
 */
public class TimeFragment extends BaseFragment {


    OccupationList commOccList;
    GetInfoDateListBean dataList;
    LinearLayout horizontalOccupation;

    String projectId;

    private RecyclerView listView;
    TimeUserMiddleAdapter timeUserMiddleAdapter;

    public static TimeFragment newInstance(String project_id) {
        TimeFragment fragment = new TimeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("projectId", project_id);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.project_manage_team);
        initView();
    }

    private void initView() {
        projectId = getArguments().getString("projectId");

        getData();

        horizontalOccupation = (LinearLayout) findViewById(R.id.horizontalOccupation);
        listView = (RecyclerView) findViewById(R.id.listView);
//
//        getOccupation();
//        getOccupationUserList();
    }

    private void getData()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId", projectId);
        new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_MEMBER_DATE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, GetInfoDateListBean.class);
                initData();

            }
        });
    }
    private void initData()
    {
        boolean is_conflict = false;

        for (int i = 0; i < dataList.getData().size(); i++) {
            if(dataList.getData().get(i).getIs_conflict().equals("true"))
            {
                is_conflict = true;
            }
        }
        if(is_conflict)
        {
            ((ImageView)findViewById(R.id.add_occupation)).setVisibility(View.VISIBLE);
            ((ImageView)findViewById(R.id.add_occupation)).setBackgroundResource(R.mipmap.ic_not_time);
        }
        else
        {
            ((ImageView)findViewById(R.id.add_occupation)).setVisibility(View.GONE);
        }
        initHorizontal();
    }

    private void initHorizontal() {
        horizontalOccupation.removeAllViews();
        for (int j = 0; j < dataList.getData().size(); j++) {
//            if (!dataList.getData().get(j).isIs_select()) {
//                continue;
//            }
            View listViewTemp = LayoutInflater.from(getContext()).inflate(R.layout.item_images_circular_50, null);
            listViewTemp.setTag(dataList.getData().get(j).getUid());
            new ImageHelper().displayBackgroundLoading(listViewTemp.findViewById(R.id.image),
                    Constant.BASE_API + dataList.getData().get(j).getDefault_avatar());

            listViewTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = v.getTag().toString();
                    for (int i = 0; i < dataList.getData().size(); i++) {
                        if (dataList.getData().get(i).getUid().equals(id)) {
                            findViewById(R.id.svscrollouter).scrollTo(0, listView.getChildAt(i).getTop());
                        }

                    }
                }
            });

            horizontalOccupation.addView(listViewTemp);
        }

        initVertical();
    }

    private void initVertical()
    {
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        timeUserMiddleAdapter = new TimeUserMiddleAdapter(dataList.getData(), getActivity(), projectId);
        listView.setAdapter(timeUserMiddleAdapter);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            getData();
        }

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(getActivity(), R.layout.project_manage_team);
////        setContentView(R.layout.project_manage_team);
////        initView();
//    }

}
