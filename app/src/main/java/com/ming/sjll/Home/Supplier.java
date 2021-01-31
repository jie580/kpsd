package com.ming.sjll.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ming.sjll.Home.bean.AdsBanner;
import com.ming.sjll.MainActivity;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.fragment.MvpFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.login.LoginActivity;
import com.ming.sjll.login.WelcomeActivity;
import com.ming.sjll.project.ReleaseProject;
import com.ming.sjll.search.ProjectNotice;
import com.ming.sjll.search.ProjectProject;
import com.ming.sjll.search.SearchFragment;
import com.ming.sjll.someone.FindFragment;
import com.ming.sjll.ui.AboutHeightViewpager;
import com.ming.sjll.ui.CustomRoundAngleImageView;
import com.ming.sjll.ui.PagedListView;
import com.ming.sjll.ui.StickyScrollView;
import com.ming.sjll.ui.WrapContentHeightViewPager;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class Supplier extends BaseFragment {

    public static Supplier newInstance() {
        return new Supplier();
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.home_supplier);

//
//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        lineLayout_dot = (LinearLayout) findViewById(R.id.lineLayout_dot);
//        adWrap = (LinearLayout) findViewById(R.id.adWrap);
        project_list_type_more = (TextView) findViewById(R.id.project_list_type_more);

        initView();
        event();
    }

    private  TextView project_list_type_more;
    private  String xurrentItem = "project";
    private void event() {
        ImageButton guide1 = (ImageButton) findViewById(R.id.guide1);
        guide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ToastShow.s("即将上线，敬请期待");
            }
        });

        ImageButton guide2 = (ImageButton) findViewById(R.id.guide2);
        guide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(getActivity(), FindFragment.class, false);
            }
        });
        ImageButton guide3 = (ImageButton) findViewById(R.id.guide3);
        guide3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastShow.s("即将上线，敬请期待");
            }
        });
        ImageButton guide4 = (ImageButton) findViewById(R.id.guide4);
        guide4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastShow.s("即将上线，敬请期待");
            }
        });

        project_list_type_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                带参数跳到搜索页面
                Bundle bundle = new Bundle();
                bundle.putString("showType", xurrentItem);
                Tools.jump(getActivity(), SearchFragment.class, bundle, false);
            }

        });
    }

    private void initView() {
//        getSildAd();
        getList();
    }

    public FrameLayout viewpager;
    private List<Fragment> fragmentList;
    private ProjectProject searchComprehensiveFragemt;
    private ProjectNotice searchNoticeFragemt;
    private TextView project_list_type1;
    private TextView project_list_type2;
    private Fragment[] mFragments;
    private ImageView tipImage;

    /**
     * 项目列表
     */
    public void getList() {
        project_list_type1 = (TextView) findViewById(R.id.project_list_type1);
        project_list_type2 = (TextView) findViewById(R.id.project_list_type2);
        tipImage = (ImageView) findViewById(R.id.tipImage);
//        project_list_type1.setActivated(true);

//        aList = new ArrayList<View>();
        searchComprehensiveFragemt = ProjectProject.newInstance();
        searchNoticeFragemt = ProjectNotice.newInstance();
        searchComprehensiveFragemt.showFooter(false);
        searchNoticeFragemt.showFooter(false);

//        fragmentList = new ArrayList<>();
//        fragmentList.add(searchComprehensiveFragemt);
//        fragmentList.add(searchNoticeFragemt);
        mFragments = new Fragment[]{searchComprehensiveFragemt, searchNoticeFragemt};

//
//        viewpager = (FrameLayout) findViewById(R.id.viewpager2);
        showFragment(searchComprehensiveFragemt);
//        viewpager.setViewForPosition(searchComprehensiveFragemt.getContentView(),0);
//        viewpager.setViewForPosition(searchNoticeFragemt.getContentView(),1);

//        viewpager.setAdapter(new Adaper(getChildFragmentManager()));
////        viewpager.requestLayoutByPosition("projectProject");
//        viewpager.setCurrentItem(0);

//        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                viewpager.resetHeight(position);
//                if (position == 0) {
////                    viewpager.requestLayoutByPosition("projectProject");
//
//                    viewpager.setCurrentItem(0);
//
////                project_list_type1.setActivated(true);
////                project_list_type2.setActivated(false);
//
//                } else {
////                    viewpager.requestLayoutByPosition("projectNotice");
//                    viewpager.setCurrentItem(1);
////                project_list_type2.setActivated(true);
////                project_list_type1.setActivated(false);
//
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        project_list_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(searchComprehensiveFragemt);
                project_list_type1.setTextColor(Color.parseColor("#80B5FF"));
                project_list_type2.setTextColor(Color.parseColor("#000000"));
                tipImage.setBackgroundResource(R.mipmap.tip_home_1);
                scrollTop();
                xurrentItem = "project";
//                PagedListView pList =  (PagedListView)searchComprehensiveFragemt.findViewById(R.id.control_main_listview);
//                pList.scrollBy(0,0);


            }
        });

        project_list_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(searchNoticeFragemt);
                project_list_type2.setTextColor(Color.parseColor("#80B5FF"));
                project_list_type1.setTextColor(Color.parseColor("#000000"));
                tipImage.setBackgroundResource(R.mipmap.tip_home_2);
                scrollTop();
                xurrentItem = "notice";
//                PagedListView pList =  (PagedListView)searchNoticeFragemt.findViewById(R.id.control_main_listview);
//                pList.scrollBy(0,0);
            }
        });


    }


    private void  scrollTop()
    {
        HomeFragemt scrollViewWarp = (HomeFragemt)getParentFragment();
        if(scrollViewWarp != null)
        {
            scrollViewWarp.canScrollTop = true;
        }

//         scrollViewWarp.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                return true;
//                }
//            });

    }

    /**
     * 显示fragment
     */
    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getChildFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.viewpager2, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
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




//    public AboutHeightViewpager viewpager;
//    private List<Fragment> fragmentList;
//    private ProjectProject searchComprehensiveFragemt;
//    private ProjectNotice searchNoticeFragemt;
//    private ImageView project_list_type1;
//    private ImageView project_list_type2;
//
//    /**
//     * 项目列表
//     */
//    public void getList() {
//        project_list_type1 = (ImageView) findViewById(R.id.project_list_type1);
//        project_list_type2 = (ImageView) findViewById(R.id.project_list_type2);
//        project_list_type1.setActivated(true);
////        aList = new ArrayList<View>();
//        searchComprehensiveFragemt = ProjectProject.newInstance();
//        searchNoticeFragemt = ProjectNotice.newInstance();
//
//        fragmentList = new ArrayList<>();
//        fragmentList.add(searchComprehensiveFragemt);
//        fragmentList.add(searchNoticeFragemt);
//
//
//        viewpager = (AboutHeightViewpager) findViewById(R.id.viewpager2);
//
////        viewpager.setViewForPosition(searchComprehensiveFragemt.getContentView(),0);
////        viewpager.setViewForPosition(searchNoticeFragemt.getContentView(),1);
//
//        viewpager.setAdapter(new Adaper(getChildFragmentManager()));
////        viewpager.requestLayoutByPosition("projectProject");
//        viewpager.setCurrentItem(0);
//
//        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                viewpager.resetHeight(position);
//                if (position == 0) {
////                    viewpager.requestLayoutByPosition("projectProject");
//
//                    viewpager.setCurrentItem(0);
//
////                project_list_type1.setActivated(true);
////                project_list_type2.setActivated(false);
//
//                } else {
////                    viewpager.requestLayoutByPosition("projectNotice");
//                    viewpager.setCurrentItem(1);
////                project_list_type2.setActivated(true);
////                project_list_type1.setActivated(false);
//
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//
//        project_list_type1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                viewpager.requestLayoutByPosition("projectProject");
//                viewpager.setCurrentItem(0);
//                project_list_type1.setActivated(true);
//                project_list_type2.setActivated(false);
////                ToastShow.s("0");
//            }
//        });
//
//        project_list_type2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                viewpager.requestLayoutByPosition("projectNotice");
//                viewpager.setCurrentItem(1);
//                project_list_type2.setActivated(true);
//                project_list_type1.setActivated(false);
////                ToastShow.s("1");
//            }
//        });
//
//
//    }


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

//        @Override
//        public boolean isViewFromObject(View view, Object object) {
////            return view == object;
//            return super.isViewFromObject(view, object);
//        }

//        @Override
//        public CharSequence getPageTitle(int position) {
////            String[] title = {"第一", "第二", "第三"};
////            return title[position];
//        }
    }

//
//    ViewPager mViewPager;
//    LinearLayout lineLayout_dot;
//    int previousPosition;
//    LinearLayout adWrap;
//    AdsBanner adsData;
//
//    ArrayList<ImageView> al;
//
//    /**
//     * 轮播图
//     */
//    private void getSildAd() {
//        HttpParamsObject param = new HttpParamsObject();
//        new HttpUtil().sendRequest(Constant.HOME_ADS_BANNER, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {
//
//                adsData = new Gson().fromJson(data, AdsBanner.class);
//
//                al = new ArrayList<ImageView>();
//
//                for (int x = 0; x < adsData.getData().size(); x++) {
//
//                    CustomRoundAngleImageView iv = new CustomRoundAngleImageView(mContext,10);
//                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    new ImageHelper().displayBackgroundLoading(iv,
//                            Constant.BASE_IMAGE + adsData.getData().get(x).getBannerImg());
//                    al.add(iv);
//                    View v = new View(mContext);
//                    v.setBackgroundResource(R.drawable.ic_dot_normal);
//                    //有多少张图就放置几个点
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
//                    layoutParams.leftMargin = 30;
//                    lineLayout_dot.addView(v, layoutParams);
//
//                }
//
//                mViewPager.setAdapter(new Supplier.Myadapter());
////                        mViewPager.setOnPageChangeListener(this);
//                mViewPager.setCurrentItem(0); //这个是无线轮询的关键
//                lineLayout_dot.getChildAt(0).setBackgroundResource(R.drawable.ic_dot_select);
//                previousPosition = 0;
//
////
//
//
//                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                    @Override
//                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//                    }
//
//                    @Override
//                    public void onPageSelected(int position) {
//                        //伪无限循环，滑到最后一张图片又从新进入第一张图片
//                        int newPosition = position % adsData.getData().size();
//                        //设置轮播点
////                                LinearLayout.LayoutParams newDotParams = (LinearLayout.LayoutParams) mDots.get(newPosition).getLayoutParams();
////                                LinearLayout.LayoutParams newDotParams = (LinearLayout.LayoutParams) lineLayout_dot.getChildAt(newPosition).getLayoutParams();
//                        lineLayout_dot.getChildAt(newPosition).setBackgroundResource(R.drawable.ic_dot_select);
//                        lineLayout_dot.getChildAt(previousPosition).setBackgroundResource(R.drawable.ic_dot_normal);
////                                newDotParams.width = 24;
////                                newDotParams.height = 24;
//
////                                LinearLayout.LayoutParams oldDotParams = (LinearLayout.LayoutParams) lineLayout_dot.getChildAt(previousPosition).();
////                                oldDotParams.width = 16;
////                                oldDotParams.height = 16;
//
////                                // 把当前的索引赋值给前一个索引变量, 方便下一次再切换.
//                        previousPosition = newPosition;
//
//                    }
//
//                    @Override
//                    public void onPageScrollStateChanged(int state) {
//
////                                if (state == ViewPager.SCROLL_STATE_IDLE) {
////                                         if (previousPosition == al.size() - 1) {
////
////                                          //当到了最后一个的时候指向第一个
////                                          mViewPager.setCurrentItem(1, false);
////                                         }
////                                         else if (previousPosition == 0) {
////                                            //因为头部和尾部各加了一个，总共加了两个所以实际的个数是：al.size() - 2
////
////                                             //当到了第一个的时候指向倒数第二个
////                                          mViewPager.setCurrentItem(al.size() - 2, false);
////                                         }
////                                }
//
//
//                    }
//                });
////                        显示banner广告
//                adWrap.setVisibility(View.VISIBLE);
//            }
//        });
//
//    }
//
//
//    /**
//     * 轮播Dapter
//     */
//    class Myadapter extends PagerAdapter {
//        @Override
//        public int getCount() {
//            return al.size(); // 要无限轮播
//        }
//
//        @Override
//        public boolean isViewFromObject(View arg0, Object arg1) {
//            return arg0 == arg1;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            Log.e("创建点：", position + "");
//            int position1 = position % al.size();
//            ImageView imageView = al.get(position1);
//
//
//            try {
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int id = adsData.getData().get(previousPosition).getAdsId();
//                        Log.e("点击了广告图ID", id + "");
//
//                    }
//                });
//
//                container.addView(imageView);
//            } catch (Exception e) {
////               ViewGroup parent = (ViewGroup) imageView.getParent();
////               if (parent != null) {
////                   parent.removeView(imageView);
////               }
//            }
//
//            return imageView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//    }
}
