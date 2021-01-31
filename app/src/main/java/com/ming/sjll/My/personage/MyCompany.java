package com.ming.sjll.My.personage;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.MyCompanyBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.adapter.FansListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.ui.CustomRoundAngleImageView;

import org.raphets.roundimageview.RoundImageView;

/**
 * 我的公司
 */
public class MyCompany extends BaseActivity {

    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private MyCompanyBean companyData;
    private FansListAdapter listAdapter;
    TextView fansCount;
    RecyclerView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_join_company);
        initView();
    }

    private void initView() {
//        fansCount = (TextView) findViewById(R.id.fansCount);
        listView = (RecyclerView) findViewById(R.id.recyclerList);
//        fansCount.setText(MainActivity.userInfo.getData().getCollect_num());
        getData();

        findViewById(R.id.quitCompany).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                退出公司
                //                提示
                DialogTextTip dialog = new DialogTextTip(MyCompany.this, "您将退出公司 "+companyData.getData().getFull_company_name()+", 是否继续操作？");
                dialog.setTextColor(6,7+companyData.getData().getFull_company_name().length());
                dialog.show(new CommonCallback() {
                    @Override
                    public void onNext() {
                        super.onNext();

                        HttpParamsObject param = new HttpParamsObject();
                        new HttpUtil().sendRequest(Constant.COMPANY_LEAVE_COMPANY, param, new CommonCallback() {
                            @Override
                            public void onSuccessJson(String data, int code) {
                                MainActivity.updateUserInfo("company_name","");
                                MyCompany.this.finish();
                            }
                            @Override
                            public void onFinal() {
                                super.onFinal();

                            }
                        });

                    }
                });


            }
        });
    }

    private void getData() {
        CustomRoundAngleImageView backdrop = findViewById(R.id.backdrop);
        RoundImageView headImage = findViewById(R.id.headImage);
        TextView homeName = findViewById(R.id.homeName);
        ImageView is_approve = findViewById(R.id.is_approve);
        if (isLoad) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.COMPANY_MY_COMPANY, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                companyData = new Gson().fromJson(data, MyCompanyBean.class);

//            填充信息
                new ImageHelper().displayBackgroundLoading(headImage,
                        Constant.BASE_API + companyData.getData().getDefault_avatar());
                new ImageHelper().displayBackgroundLoading(backdrop,
                        Constant.BASE_API + companyData.getData().getCover_img());
                homeName.setText(companyData.getData().getFull_company_name());
                if (companyData.getData().getIs_approve().equals("1")) {
                    is_approve.setVisibility(View.VISIBLE);
                } else {
                    is_approve.setVisibility(View.GONE);
                }

//                LinearLayoutManager linearmanager = new LinearLayoutManager(getContext());
                listView.setLayoutManager(new LinearLayoutManager(MyCompany.this) {
                    @Override
                    public boolean canScrollVertically() {

                        return false;
                    }
                });
                listAdapter = new FansListAdapter(companyData.getData().getMemberList());
                listView.setAdapter(listAdapter);
                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        ToastShow.s("跳转个人页面"+companyData.getData().getMemberList().get(position).getUser_id());
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


}
