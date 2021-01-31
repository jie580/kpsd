package com.ming.sjll.My.adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.EditText;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.My.personage.PersonageOccupation;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;

import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.widget.ToastShow;
import com.rey.material.app.BottomSheetDialog;

import java.util.LinkedList;
import java.util.List;

/**
 * 个人中心-个人-职业-列表
 */
public class PersonageOccupationListAdapter extends BaseQuickAdapter<GetInfoBean.DataBean.occupationInfoBean, BaseViewHolder> {


    public boolean canEdit = true;

    public PersonageOccupationListAdapter(@Nullable List<GetInfoBean.DataBean.occupationInfoBean> data) {
        super(R.layout.my_personage_occupation_list_adapter, data);
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean.occupationInfoBean dataBean) {

//        final GetInfoBean.DataBean.occupationInfoBean  dataBean = tempDataBean;
        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.icon),
                Constant.BASE_IMAGE + dataBean.getIcon());
        RecyclerView listView = baseViewHolder.getView(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(mContext));

        PersonageOccupationListItemAdapter personageOccupationListItemAdapter = new PersonageOccupationListItemAdapter(dataBean.getPayment());
        personageOccupationListItemAdapter.canEdit = canEdit;
        listView.setAdapter(personageOccupationListItemAdapter);



        personageOccupationListItemAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(!canEdit)
                {
                    return;
                }
//                dataBean.getPayment();
//                personageOccupationListItemAdapter.notifyDataSetChanged();
                if (view.getId() == R.id.delete) {
                    dataBean.getPayment().remove(position);
                    personageOccupationListItemAdapter.notifyDataSetChanged();
                } else if (view.getId() == R.id.units) {
                    BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(mContext);
                    bottomInterPasswordDialog.setContentView(R.layout.dialog_units_type);
                    bottomInterPasswordDialog.inDuration(300);
                    bottomInterPasswordDialog.outDuration(200);
//                    bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                    bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                    bottomInterPasswordDialog.show();
                    bottomInterPasswordDialog.findViewById(R.id.units1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(dataBean.getPayment(), "1")) {
                                dataBean.getPayment().get(position).setUnits("1");
                                personageOccupationListItemAdapter.notifyDataSetChanged();
                                bottomInterPasswordDialog.hide();
                            }

                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(dataBean.getPayment(), "2")) {
                                dataBean.getPayment().get(position).setUnits("2");
                                personageOccupationListItemAdapter.notifyDataSetChanged();
                                bottomInterPasswordDialog.hide();
                            }
                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(dataBean.getPayment(), "3")) {
                                dataBean.getPayment().get(position).setUnits("3");
                                personageOccupationListItemAdapter.notifyDataSetChanged();
                                bottomInterPasswordDialog.hide();
                            }
                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(dataBean.getPayment(), "4")) {
                                dataBean.getPayment().get(position).setUnits("4");
                                personageOccupationListItemAdapter.notifyDataSetChanged();
                                bottomInterPasswordDialog.hide();
                            }
                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units5).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(dataBean.getPayment(), "5")) {
                                dataBean.getPayment().get(position).setUnits("5");
                                personageOccupationListItemAdapter.notifyDataSetChanged();
                                bottomInterPasswordDialog.hide();
                            }
                        }
                    });
                    bottomInterPasswordDialog.findViewById(R.id.units6).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!hasSameUnit(dataBean.getPayment(), "6")) {
                                dataBean.getPayment().get(position).setUnits("6");
                                personageOccupationListItemAdapter.notifyDataSetChanged();
                                bottomInterPasswordDialog.hide();
                            }
                        }
                    });

                }

            }
        });

        if(!canEdit)
        {
            baseViewHolder.getView(R.id.add).setVisibility(View.GONE);
        }
//        baseViewHolder.getView(R.id.add).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(dataBean.getPayment().size() < 6)
//                {
//                    dataBean.getPayment().add(new GetInfoBean.DataBean.occupationInfoBean.paymentBean());
//                    personageOccupationListItemAdapter.notifyDataSetChanged();
//                }
//                else
//                {
//                    ToastShow.s("最多只能添加6个");
//                }
//            }
//        });
        baseViewHolder.addOnClickListener(R.id.add);


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

    private boolean hasSameUnit(List<GetInfoBean.DataBean.occupationInfoBean.paymentBean> paymentBean, String value) {
        for (GetInfoBean.DataBean.occupationInfoBean.paymentBean p :
                paymentBean) {
            if (p.getUnits().equals(value)) {
                ToastShow.s("已存在相同单位，请重新选择");
                return true;
            }
        }

        return false;
    }


}
