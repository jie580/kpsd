package com.ming.sjll.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

public class CustomViewPager extends ViewPager {
    private boolean canScroll = true;

    public void setCanScroll( boolean canScroll)
    {
        this.canScroll =canScroll;
    }
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {

        if(canScroll == false)
        {
            return true;
        }
        if (v instanceof HorizontalScrollView) {
            HorizontalScrollView h = (HorizontalScrollView) v;
            return true;
        } else if (v instanceof ListView) {
            return true;
        }
        else if(((Activity)getContext()).getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        {
            return true;
        }
//        else if (v instanceof RecyclerView) {
//            return true;
//        }
        return super.canScroll(v, checkV, dx, x, y);
    }



}