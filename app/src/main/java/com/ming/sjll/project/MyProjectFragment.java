package com.ming.sjll.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.search.ProjectNotice;
import com.ming.sjll.search.ProjectProject;
import com.ming.sjll.search.SearchFragment;
import com.ming.sjll.ui.AboutHeightViewpager;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的项目
 */
public class MyProjectFragment extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_my_project_fragment);
        initView();
    }


    private void initView() {

        getList();

//        EditText searchEdit =  (EditText) findViewById(R.id.searchEdit);
//        searchEdit.clearFocus();
//        ImageView searchBtn =  (ImageView) findViewById(R.id.searchBtn);
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) searchBtn.getLayoutParams();
//
//        searchEdit.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    // 此处为得到焦点时的处理内容
//                    lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
//                    searchBtn.setLayoutParams(lp);
//                } else {
//                    // 此处为失去焦点时的处理内容
//                    if(searchEdit.getText().toString().equals(""))
//                    {
//                        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
//                        searchBtn.setLayoutParams(lp);
//
//                    }
//                }
//            }
//        });
//
//        searchEdit.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
//                searchBtn.setLayoutParams(lp);
//                searchComprehensiveFragemt.searchKey(searchEdit.getText().toString());
//                searchNoticeFragemt.searchKey(searchEdit.getText().toString());
//
//            }
//        });

    }


//
//    private PagedListView listView;
//    private ArrayAdapter<String> listAdapter;
//    private List<String> listData;

    public AboutHeightViewpager viewpager;
    //    private ArrayList<View> aList;
    private List<Fragment> fragmentList;
    private MyProjectList myProjectList1,myProjectList2;

    private ImageView project_list_type1;
    private ImageView project_list_type2;
//    private SearchNoticeFragemt searchNoticeFragemt;

    /**
     * 项目列表
     */
    public void getList() {
        project_list_type1 = (ImageView) findViewById(R.id.project_list_type1);
        project_list_type2 = (ImageView) findViewById(R.id.project_list_type2);

        project_list_type1.setActivated(true);
//        aList = new ArrayList<View>();
         myProjectList1 = MyProjectList.newInstance();
        myProjectList2 = MyProjectList.newInstance();
        myProjectList1.setType(1);
        myProjectList2.setType(0);

        fragmentList = new ArrayList<>();
        fragmentList.add(myProjectList1);
        fragmentList.add(myProjectList2);


        viewpager = (AboutHeightViewpager) findViewById(R.id.projectList);

        viewpager.setAdapter(new MyProjectFragment.Adaper(getSupportFragmentManager()));
//        viewpager.requestLayoutByPosition("projectProject");


        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
//                    viewpager.requestLayoutByPosition("projectProject");
//                    viewpager.setCurrentItem(0);
                    project_list_type1.setActivated(true);
                    project_list_type2.setActivated(false);
//                project_list_type1.setActivated(true);
//                project_list_type2.setActivated(false);

                } else {
//                    viewpager.requestLayoutByPosition("projectNotice");
//                    viewpager.setCurrentItem(1);
                    project_list_type2.setActivated(true);
                    project_list_type1.setActivated(false);
//                project_list_type2.setActivated(true);
//                project_list_type1.setActivated(false);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        project_list_type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewpager.requestLayoutByPosition("projectProject");

//                if(!project_list_type1.isChecked())
//                {

//                }
                viewpager.setCurrentItem(0);
//                ToastShow.s("0");
            }
        });

        project_list_type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                viewpager.requestLayoutByPosition("projectNotice");
                viewpager.setCurrentItem(1);
//                if(!project_list_type2.isChecked())
//                {

//                }
//                ToastShow.s("1");
            }
        });

        viewpager.setCurrentItem(0);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getList();
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

}
