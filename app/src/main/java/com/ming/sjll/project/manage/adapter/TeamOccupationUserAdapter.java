package com.ming.sjll.project.manage.adapter;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.My.adapter.PersonageOccupationListItemAdapter;
import com.ming.sjll.My.common.Setting;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.ReleaseProjectNext;
import com.ming.sjll.project.manage.TeamFragment;
import com.ming.sjll.someone.FindListProjectInfo;
import com.rey.material.app.BottomSheetDialog;

import java.util.List;

/**
 * 个人中心-个人-职业-列表
 */
public class TeamOccupationUserAdapter extends BaseQuickAdapter<OccupationList.DataBean, BaseViewHolder> {


    public boolean canEdit = true;

    Activity mActivity;
    String projectId;
    public TeamOccupationUserAdapter(@Nullable List<OccupationList.DataBean> data, Activity mActivity,String projectId ) {
        super(R.layout.project_manage_team_item, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, OccupationList.DataBean dataBean) {

//        final OccupationList.DataBean  dataBean = tempDataBean;

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.icon),
                Constant.BASE_IMAGE + dataBean.getSmall_icon());

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.icon2),
                Constant.BASE_IMAGE + dataBean.getSmall_icon());

//        RecyclerView listView = baseViewHolder.getView(R.id.listView);
//        listView.setLayoutManager(new LinearLayoutManager(mContext));


        baseViewHolder.getView(R.id.contentDelWrap).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.contentAddWrap).setVisibility(View.GONE);

        baseViewHolder.getView(R.id.horizontalOccupation).setVisibility(View.GONE);

        if(dataBean.isShowDelWarp())
        {
            baseViewHolder.getView(R.id.contentDelWrap).setVisibility(View.VISIBLE);

            RecyclerView listView = baseViewHolder.getView(R.id.listViewVertical);
            listView.setLayoutManager(new LinearLayoutManager(mContext));
            TeamOccupationUserDeleteAdapter teamOccupationUserDeleteAdapter = new TeamOccupationUserDeleteAdapter(dataBean.getMember());
            listView.setAdapter(teamOccupationUserDeleteAdapter);

            teamOccupationUserDeleteAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.btnDeleteUser) {
                        //                提示
                        DialogTextTip dialog = new DialogTextTip(mContext, "确定刪除用戶 "+dataBean.getMember().get(position).getName()+"?");
                        dialog.setTextColor(6,7+dataBean.getMember().get(position).getName().length());
                        dialog.show(new CommonCallback() {
                            @Override
                            public void onNext() {
                                super.onNext();
                                if(dataBean.getMember().get(position) == null)
                                {
                                    return;
                                }

                                HttpParamsObject param = new HttpParamsObject();
                                param.setParam("projectId",projectId);
                                param.setParam("occupationId",dataBean.getOccupation_id());
                                param.setParam("userId",dataBean.getMember().get(position).getUid());

                                new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_DEL_OCCUPATION_USER, param, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {

                                        dataBean.getMember().remove(position);
                                        teamOccupationUserDeleteAdapter.notifyDataSetChanged();
                                        dialog.hide();

                                    }
                                });

                            }
                        });

                    }
                    if (view.getId() == R.id.imageItem) {
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", dataBean.getMember().get(position).getUid());
                        Tools.jump(mActivity, HomepageActivity.class, bundle, false);
                    }



                }
            });

        }
        else
        {
            baseViewHolder.getView(R.id.contentAddWrap).setVisibility(View.VISIBLE);
            if(dataBean.isShowShare())
            {
                baseViewHolder.getView(R.id.horizontalOccupation).setVisibility(View.VISIBLE);
            }

            if(dataBean.getMember().size() == 0)
            {
                baseViewHolder.getView(R.id.listViewHorizontal).setVisibility(View.GONE);
            }
            else
            {
                baseViewHolder.getView(R.id.listViewHorizontal).setVisibility(View.VISIBLE);
            }
            LinearLayout LinearLayout = baseViewHolder.getView(R.id.userLayout);
            LinearLayout.removeAllViews();
            for (int i = 0; i < dataBean.getMember().size(); i++) {
                LayoutInflater l_Inflater = LayoutInflater.from(mContext);
                View occupationUserView = l_Inflater.inflate(R.layout.item_images_circular_63, null);

                occupationUserView.setTag(dataBean.getMember().get(i).getUid());

                new ImageHelper().displayBackgroundLoading(occupationUserView.findViewById(R.id.image),
                        Constant.BASE_IMAGE + dataBean.getMember().get(i).getDefault_avatar());
                if(dataBean.getMember().get(i).getIs_join().equals("1"))
                {
                    occupationUserView.findViewById(R.id.image).setAlpha(1f);
                }
                else
                {
                    occupationUserView.findViewById(R.id.image).setAlpha(0.5f);
                }

                occupationUserView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String indexOf = v.getTag().toString();
                        Bundle bundle = new Bundle();
                        bundle.putString("userId", indexOf);
                        Tools.jump(mActivity, HomepageActivity.class, bundle, false);
//                        Tools.jump(TeamFragment , HomepageActivity.class, true);
                    }
                });
                LinearLayout.addView(occupationUserView);
            }

        }





        baseViewHolder.addOnClickListener(R.id.addMemberBtn);
        baseViewHolder.addOnClickListener(R.id.addRecruitBtn);
        baseViewHolder.addOnClickListener(R.id.btnEditReturn);
        baseViewHolder.addOnClickListener(R.id.btnEdit);
        baseViewHolder.addOnClickListener(R.id.deleteOccupation);

        baseViewHolder.addOnClickListener(R.id.guide1);
        baseViewHolder.addOnClickListener(R.id.guide2);
        baseViewHolder.addOnClickListener(R.id.guide3);
        baseViewHolder.addOnClickListener(R.id.guide4);
//        PersonageOccupationListItemAdapter personageOccupationListItemAdapter = new PersonageOccupationListItemAdapter(dataBean.getPayment());
//        personageOccupationListItemAdapter.canEdit = canEdit;
//        listView.setAdapter(personageOccupationListItemAdapter);



//        personageOccupationListItemAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if(!canEdit)
//                {
//                    return;
//                }
////                dataBean.getPayment();
////                personageOccupationListItemAdapter.notifyDataSetChanged();
//                if (view.getId() == R.id.delete) {
//                    dataBean.getPayment().remove(position);
//                    personageOccupationListItemAdapter.notifyDataSetChanged();
//                } else if (view.getId() == R.id.units) {
//                    BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(mContext);
//                    bottomInterPasswordDialog.setContentView(R.layout.dialog_units_type);
//                    bottomInterPasswordDialog.inDuration(300);
//                    bottomInterPasswordDialog.outDuration(200);
////                    bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
////                    bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
//                    bottomInterPasswordDialog.show();
//                    bottomInterPasswordDialog.findViewById(R.id.units1).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!hasSameUnit(dataBean.getPayment(), "1")) {
//                                dataBean.getPayment().get(position).setUnits("1");
//                                personageOccupationListItemAdapter.notifyDataSetChanged();
//                                bottomInterPasswordDialog.hide();
//                            }
//
//                        }
//                    });
//                    bottomInterPasswordDialog.findViewById(R.id.units2).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!hasSameUnit(dataBean.getPayment(), "2")) {
//                                dataBean.getPayment().get(position).setUnits("2");
//                                personageOccupationListItemAdapter.notifyDataSetChanged();
//                                bottomInterPasswordDialog.hide();
//                            }
//                        }
//                    });
//                    bottomInterPasswordDialog.findViewById(R.id.units3).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!hasSameUnit(dataBean.getPayment(), "3")) {
//                                dataBean.getPayment().get(position).setUnits("3");
//                                personageOccupationListItemAdapter.notifyDataSetChanged();
//                                bottomInterPasswordDialog.hide();
//                            }
//                        }
//                    });
//                    bottomInterPasswordDialog.findViewById(R.id.units4).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!hasSameUnit(dataBean.getPayment(), "4")) {
//                                dataBean.getPayment().get(position).setUnits("4");
//                                personageOccupationListItemAdapter.notifyDataSetChanged();
//                                bottomInterPasswordDialog.hide();
//                            }
//                        }
//                    });
//                    bottomInterPasswordDialog.findViewById(R.id.units5).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!hasSameUnit(dataBean.getPayment(), "5")) {
//                                dataBean.getPayment().get(position).setUnits("5");
//                                personageOccupationListItemAdapter.notifyDataSetChanged();
//                                bottomInterPasswordDialog.hide();
//                            }
//                        }
//                    });
//                    bottomInterPasswordDialog.findViewById(R.id.units6).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!hasSameUnit(dataBean.getPayment(), "6")) {
//                                dataBean.getPayment().get(position).setUnits("6");
//                                personageOccupationListItemAdapter.notifyDataSetChanged();
//                                bottomInterPasswordDialog.hide();
//                            }
//                        }
//                    });
//
//                }
//
//            }
//        });
//
//        if(!canEdit)
//        {
//            baseViewHolder.getView(R.id.add).setVisibility(View.GONE);
//        }
//        baseViewHolder.getView(R.id.add).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(dataBean.getPayment().size() < 6)
//                {
//                    dataBean.getPayment().add(new OccupationList.DataBean.paymentBean());
//                    personageOccupationListItemAdapter.notifyDataSetChanged();
//                }
//                else
//                {
//                    ToastShow.s("最多只能添加6个");
//                }
//            }
//        });
//        baseViewHolder.addOnClickListener(R.id.add);


//        Log.e("图片路径2222", dataBean.toString());
//        if (dataBean.equals("footerview")) {
//            baseViewHolder.setGone(R.id.content, false);
//            baseViewHolder.setGone(R.id.iv_add, true);
//
//        } else {
//            baseViewHolder.setGone(R.id.content, true);
//            baseViewHolder.setGone(R.id.iv_add, false);
////            ImageHelper.display((ImageView) baseViewHolder.getView(R.id.iv_img), dataBean);
//
//            new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.iv_img),
//                    Constant.BASE_IMAGE + dataBean);
//
//        }
//
//        baseViewHolder.addOnClickListener(R.id.iv_delete);
//        baseViewHolder.addOnClickListener(R.id.iv_add);
//        baseViewHolder.addOnClickListener(R.id.iv_img);
//

    }

//    private boolean hasSameUnit(List<OccupationList.DataBean.paymentBean> paymentBean, String value) {
//        for (OccupationList.DataBean.paymentBean p :
//                paymentBean) {
//            if (p.getUnits().equals(value)) {
//                ToastShow.s("已存在相同单位，请重新选择");
//                return true;
//            }
//        }
//
//        return false;
//    }


}
