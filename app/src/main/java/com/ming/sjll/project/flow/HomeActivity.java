package com.ming.sjll.project.flow;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoDateInfoBean;
import com.ming.sjll.Bean.ProjectFlowBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.ProjectFlowHomeBinding;
import com.ming.sjll.databinding.ProjectManageHomeBinding;
import com.ming.sjll.project.manage.CostFragment;
import com.ming.sjll.project.manage.GoodsFragment;
import com.ming.sjll.project.manage.ProjectTimeActivity;
import com.ming.sjll.project.flow.SampleUploadFragment;
import com.ming.sjll.project.manage.SiteFragment;
import com.ming.sjll.project.manage.TeamFragment;
import com.ming.sjll.project.manage.TimeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目进程
 */
public class HomeActivity extends BaseActivity {

    ProjectFlowHomeBinding binding;


    private Fragment[] mFragments;
    String projectId;


    ProjectFlowBean projectDate;

    SampleUploadFragment sampleUploadFragment;
    SampleSelectFragment sampleSelectFragment;
    MoneyAdjustFragment moneyAdjustFragment;
    ProjectEndFragment projectEndFragment;
    WorkReviewFragment workReviewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.project_flow_home);



//        setContentView(R.layout.project_manage_team);
        initView();
        event();
    }

    private void initView()
    {
        projectId = getIntent().getStringExtra("projectId");

        sampleUploadFragment = SampleUploadFragment.newInstance(projectId);
        sampleSelectFragment = SampleSelectFragment.newInstance(projectId);
        moneyAdjustFragment = MoneyAdjustFragment.newInstance(projectId);
        projectEndFragment = ProjectEndFragment.newInstance(projectId);
        workReviewFragment = WorkReviewFragment.newInstance(projectId);

        mFragments = new Fragment[]{sampleUploadFragment,sampleSelectFragment,moneyAdjustFragment, projectEndFragment};
        getFlow();

        showFragment(sampleUploadFragment);
    }


    private void getFlow()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId",projectId);

        new HttpUtil().sendRequest(Constant.PROJECT_FLOW_GET_PROJECT_FLOW, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                projectDate = new Gson().fromJson(data, ProjectFlowBean.class);

            }
        });
    }

    private void event()
    {

        binding.guide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGuide(1);
            }
        });
        binding.guide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGuide(2);
            }
        });
        binding.guide3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGuide(3);
            }
        });
        binding.guide4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGuide(4);
            }
        });
        binding.guide5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGuide(5);
            }
        });
        binding.guide6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGuide(6);
            }
        });
        binding.guide7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGuide(7);
            }
        });
    }



    private void selectGuide(int index)
    {
        binding.guide1.setBackgroundResource(0);
        binding.guide2.setBackgroundResource(0);
        binding.guide3.setBackgroundResource(0);
        binding.guide4.setBackgroundResource(0);
        binding.guide5.setBackgroundResource(0);
        binding.guide6.setBackgroundResource(0);
        binding.guide7.setBackgroundResource(0);

        switch (index)
        {
            case 1:
                binding.guide1.setBackgroundResource(R.mipmap.ic_project_flow_guide);
                showFragment(sampleUploadFragment);
                break;
            case 2:
                binding.guide2.setBackgroundResource(R.mipmap.ic_project_flow_guide);
                showFragment(sampleSelectFragment);
                break;
            case 3:
                binding.guide3.setBackgroundResource(R.mipmap.ic_project_flow_guide);
                showFragment(sampleUploadFragment);
                break;
            case 4:
                binding.guide4.setBackgroundResource(R.mipmap.ic_project_flow_guide);
                showFragment(workReviewFragment);
                break;
            case 5:
                binding.guide5.setBackgroundResource(R.mipmap.ic_project_flow_guide);
                showFragment(sampleUploadFragment);
                break;
            case 6:
                binding.guide6.setBackgroundResource(R.mipmap.ic_project_flow_guide);
                showFragment(moneyAdjustFragment);
                break;
            case 7:
                binding.guide7.setBackgroundResource(R.mipmap.ic_project_flow_guide);
                showFragment(projectEndFragment);
                break;
        }

    }


    /**
     * 显示fragment
     */
    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.frameLayout, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
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



}
