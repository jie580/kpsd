package com.necer.calendar;

import com.necer.enumeration.CalendarState;
import com.necer.listener.OnCalendarScrollingListener;
import com.necer.listener.OnCalendarStateChangedListener;

/**
 * 折叠日历特有的功能接口
 */
public interface IICalendar extends ICalendar {

    //回到周状态
    void toWeek();

    //回到月状态
    void toMonth();

    //设置周滑动到周位置固定
    void setWeekHoldEnable(boolean isWeekHoldEnable);

    //设置月状态下 下拉拉伸
    void setMonthStretchEnable(boolean isMonthStretchEnable);

    //日历月周状态变化回调
    void setOnCalendarStateChangedListener(OnCalendarStateChangedListener onCalendarStateChangedListener);

    //日历 月 周 拉伸 状态滑动监听
    void setOnCalendarScrollingListener(OnCalendarScrollingListener onCalendarScrollingListener);

    //设置日历状态
    void setCalendarState(CalendarState calendarState);

    //获取当前日历的状态  CalendarState.MONTH==月视图     CalendarState.WEEK==周视图
    CalendarState getCalendarState();


}
