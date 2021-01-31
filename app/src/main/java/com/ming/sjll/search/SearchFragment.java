package com.ming.sjll.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.ui.AboutHeightViewpager;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索项目
 */
public class SearchFragment extends BaseActivity {

    int showTypePos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showTypePos = getIntent().getStringExtra("showType").equals("project") ? 0 : 1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_search_fragment);
        initView();

    }



    private void initView() {

        getList();

        EditText searchEdit =  (EditText) findViewById(R.id.searchEdit);
        searchEdit.clearFocus();
        ImageView searchBtn =  (ImageView) findViewById(R.id.searchBtn);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) searchBtn.getLayoutParams();

        searchEdit.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                    searchBtn.setLayoutParams(lp);
                } else {
                    // 此处为失去焦点时的处理内容
                    if(searchEdit.getText().toString().equals(""))
                    {
                        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
                        searchBtn.setLayoutParams(lp);

                    }
                }
            }
        });

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                lp.removeRule(RelativeLayout.CENTER_HORIZONTAL);
                searchBtn.setLayoutParams(lp);
                searchComprehensiveFragemt.searchKey(searchEdit.getText().toString());
                searchNoticeFragemt.searchKey(searchEdit.getText().toString());

            }
        });

    }


//
//    private PagedListView listView;
//    private ArrayAdapter<String> listAdapter;
//    private List<String> listData;

    public AboutHeightViewpager viewpager;
    //    private ArrayList<View> aList;
    private List<Fragment> fragmentList;
    private ProjectProject searchComprehensiveFragemt;
    private ProjectNotice searchNoticeFragemt;
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
        searchComprehensiveFragemt = ProjectProject.newInstance();
        searchNoticeFragemt = ProjectNotice.newInstance();

        fragmentList = new ArrayList<>();
        fragmentList.add(searchComprehensiveFragemt);
        fragmentList.add(searchNoticeFragemt);

        viewpager = (AboutHeightViewpager) findViewById(R.id.searchProjectList);

        viewpager.setAdapter(new SearchFragment.Adaper(getSupportFragmentManager()));
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

        viewpager.setCurrentItem(showTypePos);

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


}
