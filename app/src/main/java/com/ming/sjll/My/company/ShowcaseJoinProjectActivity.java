package com.ming.sjll.My.company;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.Bean.GetInfoDateInfoBean;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.My.adapter.ShowcaseTagItemAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.ProjectManageTimeEditBinding;

import com.ming.sjll.databinding.ProjectManageTimeEditInfoTempBinding;
import com.ming.sjll.project.adapter.TagItemAdapter;
import com.ming.sjll.project.manage.TeamFragment;
import com.ming.sjll.project.manage.TimeFragment;
import com.ming.sjll.project.manage.adapter.TimeUserTiemEditLineAdapter;
import com.ming.sjll.search.adapter.ProjectListItemAdapter;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.toolPage.DateFrame;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;
import com.ming.sjll.ui.PagedListView;
import com.necer.calendar.BaseCalendar;
import com.necer.enumeration.SelectedModel;
import com.rey.material.app.BottomSheetDialog;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品选择时间
 */
public class ShowcaseJoinProjectActivity extends BaseActivity {

    ProjectManageTimeEditInfoTempBinding binding;


    private List<Fragment> fragmentList;

    TeamFragment teamFragment;
    TimeFragment timeFragment;
    String projectId;
    String userId;
    String occupationId;
    GoodsItem mData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.project_manage_time_edit_info_temp);
//        binding.dateFrame.setSelectedModel(SelectedModel.MULTIPLE);


//        setContentView(R.layout.project_manage_team);
        initView();
    }

    ProjectListItem projectDataList;

    private void initView() {
        projectId = getIntent().getStringExtra("projectId");
        occupationId = getIntent().getStringExtra("occupationId");
        userId = getIntent().getStringExtra("userId");

        binding.recyclerListTitle.setText("选择租赁方式");
        binding.dateTitle.setText("选择租赁时间");

        binding.titleBar.titleBarIvRight.setVisibility(View.VISIBLE);
        binding.titleBar.titleBarIvRight.setBackground(getResources().getDrawable(R.mipmap.btn_save_top9));

        binding.titleBar.titleBarIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (projectId != null && !projectId.equals("")) {
                    submit();
                    return;
                }
                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseJoinProjectActivity.this);
                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_my_select);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();

                int page = 1;
                int pageSize = 20;

                HttpParamsObject param = new HttpParamsObject();
                param.setParam("type", 2);
                param.setParam("page", page);
                param.setParam("pageSize", pageSize);
                new HttpUtil().sendRequest(Constant.PROJECT_MY_PROJECT, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        PagedListView recyclerList = bottomInterPasswordDialog.findViewById(R.id.recyclerList);
                        projectDataList = new Gson().fromJson(data, ProjectListItem.class);
                        ProjectListItemAdapter projectAdapter = new ProjectListItemAdapter(ShowcaseJoinProjectActivity.this, projectDataList.getData().getData(), false);
                        recyclerList.setAdapter(projectAdapter);
                        recyclerList.setNoData();
                        projectAdapter.setOnClickListenerInterface(new ProjectListItemAdapter.setOnClickListener() {

                            @Override
                            public void onClick(int pos, @IdRes int id, View v) {
                                switch (id) {
                                    case R.id.projectWrap:
                                        Log.e("点击", "点击了rc_wrap" + pos);
                                        for (int i = 0; i < projectDataList.getData().getData().size(); i++) {
                                            projectDataList.getData().getData().get(i).setSelect(false);
                                        }
                                        projectDataList.getData().getData().get(pos).setSelect(true);
                                        projectAdapter.notifyDataSetChanged();
//                                        clickProject(dataList.getData().getData().get(pos));
//                                        projectId = dataList.getData().getData().get(pos).getId();
//                                        submit(dataList.getData().getData().get(pos).getId());
                                }

                            }
                        });


                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomInterPasswordDialog.hide();
                    }
                });

                bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < projectDataList.getData().getData().size(); i++) {
                            if (projectDataList.getData().getData().get(i).isSelect()) {
                                submit(projectDataList.getData().getData().get(i).getId());
                                return;
                            }
                        }
                        ToastShow.s("请选择项目");
                    }
                });


            }
        });

        getData();

    }

    private void submit() {
        submit(projectId);
    }

    private void submit(String projectId) {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("userId", userId);
        param.setParam("projectId", projectId);
        param.setParam("goodsId", mData.getData().getGoods_id());
//        param.setParam("sendType", "");
        param.setParam("type", mData.getData().getType());
        param.setParam("num", binding.countInput.getText().toString());
        param.setParam("price", mData.getData().getPrice());
        List<String> temList = new LinkedList<>();
        for (int i = 0; i < mData.getData().getDateList().size(); i++) {
            param.setParam("dateList[" + i + "][start]", mData.getData().getDateList().get(i).getStart_time());
            param.setParam("dateList[" + i + "][end]", mData.getData().getDateList().get(i).getEnd_time());
            temList.add(mData.getData().getDateList().get(i).getDate_time());
        }
        param.setParam("dateTime", temList);

        new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_ADD_PROJECT_GOODS, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                Intent intent = new Intent();
                setResult(ShowcaseJoinProjectActivity.RESULT_OK, intent);
                ToastShow.s("添加成功");
                finish();
            }
        });

    }

    private void getData() {
//        HttpParamsObject param = new HttpParamsObject();
//        param.setParam("userId", userId);
//        param.setParam("projectId", projectId);
//        param.setParam("occupationId", occupationId);
//        new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_USER_PAYMENT, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {

        mData = (GoodsItem) getIntent().getSerializableExtra("DataBean");
//                mData = new Gson().fromJson(data, GetInfoDateInfoBean.class);

        initData();

//            }
//        });
    }

    private void initData() {
//        binding.dateFrame.setPointList(mData.getData().getDate_time());

        List<LocalDate> selectDateList = new LinkedList<>();
        if (mData.getData().getDateList() == null) {
            mData.getData().setDateList(new LinkedList<>());
        }
//        for (int i = 0; i < mData.getData().getDateList().size(); i++) {
//            LocalDate localDate = new LocalDate(mData.getData().getDateList().get(i).getDate_time());
//            selectDateList.add(localDate);
//        }


        binding.selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowcaseJoinProjectActivity.this, ShowcaseJoinProjectDateActivity.class);
                intent.putExtra("dateList", (Serializable) mData.getData().getDate_time());
                startActivityForResult(intent, 22);
            }
        });
//        binding.dateFrame.setSelectData(selectDateList);
//
//
//        binding.dateFrame.setListener(new DateFrame.Listener() {
//            @Override
//            public void onCalendarChangedListener(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
//                super.onCalendarChangedListener(baseCalendar, year, month, localDate);
//            }
//
//            @Override
//            public void setOnCalendarMultipleChangedListener(BaseCalendar baseCalendar, int year, int month, List<LocalDate> currPagerCheckedList, List<LocalDate> totalCheckedList) {
//                super.setOnCalendarMultipleChangedListener(baseCalendar, year, month, currPagerCheckedList, totalCheckedList);
//
//                if (totalCheckedList.size() == mData.getData().getDateList().size()) {
//                    return;
//                } else if (totalCheckedList.size() > mData.getData().getDateList().size()) {
//
//
//                    String selectData = totalCheckedList.get(totalCheckedList.size() - 1).toString();
//
//                    for (int i = 0; i < mData.getData().getDate_time().size(); i++) {
//                        if (mData.getData().getDate_time().get(i).equals(selectData)) {
//                            //                提示
//                            DialogTextTip dialog = new DialogTextTip(ShowcaseJoinProjectActivity.this, "该人员时间与本项目冲突，是否继续添加");
//                            dialog.show(new CommonCallback() {
//                                @Override
//                                public void onNext() {
//                                    super.onNext();
//                                    GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
//                                    date.setDate_time(selectData);
//                                    mData.getData().getDateList().add(date);
//
//                                    List<LocalDate> dateList = new LinkedList<>();
//                                    for (int i = 0; i < mData.getData().getDateList().size(); i++) {
//                                        LocalDate localDate = new LocalDate(mData.getData().getDateList().get(i).getDate_time());
//                                        dateList.add(localDate);
//                                    }
//                                    binding.dateFrame.setSelectData(dateList);
//                                    changeDate();
//                                    dialog.hide();
//                                }
//
//                                @Override
//                                public void onCancel() {
//                                    super.onCancel();
//                                    List<LocalDate> dateList = new LinkedList<>();
//                                    for (int i = 0; i < mData.getData().getDateList().size(); i++) {
//                                        LocalDate localDate = new LocalDate(mData.getData().getDateList().get(i).getDate_time());
//                                        dateList.add(localDate);
//                                    }
//                                    binding.dateFrame.setSelectData(dateList);
//                                    changeDate();
//                                }
//                            });
//                            return;
//                        }
//                    }
//
//
//        GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
//        date.setDate_time(selectData);
//        mData.getData().getDateList().add(date);
//
//        List<LocalDate> dateList = new LinkedList<>();
//        for (int i = 0; i < mData.getData().getDateList().size(); i++) {
//            LocalDate localDate = new LocalDate(mData.getData().getDateList().get(i).getDate_time());
//            dateList.add(localDate);
//        }
//        binding.dateFrame.setSelectData(dateList);
//        changeDate();
//                } else {
//                    for (int i = 0; i < mData.getData().getDateList().size(); i++) {
//                        boolean flag = false;
//                        for (int j = 0; j < totalCheckedList.size(); j++) {
//                            if (totalCheckedList.get(j).toString().equals(mData.getData().getDateList().get(i).getDate_time())) {
//                                flag = true;
//                            }
//                        }
//                        if (!flag) {
//                            mData.getData().getDateList().remove(i);
//                            changeDate();
//                            break;
//                        }
//                    }
//
//
//                }
//
//
//            }
//        });


        initTag();
    }

    ShowcaseTagItemAdapter adapter;

    private int selectPoint;
    private boolean isStartTime;

    private void initTag() {

        RecyclerView recyclerList = binding.recyclerList;
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(ShowcaseJoinProjectActivity.this, true);
        recyclerList.setLayoutManager(layout);
        adapter = new ShowcaseTagItemAdapter(mData.getData().getPaymentList());
        recyclerList.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mData.getData().getPaymentList().size(); i++) {
                    mData.getData().getPaymentList().get(i).setIs_select(false);
                }
                mData.getData().getPaymentList().get(position).setIs_select(true);
                mData.getData().setType(mData.getData().getPaymentList().get(position).getType());
                tagEvent();
                adapter.notifyDataSetChanged();
            }
        });
        tagEvent();
    }

    private void tagEvent() {
//        binding.selectCountWrap.setVisibility(View.GONE);
        binding.selectQuoteWrap.setVisibility(View.GONE);

        binding.dateTitle.setVisibility(View.VISIBLE);
        binding.selectTime.setVisibility(View.VISIBLE);
        binding.dateTip.setVisibility(View.VISIBLE);

//        if (mData.getData().getType().equals("2")) {
////            binding.selectCountWrap.setVisibility(View.VISIBLE);
////            binding.quoteInput.setText(mData.getData().getValue());
//        } else
        if (mData.getData().getType().equals("3")) {
            binding.dateTitle.setVisibility(View.GONE);
            binding.selectTime.setVisibility(View.GONE);
            binding.dateTip.setVisibility(View.GONE);
        }

//        if (mData.getData().getType().equals("6")) {
//            binding.selectQuoteWrap.setVisibility(View.VISIBLE);
//            binding.quoteInput.setText(mData.getData().getPrice());
//        } else if (mData.getData().getType().equals("3")) {
//            binding.selectCountWrap.setVisibility(View.VISIBLE);
//            binding.countInput.setText(mData.getData().getValue());
//            binding.unit.setText("件");
//        } else if (mData.getData().getType().equals("4")) {
//            binding.selectCountWrap.setVisibility(View.VISIBLE);
//            binding.countInput.setText(mData.getData().getValue());
//            binding.unit.setText("套");
//        } else if (mData.getData().getType().equals("5")) {
//            binding.selectCountWrap.setVisibility(View.VISIBLE);
//            binding.countInput.setText(mData.getData().getValue());
//            binding.unit.setText("张");
//        }
        changeDate();
    }

    TimeUserTiemEditLineAdapter timeUserTiemLineAdapter;

    private void changeDate() {
        if (!mData.getData().getType().equals("1")) {
            binding.dateListView.setVisibility(View.GONE);
            return;
        }
        binding.dateListView.setVisibility(View.VISIBLE);
        binding.dateListView.setLayoutManager(new LinearLayoutManager(this));
        timeUserTiemLineAdapter = new TimeUserTiemEditLineAdapter(mData.getData().getDateList(), this, projectId);
        binding.dateListView.setAdapter(timeUserTiemLineAdapter);

        timeUserTiemLineAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.startTime) {
                    selectPoint = position;
                    isStartTime = true;
                    initTimePicker(mData.getData().getDateList().get(position).getStart_time());
                } else if (view.getId() == R.id.endTime) {
                    selectPoint = position;
                    isStartTime = false;
                    initTimePicker(mData.getData().getDateList().get(position).getEnd_time());
                }

            }
        });

    }

    private void initTimePicker(String date) {//Dialog 模式下，在底部弹出

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (date != null && !date.equals("")) {
            calendar.setTime(new Date(Long.parseLong(date) * 1000));
        }

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(TimeEditActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                String hm = Tools.getTimeFormatHourMinute(date.getTime());
                String s = mData.getData().getDateList().get(selectPoint).getDate_time() + " " + hm + ":00";
                String time =Tools.getLongformatTime( s );
                if (isStartTime) {
                    mData.getData().getDateList().get(selectPoint).setStart_time(time);
                } else {
                    mData.getData().getDateList().get(selectPoint).setEnd_time(time);
                }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 22) {
                List<String> dateList = new LinkedList<>();
                mData.getData().setDateList(new LinkedList<>());
                String startTime = data.getStringExtra("startTime");
                String endTime = data.getStringExtra("endTime");
                if(startTime.equals(endTime))
                {
                    GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
                    date.setDate_time(endTime);
                    mData.getData().getDateList().add(date);
                    binding.startTime.setText(startTime);
                    binding.endTime.setText(endTime);
                    changeDate();
                    return;
                }
//                String hm = Tools.getTimeFormatHourMinute(date.getTime());
                String s = Tools.getLongformat( startTime);
                String e = Tools.getLongformat( endTime);

                dateList =  betweenDays(Long.parseLong(s) * 1000,Long.parseLong(e)* 1000);


                for (int i = 0; i < dateList.size(); i++) {

                    GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
                    date.setDate_time(dateList.get(i));
                    mData.getData().getDateList().add(date);

                }
                binding.startTime.setText(startTime);
                binding.endTime.setText(endTime);

                changeDate();
            }

        }
    }


    private long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    /**
     * 计算两个日期之间的日期
     *
     * @param startTime
     * @param endTime
     */
    private List<String> betweenDays(long startTime, long endTime) {

        List<String> list = new LinkedList<>();
        Date date_start = new Date(startTime);
        Date date_end = new Date(endTime);

        //计算日期从开始时间于结束时间的0时计算
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date_start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date_end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        int s = (int) ((toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) / (ONE_DAY_MS));
        if (s > 0) {
            for (int i = 0; i <= s; i++) {
                long todayDate = fromCalendar.getTimeInMillis() + i * ONE_DAY_MS;
                /**
                 * yyyy-MM-dd E :2012-09-01
                 */
                Log.i("打印日期", getCustonFormatTime(todayDate, "yyyy-MM-dd"));

                list.add(getCustonFormatTime(todayDate, "yyyy-MM-dd"));
                //取出多少号  添加集合  九宫格展示


            }
        }
//        else {//此时在同一天之内
//            Log.i("打印日期",getCustonFormatTime(startTime,"yyyy-MM-dd"));
//        }
        return list;
    }

    /**
     * 格式化传入的时间
     *
     * @param time      需要格式化的时间
     * @param formatStr 格式化的格式
     * @return
     */
    public String getCustonFormatTime(long time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date d1 = new Date(time);
        return format.format(d1);
    }


}
