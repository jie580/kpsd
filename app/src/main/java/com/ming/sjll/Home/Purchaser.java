package com.ming.sjll.Home;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.ming.sjll.Home.bean.AdsBanner;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.R;
import com.ming.sjll.Show.ShowFragemt;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.pay.HomePay;
import com.ming.sjll.project.MyProjectFragment;
import com.ming.sjll.project.ReleaseProject;
import com.ming.sjll.project.ReleaseProjectNext;
import com.ming.sjll.project.manage.HomeActivity;
import com.ming.sjll.search.GoodsFragment;
import com.ming.sjll.search.SearchFragment;
import com.ming.sjll.search.SearchOccupationFragment;
import com.ming.sjll.someone.FindFragment;
import com.ming.sjll.ui.CustomRoundAngleImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Purchaser extends BaseFragment {


//    ViewPager mViewPager;
//    LinearLayout lineLayout_dot;
//    int previousPosition;
//    LinearLayout adWrap;
//    AdsBanner adsData;

    public static Purchaser newInstance() {
        return new Purchaser();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        Log.e("创建：", "onCreateView");
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.home_purchaser);

//        mViewPager = (ViewPager) findViewById(R.id.viewPager);
//        lineLayout_dot = (LinearLayout) findViewById(R.id.lineLayout_dot);
//        adWrap = (LinearLayout) findViewById(R.id.adWrap);
        initView();
        event();
    }

    private void event() {
        ImageButton guide1 = (ImageButton) findViewById(R.id.guide1);
        guide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jump(getActivity(), ReleaseProject.class, false);
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
                Tools.jump(getActivity(), MyProjectFragment.class, false);
//                Tools.jump(getActivity(), Myfragemt.class, false);
            }
        });
        ImageButton guide4 = (ImageButton) findViewById(R.id.guide4);
        guide4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastShow.s("即将上线，敬请期待");
            }
        });


        /**
         * 跳转商品
         */
        findViewById(R.id.service1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("classId", "3");
//                Tools.jump(getActivity(), GoodsFragment.class, bundle, false);

//                Bundle bundle = new Bundle();
//                bundle.putString("projectId", "172");
//                Tools.jump(getActivity(), HomeActivity.class, bundle, false);
//
                Bundle bundle = new Bundle();
                bundle.putString("orderNo", "2020112903352179480");
                Tools.jump(getActivity(), HomePay.class, bundle, false);

                List<String> data1 = new LinkedList<>();
                for (int i = 2020; i < 2030; i++) {
                    data1.add(i+"年");
                }
                List<String> data2 = new LinkedList<>();
                for (int i = 1; i < 13; i++) {
                    data2.add(i+"月");
                }
                //条件选择器
                OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = data1.get(options1)
                                + data2.get(options1);
                        ToastShow.s( tx);
                    }
                })
                        .setCancelText("取消")
                        .setSubmitText("确定")
                        .build();
                pvOptions.setNPicker(data1, data2,null);
                pvOptions.show();


            }
        });

        findViewById(R.id.service2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("classId", "4");
                Tools.jump(getActivity(), GoodsFragment.class, bundle, false);
            }
        });
        findViewById(R.id.service3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("classId", "8");
                Tools.jump(getActivity(), GoodsFragment.class, bundle, false);
            }
        });
        findViewById(R.id.service4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("classId", "13");
                Tools.jump(getActivity(), GoodsFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("classId", "9");
                Tools.jump(getActivity(), GoodsFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("classId", "11");
                Tools.jump(getActivity(), GoodsFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("classId", "10");
                Tools.jump(getActivity(), GoodsFragment.class, bundle, false);
            }
        });


        /**
         * 搜索职业
         */
        findViewById(R.id.occ1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("occupationId", "2");
                Tools.jump(getActivity(), SearchOccupationFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("occupationId", "4");
                Tools.jump(getActivity(), SearchOccupationFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("occupationId", "3");
                Tools.jump(getActivity(), SearchOccupationFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("occupationId", "5");
                Tools.jump(getActivity(), SearchOccupationFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("occupationId", "8");
                Tools.jump(getActivity(), SearchOccupationFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("occupationId", "1");
                Tools.jump(getActivity(), SearchOccupationFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("occupationId", "6");
                Tools.jump(getActivity(), SearchOccupationFragment.class, bundle, false);
            }
        });
        findViewById(R.id.occ11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("occupationId", "7");
                Tools.jump(getActivity(), SearchOccupationFragment.class, bundle, false);
            }
        });


    }

    private void binds() {

//        SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.mSwipeRefreshLayout);
//        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
//        // 设置下拉进度的背景颜色，默认就是白色的
//        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
//        // 设置下拉进度的主题颜色
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
//
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // 开始刷新，设置当前为刷新状态
//                //swipeRefreshLayout.setRefreshing(true);
//                ToastShow.s("刷新了");
//
//                // 需要写在接口回调
//                //swipeRefreshLayout.setRefreshing(false);
//            }
//
//        });
        // 模拟下拉刷新
//        mSwipeRefreshLayout.setOnRefreshListener {
//            mSwipeRefreshLayout.isRefreshing = true
//            Observable.just(0)
//                    .delay(2, TimeUnit.SECONDS)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnNext {
//                mSwipeRefreshLayout.isRefreshing = false
//                mAdapter.submitList(null)
//                viewModel.getRefreshLiveData()
//                        .observe(this, Observer { mAdapter.submitList(it) })
//            }
//                    .subscribe()
//        }
    }

    private void initView() {
        binds();

//        getSildAd();
    }

//    ArrayList<View> al;
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
//                al = new ArrayList<View>();
//
//                for (int x = 0; x < adsData.getData().size(); x++) {
//
////                    CustomRoundAngleImageView iv = new CustomRoundAngleImageView(mContext,10);
////
////                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
//////                    ImageHelper.displayDef(iv, Constant.BASE_API + adsData.getData().get(x).getBannerImg(), R.mipmap.ic_launcher);
////                    new ImageHelper().displayBackgroundLoading(iv,
////                            Constant.BASE_IMAGE + adsData.getData().get(x).getBannerImg());
//                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_shadow_image, null);
//                    new ImageHelper().displayBackgroundLoading(view.findViewById(R.id.image),
//                            Constant.BASE_IMAGE + adsData.getData().get(x).getBannerImg());
//                    al.add(view);
//                    View v = new View(mContext);
//                    v.setBackgroundResource(R.drawable.ic_dot_normal);
//                    //有多少张图就放置几个点
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
//                    layoutParams.leftMargin = 30;
//                    lineLayout_dot.addView(v, layoutParams);
//
//                }
//
//                mViewPager.setAdapter(new Purchaser.Myadapter());
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
//            View view = al.get(position1);
//
//
//            try {
//                view.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int id = adsData.getData().get(previousPosition).getAdsId();
//                        Log.e("点击了广告图ID", id + "");
//
//                    }
//                });
//
//                container.addView(view);
//            } catch (Exception e) {
////               ViewGroup parent = (ViewGroup) imageView.getParent();
////               if (parent != null) {
////                   parent.removeView(imageView);
////               }
//            }
//
//            return view;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//    }


}

