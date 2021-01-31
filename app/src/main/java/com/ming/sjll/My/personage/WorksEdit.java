package com.ming.sjll.My.personage;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.Bean.ScheduleIListItem;
import com.ming.sjll.Bean.ScheduleItemBean;
import com.ming.sjll.Bean.StringListBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.ScheduleListAdapter;
import com.ming.sjll.My.company.ShowcaseJoinProjectDateActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyWorkEditBinding;
import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.project.dialog.ProjectDate;
import com.ming.sjll.project.manage.adapter.TimeUserTiemEditLineAdapter;
import com.ming.sjll.toolPage.DateFrame;
import com.rey.material.app.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 个人中心-添加、编辑工作
 */
public class WorksEdit extends BaseActivity {


    boolean isDay = true;

    String projectId;

    private int selectPoint;
    private boolean isStartTime;
    TimeUserTiemEditLineAdapter timeUserTiemLineAdapter;

    private MyWorkEditBinding binding;
    private List<GetInfoDateListBean.DataBean.DataBeanY> dateList = new LinkedList<>();

    HttpParamsObject commonParam = new HttpParamsObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.my_work_edit);
        binding.titleBar.titleBarIvRight.setVisibility(View.VISIBLE);
        binding.titleBar.titleBarIvRight.setBackground(getResources().getDrawable(R.drawable.btn_save_top2));
        initView();
    }


    private void initView()
    {


        ScheduleItemBean schedule = (ScheduleItemBean)getIntent().getSerializableExtra("project");
        if(schedule != null)
        {
            for (int i = 0; i < schedule.getData().getDateList().size(); i++) {
                String s = Tools.getDateformat3(Long.parseLong(schedule.getData().getDateList().get(i).getDate_time()),"yyyy-MM-dd");
                schedule.getData().getDateList().get(i).setDate_time(s);
            }
            dateList = schedule.getData().getDateList();

            commonParam.setParam("area_code",schedule.getData().getArea_code());
            commonParam.setParam("provinces",schedule.getData().getProvinces());
            commonParam.setParam("city",schedule.getData().getCity());
            commonParam.setParam("county",schedule.getData().getCounty());
            commonParam.setParam("address_title",schedule.getData().getAddress_title());
            commonParam.setParam("address",schedule.getData().getAddress());
            commonParam.setParam("meridian",schedule.getData().getMeridian());
            commonParam.setParam("weft",schedule.getData().getWeft());

            projectId = schedule.getData().getProject_id();


            if (schedule.getData().getDate_type().equals("2")) {
                isDay = true;
                commonParam.setParam("type","2");
                binding.isDay.setBackgroundResource(R.mipmap.btn_switch_y);
            } else {
                isDay = false;
                commonParam.setParam("type","1");
                binding.isDay.setBackgroundResource(R.mipmap.btn_switch_n);
            }
            binding.demandTip.setText(schedule.getData().getDemand());
            binding.addressTip.setText(schedule.getData().getAddress_title());

            TextView startTimeDay = binding.startTimeDay;
            TextView endTimeDay =binding.endTimeDay;

            TextView startTime =binding.startTime;
            TextView endTime =binding.endTime;

            String st = Tools.getDateformatDay(Long.parseLong(schedule.getData().getDateList().get(0).getStart_time()));
            String std = Tools.getDateformat3(Long.parseLong(schedule.getData().getDateList().get(0).getStart_time()),"MM月dd日");
            if(schedule.getData().getDateList().size() == 1)
            {
                startTimeDay.setText(st);
                endTimeDay.setText(st);

                startTime.setText(std);
                endTime.setText(std);
            }
            else if(schedule.getData().getDateList().size()>1)
            {
                String et = Tools.getDateformatDay(Long.parseLong(schedule.getData().getDateList().get(schedule.getData().getDateList().size()-1).getStart_time()));
                String etd = Tools.getDateformat3(Long.parseLong(schedule.getData().getDateList().get(schedule.getData().getDateList().size()-1).getStart_time()),"MM月dd日");
                startTimeDay.setText(st);
                endTimeDay.setText(et);

                startTime.setText(std);
                endTime.setText(etd);
            }
            changeDate();


        }
        else
        {
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
            GetInfoDateListBean.DataBean.DataBeanY temp = new GetInfoDateListBean.DataBean.DataBeanY();
            binding.startTimeDay.setText(Tools.getDateformatDay(date.getTime() / 1000));
            binding.endTimeDay.setText(Tools.getDateformatDay(date.getTime() / 1000));
            binding.startTime.setText(df.format(date));
            binding.endTime.setText(df.format(date));
            temp.setDate_time(Tools.getDateformat(date.getTime()/ 1000));
            dateList.add(temp);
        }

        event();
    }

    private void event()
    {

        //        选择地址
        binding.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.init(getContext());
                Intent intent = new Intent(getContext(), MyTencentMap.class);
                startActivityForResult(intent, PictureConfig.MAP_HEAD_REQUEST);
            }
        });

        binding.selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowcaseJoinProjectDateActivity.class);
//                intent.putExtra("dateList", (Serializable) getConflictDate());
                startActivityForResult(intent, 22);
            }
        });
        binding.daySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDay = !isDay;
                if (isDay) {
                    commonParam.setParam("type","2");
                    binding.isDay.setBackgroundResource(R.mipmap.btn_switch_y);
                } else {
                    commonParam.setParam("type","1");
                    binding.isDay.setBackgroundResource(R.mipmap.btn_switch_n);
                }
                changeDate();
            }
        });

        binding.titleBar.titleBarIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                commonParam.setParam("project_id", projectId);
                commonParam.setParam("demand", binding.demandTip.getText().toString());
                commonParam.setParam("type", isDay ? 2 : 1);

                List<String> temList = new LinkedList<>();
                for (int i = 0; i < getDateList().size(); i++) {
                    commonParam.setParam("dateList[" + i + "][start]", getDateList().get(i).getStart_time());
                    commonParam.setParam("dateList[" + i + "][end]", getDateList().get(i).getEnd_time());
                    temList.add(getDateList().get(i).getDate_time());
                }
                commonParam.setParam("dateTime", temList);


                new HttpUtil().sendRequest(Constant.MEMBER_ADD_SCHEDULE, commonParam, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("保存成功");
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                });
            }
        });
    }


    private void changeDate() {

        if (isDay) {
            binding.dateListView.setVisibility(View.GONE);
        } else {
            binding.dateListView.setVisibility(View.VISIBLE);
        }

        binding.dateListView.setLayoutManager(new LinearLayoutManager(this));
        timeUserTiemLineAdapter = new TimeUserTiemEditLineAdapter(getDateList(), this, "");
        binding.dateListView.setAdapter(timeUserTiemLineAdapter);

        timeUserTiemLineAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.startTime) {
                    selectPoint = position;
                    isStartTime = true;
                    initTimePicker(getDateList().get(position).getStart_time());
                } else if (view.getId() == R.id.endTime) {
                    selectPoint = position;
                    isStartTime = false;
                    initTimePicker(getDateList().get(position).getEnd_time());
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
                String s = getDateList().get(selectPoint).getDate_time() + " " + hm + ":00";
                String time = Tools.getLongformatTime(s);
                if (isStartTime) {
                    getDateList().get(selectPoint).setStart_time(time);
                } else {
                    getDateList().get(selectPoint).setEnd_time(time);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.MAP_HEAD_REQUEST) {
                double longitude = data.getDoubleExtra("longitude", 0);
                double latitude = data.getDoubleExtra("latitude", 0);
                String area_code = data.getStringExtra("area_code");
                String title = data.getStringExtra("title");
                String adname = data.getStringExtra("address");
                String province = data.getStringExtra("province");
                String city = data.getStringExtra("city");
                String district = data.getStringExtra("district");

                commonParam.setParam("area_code",area_code);
                commonParam.setParam("provinces",province);
                commonParam.setParam("city",city);
                commonParam.setParam("county",district);
                commonParam.setParam("address_title",title);
                commonParam.setParam("address",adname);
                commonParam.setParam("meridian",longitude+"");
                commonParam.setParam("weft",latitude+"");

//                String  s = province+city+district;

               binding.addressTip.setText(title);

            }
            else if (requestCode == 22) {
                SimpleDateFormat df = new SimpleDateFormat("MM月dd日");
                List<String> dateList = new LinkedList<>();
                setDateList(new LinkedList<>());
                String startTime = data.getStringExtra("startTime");
                String endTime = data.getStringExtra("endTime");
                String s = Tools.getLongformat(startTime);
                String e = Tools.getLongformat(endTime);

                if (startTime.equals(endTime)) {
                    GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
                    date.setDate_time(endTime);
                    getDateList().add(date);

                    binding.startTimeDay.setText(Tools.getDateformatDay(Long.parseLong(s)));
                    binding.endTimeDay.setText(Tools.getDateformatDay(Long.parseLong(e)));

                    binding.startTime.setText(df.format(Long.parseLong(s) * 1000));
                    binding.endTime.setText(df.format(Long.parseLong(s) * 1000));
                    changeDate();
                    return;
                }
//                String hm = Tools.getTimeFormatHourMinute(date.getTime());



                binding.startTimeDay.setText(Tools.getDateformatDay(Long.parseLong(s)));
                binding.endTimeDay.setText(Tools.getDateformatDay(Long.parseLong(e)));

                binding.startTime.setText(df.format(Long.parseLong(s) * 1000));
                binding.endTime.setText(df.format(Long.parseLong(e) * 1000));

                dateList = betweenDays(Long.parseLong(s) * 1000, Long.parseLong(e) * 1000);


                for (int i = 0; i < dateList.size(); i++) {

                    GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
                    date.setDate_time(dateList.get(i));
                    getDateList().add(date);

                }


//                df.format(s);
//                binding.startTime.setText(startTime);
//                binding.endTime.setText(endTime);

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


    public List<GetInfoDateListBean.DataBean.DataBeanY> getDateList() {
        return dateList;
    }

    public void setDateList(List<GetInfoDateListBean.DataBean.DataBeanY> dateList) {
        this.dateList = dateList;
    }





}
