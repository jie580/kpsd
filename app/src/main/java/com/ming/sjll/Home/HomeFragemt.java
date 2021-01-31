package com.ming.sjll.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ming.sjll.Home.bean.AdsBanner;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.event.UserUpdateEvent;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.fragment.MvpFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.tool.http.ObjeGetValue;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.toolPage.DateFrame;
import com.ming.sjll.ui.CircleImageView;
import com.ming.sjll.ui.StickyScrollView;
import com.necer.enumeration.SelectedModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class HomeFragemt extends BaseFragment {

    CheckBox switchBtn;
    private Supplier supplier;//供应商
    private Purchaser purchaser;//采购商
    private Fragment[] mFragments;


    StickyScrollView scrollViewWarp;
    public boolean canScrollTop = false;

    public static HomeFragemt newInstance() {
        return new HomeFragemt();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_home_fragemt, container, false);
        super.setContentView(view);
        init();
        return view;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }
private void init(){
    if(!EventBus.getDefault().isRegistered(this))
    {
        EventBus.getDefault().register(this);
    }
//    @Override
//    protected void onCreateView(Bundle savedInstanceState) {
//        super.onCreateView(savedInstanceState);
//        setContentView(R.layout.home_home_fragemt);
        initView();
        welcome();

         scrollViewWarp = (StickyScrollView)findViewById(R.id.scrollViewWarp);
//         scrollViewWarp.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                return true;
//                }
//            });
        scrollViewWarp.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener(){
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                Log.e("滚动","scrollX:"+scrollX+",,,scrollY："+scrollY+",,,,,oldScrollX"+oldScrollX+",,,,oldScrollY"+oldScrollY);
//                if(supplier != null)
//                Log.e("高度：",supplier.findViewById(R.id.tabMode).getTop()+"");
                if(canScrollTop)
                {
                    scrollViewWarp.scrollTo(0,0);
                    canScrollTop = false;
                }

            }
        });


    }


    private void initView() {

//        供应商和采购商显示
        supplier = Supplier.newInstance();
        purchaser =  Purchaser.newInstance();
//        purchaser = DateFrame.newInstance(SelectedModel.SINGLE_UNSELECTED,null,null);

        mFragments = new Fragment[]{purchaser, supplier};
        switchBtn = (CheckBox)findViewById(R.id.rb_1);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    scrollViewWarp.setShowSticky(false);
                    showFragment(purchaser);
                } else {
                    scrollViewWarp.setShowSticky(true);
                    showFragment(supplier);
                }
                canScrollTop = true;
//                scrollViewWarp.scrollTo(0,0);

            }
        });
        showFragment(purchaser);
        getSildAd();
    }

    /**
     * 加载欢迎语和用户头像
     */
    private void welcome() {
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.WELCOME, param, new CommonCallback() {
            @Override
            public void onSuccess(Object data, int code) {
                String str = ObjeGetValue.GetValu("str", data).toString();
                String defaultAvatar = ObjeGetValue.GetValu("defaultAvatar", data).toString();
                String defaultAvatarRect = ObjeGetValue.GetValu("defaultAvatarRect", data).toString();
                ((TextView) findViewById(R.id.tv_name)).setText(str);
                new ImageHelper().displayBackgroundLoading(((ImageView) findViewById(R.id.tv_login_im_procurer)), Constant.BASE_IMAGE + defaultAvatarRect);
                new ImageHelper().displayBackgroundLoading(((ImageView) findViewById(R.id.iv_img)), Constant.BASE_IMAGE + defaultAvatar);
                RongImUtils.connect();
            }

            ;
        });
    }

    /**
     * 用户信息更新
     *
     * @param e
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserUpdateEvent e) {

        String value = e.value;
        String key = e.updateKey;
        welcome();
    }


    /**
     * 显示fragment
     */
    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getChildFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.framelayout, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
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















    ViewPager mViewPager;
    LinearLayout lineLayout_dot;
    int previousPosition;
    LinearLayout adWrap;
    AdsBanner adsData;

    ArrayList<View> al;

    /**
     * 轮播图
     */
    private void getSildAd() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        lineLayout_dot = (LinearLayout) findViewById(R.id.lineLayout_dot);
        adWrap = (LinearLayout) findViewById(R.id.adWrap);

        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.HOME_ADS_BANNER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                adsData = new Gson().fromJson(data, AdsBanner.class);

                al = new ArrayList<View>();

                for (int x = 0; x < adsData.getData().size(); x++) {

//                    CustomRoundAngleImageView iv = new CustomRoundAngleImageView(mContext,10);
//
//                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
////                    ImageHelper.displayDef(iv, Constant.BASE_API + adsData.getData().get(x).getBannerImg(), R.mipmap.ic_launcher);
//                    new ImageHelper().displayBackgroundLoading(iv,
//                            Constant.BASE_IMAGE + adsData.getData().get(x).getBannerImg());
                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_shadow_image, null);
                    new ImageHelper().displayBackgroundLoading(view.findViewById(R.id.image),
                            Constant.BASE_IMAGE + adsData.getData().get(x).getBannerImg());
                    al.add(view);
                    View v = new View(mContext);
                    v.setBackgroundResource(R.drawable.ic_dot_normal);
                    //有多少张图就放置几个点
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
                    layoutParams.leftMargin = 30;
                    lineLayout_dot.addView(v, layoutParams);

                }

                mViewPager.setAdapter(new Myadapter());
//                        mViewPager.setOnPageChangeListener(this);
                mViewPager.setCurrentItem(0); //这个是无线轮询的关键
//                lineLayout_dot.getChildAt(0).setBackgroundResource(R.drawable.ic_dot_select);
//                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Tools.px2dip(getContext(),16), Tools.px2dip(getContext(),3));
                lineLayout_dot.getChildAt(0).getLayoutParams().width = 50;
                previousPosition = 0;

//


                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        //伪无限循环，滑到最后一张图片又从新进入第一张图片
                        int newPosition = position ;
                        //设置轮播点
//                                LinearLayout.LayoutParams newDotParams = (LinearLayout.LayoutParams) mDots.get(newPosition).getLayoutParams();
//                                LinearLayout.LayoutParams newDotParams = (LinearLayout.LayoutParams) lineLayout_dot.getChildAt(newPosition).getLayoutParams();
//                        lineLayout_dot.getChildAt(newPosition).setBackgroundResource(R.drawable.ic_dot_select);
//                        lineLayout_dot.getChildAt(previousPosition).setBackgroundResource(R.drawable.ic_dot_normal);
//
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 15);
                        layoutParams.leftMargin = 30;
                        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(15, 15);
                        layoutParams2.leftMargin = 30;
//                        lineLayout_dot.getChildAt(0).getLayoutParams().width = 50;

                        lineLayout_dot.getChildAt(newPosition).setLayoutParams(layoutParams);
                        lineLayout_dot.getChildAt(previousPosition).setLayoutParams(layoutParams2);
//                        lineLayout_dot.getChildAt(previousPosition).notify();

//                                newDotParams.width = 24;
//                                newDotParams.height = 24;

//                                LinearLayout.LayoutParams oldDotParams = (LinearLayout.LayoutParams) lineLayout_dot.getChildAt(previousPosition).();
//                                oldDotParams.width = 16;
//                                oldDotParams.height = 16;

//                                // 把当前的索引赋值给前一个索引变量, 方便下一次再切换.
                        previousPosition = newPosition;

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

//                                if (state == ViewPager.SCROLL_STATE_IDLE) {
//                                         if (previousPosition == al.size() - 1) {
//
//                                          //当到了最后一个的时候指向第一个
//                                          mViewPager.setCurrentItem(1, false);
//                                         }
//                                         else if (previousPosition == 0) {
//                                            //因为头部和尾部各加了一个，总共加了两个所以实际的个数是：al.size() - 2
//
//                                             //当到了第一个的时候指向倒数第二个
//                                          mViewPager.setCurrentItem(al.size() - 2, false);
//                                         }
//                                }


                    }
                });
//                        显示banner广告
                adWrap.setVisibility(View.VISIBLE);
            }
        });

    }


    /**
     * 轮播Dapter
     */
    class Myadapter extends PagerAdapter {
        @Override
        public int getCount() {
            return al.size(); // 要无限轮播
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e("创建点：", position + "");
            int position1 = position % al.size();
            View view = al.get(position1);


            try {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = adsData.getData().get(previousPosition).getAdsId();
                        Log.e("点击了广告图ID", id + "");

                    }
                });

                container.addView(view);
            } catch (Exception e) {
//               ViewGroup parent = (ViewGroup) imageView.getParent();
//               if (parent != null) {
//                   parent.removeView(imageView);
//               }
            }

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }




}
