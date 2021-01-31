package com.ming.sjll.project.manage;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.ming.sjll.Bean.GetInfoDateInfoBean;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.My.common.Setting;
import com.ming.sjll.My.personage.PersonageTag;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.DialogTextTip;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.databinding.ProjectManageHomeBinding;
import com.ming.sjll.databinding.ProjectManageTimeEditBinding;
import com.ming.sjll.project.adapter.TagItemAdapter;
import com.ming.sjll.project.manage.adapter.TimeUserTiemEditLineAdapter;
import com.ming.sjll.project.manage.adapter.TimeUserTiemLineAdapter;
import com.ming.sjll.toolPage.DateFrame;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;
import com.necer.calendar.BaseCalendar;
import com.necer.enumeration.SelectedModel;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 项目管理
 */
public class TimeEditActivity extends BaseActivity {

    ProjectManageTimeEditBinding binding;


    private List<Fragment> fragmentList;

    TeamFragment teamFragment;
    TimeFragment timeFragment;
    String projectId;
    String userId;
    String occupationId;
    GetInfoDateInfoBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.project_manage_time_edit);
        binding.dateFrame.setSelectedModel(SelectedModel.MULTIPLE);


//        setContentView(R.layout.project_manage_team);
        initView();
    }

    private void initView() {
        projectId = getIntent().getStringExtra("projectId");
        occupationId = getIntent().getStringExtra("occupationId");
        userId = getIntent().getStringExtra("userId");

        binding.titleBar.titleBarIvRight.setVisibility(View.VISIBLE);
        binding.titleBar.titleBarIvRight.setBackground(getResources().getDrawable(R.mipmap.btn_save_top8));

        binding.titleBar.titleBarIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpParamsObject param = new HttpParamsObject();
                param.setParam("userId", userId);
                param.setParam("projectId", projectId);
                param.setParam("occupationId", occupationId);
                param.setParam("type", mData.getData().getType());
                param.setParam("value", mData.getData().getValue());
                param.setParam("price", mData.getData().getPrice());
                List<String> temList = new LinkedList<>();
                for (int i = 0; i < mData.getData().getDateList().size(); i++) {
                    param.setParam("dateList["+i+"][start]", mData.getData().getDateList().get(i).getStart_time());
                    param.setParam("dateList["+i+"][end]", mData.getData().getDateList().get(i).getEnd_time());
                    temList.add(mData.getData().getDateList().get(i).getDate_time());
                }
                param.setParam("dateTime",temList);

                new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_SCHEDULE, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        Intent intent = new Intent();
                        setResult(TimeEditActivity.RESULT_OK, intent);
                        finish();
                    }
                });

            }
        });

        getData();

    }

    private void getData() {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("userId", userId);
        param.setParam("projectId", projectId);
        param.setParam("occupationId", occupationId);
        new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_USER_PAYMENT, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                mData = new Gson().fromJson(data, GetInfoDateInfoBean.class);

                initData();

            }
        });
    }

    private void initData() {
        binding.dateFrame.setPointList(mData.getData().getConflictDate());

        List<LocalDate> selectDateList = new LinkedList<>();
        for (int i = 0; i < mData.getData().getDateList().size(); i++) {
            LocalDate localDate = new LocalDate(mData.getData().getDateList().get(i).getDate_time());
            selectDateList.add(localDate);
        }
        binding.dateFrame.setSelectData(selectDateList);


        binding.dateFrame.setListener(new DateFrame.Listener() {
            @Override
            public void onCalendarChangedListener(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
                super.onCalendarChangedListener(baseCalendar, year, month, localDate);
            }

            @Override
            public void setOnCalendarMultipleChangedListener(BaseCalendar baseCalendar, int year, int month, List<LocalDate> currPagerCheckedList, List<LocalDate> totalCheckedList) {
                super.setOnCalendarMultipleChangedListener(baseCalendar, year, month, currPagerCheckedList, totalCheckedList);

                if (totalCheckedList.size() == mData.getData().getDateList().size()) {
                    return;
                } else if (totalCheckedList.size() > mData.getData().getDateList().size()) {


                    String selectData = totalCheckedList.get(totalCheckedList.size() - 1).toString();

                    for (int i = 0; i < mData.getData().getConflictDate().size(); i++) {
                        if (mData.getData().getConflictDate().get(i).equals(selectData)) {
                            //                提示
                            DialogTextTip dialog = new DialogTextTip(TimeEditActivity.this, "该人员时间与本项目冲突，是否继续添加");
                            dialog.show(new CommonCallback() {
                                @Override
                                public void onNext() {
                                    super.onNext();
                                    GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
                                    date.setDate_time(selectData);
                                    mData.getData().getDateList().add(date);

                                    List<LocalDate> dateList = new LinkedList<>();
                                    for (int i = 0; i < mData.getData().getDateList().size(); i++) {
                                        LocalDate localDate = new LocalDate(mData.getData().getDateList().get(i).getDate_time());
                                        dateList.add(localDate);
                                    }
                                    binding.dateFrame.setSelectData(dateList);
                                    changeDate();
                                    dialog.hide();
                                }

                                @Override
                                public void onCancel() {
                                    super.onCancel();
                                    List<LocalDate> dateList = new LinkedList<>();
                                    for (int i = 0; i < mData.getData().getDateList().size(); i++) {
                                        LocalDate localDate = new LocalDate(mData.getData().getDateList().get(i).getDate_time());
                                        dateList.add(localDate);
                                    }
                                    binding.dateFrame.setSelectData(dateList);
                                    changeDate();
                                }
                            });
                            return;
                        }
                    }


                    GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
                    date.setDate_time(selectData);
                    mData.getData().getDateList().add(date);

                    List<LocalDate> dateList = new LinkedList<>();
                    for (int i = 0; i < mData.getData().getDateList().size(); i++) {
                        LocalDate localDate = new LocalDate(mData.getData().getDateList().get(i).getDate_time());
                        dateList.add(localDate);
                    }
                    binding.dateFrame.setSelectData(dateList);
                    changeDate();
                } else {
                    for (int i = 0; i < mData.getData().getDateList().size(); i++) {
                        boolean flag = false;
                        for (int j = 0; j < totalCheckedList.size(); j++) {
                            if (totalCheckedList.get(j).toString().equals(mData.getData().getDateList().get(i).getDate_time())) {
                                flag = true;
                            }
                        }
                        if (!flag) {
                            mData.getData().getDateList().remove(i);
                            changeDate();
                            break;
                        }
                    }


                }


            }
        });


        initTag();
    }

    TagItemAdapter adapter;

    private int selectPoint;
    private boolean isStartTime;

    private void initTag() {

        RecyclerView recyclerList = binding.recyclerList;
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(TimeEditActivity.this, true);
        recyclerList.setLayoutManager(layout);
        adapter = new TagItemAdapter(mData.getData().getPayment());
        recyclerList.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mData.getData().getPayment().size(); i++) {
                    mData.getData().getPayment().get(i).setIs_select(false);
                }
                mData.getData().getPayment().get(position).setIs_select(true);
                mData.getData().setType(mData.getData().getPayment().get(position).getType());
                tagEvent();
                adapter.notifyDataSetChanged();
            }
        });
        tagEvent();
    }

    private void tagEvent() {
        binding.selectCountWrap.setVisibility(View.GONE);
        binding.selectQuoteWrap.setVisibility(View.GONE);

        if (mData.getData().getType().equals("6")) {
            binding.selectQuoteWrap.setVisibility(View.VISIBLE);
            binding.quoteInput.setText(mData.getData().getPrice());
        } else if (mData.getData().getType().equals("3")) {
            binding.selectCountWrap.setVisibility(View.VISIBLE);
            binding.countInput.setText(mData.getData().getValue());
            binding.unit.setText("件");
        } else if (mData.getData().getType().equals("4")) {
            binding.selectCountWrap.setVisibility(View.VISIBLE);
            binding.countInput.setText(mData.getData().getValue());
            binding.unit.setText("套");
        } else if (mData.getData().getType().equals("5")) {
            binding.selectCountWrap.setVisibility(View.VISIBLE);
            binding.countInput.setText(mData.getData().getValue());
            binding.unit.setText("张");
        }
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
                String time = Long.parseLong(Tools.getLongformatTime( mData.getData().getDateList().get(selectPoint).getDate_time() + " " + hm + ":00"))   + "";

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

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


}
