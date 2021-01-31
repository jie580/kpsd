package com.ming.sjll.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.ui.AboutHeightViewpager;

import java.util.ArrayList;
import java.util.List;

public class ProjectInfoFragment extends BaseActivity {

    String ProjectId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ProjectId = getIntent().getStringExtra("ProjectId");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_search_fragment);
        initView();

    }



    private void initView() {


    }


}
