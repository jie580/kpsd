package com.ming.sjll.project.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;

import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.project.ReleaseProjectCover;
import com.ming.sjll.project.manage.adapter.TeamOccupationAdapter;
import com.ming.sjll.project.manage.adapter.TeamOccupationUserAdapter;
import com.ming.sjll.search.SearchMemberProjectMultipleActivity;
import com.ming.sjll.search.SearchOccupationFragment;
import com.rey.material.app.BottomSheetDialog;

import java.util.LinkedList;
import java.util.List;

/**
 * 团队成员
 */
public class TeamFragment extends BaseFragment {


    OccupationList commOccList;
    OccupationList commOccUserList;
    LinearLayout horizontalOccupation;

    String projectId;

    private RecyclerView listView;
    TeamOccupationUserAdapter personageOccupationListAdapter;

    public static TeamFragment newInstance(String project_id) {
        TeamFragment fragment = new TeamFragment();
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
        event();
    }

    private void initView() {
        projectId = getArguments().getString("projectId");

        horizontalOccupation = (LinearLayout) findViewById(R.id.horizontalOccupation);
        listView = (RecyclerView) findViewById(R.id.listView);

        getOccupation();
        getOccupationUserList();
    }

    private void getOccupationUserList() {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId", projectId);
        new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_GET_OCCUPATION_USER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                commOccUserList = new Gson().fromJson(data, OccupationList.class);
                initOccupationUser();
            }
        });
    }

    private void initOccupationUser() {

        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        personageOccupationListAdapter = new TeamOccupationUserAdapter(commOccUserList.getData(), getActivity(), projectId);
        listView.setAdapter(personageOccupationListAdapter);

        personageOccupationListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.addMemberBtn) {
//                    添加人员
                    commOccUserList.getData().get(position).setShowShare(!commOccUserList.getData().get(position).isShowShare());
                } else if (view.getId() == R.id.addRecruitBtn) {
//                   招募
                    Intent intent = new Intent(getActivity(), TeamRecruitActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("projectId", projectId);
                    bundle.putString("occupationId", commOccUserList.getData().get(position).getOccupation_id());
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 44);

                } else if (view.getId() == R.id.btnEditReturn || view.getId() == R.id.btnEdit) {
//                    编辑按钮
                    commOccUserList.getData().get(position).setShowDelWarp(!commOccUserList.getData().get(position).isShowDelWarp());
                } else if (view.getId() == R.id.guide1) {
                    Intent intent = new Intent(getActivity(), SearchMemberProjectMultipleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("projectId", projectId);
                    bundle.putString("occupationId", commOccUserList.getData().get(position).getOccupation_id());
                    bundle.putString("listType", "project");
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 44);
                } else if (view.getId() == R.id.guide2) {
                    Intent intent = new Intent(getActivity(), SearchMemberProjectMultipleActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("projectId", projectId);
                    bundle.putString("occupationId", commOccUserList.getData().get(position).getOccupation_id());
                    bundle.putString("listType", "company ");
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 44);
                } else if (view.getId() == R.id.guide3) {

                } else if (view.getId() == R.id.guide4) {
                    Intent intent = new Intent(getActivity(), SearchOccupationFragment.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("projectId", projectId);
                    bundle.putString("fromType", "projectMember");
                    bundle.putString("occupationId", commOccUserList.getData().get(position).getOccupation_id());
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 44);
                } else if (view.getId() == R.id.deleteOccupation) {
                    DialogTextTip dialog = new DialogTextTip(mContext, "确定刪除职业 " + commOccUserList.getData().get(position).getTitle() + "?");
                    dialog.setTextColor(6, 7 + commOccUserList.getData().get(position).getTitle().length());
                    dialog.show(new CommonCallback() {
                        @Override
                        public void onNext() {
                            super.onNext();
                            loadingDailog.show();
//                    删除职业
                            HttpParamsObject param = new HttpParamsObject();
                            param.setParam("projectId", projectId);
                            param.setParam("occupationId", commOccUserList.getData().get(position).getOccupation_id());

                            new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_DEL_OCCUPATION, param, new CommonCallback() {
                                @Override
                                public void onSuccessJson(String data, int code) {

                                    commOccUserList.getData().remove(position);
                                    personageOccupationListAdapter.notifyDataSetChanged();
                                    dialog.hide();
                                }

                                @Override
                                public void onFinal() {
                                    super.onFinal();
                                    loadingDailog.hide();
                                }
                            });
                        }
                    });
                }


                personageOccupationListAdapter.notifyDataSetChanged();

            }
        });
    }

    private void getOccupation() {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId", projectId);
        new HttpUtil().sendRequest(Constant.PROFESSION_PROJECT_OCCUPATION, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                commOccList = new Gson().fromJson(data, OccupationList.class);
                initOccupation();
            }
        });
    }

    private void initOccupation() {
        horizontalOccupation.removeAllViews();
        for (int j = 0; j < commOccList.getData().size(); j++) {
            if (!commOccList.getData().get(j).isIs_select()) {
                continue;
            }
            View listViewTemp = LayoutInflater.from(getContext()).inflate(R.layout.item_images_circular_50, null);
            listViewTemp.setTag(commOccList.getData().get(j).getOccupation_id());
            new ImageHelper().displayBackgroundLoading(listViewTemp.findViewById(R.id.image),
                    Constant.BASE_API + commOccList.getData().get(j).getIcon());

            listViewTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = v.getTag().toString();
                    for (int i = 0; i < commOccUserList.getData().size(); i++) {
                        if (commOccUserList.getData().get(i).getOccupation_id().equals(id)) {
                            findViewById(R.id.svscrollouter).scrollTo(0, listView.getChildAt(i).getTop());
                        }

                    }
                }
            });

            horizontalOccupation.addView(listViewTemp);
        }
    }


    private void event() {
        findViewById(R.id.add_occupation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < commOccList.getData().size(); i++) {
                    commOccList.getData().get(i).setVirtualIsSelect(commOccList.getData().get(i).isIs_select());
                }

                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_occupation_select_team);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);

                RecyclerView recyclerOccupation = bottomInterPasswordDialog.findViewById(R.id.recyclerList);
                recyclerOccupation.setLayoutManager(new GridLayoutManager(getContext(), 4));
                TeamOccupationAdapter listAdapter = new TeamOccupationAdapter(commOccList.getData());
                recyclerOccupation.setAdapter(listAdapter);

                bottomInterPasswordDialog.show();

                listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        commOccList.getData().get(position).setVirtualIsSelect(!commOccList.getData().get(position).isVirtualIsSelect());
                        listAdapter.notifyDataSetChanged();
                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomInterPasswordDialog.hide();
                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingDailog.show();
                        List<String> listTemp = new LinkedList<>();
                        for (int i = 0; i < commOccList.getData().size(); i++) {
                            if (commOccList.getData().get(i).isVirtualIsSelect()) {
                                listTemp.add(commOccList.getData().get(i).getOccupation_id());
                            }

                        }

                        HttpParamsObject param = new HttpParamsObject();
                        param.setParam("occupationId", listTemp);
                        param.setParam("projectId", projectId);
                        new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_ADD_OCCUPATION, param, new CommonCallback() {
                            @Override
                            public void onSuccessJson(String data, int code) {
                                for (int i = 0; i < commOccList.getData().size(); i++) {
                                    commOccList.getData().get(i).setIs_select(commOccList.getData().get(i).isVirtualIsSelect());
                                }
                                initOccupation();
                                getOccupationUserList();
                                bottomInterPasswordDialog.hide();
                            }

                            @Override
                            public void onFinal() {
                                super.onFinal();
                                loadingDailog.hide();
                            }
                        });



                    }
                });
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            getOccupationUserList();
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
