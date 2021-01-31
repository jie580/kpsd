package com.ming.sjll.My;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.PersonageGui;
import com.ming.sjll.Bean.ShowCaseItemBean;
import com.ming.sjll.Bean.UserTypeBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.Other.FansFrame;
import com.ming.sjll.My.adapter.PersonageAdapter;
import com.ming.sjll.My.common.Collect;
import com.ming.sjll.My.common.Home;
import com.ming.sjll.My.common.Money;
import com.ming.sjll.My.common.Order;
import com.ming.sjll.My.company.Company;
import com.ming.sjll.My.company.Showcase;
import com.ming.sjll.My.personage.WorksEdit;
import com.ming.sjll.My.personage.Personage;
import com.ming.sjll.My.common.Sample;
import com.ming.sjll.My.common.SampleEdit;
import com.ming.sjll.My.personage.Works;
import com.ming.sjll.My.Other.ScheduleFrame;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.event.UserUpdateEvent;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.toolPage.CropPicture;
import com.ming.sjll.toolPage.CropPictureConfig;
import com.ming.sjll.ui.ContentViewPager;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.ming.sjll.ui.DragFloatActionButton;
import com.ming.sjll.ui.LocateCenterHorizontalView;
import com.ming.sjll.ui.StickyScrollView;
import com.rey.material.app.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.utilities.RongUtils;

public class Myfragemt extends BaseFragment {

//    private static Myfragemt myfragemt;

    public static Myfragemt newInstance() {
//        if (myfragemt == null) {
//            myfragemt = new Myfragemt();
//        }
        return new Myfragemt();
    }


    StickyScrollView stickyScrollView;
    LocateCenterHorizontalView recyclerview;
    ContentViewPager viewPager;
    DragFloatActionButton addSample;
    CustomRoundAngleImageView backdrop;
    ImageView btnInteraction;
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
    ScheduleFrame scheduleFrame;
    FansFrame fansFrame;

    int circle = 1;

    int lastShowPage = 0;//0个人或公司，1公司员工排期
    int currentShowPage = 0;//0个人或公司或排期，1公司员工排期 2是收藏

    /**
     * 是不是个人
     */
    public static boolean isPersonage;

    /**
     * 可管理橱窗
     */
    public static boolean isManage;

    /**
     * 可管理排期
     */
    public static boolean isSchedule;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_home_fragemt, container, false);
        EventBus.getDefault().register(this);
        super.setContentView(view);

        circle = 1;

        lastShowPage = 0;//0个人或公司，1公司员工排期
        currentShowPage = 0;//0个人或公司或排期，1公司员工排期 2是收藏

        checkUserType();


        return view;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void checkUserType() {
        //        获取账号类型
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.MEMBER_GET_USER_TYPE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                UserTypeBean tempData = new Gson().fromJson(data, UserTypeBean.class);
                isPersonage = tempData.getData().getType().equals("user");
                isManage = tempData.getData().getIs_manage().equals("1");//管理橱窗
                isSchedule  = tempData.getData().getIs_schedule().equals("1");//管理排期
                init();
            }
        });
    }

    private void init() {
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


        initView();
        event();

        fansFrame = FansFrame.newInstance();
        if (isPersonage && !isSchedule) {

            mFragments = new Fragment[]{fansFrame};
        } else {
            scheduleFrame = ScheduleFrame.newInstance();
            mFragments = new Fragment[]{scheduleFrame, fansFrame};
        }
        showPage();


        SwipeRefreshLayout mSwipe = (SwipeRefreshLayout) findViewById(R.id.mswipeRefreshLayout);

        /*
         * 设置下拉刷新球颜色
         */
        mSwipe.setColorSchemeResources(R.color.blue);
//        mSwipe.setRefreshing(true);
        /*
         * 设置下拉刷新的监听
         */
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(currentShowPage == 2)
                {
                    fansFrame.getList();
                }
                else if (fragmentList.get(viewPager.getCurrentItem()) instanceof Sample) {
                    ((Sample) fragmentList.get(viewPager.getCurrentItem())).bindView();
                } else if (fragmentList.get(viewPager.getCurrentItem()) instanceof Showcase) {
                    ((Showcase) fragmentList.get(viewPager.getCurrentItem())).getList();
                }
                else if (fragmentList.get(viewPager.getCurrentItem()) instanceof Works) {
                    ((Works) fragmentList.get(viewPager.getCurrentItem())).getProjectList();
                }


                //刷新需执行的操作
                mSwipe.setRefreshing(false);
            }
        });

    }
//    int fff = 0;


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


        if (currentShowPage == 0) {
//                显示主页
            if (viewPager.getCurrentItem() == 0) {
                stickyScrollView.setShowSticky(true);
            }
            recyclerviewWrap.setVisibility(View.VISIBLE);
            recyclerviewWrap2.setVisibility(View.GONE);
            fansWrap.setVisibility(View.GONE);

            if (isPersonage) {
                if(isSchedule)
                {
                    btnInteraction.setVisibility(View.VISIBLE);
                }
                else {
                    btnInteraction.setVisibility(View.GONE);
                }
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
                homeName.setText(MainActivity.userInfo.getData().getName());
            } else {
                homeName.setText(MainActivity.userInfo.getData().getFull_company_name());
            }
            new ImageHelper().displayBackgroundLoading(headImage,
                    Constant.BASE_IMAGE + MainActivity.userInfo.getData().getDefault_avatar());
            homeSex.setVisibility(View.VISIBLE);
            if (MainActivity.userInfo.getData().getSex() == null) {
                homeSex.setVisibility(View.GONE);
//                homeSex.setBackgroundResource(R.mipmap.ic_my_sex_man);
            } else {
                if (MainActivity.userInfo.getData().getSex().equals("0")) {
                    homeSex.setBackgroundResource(R.mipmap.ic_my_sex_man);
                } else {
                    homeSex.setBackgroundResource(R.mipmap.ic_my_sex_woman);
                }
            }
            if (MainActivity.userInfo.getData().getIs_approve().equals("0")) {
                is_approve.setVisibility(View.GONE);
            } else {
                is_approve.setVisibility(View.VISIBLE);
            }
            showFragment(fansFrame);
        }

    }


    /**
     * 添加作品
     */
    private void addSample() {

//                ToastShow.s("点击了addSample");
        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
        bottomInterPasswordDialog.setContentView(R.layout.dialog_sample_type);
        bottomInterPasswordDialog.inDuration(300);
        bottomInterPasswordDialog.outDuration(200);
//        bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//        bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
        bottomInterPasswordDialog.show();
        View txt = bottomInterPasswordDialog.findViewById(R.id.txt);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                                跳转发布图文作品
                bottomInterPasswordDialog.hide();
//                        ToastShow.s("跳转发布图文作品");
                Intent intent = new Intent(getContext(), SampleEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "1");
                intent.putExtras(bundle);
                startActivityForResult(intent, 22);
            }
        });
        View video = bottomInterPasswordDialog.findViewById(R.id.video);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                                                跳转发布视频作品
//                        ToastShow.s("跳转发布视频作品");
                bottomInterPasswordDialog.hide();
                ;
                Intent intent = new Intent(getContext(), SampleEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("type", "2");
                intent.putExtras(bundle);
                startActivityForResult(intent, 22);

            }
        });
    }

    LinearLayout caseWrap;
    /**
     * 添加橱窗分类
     */
    private void addShowCase() {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.SHOW_CASE_CONFIG_SHOW, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                ShowCaseItemBean showCaseItemBean = new Gson().fromJson(data, ShowCaseItemBean.class);

                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_show_case);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
//                bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//                bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
                bottomInterPasswordDialog.show();
                caseWrap = bottomInterPasswordDialog.findViewById(R.id.caseWrap);
                for (int j = 0; j < showCaseItemBean.getData().size(); j++) {
                    View caseItem = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_show_case_item, null);
                    caseItem.setTag(j);
                    LinearLayout caseHorizontal = caseItem.findViewById(R.id.caseHorizontal);
                    ImageView imageItem = caseItem.findViewById(R.id.image);

                    caseItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Log.e("caseItem","onClickonClickonClick");
//                            int indexOf = (int) v.getTag();
                            for (int i = 0; i < caseWrap.getChildCount(); i++) {
                                View vv = caseWrap.getChildAt(i);
                                vv.findViewById(R.id.caseHorizontal).setVisibility(View.GONE);
                                vv.findViewById(R.id.image).setVisibility(View.VISIBLE);
                            }
                            v.findViewById(R.id.caseHorizontal).setVisibility(View.VISIBLE);
                            v.findViewById(R.id.image).setVisibility(View.GONE);
                        }
                    });

                    new ImageHelper().displayBackgroundLoading(imageItem,
                            Constant.BASE_API + showCaseItemBean.getData().get(j).getIcon());

                    for (int i = 0; i < showCaseItemBean.getData().get(j).getChild().size(); i++) {
                        View caseItem2 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_show_case_item_horizontal, null);
                        caseItem2.setTag(showCaseItemBean.getData().get(j).getChild().get(i).getClass_id());
                        ImageView imageItem2 = caseItem2.findViewById(R.id.image);
                        new ImageHelper().displayBackgroundLoading(imageItem2,
                                Constant.BASE_API + showCaseItemBean.getData().get(j).getChild().get(i).getIcon());

                        caseItem2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("caseItem2","onClickonClickonClick");
                                String indexOf = (String) v.getTag();
//                                ToastShow.s("分类ID"+indexOf);


                                HttpParamsObject param = new HttpParamsObject();
                                param.setParam("class_id",indexOf);
                                new HttpUtil().sendRequest(Constant.SHOW_CASE_SAVE_SHOW, param, new CommonCallback() {
                                    @Override
                                    public void onSuccessJson(String data, int code) {
                                        ToastShow.s("添加成功");
                                        showcase.getList();
                                        bottomInterPasswordDialog.hide();
                                    }
                                });


                            }
                        });
                        caseHorizontal.addView(caseItem2);
                    }
                    caseWrap.addView(caseItem);
                }


            }
        });

    }

//    添加排期
    private void  addWork(){
        Intent intent = new Intent(getContext(), WorksEdit.class);
        startActivityForResult(intent, 22);
    }

    private void event() {


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
         * 点击悬浮球添加
         */
        addSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragmentList.get(viewPager.getCurrentItem()) instanceof Sample) {
                    addSample();
                } else if (fragmentList.get(viewPager.getCurrentItem()) instanceof Showcase) {
                    addShowCase();
                }
                else if (fragmentList.get(viewPager.getCurrentItem()) instanceof Works) {
                    addWork();
                }


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
         * 更换背景
         */
        coverImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropPictureConfig config = new CropPictureConfig();
                config.setCoverXY();
                config.context = v.getContext();
                config.activity = getActivity();
                config.forResult = PictureConfig.COVER_HEAD_REQUEST;
                new CropPicture(config);
            }
        });
    }

    private void initView() {

        updateUserUi("cover_img", MainActivity.userInfo.getData().getCover_img());
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
                Log.e("selectedPositionChanged", "选择后事件" + pos);
                for (int i = 0; i < dataList.size(); i++) {
                    dataList.get(i).setIsetSelected(false);
                }
                dataList.get(pos % dataList.size()).setIsetSelected(true);
                viewPager.setCurrentItem(pos % dataList.size());
//                photo = headAdpter.getData().get(pos % headAdpter.getData().size()).gethoto();
            }
        });

        PagerAdapter pp = viewPager.getAdapter();
        viewPager.setAdapter(new Adaper(getChildFragmentManager()));
        PagerAdapter pp2 = viewPager.getAdapter();
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

        viewPager.setOffscreenPageLimit(7);

        stickyScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("<,,,,>", scrollX + ",,," + ",,,," + scrollY + ",,,,," + oldScrollX + ",,,,," + oldScrollY);
                Log.e("+++++++", v.getChildAt(0).getMeasuredHeight() + "========" + v.getMeasuredHeight());
                if (scrollY >= (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) - 200) {
                    if (currentShowPage == 0) {
                        //到底了
                        if (fragmentList.get(viewPager.getCurrentItem()) instanceof Sample) {
                            ((Sample) fragmentList.get(viewPager.getCurrentItem())).nextPage();
                        } else if (fragmentList.get(viewPager.getCurrentItem()) instanceof Works) {
//                            ((Works) fragmentList.get(viewPager.getCurrentItem())).nextPage();
                        }
                    } else if (currentShowPage == 1) {

                    } else if (currentShowPage == 2) {
                        fansFrame.nextPage();
                    }

                }
            }
        });


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


    /**
     * 作品
     */
    Sample sampleCommon;
    /**
     * 订单
     */
    Order orderCommon;
    /**
     * 资金
     */
    Money moneyCommon;
    /**
     * 收藏
     */
    Collect collectCommon;
    /**
     * 酷拍商店
     */
    Home homeCommon;
    /**
     * 个人
     */
    Personage personage;

    /**
     * 工作
     */
    Works works;


    /**
     * 公司
     */
    Company company;
    /**
     * 橱窗
     */
    Showcase showcase;

    /**
     * 个人Fragment
     *
     * @return
     */
    private List<Fragment> getPersonageFragment() {
        List<Fragment> newlist = new ArrayList<>();

        if(isManage)
        {
            works = Works.newInstance();
            works.point = 0;

            sampleCommon = Sample.newInstance();
            sampleCommon.point = 1;

            showcase = Showcase.newInstance();
            showcase.point = 2;

            collectCommon = Collect.newInstance();
            collectCommon.point = 3;

            moneyCommon = Money.newInstance();
            moneyCommon.point = 4;

            personage = Personage.newInstance();
            personage.point = 5;

            orderCommon = Order.newInstance();
            orderCommon.point = 6;

            homeCommon = Home.newInstance();
            homeCommon.point = 7;

            newlist.add(works);
            newlist.add(sampleCommon);
            newlist.add(showcase);
            newlist.add(collectCommon);
            newlist.add(moneyCommon);
            newlist.add(personage);
            newlist.add(orderCommon);
            newlist.add(homeCommon);

        }
        else
        {
            works = Works.newInstance();
            works.point = 0;

            sampleCommon = Sample.newInstance();
            sampleCommon.point = 1;

            collectCommon = Collect.newInstance();
            collectCommon.point = 2;

            moneyCommon = Money.newInstance();
            moneyCommon.point = 3;

            personage = Personage.newInstance();
            personage.point = 4;

            orderCommon = Order.newInstance();
            orderCommon.point = 5;

            homeCommon = Home.newInstance();
            homeCommon.point = 6;

            newlist.add(works);
            newlist.add(sampleCommon);
            newlist.add(collectCommon);
            newlist.add(moneyCommon);
            newlist.add(personage);
            newlist.add(orderCommon);
            newlist.add(homeCommon);

        }

        return newlist;
    }

    /**
     * 公司Fragment
     *
     * @return
     */
    private List<Fragment> getCompanyFragment() {
        List<Fragment> newlist = new ArrayList<>();

        sampleCommon = Sample.newInstance();
        sampleCommon.point = 0;

        collectCommon = Collect.newInstance();
        collectCommon.point = 1;

        moneyCommon = Money.newInstance();
        moneyCommon.point = 2;

        company = Company.newInstance();
        company.point = 3;

        showcase = Showcase.newInstance();
        showcase.point = 4;

        homeCommon = Home.newInstance();
        homeCommon.point = 5;

        orderCommon = Order.newInstance();
        orderCommon.point = 6;

        newlist.add(sampleCommon);
        newlist.add(collectCommon);
        newlist.add(moneyCommon);
        newlist.add(company);
        newlist.add(showcase);
        newlist.add(homeCommon);
        newlist.add(orderCommon);

        return newlist;
    }


    /**
     * 获取个人菜单的资源
     *
     * @return
     */
    private List<PersonageGui.DataBean> getPersonageGui() {
        List<PersonageGui.DataBean> newlist = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PersonageGui.DataBean newM = new PersonageGui.DataBean();
            newM.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui" + i));
            newM.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "personage_gui" + i + "_select"));
            newlist.add(newM);
            if(i == 2 && isManage)
            {
                PersonageGui.DataBean newM2 = new PersonageGui.DataBean();
                newM2.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui5"));
                newM2.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui5_select"));
                newlist.add(newM2);
            }
        }
        return newlist;
    }

    /**
     * 获取公司菜单的资源
     *
     * @return
     */
    private List<PersonageGui.DataBean> getCompanyGui() {
        List<PersonageGui.DataBean> newlist = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            PersonageGui.DataBean newM = new PersonageGui.DataBean();
            newM.setIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui" + i));
            newM.setSelectIcon(getResourdIdByResourdName(getContext(), "mipmap", "company_gui" + i + "_select"));
            newlist.add(newM);
        }
        return newlist;
    }


    //把字符串转换为资源Id
    public static int getResourdIdByResourdName(Context context, String defType, String ResName) {
        Resources res = context.getResources();
        return res.getIdentifier(ResName, defType, context.getPackageName());
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
            int count = fragmentList != null ? fragmentList.size() : 0;
            return count;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
            return super.isViewFromObject(view, object);
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.COVER_HEAD_REQUEST) {

                loadingDailog.showLoading();
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                HttpParamsObject param = new HttpParamsObject();
                HttpUtil.uplaodFile(selectList.get(0).getPath(), param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        try {
                            JSONObject json = new JSONObject(data);
                            String path = (String) json.get("data");
                            MainActivity.saveInfo("cover_img", path);
//                            updateBgPath(path);
                        } catch (Exception e) {
                            Log.e("Handler 回调", e.getMessage());
                            ToastShow.s("服务器错误");
                        }
                    }

                    @Override
                    public void onProgress(int currentProgress, long currentSize, long totalSize) {
                        loadingDailog.setMsg(currentProgress + "%");

                        Log.e("进度：", "currentProgress" + currentProgress + ",,currentSize" + currentSize + ",,totalSize" + totalSize);
                    }

                    @Override
                    public void onFinal() {
                        loadingDailog.hide();
                    }

                });


            }
            else if (fragmentList != null) {
                for (Fragment fragment : fragmentList) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
//        }

    }


    /**
     * 显示fragment
     */
    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getChildFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.scheduleFrameLayout, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
            for (Fragment f : mFragments) {
                FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
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
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
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
     * 用户信息更新
     *
     * @param e
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserUpdateEvent e) {

        Object objectValue = e.objectValue;
        String value = e.value;
        String key = e.updateKey;
        updateUserUi(key, value);
//        updateUserUi(key,objectValue);
    }


    /**
     * 更新字符串的UI
     *
     * @param key
     * @param value
     */
    private void updateUserUi(String key, String value) {
        if (key.equals("cover_img")) {
            new ImageHelper().displayBackgroundLoading(backdrop,
                    Constant.BASE_IMAGE + value);
        }

    }


}
