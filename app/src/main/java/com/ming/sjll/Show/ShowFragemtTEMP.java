package com.ming.sjll.Show;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.R;
import com.ming.sjll.Show.adapter.ShowAdapter;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
//
//import me.yuqirong.cardswipelayout.CardItemTouchHelperCallback;
//import me.yuqirong.cardswipelayout.CardLayoutManager;
//import me.yuqirong.cardswipelayout.OnSwipeListener;

public class ShowFragemtTEMP extends BaseActivity {

    public static ShowFragemtTEMP newInstance() {
        return new ShowFragemtTEMP();
    }

    public String occupationId  = "1";
    private boolean isLoad = false;
    private WorkListItem.DataBean dataList;


    private FrameLayout recyclerview;
    private ShowAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_home_fragemt);
        initView();
        Log.d(TAG, "onCreate: ");
    }


    private Fragment[] mFragments;
    private void initView()
    {
//        recyclerview = (FrameLayout)findViewById(R.id.recyclerview);

        ShowInnerFragment searchComprehensiveFragemt = ShowInnerFragment.newInstance();
        mFragments = new Fragment[]{searchComprehensiveFragemt};
        showFragment(searchComprehensiveFragemt);
//        getList();
    }


    /**
     * 显示fragment
     */
    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.recyclerview, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
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



    private void getList()
    {
        if (isLoad) {
            return;
        }
        isLoad = true;
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("occupationId", occupationId);
        new HttpUtil().sendRequest(Constant.HOME_SHOW, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                isLoad = false;
                dataList = new Gson().fromJson(data, WorkListItem.DataBean.class);

//                mAdapter = new ShowAdapter(dataList.getData());
//                recyclerview.setAdapter(mAdapter);
//
//                recyclerview.setLayoutManager(new LinearLayoutManager(ShowFragemt.this));
//                CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(recyclerview.getAdapter(), dataList.getData());
//                ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
//                CardLayoutManager cardLayoutManager = new CardLayoutManager(recyclerview, touchHelper);
//                recyclerview.setLayoutManager(cardLayoutManager);
//                touchHelper.attachToRecyclerView(recyclerview);
//                cardCallback.setOnSwipedListener(new OnSwipeListener() {
//                    @Override
//                    public void onSwiping(RecyclerView.ViewHolder viewHolder, float v, int i) {
//
//                    }
//
//                    @Override
//                    public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int i) {
//                        if (i == dataList.getData().size()) {
//                            getList();
//                        }
//                    }
//
//                    @Override
//                    public void onSwipedClear() {
//
//                    }
//                });




            }
        });
    }



}
