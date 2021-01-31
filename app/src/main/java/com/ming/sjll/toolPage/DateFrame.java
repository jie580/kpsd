package com.ming.sjll.toolPage;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ming.sjll.Home.Purchaser;
import com.ming.sjll.R;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.ToolPageDateFrameBinding;
import com.ming.sjll.ui.FalseCalendar;
import com.necer.calendar.BaseCalendar;
import com.necer.enumeration.SelectedModel;
import com.necer.listener.OnCalendarChangedListener;
import com.necer.listener.OnCalendarMultipleChangedListener;
import com.necer.painter.InnerPainter;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class DateFrame extends FrameLayout {

    private ToolPageDateFrameBinding binding;

    /**
     * 选择模式
     */
    SelectedModel mSelectedModel  = SelectedModel.SINGLE_SELECTED;

    /**
     * 事件监听
     */
    Listener listener;

    /**
     * 时间区间的开始时间
     */
    String startTime;

    /**
     * 今天日期
     */
    Integer nowYear,nowMonth,nowDay;

    private boolean canSelectPastTime = false;

    public DateFrame(Context context) {
        super(context);
        // 加载布局
//        LayoutInflater.from(context).inflate(R.layout.tool_page_date_frame, this);
//        initView();
//        dateEvent();
    }

    LayoutInflater inflater;
    View mView;
    public DateFrame(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        // 加载布局
//        LayoutInflater.from(context).inflate(R.layout.tool_page_date_frame, this);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mView = inflater.inflate(R.layout.tool_page_date_frame, this, true);
        initView();
        dateEvent();
    }

    public void setListener(Listener listener)
    {
        this.listener  = listener;
    }
    /**
     * 设置区间的开始时间，默认是今天
     * @param time
     */
    public void setStartTime(String time)
    {
        this.startTime = time;
    }
    /**
     * 设置模式
     * @param selectedMode
     */
    public void setSelectedModel(SelectedModel selectedMode)
    {
        this.mSelectedModel = selectedMode;
        monthCalendar.setSelectedMode(mSelectedModel);
    }

    FalseCalendar monthCalendar;
    ImageView subYear;
    TextView yearText;
    ImageView addYear;
    ImageView subMonth;
    TextView monthText;
    ImageView addMonth;

    private  void initView()
    {
        if(startTime == null)
        {
            Tools.Date();
        }
        final Calendar c = Calendar.getInstance();
        nowYear = c.get(Calendar.YEAR);
        nowMonth = c.get(Calendar.MONTH)+1 ;
        nowDay = c.get(Calendar.DAY_OF_MONTH);

        monthCalendar = (FalseCalendar)mView.findViewById(R.id.monthCalendar);
        subYear = (ImageView)mView.findViewById(R.id.subYear);
        yearText = (TextView)mView.findViewById(R.id.yearText);
        addYear = (ImageView)mView.findViewById(R.id.addYear);

        subMonth = (ImageView)mView.findViewById(R.id.subMonth);
        monthText = (TextView)mView.findViewById(R.id.monthText);
        addMonth = (ImageView)mView.findViewById(R.id.addMonth);

        monthCalendar.setDateInterval(Tools.Date(), "2099-12-30");

        //                选中
//        jumpDate(2020,7,2);
//        monthCalendar.toToday();
//        monthCalendar.setInitializeDate("2020-7-2");


//        List<String> pointList = Arrays.asList("2020-6-29", "2020-7-29", "2018-11-20", "2020-6-4", "2019-01-01");
//        InnerPainter innerPainter = (InnerPainter) monthCalendar.getCalendarPainter();
//        innerPainter.setPointList(pointList);

//        List<String> list = Arrays.asList();
//        list.add("2020-6-29");
//        list.add("2020-7-29");
//        list.add("2020-6-4");
//        setPointList(list);

//        jumpDate(nowYear,nowMonth,nowDay);
    }


    private void dateEvent()
    {
//


        /**
         * 单选选择日期后
         */
        monthCalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, LocalDate localDate){
                if(localDate == null)
                {
                    Log.e("日期dan选","空");
                }else
                {
                    Log.e("日期dan选",localDate.toString());
                }

                Integer y = Integer.valueOf(year);
                yearText.setText(y.toString());
                Integer m = Integer.valueOf(month);
                if( m < 10)
                {
                    monthText.setText("0"+m);
                }
                else
                {
                    monthText.setText(m.toString());
                }
//                事件回调
                if (listener != null)
                {
                    listener.onCalendarChangedListener( baseCalendar,  year, month, localDate);
                }
            }

        });
        /**
         * 多选选择日期后
         */
        monthCalendar.setOnCalendarMultipleChangedListener(new OnCalendarMultipleChangedListener() {
            @Override
            public void onCalendarChange(BaseCalendar baseCalendar, int year, int month, List<LocalDate> currectSelectList, List<LocalDate> allSelectList)
            {
                    Log.e("日期多选","duoxu");
                Integer m = Integer.valueOf(month);
                if( m < 10)
                {
                    monthText.setText("0"+m);
                }
                else
                {
                    monthText.setText(m.toString());
                }
                if (listener != null)
                {
                    listener.setOnCalendarMultipleChangedListener( baseCalendar,  year, month, currectSelectList,allSelectList);
                }
            }
        });
//        加年
        addYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentYear = Integer.valueOf(yearText.getText().toString()) + 1;
                int currentMonth = Integer.valueOf(monthText.getText().toString());

                jumpMonth(currentYear,currentMonth);
            }
        });
//        减年
        subYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentYear = Integer.valueOf(yearText.getText().toString()) - 1;
                int currentMonth = Integer.valueOf(monthText.getText().toString());
                if(canSelectPastTime || check(currentYear,currentMonth))
                {
                    jumpMonth(currentYear,currentMonth);
                }
                else {
                    ToastShow.s("过去日期不可选");
                }
            }
        });
//        加月
        addMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentYear = Integer.valueOf(yearText.getText().toString());
                int currentMonth = Integer.valueOf(monthText.getText().toString()) + 1;
                if(currentMonth > 12)
                {
                    currentMonth = 1;
                    currentYear += 1;
                }
                if(canSelectPastTime || check(currentYear,currentMonth))
                {
                    jumpMonth(currentYear,currentMonth);
                }
                else {
                    ToastShow.s("过去日期不可选");
                }
            }
        });
//        减月
        subMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentYear = Integer.valueOf(yearText.getText().toString());
                int currentMonth = Integer.valueOf(monthText.getText().toString()) - 1;
                if(currentMonth <= 1)
                {
                    currentMonth = 12;
                    currentYear -= 1;
                }
                if(canSelectPastTime || check(currentYear,currentMonth))
                {
                    jumpMonth(currentYear,currentMonth);
                }
                else {
                    ToastShow.s("过去日期不可选");
                }
            }
        });


    }

    /**
     *设置是否显示过去的日期
     * @return
     */
    public void setInitializeFormerlyDate()
    {
        canSelectPastTime = true;
        monthCalendar.setDateInterval("1901-01-02", "2099-12-30");
    }


    /**
     * 获取单个选中的日历
     * @return
     */
    public  String getCheckedDateString()
    {
        List<LocalDate> list =  monthCalendar.getAllSelectDateList();
        if(list.size() <= 0 )
        {
            return "";
        }
        else
        {
            return list.get(0).toString();
        }
    }

    /**
     * 获取多选选中的日期
     * @return
     */
    public List<LocalDate> getAllSelectDateList()
    {
            return monthCalendar.getAllSelectDateList();
    }

    /**
     * 判断年月是否合法
     * @param y
     * @param m
     * @return
     */
    private boolean check(int y,int m)
    {
        if(y == nowYear && m >= nowMonth)
        {
            return  true;
        }
        else if(y > nowYear)
        {
            return true;
        }
        return false;
    }

    public void setSelectData(List<LocalDate> dateList) {
        monthCalendar.exchangeSelectDateList(dateList);
    }


    /**
     * 删除旧标记并且重新添加标记
     */
    public void setPointList(List<String> formatDateList)
    {
        //        添加标记
        InnerPainter innerPainter = (InnerPainter) monthCalendar.getCalendarPainter();
        innerPainter.setPointList( formatDateList);
    }


    /**
     * 获取日历控件
     * @return
     */
    public FalseCalendar getFalseCalendar()
    {
        return  monthCalendar;
    }

    /**
     * 跳转至选择的年月
     * @param year
     * @param moth
     */
    public void jumpMonth(int year,int moth) {
        Integer y = Integer.valueOf(year);
        yearText.setText(y.toString());
        Integer m = Integer.valueOf(moth);
        if( m < 10)
        {
            monthText.setText("0"+m);
        }
        else
        {
            monthText.setText(m.toString());
        }
        monthCalendar.jumpMonth(year, moth);
    }


    /**
     * 跳转至选择的日期
     */
    public void jumpDate(int year,int moth,int day) {

        monthCalendar.jumpDate(year+"-"+moth+"-"+day);
    }

    /**
     * 跳转至选择的日期
     * @param formatDate
     */
    public void jumpDate(String formatDate)
    {
        if (TextUtils.isEmpty(formatDate)) {
            return ;
        }
        int startYear = 0;
        int startMoth = 0;
        int startDay = 0;
        try {
            String[] strings = formatDate.split("-");
            startYear = Integer.valueOf(strings[0]);
            startMoth = Integer.valueOf(strings[1]);
            startDay = Integer.valueOf(strings[2]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        jumpDate(startYear,startMoth,startDay);
    }

    public static class  Listener
    {
        /**
         * 单选事件
         * @param baseCalendar       日历对象，MonthCalendar和WeekCalendar
         * @param year               日历当前页面中间日期->年
         * @param month              日历当前页面中间日期->月
         * @param localDate          日历当前页面选中日期，有选中则返回选中日期，无选中则返回null
         */
        public void onCalendarChangedListener(BaseCalendar baseCalendar, int year, int month, LocalDate localDate){};

        /**
         * 多选事件
         * @param baseCalendar         日历对象，MonthCalendar和WeekCalendar
         * @param year                 日历当前页面中间日期->年
         * @param month                日历当前页面中间日期->月
         * @param currPagerCheckedList 当前页面选中的日期集合，无选中则为空集合
         * @param totalCheckedList     日历总共的选中集合，无选中则为空集合
         */
        public void setOnCalendarMultipleChangedListener(BaseCalendar baseCalendar, int year, int month, List<LocalDate> currPagerCheckedList, List<LocalDate> totalCheckedList){};
    }

}
