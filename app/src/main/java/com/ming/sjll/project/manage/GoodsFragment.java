package com.ming.sjll.project.manage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.Bean.ProjectSiteListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.project.manage.adapter.SiteItemAdapter;
import com.rey.material.app.BottomSheetDialog;

/**
 * 添加道具
 */
public class GoodsFragment extends BaseFragment {


    OccupationList commOccList;
    ProjectSiteListBean dataList;
    LinearLayout horizontalOccupation;

    String projectId;

    private RecyclerView listView;
    SiteItemAdapter timeUserMiddleAdapter;

    public static GoodsFragment newInstance(String project_id) {
        GoodsFragment fragment = new GoodsFragment();
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


        findViewById(R.id.add_occupation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_goods_select);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);

                bottomInterPasswordDialog.findViewById(R.id.guide1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomInterPasswordDialog.hide();
                        Intent intent = new Intent(getActivity(), com.ming.sjll.search.GoodsFragment.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("projectId", projectId);
                        bundle.putString("classId", "10");
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 44);

                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.guide2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomInterPasswordDialog.hide();
                        Intent intent = new Intent(getActivity(), com.ming.sjll.search.GoodsFragment.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("projectId", projectId);
                        bundle.putString("classId", "11");
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 44);
                    }
                });
                bottomInterPasswordDialog.show();
            }
        });


//        getOccupation();
//        getOccupationUserList();
    }

    private void getData()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId", projectId);
        new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_COSTUMES, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                dataList = new Gson().fromJson(data, ProjectSiteListBean.class);
                initData();

            }
        });
    }
    private void initData()
    {


        initHorizontal();
        initVertical();
    }

    private void initHorizontal() {
        horizontalOccupation.removeAllViews();
        for (int j = 0; j < dataList.getData().size(); j++) {
//            if (!dataList.getData().get(j).isIs_select()) {
//                continue;
//            }
            View listViewTemp = LayoutInflater.from(getContext()).inflate(R.layout.item_images_circular_50, null);
            listViewTemp.setTag(dataList.getData().get(j).getTarget_id());
            new ImageHelper().displayBackgroundLoading(listViewTemp.findViewById(R.id.image),
                    Constant.BASE_API + dataList.getData().get(j).getCover_img());

            listViewTemp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = v.getTag().toString();
                    for (int i = 0; i < dataList.getData().size(); i++) {
                        if (dataList.getData().get(i).getTarget_id().equals(id)) {
                            findViewById(R.id.svscrollouter).scrollTo(0, listView.getChildAt(i).getTop());
                        }
                    }
                }
            });

            horizontalOccupation.addView(listViewTemp);
        }


    }

    private void initVertical()
    {
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        timeUserMiddleAdapter = new SiteItemAdapter(dataList.getData(), getActivity(), projectId);
        listView.setAdapter(timeUserMiddleAdapter);
        timeUserMiddleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.delete:
                        DialogTextTip dialog = new DialogTextTip(getContext(), "是否确定删除该商品？");
                        dialog.show(new CommonCallback() {
                            @Override
                            public void onNext() {
                                super.onNext();


                                HttpParamsObject param = new HttpParamsObject();
                                param.setParam("targetId", dataList.getData().get(position).getTarget_id());
                                param.setParam("projectId", projectId);
                                param.setParam("addressType", dataList.getData().get(position).getAddress_type());
                                new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_DEL_PROJECT_GOODS, param, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {
                                        dataList.getData().remove(position);
                                        dialog.hide();
                                        initData();


                                    }
                                });



                            }
                        });
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == PictureConfig.MAP_HEAD_REQUEST) {
                double longitude = data.getDoubleExtra("longitude", 0);
                double latitude = data.getDoubleExtra("latitude", 0);
                String area_code = data.getStringExtra("area_code");
                String title = data.getStringExtra("title");
                String adname = data.getStringExtra("address");
                String province = data.getStringExtra("province");
                String city = data.getStringExtra("city");
                String district = data.getStringExtra("district");

                HttpParamsObject params = new HttpParamsObject();
                params.setParam("projectId",projectId);
                params.setParam("area_code",area_code);
                params.setParam("provinces",province);
                params.setParam("city",city);
                params.setParam("county",district);
                params.setParam("address_title",title);
                params.setParam("address",adname);
                params.setParam("meridian",longitude+"");
                params.setParam("weft",latitude+"");

                loadingDailog.show();
                new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_FREE_ADDRESS, params, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        getData();
                        ToastShow.s("添加成功");
                    }

                    @Override
                    public void onFinal() {
                        super.onFinal();
                        loadingDailog.hide();
                    }
                });

            }

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
