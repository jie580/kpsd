package com.ming.sjll.My.company;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.My.adapter.ShowcaseTagItemAdapter;
import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.MyUtil;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;

import com.ming.sjll.databinding.ProjectManageTimeEditInfoTempBinding;
import com.ming.sjll.project.manage.adapter.TimeUserTiemEditLineAdapter;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;
import com.rey.material.app.BottomSheetDialog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 服务下单页面
 */
public class ShowcaseServiceSelectTimeActivity_temp extends BaseActivity {

    ProjectManageTimeEditInfoTempBinding binding;

//    时
    private static final String HOUR = "1";
//    天
    private static final String DAY = "2";
//    件
    private static final String PIECE = "4";
//    套
    private static final String SET = "5";
//    张
    private static final String SHEET = "6";
//     报价
    private static final String OFFER = "7";

    String unitSelectType = "";
    GoodsItem.DataBean.DataBeanX unitSelect;
    String goodsId;
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
        goodsId = getIntent().getStringExtra("goodsId");

        binding.recyclerListTitle.setText("选择薪酬方式");
        binding.dateTitle.setText("选择预约时间");

        binding.titleBar.titleBarIvRight.setVisibility(View.VISIBLE);
        binding.titleBar.titleBarIvRight.setBackground(getResources().getDrawable(R.mipmap.btn_save_top9));

        binding.titleBar.titleBarIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(ShowcaseServiceSelectTimeActivity.this);
//                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_my_select);
//                bottomInterPasswordDialog.inDuration(300);
//                bottomInterPasswordDialog.outDuration(200);
//                bottomInterPasswordDialog.show();

                submit();

            }
        });

        getData();

    }



    private void submit() {




            BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
            bottomInterPasswordDialog.setContentView(R.layout.dialog_show_case_buy_service);
            bottomInterPasswordDialog.inDuration(300);
            bottomInterPasswordDialog.outDuration(200);

            LinearLayout guide1menu = bottomInterPasswordDialog.findViewById(R.id.guide1menu);
            LinearLayout guide2menu = bottomInterPasswordDialog.findViewById(R.id.guide2menu);
            LinearLayout guide3menu = bottomInterPasswordDialog.findViewById(R.id.guide3menu);

            if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"1"))
            {
                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
            }
            if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"2"))
            {
                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
            }
            if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"3"))
            {
                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
            }
            guide1menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"1"))
                    {
                        return;
                    }
                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked);
                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_un);
                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_un);
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"1"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"2"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"3"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.VISIBLE);
                    bottomInterPasswordDialog.findViewById(R.id.selectType2).setVisibility(View.GONE);
                }
            });
            guide2menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"2"))
                    {
                        return;
                    }
                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_un);
                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked);
                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_un);
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"1"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"2"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"3"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.GONE);
                    bottomInterPasswordDialog.findViewById(R.id.selectType2).setVisibility(View.VISIBLE);
                }
            });
            guide3menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"3"))
                    {
                        return;
                    }
                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_un);
                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_un);
                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked);
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"1"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"2"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"3"))
                    {
                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
                    }
                    bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.GONE);
                    bottomInterPasswordDialog.findViewById(R.id.selectType2).setVisibility(View.GONE);
                }
            });

            bottomInterPasswordDialog.show();





        HttpParamsObject param = new HttpParamsObject();

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
//
//        new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_ADD_PROJECT_GOODS, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {
//                Intent intent = new Intent();
//                setResult(ShowcaseServiceSelectTimeActivity.RESULT_OK, intent);
//                ToastShow.s("添加成功");
//                finish();
//            }
//        });

    }

    private void getData() {
//        HttpParamsObject param = new HttpParamsObject();
//        param.setParam("userId", userId);
//        param.setParam("projectId", projectId);
//        param.setParam("occupationId", occupationId);
//        new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_USER_PAYMENT, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {
        binding.initHide.setVisibility(View.GONE);
        mData = (GoodsItem) getIntent().getSerializableExtra("DataBean");
//                mData = new Gson().fromJson(data, GetInfoDateInfoBean.class);
        initData();

//            }
//        });
    }

    private void initData() {

        mData.getData().setDateList(new LinkedList<>());

        binding.selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowcaseServiceSelectTimeActivity_temp.this, ShowcaseJoinProjectDateActivity.class);
                intent.putExtra("dateList", (Serializable) mData.getData().getDate_time());
                startActivityForResult(intent, 22);
            }
        });

        initTag();
//        tagEvent();
    }

    ShowcaseTagItemAdapter adapter;

    private int selectPoint;
    private boolean isStartTime;

    /**
     * 初始化单位
     */
    private void initTag() {

        RecyclerView recyclerList = binding.recyclerList;
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(ShowcaseServiceSelectTimeActivity_temp.this, true);
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
                unitSelect = mData.getData().getPaymentList().get(position);
                unitSelectType = mData.getData().getPaymentList().get(position).getType();
                tagEvent();
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void tagEvent() {
        binding.selectCountWrap.setVisibility(View.GONE);
        binding.selectQuoteWrap.setVisibility(View.GONE);

        binding.dateTitle.setVisibility(View.GONE);
        binding.selectTime.setVisibility(View.GONE);
        binding.dateTip.setVisibility(View.GONE);
        binding.dateListView.setVisibility(View.GONE);

        if(unitSelectType.equals(HOUR))
        {
            binding.initHide.setVisibility(View.VISIBLE);
            binding.dateTitle.setVisibility(View.VISIBLE);
            binding.selectTime.setVisibility(View.VISIBLE);
            changeDate();
        }
        else if (unitSelectType.equals(DAY)) {
            binding.initHide.setVisibility(View.VISIBLE);
            binding.dateTitle.setVisibility(View.VISIBLE);
            binding.selectTime.setVisibility(View.VISIBLE);
        } else if (unitSelectType.equals(OFFER)) {
            binding.initHide.setVisibility(View.VISIBLE);
            binding.selectQuoteWrap.setVisibility(View.VISIBLE);
            binding.quoteInput.setText(unitSelect.getPrice());
        }
        else {
            binding.initHide.setVisibility(View.VISIBLE);
            binding.selectCountWrap.setVisibility(View.VISIBLE);
          if (unitSelectType.equals(PIECE)) {
                binding.countInput.setText(mData.getData().getValue());
                binding.unit.setText("件");
            } else if (unitSelectType.equals(SET)) {
                binding.countInput.setText(mData.getData().getValue());
                binding.unit.setText("套");
            } else if (unitSelectType.equals(SHEET)) {
                binding.countInput.setText(mData.getData().getValue());
                binding.unit.setText("张");
            }
        }

    }

    TimeUserTiemEditLineAdapter timeUserTiemLineAdapter;

    private void changeDate() {
        if (!unitSelectType.equals("1")) {
            binding.dateListView.setVisibility(View.GONE);
            return;
        }
        binding.dateListView.setVisibility(View.VISIBLE);
        binding.dateListView.setLayoutManager(new LinearLayoutManager(this));
        timeUserTiemLineAdapter = new TimeUserTiemEditLineAdapter(mData.getData().getDateList(), this);
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
