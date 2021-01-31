package com.ming.sjll.project.manage.adapter;


import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.Bean.ProjectSiteListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SiteItemAdapter extends BaseQuickAdapter<ProjectSiteListBean.DataBean, BaseViewHolder> {


    public boolean canEdit = true;

    Activity mActivity;
    String projectId;
    public SiteItemAdapter(@Nullable List<ProjectSiteListBean.DataBean> data, Activity mActivity, String projectId ) {
        super(R.layout.project_manage_site_item, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectSiteListBean.DataBean dataBean) {

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.icon),
                Constant.BASE_IMAGE + dataBean.getCover_img());

        baseViewHolder.setText(R.id.name, dataBean.getTitle());
        baseViewHolder.setText(R.id.occupation, dataBean.getAddress());


        baseViewHolder.getView(R.id.money).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.inputMoney).setVisibility(View.GONE);
//        baseViewHolder.getView(R.id.isPay).setVisibility(View.GONE);
        if(dataBean.getAddress_type() == null || dataBean.getAddress_type().equals("1"))
        {
            baseViewHolder.getView(R.id.money).setVisibility(View.VISIBLE);
            baseViewHolder.setText(R.id.money, dataBean.getTotal_price());
            if(dataBean.getStatus().equals("1"))
            {
                ((ImageView)baseViewHolder.getView(R.id.isPay)).setBackgroundResource(R.mipmap.btn_project_goods_status1);
//                baseViewHolder.getView(R.id.isPay).setVisibility(View.VISIBLE);
            }
            else if(dataBean.getStatus().equals("2"))
            {
                ((ImageView)baseViewHolder.getView(R.id.isPay)).setBackgroundResource(R.mipmap.btn_project_goods_status2);
            }
            else if(dataBean.getStatus().equals("3"))
            {
                ((ImageView)baseViewHolder.getView(R.id.isPay)).setBackgroundResource(R.mipmap.btn_project_goods_status3);
            }
        }
        else
        {
            baseViewHolder.getView(R.id.inputMoney).setVisibility(View.VISIBLE);
//            baseViewHolder.setText(R.id.inputMoney, dataBean.getTotal());


            EditText price = (EditText)baseViewHolder.getView(R.id.inputMoney);
            //        通过tag判断当前editText是否已经设置监听，有监听的话，移除监听再给editText赋值
            if (price.getTag() instanceof TextWatcher){
                price.removeTextChangedListener((TextWatcher) price.getTag());
            }
            //        必须在判断tag后给editText赋值，否则会数据错乱
//            price.setText(dataBean.getPrice());
            baseViewHolder.setText(R.id.inputMoney, dataBean.getTotal_price());

            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
//                    修改費用
                    dataBean.setTotal_price(editable.toString());


                    HttpParamsObject param = new HttpParamsObject();
                    param.setParam("projectId", projectId);
                    param.setParam("targetId", dataBean.getTarget_id());
                    param.setParam("totalPrice", editable.toString());
                    new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_SAVE_SITE_COST, param, new CommonCallback() {
                        @Override
                        public void onSuccessJson(String data, int code) {

                        }
                    });

                }
            };
            //        给item中的editText设置监听
            price.addTextChangedListener(watcher);
//        给editText设置tag，以便于判断当前editText是否已经设置监听
            price.setTag(watcher);



        }



//        baseViewHolder.getView(R.id.noneData).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.listView).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.listViewHorizontal).setVisibility(View.GONE);

        if (dataBean.getDateTime().size() == 0) {
//            没有数据
//            baseViewHolder.getView(R.id.noneData).setVisibility(View.VISIBLE);
        } else {
            if (dataBean.getType().equals("1")) {
//                显示时间
                baseViewHolder.getView(R.id.listView).setVisibility(View.VISIBLE);
                RecyclerView listView = baseViewHolder.getView(R.id.listView);
                listView.setLayoutManager(new LinearLayoutManager(mContext));
                TimeUserTiemLineAdapter timeUserTiemLineAdapter = new TimeUserTiemLineAdapter(dataBean.getDateTime(), mActivity, projectId);
                listView.setAdapter(timeUserTiemLineAdapter);

                //            修改场地时间
                timeUserTiemLineAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if(dataBean.getAddress_type() == null || dataBean.getAddress_type().equals("1"))
                        {
                            return;
                        }
                        switch (view.getId())
                        {
                            case R.id.startTime:
                                initTimePicker(dataBean.getDateTime().get(position).getStart_time(), dataBean.getDateTime().get(position),true,timeUserTiemLineAdapter,dataBean);
                                break;
                            case  R.id.endTime:
                                initTimePicker(dataBean.getDateTime().get(position).getEnd_time(), dataBean.getDateTime().get(position),false,timeUserTiemLineAdapter,dataBean);
                                break;
                        }
                    }
                });

            } else {
//                显示日期
                baseViewHolder.getView(R.id.listViewHorizontal).setVisibility(View.VISIBLE);
                for (int i = 0; i < dataBean.getDateTime().size(); i++) {

                    View listViewTemp = LayoutInflater.from(mContext).inflate(R.layout.project_manage_time_text, null);
                    ((TextView) listViewTemp.findViewById(R.id.text)).setText(dataBean.getDateTime().get(i).getStart_date());
                    ((LinearLayout) baseViewHolder.getView(R.id.userLayout)).addView(listViewTemp);
                }
            }

        }


        baseViewHolder.addOnClickListener(R.id.delete);
    }



    private void initTimePicker(String date, GetInfoDateListBean.DataBean.DataBeanY mData,boolean isStartTime,TimeUserTiemLineAdapter timeUserTiemLineAdapter,ProjectSiteListBean.DataBean dataBean) {//Dialog 模式下，在底部弹出

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (date != null && !date.equals("") && Long.parseLong(date) > 0) {
            calendar.setTime(new Date(Long.parseLong(date) * 1000));
        }

        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("projectId", projectId);
                param.setParam("targetId", dataBean.getTarget_id());
                param.setParam("timeId", mData.getTime_id());


                String hm = Tools.getTimeFormatHourMinute(date.getTime());
                String time = Long.parseLong(Tools.getLongformatTime( mData.getDate_time_str() + " " + hm + ":00"))   + "";

                if (isStartTime) {
                    mData.setStart_time(time);
                    param.setParam("startTime", mData.getStart_time());
                } else {

                    mData.setEnd_time(time);
                    param.setParam("endTime", mData.getEnd_time());
                }



                new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_SAVE_SITE_TIME, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {

                    }
                });

                timeUserTiemLineAdapter.notifyDataSetChanged();
                Log.i("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .isCyclic(true)
                .setDate(calendar)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
        pvTime.show();
    }

}
