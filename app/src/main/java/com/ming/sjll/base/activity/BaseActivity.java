package com.ming.sjll.base.activity;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.appication.SJLLApplication;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.bar.StatusBarUtil;
import com.ming.sjll.base.utils.Tools;

import com.ming.sjll.login.LoginActivity;
import com.ming.sjll.toolPage.LoadingDailog;
import com.ming.sjll.ui.PagedListView;


import java.lang.reflect.Field;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getSimpleName();
    protected LoadingDailog loadingDailog;


    protected LocalActivityManager manager = null;

    protected AppCompatActivity getContext()
    {
        return this;
    }
    protected AppCompatActivity getActivity()
    {
        return this;
    }
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

//        ButterKnife.bind(savedInstanceState.getClass());

        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        setTypeface();
        initLoading();

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Helvetica.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
    }

    private void initLoading()
    {
        if(loadingDailog == null)
        {
            loadingDailog = new LoadingDailog(this.getWindow().getContext());
        }

//        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
//                .setMessage("加载中...")
//                .setCancelable(true)
//                .setCancelOutside(true);
//        loadingDailog = loadBuilder.create();

    }
    public Typeface typeFace;
    /**
     * 通过反射方法设置app全局字体
     */
    public void setTypeface(){
        typeFace = Typeface.createFromAsset(getAssets(), "fonts/pu_hui_ti.ttf");
        try
        {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, typeFace);
        }
        catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 点击后退按钮
     * @param view
     */
    public void onBackClick(View view){
        finish();
    }

    /**
     * 通过activity获取视图
     *
     * @param id
     * @param intent
     * @return
     */
    protected View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }


    /**
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
//        StatusBarUtil.setTranslucentStatus(this);
//        StatusBarUtil.setStatusBarColor(this,Color.TRANSPARENT);
//        StatusBarUtil.setImmersiveStatusBar(this, false);
//        StatusBarUtil.setStatusBarFontIconDark(this, StatusBarUtil.TYPE_M);

    }


    /**
     * 登录后跳转到首页
     *
     * @param token
     * @param rcToken
     * @param userId
     */
    protected void loginToHome(String token, String rcToken, String userId) {
        Cache.setUserToken(token);
        Cache.CacheValue("rcToken", rcToken);
        Cache.CacheValue("userId", userId);
        Cache.CacheValue("test", "");
//        Tools.jump(this, MainActivity.class, true);
        loginToHome();
    }
    protected void loginToHome()
    {
        Context context = SJLLApplication.getInstance();
//        SavePreferencesData mSavePreferencesData = new SavePreferencesData(context);
//        mSavePreferencesData.putStringData("token", null);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    protected void setListViewHeightBasedOnChildren(PagedListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); //获得Adapter
        if (listAdapter == null) {  //判断是否为空
            return;
        }
        int totalHeight = 0;  //定义总高度
        //根据listAdapter.getCount()获取当前拥有多少个item项，然后进行遍历对每一个item获取高度再相加最终获得总的高度。
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //获取到list的布局属性
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //listview最终高度为item的高度+分隔线的高度，这是重新设置listview的属性
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//将重新设置的params再应用到listview中
        listView.setLayoutParams(params);
    }

}
