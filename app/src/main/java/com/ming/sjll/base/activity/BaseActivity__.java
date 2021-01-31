package com.ming.sjll.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.ming.sjll.base.cache.ACache;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseActivity__ extends BasePermissionAty {
    public final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private LinearLayout mViewContainer;
    private Unbinder mUnbinder;
    /**
     * 记录所有活动的Activity
     */
    private static final List<BaseActivity__> mActivities = new LinkedList<BaseActivity__>();
    public static ACache mCache;


    //适配矢量图
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivities.add(this);
        mCache = ACache.get(this);
    }

    public void setContentView(int layoutId, String title) {
        setContentViews(layoutId, title);
    }


    public void setContentView(int layoutId) {
        setContentViews(layoutId, "");
    }

    private void setContentViews(int layoutId, String title) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout parentView = (LinearLayout) inflater.inflate(layoutId, null);
        super.setContentView(parentView);
        setContentView(parentView);

        mContext = this;

//        mViewContainer = (LinearLayout) parentView.findViewById(R.id.ll_container);
        // mTitleBar = (TitleBar) parentView.findViewById(R.id.titleBar);
//        if (!TextUtils.isEmpty(title)) {
//            mTitleBar.setVisibility(View.VISIBLE);
//            mTitleBar.setTitle(title);
//        } else {
//            mTitleBar.setVisibility(View.GONE);
//        }
//        View childView = inflater.inflate(layoutId, null);
//        mViewContainer.addView(childView);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        mActivities.remove(this);

    }

    /**
     * 关闭指定类的activity
     *
     * @param clazz
     */
    public static void finishActivity(Class clazz) {
        List<BaseActivity__> copy;
        synchronized (mActivities) {
            copy = new ArrayList<BaseActivity__>(mActivities);
        }
        for (BaseActivity__ activity : copy) {
            if (activity != null
                    && activity.getClass().getName().equals(clazz.getName())) {
                activity.finish();
            }
        }
    }


}
