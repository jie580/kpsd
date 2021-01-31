package com.ming.sjll.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class AboutHeightViewpager extends CustomViewPager {



    public AboutHeightViewpager(Context context) {
        super(context);
    }

    public AboutHeightViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int index = getCurrentItem();
//        int height = 0;
//        View v = ((Fragment) getAdapter().instantiateItem(this, index)).getView();
//        if (v != null) {
//            v.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            height = v.getMeasuredHeight();
//        }
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec );
    }

}
