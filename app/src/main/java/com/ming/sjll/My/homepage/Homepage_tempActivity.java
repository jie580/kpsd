package com.ming.sjll.My.homepage;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.PersonageGui;
import com.ming.sjll.Bean.UserTypeBean;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.My.adapter.PersonageAdapter;
import com.ming.sjll.My.common.Collect;
import com.ming.sjll.My.company.Showcase;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.ui.ContentViewPager;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.ming.sjll.ui.DragFloatActionButton;
import com.ming.sjll.ui.LocateCenterHorizontalView;
import com.ming.sjll.ui.StickyScrollView;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.utilities.RongUtils;

public class Homepage_tempActivity extends BaseActivity {

    public  boolean isPersonage = true;
    private  String userId;
    public  GetInfoBean userInfo;




    StickyScrollView stickyScrollView;
    LocateCenterHorizontalView recyclerview;
    ContentViewPager viewPager;
    DragFloatActionButton addSample;
    CustomRoundAngleImageView backdrop;
    ImageView btnInteraction,btnFans;
    View openChar;
    LinearLayout recyclerviewWrap;
    LinearLayout contentWrap;


    FrameLayout scheduleFrameLayout;
    LinearLayout recyclerviewWrap2;


    RelativeLayout fansWrap;
    ImageView homeSex, headImage, is_approve;
    TextView homeName, personalizedTip;

    TextView coverImg;

    PersonageAdapter headAdpter;
    List<PersonageGui.DataBean> dataList;
    private List<Fragment> fragmentList;


    private Fragment[] mFragments;
    HomeScheduleFrame scheduleFrame;
    HomeFans fansFrame;


    int circle = 1;
    int lastShowPage = 0;//0个人或公司，1公司员工排期
    int currentShowPage = 0;//0个人或公司或排期，1公司员工排期 2是收藏


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_homepage_fragemt);
        userId = getIntent().getStringExtra("userId");
        getUserInfo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setContentView(R.layout.my_homepage_fragemt);
        userId = intent.getStringExtra("userId");
        getUserInfo();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfo()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("user_id",userId);
        new HttpUtil().sendRequest(Constant.MEMBER_USER_CENTER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                userInfo = new Gson().fromJson(data, GetInfoBean.class);
                checkUserType();
            }
        });
    }

    public void checkUserType() {
        //        获取账号类型
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("user_id",userId);
        new HttpUtil().sendRequest(Constant.MEMBER_GET_USER_TYPE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                UserTypeBean tempData = new Gson().fromJson(data, UserTypeBean.class);
                isPersonage = tempData.getData().getType().equals("user");
                init();
            }
        });
    }
    private void init() {
        bindingView();
        initView();
        event();
    }

    private void event() {
        /**
         * 打开聊天
         */
        openChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongImUtils.privateChat(getContext(),userInfo.getData().getIm_uid(),userInfo.getData().getIm_name());
            }
        });

        /**
         * 点击背景图
         */
        backdrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPage(2);
            }
        });

        /**
         * 公司主页切换
         */
        btnInteraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPage(1);

            }
        });

        /**
         * 点击关注
         */

        btnFans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("targetId",userId);
                param.setParam("type","user");
                new HttpUtil().sendRequest(Constant.USER_COLLECTION_COLLECT, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        GetInfoBean temp = new Gson().fromJson(data, GetInfoBean.class);
                        updateUserUi("is_collect",temp.getData().getIs_collect());
                    }
                });
            }
        });
    }

    private void bindingView()
    {
        stickyScrollView = ((StickyScrollView) findViewById(R.id.StickyScrollView));
        recyclerview = (LocateCenterHorizontalView) findViewById(R.id.recyclerview);
        viewPager = (ContentViewPager) findViewById(R.id.viewPager);
        addSample = (DragFloatActionButton) findViewById(R.id.addSample);
        backdrop = (CustomRoundAngleImageView) findViewById(R.id.backdrop);
        recyclerviewWrap = (LinearLayout) findViewById(R.id.recyclerviewWrap);
        btnInteraction = (ImageView) findViewById(R.id.btnInteraction);
        contentWrap = (LinearLayout) findViewById(R.id.contentWrap);

        recyclerviewWrap2 = (LinearLayout) findViewById(R.id.recyclerviewWrap2);
        scheduleFrameLayout = (FrameLayout) findViewById(R.id.scheduleFrameLayout);
        recyclerviewWrap2.setGravity(View.GONE);

        fansWrap = (RelativeLayout) findViewById(R.id.fansWrap);
        homeSex = (ImageView) findViewById(R.id.homeSex);
        headImage = (ImageView) findViewById(R.id.headImage);
        is_approve = (ImageView) findViewById(R.id.is_approve);
        homeName = (TextView) findViewById(R.id.homeName);
        coverImg = (TextView) findViewById(R.id.coverImg);
        btnFans = (ImageView) findViewById(R.id.btnFans);
        openChar = (View) findViewById(R.id.openChar);

        fansFrame = HomeFans.newInstance();
        fansFrame.userInfo = userInfo;
        if (isPersonage) {
            mFragments = new Fragment[]{fansFrame};
        } else {
            scheduleFrame = HomeScheduleFrame.newInstance();
            scheduleFrame.userInfo = userInfo;
            mFragments = new Fragment[]{scheduleFrame, fansFrame};
        }
        coverImg.setVisibility(View.GONE);
        addSample.setVisibility(View.GONE);

        showPage();
        SwipeRefreshLayout mSwipe = (SwipeRefreshLayout) findViewById(R.id.mswipeRefreshLayout);

        /*
         * 设置下拉刷新球颜色
         */
        mSwipe.setColorSchemeResources(R.color.blue);
        mSwipe.setRefreshing(false);
//        /*
//         * 设置下拉刷新的监听
//         */
//        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                //刷新需执行的操作
//                mSwipe.setRefreshing(false);
//            }
//        });
    }


    private void showPage() {
        showPage(-1, true);
    }

    private void showPage(int clickBtn) {
        showPage(clickBtn, false);
    }
    /**
     * @param clickBtn 1点击切换按钮 ， 2点击背景图
     */
    private void showPage(int clickBtn, boolean isInit) {

//          int lastShowPage = 0;//0个人或公司，1公司员工排期
//    int currentShowPage = 0;//0个人或公司或排期，1公司员工排期 , 2是收藏
        if (!isInit) {
            if (clickBtn == 1 && lastShowPage == 0)// 上一次是主页，切换到排期
            {
                lastShowPage = 1;
                currentShowPage = 1;
            } else if ((clickBtn == 1 && lastShowPage == 1))// 上一次是排期，切换到主页
            {
                lastShowPage = 0;
                currentShowPage = 0;
                Log.e("dd", "");
            }

            if (clickBtn == 2 && currentShowPage == 2 && lastShowPage == 0)//现在显示收藏，返回lastShowPage的页面，0主页
            {
                currentShowPage = 0;
            } else if (clickBtn == 2 && currentShowPage == 2 && lastShowPage == 1)//现在显示收藏，返回lastShowPage的页面，1排期
            {
                currentShowPage = 1;
            } else if (clickBtn == 2 && currentShowPage != 2) //现在是主页或排期，切换到收藏
            {
                lastShowPage = currentShowPage;
                currentShowPage = 2;
            }
        }

        btnFans.setVisibility(View.GONE);
        if (currentShowPage == 0) {
//                显示主页
            if (viewPager.getCurrentItem() == 0) {
                stickyScrollView.setShowSticky(true);
            }
            btnFans.setVisibility(View.VISIBLE);
            recyclerviewWrap.setVisibility(View.VISIBLE);
            recyclerviewWrap2.setVisibility(View.GONE);
            fansWrap.setVisibility(View.GONE);

            if (isPersonage) {
                btnInteraction.setVisibility(View.GONE);
                ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(backdrop, "translationY", RongUtils.dip2px(-243.0F));
                valueAnimator.setDuration(500);
                valueAnimator.start();
                contentWrap.setPadding(0, RongUtils.dip2px(71.0F), 0, 0);
            } else {
                btnInteraction.setVisibility(View.VISIBLE);
                ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(backdrop, "translationY", RongUtils.dip2px(-175.0F));
                valueAnimator.setDuration(500);
                valueAnimator.start();
                contentWrap.setPadding(0, RongUtils.dip2px(94.0F), 0, 0);
            }


        } else if (currentShowPage == 1) {
//                显示排期
            stickyScrollView.setShowSticky(false);
            recyclerviewWrap.setVisibility(View.GONE);
            recyclerviewWrap2.setVisibility(View.VISIBLE);
            fansWrap.setVisibility(View.GONE);
            btnInteraction.setVisibility(View.VISIBLE);
            ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(backdrop, "translationY", RongUtils.dip2px(-65.0F));
            valueAnimator.setDuration(500);
            valueAnimator.start();
            contentWrap.setPadding(0, RongUtils.dip2px(145.0F), 0, 0);
            showFragment(scheduleFrame);

        } else if (currentShowPage == 2) {
//                显示粉丝
            stickyScrollView.setShowSticky(false);
            recyclerviewWrap.setVisibility(View.GONE);
            recyclerviewWrap2.setVisibility(View.VISIBLE);
            fansWrap.setVisibility(View.VISIBLE);
            btnInteraction.setVisibility(View.GONE);
            ObjectAnimator valueAnimator = ObjectAnimator.ofFloat(backdrop, "translationY", RongUtils.dip2px(0.0F));
            valueAnimator.setDuration(500);
            valueAnimator.start();
            contentWrap.setPadding(0, RongUtils.dip2px(350.0F), 0, 0);

            if (isPersonage) {
                homeName.setText(userInfo.getData().getName());
            } else {
                homeName.setText(userInfo.getData().getFull_company_name());
            }
            new ImageHelper().displayBackgroundLoading(headImage,
                    Constant.BASE_IMAGE + userInfo.getData().getDefault_avatar());
            homeSex.setVisibility(View.VISIBLE);
            if (userInfo.getData().getSex() == null) {
                homeSex.setVisibility(View.GONE);
//                homeSex.setBackgroundResource(R.mipmap.ic_my_sex_man);
            } else {
                if (userInfo.getData().getSex().equals("0")) {
                    homeSex.setBackgroundResource(R.mipmap.ic_my_sex_man);
                } else {
                    homeSex.setBackgroundResource(R.mipmap.ic_my_sex_woman);
                }
            }
            if (userInfo.getData().getIs_approve().equals("0")) {
                is_approve.setVisibility(View.GONE);
            } else {
                is_approve.setVisibility(View.VISIBLE);
            }
            showFragment(fansFrame);
        }

    }


    private void initView()
    {
        updateUserUi("cover_img", userInfo.getData().getCover_img());
        updateUserUi("is_collect",userInfo.getData().getIs_collect());

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        if (isPersonage) {
            fragmentList = getPersonageFragment();
            dataList = getPersonageGui();//个人
        } else {
            fragmentList = getCompanyFragment();
            dataList = getCompanyGui();//公司
        }


        headAdpter = new PersonageAdapter(getContext(), dataList, circle, recyclerview);
        recyclerview.setAdapter(headAdpter);

        recyclerview.setOnSelectedPositionChangedListener(new LocateCenterHorizontalView.OnSelectedPositionChangedListener() {
            @Override
            public void selectedPositionChanged(int pos) {
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setIsetSelected(false);
                }
                dataList.get(pos % dataList.size()).setIsetSelected(true);
                viewPager.setCurrentItem(pos % dataList.size());
                recyclerview.moveToPosition(pos);
            }
        });

        PagerAdapter pp =  viewPager.getAdapter();
        viewPager.setAdapter(new Homepage_tempActivity.Adaper(getSupportFragmentManager()));
        PagerAdapter pp2 =  viewPager.getAdapter();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setIsetSelected(false);
                }

                dataList.get(position % dataList.size()).setIsetSelected(true);
                recyclerview.moveToPosition(position % dataList.size());
                viewPager.resetHeight(position);

                if (viewPager.getCurrentItem() == 0) {
                    stickyScrollView.setShowSticky(true);
                } else {
                    stickyScrollView.setShowSticky(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOffscreenPageLimit(4);
        viewPager.setCurrentItem(0);
        stickyScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (currentShowPage == 0) {
                    if (fragmentList.get(viewPager.getCurrentItem()) instanceof HomeSample) {
                        ((HomeSample) fragmentList.get(viewPager.getCurrentItem())).nextPage();
                    }
                }
            }
        });


        stickyScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("<,,,,>", scrollX + ",,," + ",,,," + scrollY + ",,,,," + oldScrollX + ",,,,," + oldScrollY);
                Log.e("+++++++", v.getChildAt(0).getMeasuredHeight() + "========" + v.getMeasuredHeight());
                if (scrollY >= (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) - 200) {
//                    if (currentShowPage == 0) {
//                        //到底了
//                        if (fragmentList.get(viewPager.getCurrentItem()) instanceof HomeSample) {
//                            ((Sample) fragmentList.get(viewPager.getCurrentItem())).nextPage();
//                        } else if (fragmentList.get(viewPager.getCurrentItem()) instanceof HomeWorks) {
////                            ((Works) fragmentList.get(viewPager.getCurrentItem())).nextPage();
//                        }
//                    } else if (currentShowPage == 1) {
//
//                    } else if (currentShowPage == 2) {
//                        fansFrame.nextPage();
//                    }

                }
            }
        });


    }






    /**
     * 作品
     */
    HomeSample homeSample;

    /**
     * 工作
     */
    HomeWorks homeWorks;

    /**
     * 个人Fragment
     * @return
     */
    HomePersonage homePersonage;

    /**
     * 收藏
     * @return
     */
    Collect homeCollect;

    /**
     * 櫥窗
     */
    Showcase homeShowcase;

    private List<Fragment> getPersonageFragment() {
        List<Fragment> newlist = new ArrayList<>();

        homeWorks = HomeWorks.newInstance();
        homeWorks.point = 0;
        homeWorks.userId = userId;

        homeSample = HomeSample.newInstance();
        homeSample.point = 1;
        homeSample.userId = userId;

        homePersonage = HomePersonage.newInstance();
        homePersonage.point = 2;
        homePersonage.userId = userId;
        homePersonage.userInfo = userInfo;

        homeCollect = Collect.newInstance();
        homeCollect.point = 3;
        homeCollect.userId = userId;

        newlist.add(homeWorks);
        newlist.add(homeSample);
        newlist.add(homePersonage);
        newlist.add(homeCollect);

        return newlist;
    }

    private  List<PersonageGui.DataBean> getPersonageGui() {
        List<PersonageGui.DataBean> newlist = new ArrayList<>();

        PersonageGui.DataBean newM = new PersonageGui.DataBean();
        newM.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui1"));
        newM.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui1_select"));
        newlist.add(newM);


        PersonageGui.DataBean newM2 = new PersonageGui.DataBean();
        newM2.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui2"));
        newM2.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui2_select"));
        newlist.add(newM2);

        PersonageGui.DataBean newM5 = new PersonageGui.DataBean();
        newM5.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui5"));
        newM5.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui5_select"));
        newlist.add(newM5);


        PersonageGui.DataBean newM3 = new PersonageGui.DataBean();
        newM3.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui3"));
        newM3.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui3_select"));
        newlist.add(newM3);


        return newlist;
    }

    /**
     * 公司菜单
     * @return
     */

    private  List<PersonageGui.DataBean> getCompanyGui() {
        List<PersonageGui.DataBean> newlist = new ArrayList<>();


        PersonageGui.DataBean newM = new PersonageGui.DataBean();
        newM.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui1"));
        newM.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui1_select"));
        newlist.add(newM);


        PersonageGui.DataBean newM5 = new PersonageGui.DataBean();
        newM5.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui5"));
        newM5.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui5_select"));
        newlist.add(newM5);


        PersonageGui.DataBean newM4 = new PersonageGui.DataBean();
        newM4.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui4"));
        newM4.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui4_select"));
        newlist.add(newM4);

        PersonageGui.DataBean newM2 = new PersonageGui.DataBean();
        newM2.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui2"));
        newM2.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui2_select"));
        newlist.add(newM2);

        return newlist;
    }

    HomeCompany homeCompany;
    private List<Fragment> getCompanyFragment() {

        List<Fragment> newlist = new ArrayList<>();

        homeSample = HomeSample.newInstance();
        homeSample.point = 0;
        homeSample.userId = userId;


        homeShowcase = Showcase.newInstance();
        homeShowcase.point = 1;
        homeShowcase.company_id = userInfo.getData().getCompany_id();


        homeCompany = HomeCompany.newInstance();
        homeCompany.point = 2;
        homeCompany.userId = userId;
        homeCompany.userInfo = userInfo;

        homeCollect = Collect.newInstance();
        homeCollect.point = 3;
        homeCollect.userId = userId;


        newlist.add(homeSample);
        newlist.add(homeShowcase);
        newlist.add(homeCompany);
        newlist.add(homeCollect);

        return newlist;

    }



    //把字符串转换为资源Id
    public static int getResourdIdByResourdName(Context context, String defType, String ResName) {
        Resources res = context.getResources();
        return res.getIdentifier(ResName, defType, context.getPackageName());
    }



    /**
     * 设置子page高度
     *
     * @param view
     * @param point
     */
    public void setChildObjectForPosition(Fragment view, int point) {
        viewPager.setObjectForPosition(view, point);
    }



    class Adaper extends FragmentStatePagerAdapter {
        public Adaper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList != null ? fragmentList.get(position) : null;
        }


        @Override
        public int getCount() {
            return fragmentList != null ? fragmentList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
            return super.isViewFromObject(view, object);
        }


    }

    /**
     * 显示fragment
     */
    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.scheduleFrameLayout, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
            for (Fragment f : mFragments) {
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                if (f == fragment) {
                    if (fragment.isHidden()) {
                        transaction2.show(f).commitAllowingStateLoss();
                    }
                } else {
                    if (f.isAdded()) {
                        transaction2.hide(f).commitAllowingStateLoss();
                    }
                }
            }
        } else {
            for (Fragment f : mFragments) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (f == fragment) {
                    if (fragment.isHidden()) {
                        transaction.show(f).commitAllowingStateLoss();
                    }
                } else {
                    if (f.isAdded()) {
                        transaction.hide(f).commitAllowingStateLoss();
                    }
                }
            }

        }
    }



    /**
     * 更新字符串的UI
     *
     * @param key
     * @param value
     */
    private void updateUserUi(String key, String value) {
        if (key.equals("cover_img")) {
            if(value.equals(""))
            {
                return;
            }
            new ImageHelper().displayBackgroundLoading(backdrop,
                    Constant.BASE_IMAGE + value);
        }
        else if(key.equals("is_collect"))
        {
            if(value.equals("true"))
            {
                btnFans.setBackgroundResource(R.mipmap.homepage_fansed);
            }
            else
            {
                btnFans.setBackgroundResource(R.mipmap.homepage_fans);
            }
        }

    }


}
