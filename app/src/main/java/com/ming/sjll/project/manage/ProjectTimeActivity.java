package com.ming.sjll.project.manage;

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
import com.ming.sjll.Bean.GetInfoDateInfoBean;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.My.adapter.ShowcaseTagItemAdapter;
import com.ming.sjll.My.company.ShowcaseJoinProjectActivity;
import com.ming.sjll.My.company.ShowcaseJoinProjectDateActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.ProjectManageProjectTimeBinding;
import com.ming.sjll.project.manage.adapter.TimeUserTiemEditLineAdapter;
import com.ming.sjll.search.adapter.ProjectListItemAdapter;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;
import com.ming.sjll.ui.PagedListView;
import com.rey.material.app.BottomSheetDialog;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 项目时间
 */
public class ProjectTimeActivity extends BaseActivity {

    ProjectManageProjectTimeBinding binding;
    TimeUserTiemEditLineAdapter timeUserTiemLineAdapter;
    ShowcaseTagItemAdapter adapter;

    private int selectPoint;
    private boolean isStartTime;


    GetInfoDateInfoBean projectDate;
    String projectId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.project_manage_project_time);
//        binding.dateFrame.setSelectedModel(SelectedModel.MULTIPLE);


//        setContentView(R.layout.project_manage_team);
        initView();
    }

    ProjectListItem projectDataList;

    private void initView() {

        projectId = getIntent().getStringExtra("projectId");


        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId", projectId);
        new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_PROJECT_DATE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                projectDate = new Gson().fromJson(data, GetInfoDateInfoBean.class);
                if(projectDate.getData().getDateList().size() == 1)
                {
                    binding.startTime.setText(projectDate.getData().getDateList().get(0).getDate_time());
                    binding.endTime.setText(projectDate.getData().getDateList().get(0).getDate_time());
                }
                else if(projectDate.getData().getDateList().size()>1)
                {
                    binding.startTime.setText(projectDate.getData().getDateList().get(0).getDate_time());
                    binding.endTime.setText(projectDate.getData().getDateList().get(projectDate.getData().getDateList().size()-1).getDate_time());
                }

                changeDate();

            }
        });

        binding.selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectTimeActivity.this, ShowcaseJoinProjectDateActivity.class);
                intent.putExtra("dateList", (Serializable) projectDate.getData().getConflictDate());
                startActivityForResult(intent, 22);
            }
        });





        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    submit(projectId);
                    return;
            }
        });


    }




    private void submit(String projectId) {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId", projectId);
        for (int i = 0; i < projectDate.getData().getDateList().size(); i++) {
            param.setParam("dateList[" + i + "][start]", projectDate.getData().getDateList().get(i).getStart_time());
            param.setParam("dateList[" + i + "][end]", projectDate.getData().getDateList().get(i).getEnd_time());

        }


        new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_ADD_DATE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
//                Intent intent = new Intent();
//                setResult(ShowcaseJoinProjectActivity.RESULT_OK, intent);
                ToastShow.s("添加成功");
                Bundle bundle = new Bundle();
                bundle.putString("projectId", projectId);
                Tools.jump(getContext(), HomeActivity.class, bundle,true);
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
                String s = projectDate.getData().getDateList().get(selectPoint).getDate_time() + " " + hm + ":00";
                String time =Tools.getLongformatTime( s );
                if (isStartTime) {
                    projectDate.getData().getDateList().get(selectPoint).setStart_time(time);
                } else {
                    projectDate.getData().getDateList().get(selectPoint).setEnd_time(time);
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



    private void changeDate() {

        binding.dateListView.setVisibility(View.VISIBLE);
        binding.dateListView.setLayoutManager(new LinearLayoutManager(this));
        timeUserTiemLineAdapter = new TimeUserTiemEditLineAdapter(projectDate.getData().getDateList(), this, projectId);
        binding.dateListView.setAdapter(timeUserTiemLineAdapter);

        timeUserTiemLineAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.startTime) {
                    selectPoint = position;
                    isStartTime = true;
                    initTimePicker(projectDate.getData().getDateList().get(position).getStart_time());
                } else if (view.getId() == R.id.endTime) {
                    selectPoint = position;
                    isStartTime = false;
                    initTimePicker(projectDate.getData().getDateList().get(position).getEnd_time());
                }

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 22) {
                List<String> dateList = new LinkedList<>();
                projectDate.getData().setDateList(new LinkedList<>());
                String startTime = data.getStringExtra("startTime");
                String endTime = data.getStringExtra("endTime");
                if(startTime.equals(endTime))
                {
                    GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
                    date.setDate_time(endTime);
                    projectDate.getData().getDateList().add(date);
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
                    projectDate.getData().getDateList().add(date);

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
