package com.ming.sjll;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;


//import com.ming.sjll.base.activity.MvpActivity;

import com.google.gson.Gson;
import com.heartfor.heartvideo.video.HeartVideoManager;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Home.HomeFragemt;
import com.ming.sjll.Home.bean.AdsBanner;
import com.ming.sjll.Message.MessageChatActivity;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.company.ShowcaseListActivity;
import com.ming.sjll.Show.ShowFragemt;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.Message.Messagefragemt;
import com.ming.sjll.base.event.UserUpdateEvent;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.map.selectcity.SelectCityActivity;
import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.search.GoodsFragment;
import com.ming.sjll.search.SearchFragment;
import com.ming.sjll.ui.CustomViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Group;
import io.rong.imlib.model.UserInfo;


//public class MainActivity extends MvpActivity<MainView, MainPresenter> {

public class MainActivity extends BaseActivity {



    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.rb_3)
    RadioButton rb3;
    @BindView(R.id.rb_4)
    RadioButton rb4;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;


//    private ArrayList<View> aList;
        private List<Fragment> fragmentList;
//    private HomeFragemt HomeFragemt;
    //    private ShowFragemt showFragemt;
//    private MessageFragment messageFragemt;
//    private MyFragemt myFragemt;
    private long mExitTime;
//    LocalActivityManager manager = null;

    /**
     * 全局个人信息
     */
    public static GetInfoBean userInfo;

//    public static Context mycontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Tools.jump(MainActivity.this, ReleaseProject.class, false);
//        loadingDailog.showLoading();

//        manager = new LocalActivityManager(this , true);
//        manager.dispatchCreate(savedInstanceState);


//        this.mycontext = getWindow().getContext();


        getUserInfo();
//        Bundle bundle = new Bundle();
//        bundle.putString("classId", "3");
//        Tools.jump(MainActivity.this, GoodsFragment.class, bundle, false);
    }

    RadioButton btn1, btn2, btn3, btn4;
    PagerAdapter adapter;

    private void initView() {
//        aList = new ArrayList<View>();
        btn1 = (RadioButton) findViewById(R.id.rb_1);
        btn2 = (RadioButton) findViewById(R.id.rb_2);
        btn3 = (RadioButton) findViewById(R.id.rb_3);
        btn4 = (RadioButton) findViewById(R.id.rb_4);


//
//        Intent intent = new Intent();
//
//        intent.setClass(MainActivity.this, HomeFragemt.class);
//        intent.putExtra("id", 1);
//        aList.add(getView("HomeFragemt", intent));
////
//        intent.setClass(MainActivity.this, ShowFragemt.class);
//        intent.putExtra("id", 2);
//        aList.add(getView("ShowFragemt", intent));
//
//        intent.setClass(MainActivity.this, Messagefragemt.class);
//        intent.putExtra("id", 3);
//        aList.add(getView("Messagefragemt", intent));
//
//        intent.setClass(MainActivity.this, Myfragemt.class);
//        intent.putExtra("id", 4);
//        aList.add(getView("Myfragemt", intent));


        fragmentList = new ArrayList<>();
        fragmentList.add(HomeFragemt.newInstance());
        fragmentList.add(ShowFragemt.newInstance());
        fragmentList.add(Messagefragemt.newInstance());
        fragmentList.add(Myfragemt.newInstance());
        viewPager = (CustomViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new Adaper(getSupportFragmentManager()));
        viewPager.setCanScroll(false);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(4);
//        adapter = new PagerAdapter() {
//            //得到滑动layout的数量
//            @Override
//            public int getCount() {
//
//                return aList.size();
//            }
//
//            //instantiateItem(ViewGroup, int)函数所返回来的Key与一个页面视图是否是代表的同一个视图(即它俩是否是对应的，对应的表示同一个View)
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//
//                return view == object;
//            }
//
//            //从当前container中删除指定位置（position）的View;
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                // super.destroyItem(container, position, object);
//                container.removeView(aList.get(position));
//            }
//
//            //instantiateItem()：做了两件事，第一：将当前视图添加到container中，第二：返回当前View
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//
//
//                View view = aList.get(position);
//                container.addView(view);
//                return view;
//            }
//        };
//        viewPager.setAdapter(adapter);



    }


    private void event() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.rb_1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_3:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_4:
                        viewPager.setCurrentItem(3);
                        break;

                }



            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int currentItem = viewPager.getCurrentItem();

                switch (currentItem) {
                    case 0:
                        btn1.setChecked(true);
                        break;
                    case 1:
                        btn2.setChecked(true);
                        break;
                    case 2:
                        btn3.setChecked(true);
                        break;
                    case 3:
                        btn4.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private void getUserInfo()
    {
        HttpParamsObject param = new HttpParamsObject();

        new HttpUtil().sendRequest(Constant.MEMBER_USER_CENTER, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                userInfo = new Gson().fromJson(data, GetInfoBean.class);
                initView();
                event();
//                updateUserUi("name",userInfo.getData().getName());
//                updateUserUi("default_avatar",userInfo.getData().getDefault_avatar());
//                updateUserUi("default_avatar_rect",userInfo.getData().getDefault_avatar_rect());
//                updateUserUi("sex",userInfo.getData().getSex());
//                updateUserUi("payment",userInfo.getData().getOccupationInfo());
            }
        });
    }

    /**
     * 事件分发
     * @param key
     * @param value
     */
    public static void updateUserInfo(String key, String value)
    {
        switch (key)
        {
            case "name":
                userInfo.getData().setName(value);
                break;
            case "sex":
                userInfo.getData().setSex(value);
                break;
            case "default_avatar":
                userInfo.getData().setDefault_avatar(value);
                break;
            case "default_avatar_rect":
                userInfo.getData().setDefault_avatar_rect(value);
                break;
            case "company_name":
                userInfo.getData().setCompany_name(value);
                break;
            case "full_company_name":
                userInfo.getData().setFull_company_name(value);
                break;
            case "short_company_name":
                userInfo.getData().setShort_company_name(value);
                break;

            case "describe":
                userInfo.getData().setDescribe(value);
                break;
            case "address":
                userInfo.getData().setAddress(value);
                break;

        }
        EventBus.getDefault().post(new UserUpdateEvent(key,value));
    }


    public static void updateUserInfo(String key, Object value)
    {

        if(key.equals("payment"))
        {
            userInfo.getData().setOccupationInfo((List<GetInfoBean.DataBean.occupationInfoBean>)value);
//        ;
        }
        if(key.equals("tags"))
        {
            userInfo.getData().setTags((List<String>)value);
        }
        EventBus.getDefault().post(new UserUpdateEvent(key,value));
    }


    /**
     * 其他
     * @param key
     * @param value
     */
    public static void saveInfo(String key, Object value)
    {
//        saveInfo(key,value,null);
    }
    //    public static void saveInfo(String key, List<Object> listValue,CommonCallback cb)
    public static void saveInfo(String key, Object value,CommonCallback cb)
    {
        HttpParamsObject param = new HttpParamsObject();

        if(key.equals("payment"))
        {
            List<GetInfoBean.DataBean.occupationInfoBean> dataTemp = (List<GetInfoBean.DataBean.occupationInfoBean>)value;
            for (int i = 0; i < dataTemp.size(); i++) {
                for (int j = 0; j < dataTemp.get(i).getPayment().size(); j++) {
                    param.setParam("payment["+i+"_"+j+"][occupation_id]", dataTemp.get(i).getOccupation_id());
                    param.setParam("payment["+i+"_"+j+"][price]", dataTemp.get(i).getPayment().get(j).getPrice());
                    param.setParam("payment["+i+"_"+j+"][units]", dataTemp.get(i).getPayment().get(j).getUnits());
                }
            }
        }
        if(key.equals("tags"))
        {
            List<String> dataTemp = (List<String>)value;
            param.setParam("tags",dataTemp);
        }

        new HttpUtil().sendRequest(Constant.MEMBER_SAVE_USER_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                updateUserInfo(key,value);
                if(cb != null)
                {
                    cb.onSuccessJson(data,code);
                }
            }
            @Override
            public void onFinal() {
                super.onFinal();
                cb.onFinal();
            }
        });
    }

    /**
     * 更新信息到服务器
     * @param key
     * @param value
     */
    public static void saveInfo(String key, String value,CommonCallback cb)
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam(key,value);
        new HttpUtil().sendRequest(Constant.MEMBER_SAVE_USER_INFO, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                updateUserInfo(key,value);
                if(cb != null)
                {
                    cb.onSuccessJson(data,code);
                }
            }
            @Override
            public void onFinal() {
                super.onFinal();
                if(cb != null) {
                    cb.onFinal();
                }
            }
        });
    }
    public static void saveInfo(String key, String value)
    {
        saveInfo(key,value,null);
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        openPdfChatActivity(intent);
    }


    /**
     * 微信打开APP，发送pdf文件信息
     */
    private void openPdfChatActivity(Intent intent) {

        try {
            if (TextUtils.equals(intent.getAction(), Intent.ACTION_VIEW) && intent.getType().equals("application/pdf")) {
                //记录上次的用户id
                Intent newIntent = new Intent(this, MessageChatActivity.class);
                newIntent.putExtra("fromWechat", true);
                newIntent.setData(intent.getData());
                startActivity(newIntent);
            }
        } catch (Exception e) {
        }

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

//        @Override
//        public CharSequence getPageTitle(int position) {
////            String[] title = {"第一", "第二", "第三"};
////            return title[position];
//        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        HeartVideoManager.getInstance().stop();
    }

    @Override
    public void onBackPressed() {
        if (HeartVideoManager.getInstance().onBackPressd()) return;
        super.onBackPressed();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            radioGroup.setVisibility(View.GONE);
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            radioGroup.setVisibility(View.VISIBLE);
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }



}


