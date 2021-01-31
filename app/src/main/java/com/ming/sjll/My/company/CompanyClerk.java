package com.ming.sjll.My.company;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GetInfoBeans;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.CompanyClerkListAdapter;
import com.ming.sjll.My.adapter.GoodsListAdapter;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.My.personage.PersonageOccupation;
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
import com.ming.sjll.search.CompanyFragment;
import com.ming.sjll.search.CompanyMemberFragment;
import com.ming.sjll.toolPage.LoadingDailog;
import com.ming.sjll.ui.CircleImageView;
import com.ming.sjll.ui.CustomRoundAngleImageView;

import java.util.LinkedList;
import java.util.List;

/**
 * 个人中心-公司-职员
 */
public class CompanyClerk extends BaseActivity {


    CustomRoundAngleImageView backdrop;

    GetInfoBeans dataList;
    List<GetInfoBean.DataBean> selectData;
    CompanyClerkListAdapter listAdapter;
    RecyclerView listView;
    Button submitBtnNone, submitBtn;
    RelativeLayout searchWrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_company_clerk);

        initView();
        event();

    }

    private void event() {
//        跳转搜索
        searchWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyClerk.this, CompanyMemberFragment.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("comeType",2);
                intent.putExtra("comeType",2);
                startActivityForResult(intent, 1); // 这里请求码唯一就可以，我这里是
//                Tools.jump(CompanyClerk.this, CompanyMemberFragment.class,bundle, false);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dataList.getData().removeAll(selectData);
//                selectData = new LinkedList<>();
//                changeBtn();
//                listAdapter.notifyDataSetChanged();
                loadingDailog.show();
                List<String> strings = new LinkedList<>();
                for (int i = 0; i < selectData.size(); i++) {
                    strings.add(selectData.get(i).getApply_id());
                }

                HttpParamsObject param = new HttpParamsObject();
                param.setParam("apply_id", strings);
                new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_DEL_MEMBER, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {

//                        for (int i = 0; i < selectData.size(); i++) {
//                            dataList.getData().remove(selectData.get(i));
//                        }
                        dataList.getData().removeAll(selectData);
                        selectData = new LinkedList<>();
                        changeBtn();
                        listAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.show();
                    }
                });
            }

        });
    }

    private void initView() {
        backdrop = (CustomRoundAngleImageView) findViewById(R.id.backdrop);
        new ImageHelper().displayBackgroundLoading(backdrop,
                Constant.BASE_IMAGE + MainActivity.userInfo.getData().getCover_img());
        listView = (RecyclerView) findViewById(R.id.recyclerList);
        submitBtnNone = (Button) findViewById(R.id.submitBtnNone);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        searchWrap = (RelativeLayout) findViewById(R.id.searchWrap);

        getList();
    }

    private void getList() {
        HttpParamsObject param = new HttpParamsObject();

        new HttpUtil().sendRequest(Constant.COMPANY_MEMBER_MEMBER_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, GetInfoBeans.class);
//                for (int i = 0; i < 20; i++) {
//                    dataList.getData().add(dataList.getData().get(0));
//                }
                listView.setLayoutManager(new GridLayoutManager(CompanyClerk.this, 4));
                listAdapter = new CompanyClerkListAdapter(dataList.getData());
                listView.setAdapter(listAdapter);
                selectData = new LinkedList<>();

                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        String s = dataList.getData().get(position).getStatus();
                        String id = dataList.getData().get(position).getUser_id();
                        String id2 = MainActivity.userInfo.getData().getUser_id();
                        if (dataList.getData().get(position).getStatus().equals("0")) {
                            ToastShow.s("审核中不可以移除");
                            return;
                        } else if (dataList.getData().get(position).getUser_id().equals(MainActivity.userInfo.getData().getUser_id())) {
                            ToastShow.s("不可移除当前账户");
                            return;
                        }
                        if (dataList.getData().get(position).isSelect()) {
                            dataList.getData().get(position).setSelect(false);
                            selectData.remove(dataList.getData().get(position));
                        } else {
                            dataList.getData().get(position).setSelect(true);
                            selectData.add(dataList.getData().get(position));
                        }
                        changeBtn();
                        listAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }


    private void changeBtn() {
        if (selectData.size() > 0) {
            submitBtn.setVisibility(View.VISIBLE);
            submitBtnNone.setVisibility(View.GONE);
        } else {
            submitBtn.setVisibility(View.GONE);
            submitBtnNone.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getList();
    }

}
