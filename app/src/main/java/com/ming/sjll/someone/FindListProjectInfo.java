package com.ming.sjll.someone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.ProjectSomeoneDataBean;
import com.ming.sjll.My.adapter.GoodsListAdapter;
import com.ming.sjll.My.company.ShowcaseInfoActivity;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.rey.material.app.BottomSheetDialog;

import butterknife.BindView;

/**
 * 图片找人-项目-详情
 */
public class FindListProjectInfo extends BaseActivity {

    @BindView(R.id.personList)
    LinearLayout personList;

    @BindView(R.id.siteRecyclerList)
    RecyclerView siteRecyclerList;

    GoodsListAdapter siteListAdapter;


    @BindView(R.id.goodsRecyclerList)
    RecyclerView goodsRecyclerList;


    @BindView(R.id.personTitle)
    FrameLayout personTitle;
    @BindView(R.id.siteTitle)
    FrameLayout siteTitle;
    @BindView(R.id.goodsTitle)
    FrameLayout goodsTitle;

    GoodsListAdapter goodsListAdapter;


    String projectId;

    ProjectSomeoneDataBean projectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.someone_project_info);
        projectId = getIntent().getStringExtra("projectId");
        initPersonList();
        event();
    }


    private void event()
    {
        personTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(FindListProjectInfo.this);
                bottomInterPasswordDialog.setContentView(R.layout.dialog_someone_person);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();
                LinearLayout dialogList = bottomInterPasswordDialog.findViewById(R.id.dialogList);

                for (int i = 0; i < projectData.getData().getUser().size(); i++) {
                    View userView = LayoutInflater.from(FindListProjectInfo.this).inflate(R.layout.someone_project_info_dialog_person, null);
                    LinearLayout personList = userView.findViewById(R.id.personList);
                    TextView title = userView.findViewById(R.id.title);
                    title.setText(projectData.getData().getUser().get(i).getTitle());

                    for (int j = 0; j < projectData.getData().getUser().get(i).getUserList().size(); j++) {
                        View userView2 = LayoutInflater.from(FindListProjectInfo.this).inflate(R.layout.someone_project_info_user, null);
                        userView2.setTag(projectData.getData().getUser().get(i).getUserList().get(j));
                        ((TextView) userView2.findViewById(R.id.name)).setText(projectData.getData().getUser().get(i).getUserList().get(j).getName());
                        new ImageHelper().displayBackgroundLoading(userView2.findViewById(R.id.userHead),
                                Constant.BASE_API + projectData.getData().getUser().get(i).getUserList().get(j).getDefault_avatar());
                        new ImageHelper().displayBackgroundLoading(userView2.findViewById(R.id.imageItemWrap),
                                Constant.BASE_API + projectData.getData().getUser().get(i).getIcon());
                        userView2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GetInfoBean.DataBean indexOf = (GetInfoBean.DataBean) v.getTag();
                                Bundle bundle = new Bundle();
                                bundle.putString("userId", indexOf.getUid());
                                Tools.jump(FindListProjectInfo.this, HomepageActivity.class, bundle, false);
                            }
                        });

                        personList.addView(userView2);
                    }

                    dialogList.addView(userView);

                }

            }
        });

        siteTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(FindListProjectInfo.this);
                bottomInterPasswordDialog.setContentView(R.layout.dialog_someone_person);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();
                LinearLayout dialogList = bottomInterPasswordDialog.findViewById(R.id.dialogList);
                View userView = LayoutInflater.from(FindListProjectInfo.this).inflate(R.layout.someone_project_info_dialog_goods, null);
                TextView title = userView.findViewById(R.id.title);
                title.setText("使用场地");
                RecyclerView siteRecyclerList = userView.findViewById(R.id.siteRecyclerList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(FindListProjectInfo.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                siteRecyclerList.setLayoutManager(layoutManager);
                siteRecyclerList.setAdapter(siteListAdapter);
                dialogList.addView(userView);
            }
        });


        goodsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(FindListProjectInfo.this);
                bottomInterPasswordDialog.setContentView(R.layout.dialog_someone_person);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();
                LinearLayout dialogList = bottomInterPasswordDialog.findViewById(R.id.dialogList);
                View userView = LayoutInflater.from(FindListProjectInfo.this).inflate(R.layout.someone_project_info_dialog_goods, null);
                TextView title = userView.findViewById(R.id.title);
                title.setText("使用道具");
                RecyclerView goodsRecyclerList = userView.findViewById(R.id.siteRecyclerList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(FindListProjectInfo.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                goodsRecyclerList.setLayoutManager(layoutManager);
                goodsRecyclerList.setAdapter(goodsListAdapter);
                dialogList.addView(userView);
            }
        });

    }

    private void initPersonList() {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId", projectId);
        new HttpUtil().sendRequest(Constant.SEARCH_PROJECT_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                projectData = new Gson().fromJson(data, ProjectSomeoneDataBean.class);

                for (int i = 0; i < projectData.getData().getUser().size(); i++) {
                    for (int j = 0; j < projectData.getData().getUser().get(i).getUserList().size(); j++) {


                    View userView = LayoutInflater.from(FindListProjectInfo.this).inflate(R.layout.someone_project_info_user, null);
                    userView.setTag(projectData.getData().getUser().get(i).getUserList().get(j));
                    ((TextView) userView.findViewById(R.id.name)).setText(projectData.getData().getUser().get(i).getUserList().get(j).getName());
                    new ImageHelper().displayBackgroundLoading(userView.findViewById(R.id.userHead),
                            Constant.BASE_API + projectData.getData().getUser().get(i).getUserList().get(j).getDefault_avatar());
                    new ImageHelper().displayBackgroundLoading(userView.findViewById(R.id.imageItemWrap),
                            Constant.BASE_API + projectData.getData().getUser().get(i).getIcon());
                    userView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GetInfoBean.DataBean indexOf = (GetInfoBean.DataBean) v.getTag();
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", indexOf.getUid());
                            Tools.jump(FindListProjectInfo.this, HomepageActivity.class, bundle, false);
                        }
                    });
                    personList.addView(userView);
                    }
                }

//
//                for (int i = 0; i < 20; i++) {
//                    projectData.getData().getSite().get(0).setTitle("方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方 ");
//                    projectData.getData().getSite().add(projectData.getData().getSite().get(0));
//                }
                siteListAdapter = new GoodsListAdapter(projectData.getData().getSite(),false);
//                siteRecyclerList.setLayoutManager(new LinearLayoutManager(FindListProjectInfo.this));

                LinearLayoutManager layoutManager = new LinearLayoutManager(FindListProjectInfo.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                siteRecyclerList.setLayoutManager(layoutManager);

                siteRecyclerList.setAdapter(siteListAdapter);
                siteListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                                查看详情
                        Intent intent = new Intent(FindListProjectInfo.this, ShowcaseInfoActivity.class);
                        intent.putExtra("goodsId", projectData.getData().getSite().get(position).getGoods_id());
                        startActivity(intent);
                    }
                });



//                for (int i = 0; i < 20; i++) {
//                    projectData.getData().getProps().get(0).setTitle("方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方方 ");
//                    projectData.getData().getProps().add(projectData.getData().getProps().get(0));
//                }
                goodsListAdapter = new GoodsListAdapter(projectData.getData().getProps(),false);
//                goodsRecyclerList.setLayoutManager(new LinearLayoutManager(FindListProjectInfo.this));

                LinearLayoutManager layoutManager2 = new LinearLayoutManager(FindListProjectInfo.this);
                layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
                goodsRecyclerList.setLayoutManager(layoutManager2);

                goodsRecyclerList.setAdapter(goodsListAdapter);
                goodsListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                                查看详情
                        Intent intent = new Intent(FindListProjectInfo.this, ShowcaseInfoActivity.class);
                        intent.putExtra("goodsId", projectData.getData().getProps().get(position).getGoods_id());
                        startActivity(intent);
                    }
                });



            }
        });
    }


}
