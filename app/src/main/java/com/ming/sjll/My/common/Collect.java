package com.ming.sjll.My.common;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.homepage.HomepageActivity;
import com.ming.sjll.R;
import com.ming.sjll.base.fragment.BaseFragment;

import java.util.List;

/**
 * 个人中心-收藏
 */
public class Collect extends BaseFragment {


    public int point;

    public String userId;

    private Fragment[] mFragments;

    public static Collect newInstance() {
        return new Collect();
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_collect);
        initView();
        event();
    }


    CollectUser collectUser;
    CollectSample collectSample;
    CollectGoods collectGoods;

    private void initView() {
        if (getParentFragment() instanceof Myfragemt) {
            ((Myfragemt) getParentFragment()).setChildObjectForPosition(this, point);
        }
        else
        {
            ((HomepageActivity) getActivity()).setChildObjectForPosition(this,point);
        }

        collectUser = CollectUser.newInstance();
        collectUser.userId = userId;
        collectSample = CollectSample.newInstance();
        collectSample.userId = userId;
        collectGoods = CollectGoods.newInstance();
        collectGoods.userId = userId;
        mFragments = new Fragment[]{collectUser,collectGoods,collectSample};
        showFragment(collectUser);
    }

    private void event() {
        LinearLayout view = (LinearLayout) findViewById(R.id.tabWrap);
        View user = findViewById(R.id.user);
        View goods = findViewById(R.id.goods);
        View sample = findViewById(R.id.sample);
        View course = findViewById(R.id.course);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < view.getChildCount(); i++) {
                    View cv = view.getChildAt(i);
                    cv.setBackgroundResource(R.drawable.shape_white_10_corner);
                    ((TextView)cv.findViewById(R.id.contentText)).setTextColor(Color.BLACK);
                }
                user.setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)user.findViewById(R.id.contentText)).setTextColor(Color.WHITE);
                showFragment(collectUser);
            }
        });
        goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < view.getChildCount(); i++) {
                    View cv = view.getChildAt(i);
                    cv.setBackgroundResource(R.drawable.shape_white_10_corner);
                    ((TextView)cv.findViewById(R.id.contentText)).setTextColor(Color.BLACK);
                }
                goods.setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)goods.findViewById(R.id.contentText)).setTextColor(Color.WHITE);
                showFragment(collectGoods);
            }
        });
        sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < view.getChildCount(); i++) {
                    View cv = view.getChildAt(i);
                    cv.setBackgroundResource(R.drawable.shape_white_10_corner);
                    ((TextView)cv.findViewById(R.id.contentText)).setTextColor(Color.BLACK);

                }
                sample.setBackgroundResource(R.drawable.shape_blue_10_corner);
                ((TextView)sample.findViewById(R.id.contentText)).setTextColor(Color.WHITE);
                showFragment(collectSample);
            }
        });
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < view.getChildCount(); i++) {
//                    View cv = view.getChildAt(i);
//                    cv.setBackgroundResource(R.drawable.shape_white_10_corner);
////                    if(cv == view.getChildAt(v))
////                    {
////                        cv.setBackgroundResource(R.drawable.shape_blue_10_corner);
////                    }
//                }
//                if (v.getId() == R.id.user) {
//                    user.setBackgroundResource(R.drawable.shape_blue_10_corner);
//                } else if (v.getId() == R.id.goods) {
//                    goods.setBackgroundResource(R.drawable.shape_blue_10_corner);
//                }
//            }
//        });
    }


    /**
     * 显示fragment
     */
    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getChildFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
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


}
