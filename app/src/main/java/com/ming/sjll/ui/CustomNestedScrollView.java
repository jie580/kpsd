package com.ming.sjll.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomNestedScrollView extends NestedScrollView {

    boolean isNeedScroll = true;

    public CustomNestedScrollView(@NonNull Context context) {
        super(context);
    }

    //    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int a = ev.getAction();
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                return isNeedScroll;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }
//
//    /*
//   改方法用来处理NestedScrollView是否拦截滑动事件
//    */
//    public void setNeedScroll(boolean isNeedScroll) {
//        this.isNeedScroll = isNeedScroll;
//    }

}
