package com.ming.sjll.project.manage;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.ming.sjll.Bean.GetInfoDateInfoBean;
import com.ming.sjll.Bean.ProjectSiteListBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.ProjectManageHomeBinding;
import com.ming.sjll.search.SearchFragment;
import com.rey.material.app.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目管理
 */
public class HomeActivity extends BaseActivity {

    ProjectManageHomeBinding binding;


    private List<Fragment> fragmentList;

    TeamFragment teamFragment;
    TimeFragment timeFragment;
    SiteFragment siteFragment;
    GoodsFragment goodsFragment;
    CostFragment costFragment;
    String projectId;
    String identification;

    GetInfoDateInfoBean projectDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.project_manage_home);

        binding.titleBar.titleBarTvRight.setText("项目时间");
        binding.titleBar.titleBarTvRight.setVisibility(View.VISIBLE);
        binding.titleBar.titleBarTvRight.setTextColor(Color.parseColor("#80B5FF"));
        binding.titleBar.titleBarTvRight.setTextSize(16);

//        setContentView(R.layout.project_manage_team);
        initView();
    }

    private void initView()
    {
        projectId = getIntent().getStringExtra("projectId");

        binding.sendProjectMoney.setVisibility(View.GONE);
        identification = "member";

        teamFragment = TeamFragment.newInstance(projectId);
        timeFragment = TimeFragment.newInstance(projectId);
        siteFragment = SiteFragment.newInstance(projectId);
        goodsFragment = GoodsFragment.newInstance(projectId);
        costFragment = CostFragment.newInstance(projectId);

        fragmentList = new ArrayList<>();
        fragmentList.add(teamFragment);
        fragmentList.add(timeFragment);
        fragmentList.add(siteFragment);
        fragmentList.add(goodsFragment);
        fragmentList.add(costFragment);


        binding.viewpager.setAdapter(new Adaper(getSupportFragmentManager()));

        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.guide1.setBackgroundResource(R.mipmap.project_manage_gui1);
                binding.guide2.setBackgroundResource(R.mipmap.project_manage_gui2);
                binding.guide3.setBackgroundResource(R.mipmap.project_manage_gui3);
                binding.guide4.setBackgroundResource(R.mipmap.project_manage_gui4);
                binding.guide5.setBackgroundResource(R.mipmap.project_manage_gui5);
                binding.sendProjectMoney.setVisibility(View.GONE);
                if (position == 0) {
                    binding.guide1.setBackgroundResource(R.mipmap.project_manage_gui1_select);
                    identification = "member";
                }
                else if(position == 1)
                {
                    binding.guide2.setBackgroundResource(R.mipmap.project_manage_gui2_select);
                    identification = "schedule";
                }
                else if(position == 2)
                {
                    binding.guide3.setBackgroundResource(R.mipmap.project_manage_gui3_select);
                    identification = "site";
                }
                else if(position == 3)
                {
                    binding.guide4.setBackgroundResource(R.mipmap.project_manage_gui4_select);
                    identification = "prop";
                }
                else if(position == 4)
                {
                    binding.sendProjectMoney.setVisibility(View.VISIBLE);
                    binding.guide5.setBackgroundResource(R.mipmap.project_manage_gui5_select);
                    identification = "detail";
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.viewpager.setCurrentItem(0);
        binding.guide1.setBackgroundResource(R.mipmap.project_manage_gui1_select);


        binding.guide1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(0);
            }
        });
        binding.guide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(1);
            }
        });
        binding.guide3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(2);
            }
        });
        binding.guide4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(3);
            }
        });
        binding.guide5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.viewpager.setCurrentItem(4);
            }
        });


        binding.sendProjectStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("projectId",projectId);
                param.setParam("identification",identification);
                new HttpUtil().sendRequest(Constant.PROJECT_FLOW_SEND_MSG, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("发送成功");
                    }
                });
            }
        });
        binding.sendProjectMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("projectId",projectId);
                new HttpUtil().sendRequest(Constant.PROJECT_FLOW_SEND_ORDER, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("发送成功");
                    }
                });
            }
        });

        getProjectDate();
    }

//    获取项目时间
    private void getProjectDate()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("projectId", projectId);
        new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_PROJECT_DATE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {

                projectDate = new Gson().fromJson(data, GetInfoDateInfoBean.class);
                if(projectDate.getData().getDateList().size() == 0)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("projectId", projectId);
                    Tools.jump(getContext(), ProjectTimeActivity.class, bundle,true);
                }

            }
        });
        binding.titleBar.titleBarTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("projectId", projectId);
                Tools.jump(getContext(), ProjectTimeActivity.class, bundle,false);
//                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
//                bottomInterPasswordDialog.setContentView(R.layout.dialog_project_date);
//                bottomInterPasswordDialog.inDuration(300);
//                bottomInterPasswordDialog.outDuration(200);
//
//
//
//
//                bottomInterPasswordDialog.show();
            }
        });

    }

    private void openProjectDateWindows()
    {

    }



//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e(TAG,resultCode+"收到回到"+data.getStringExtra("backgroundImage")+"编码"+requestCode);

        if (resultCode == RESULT_OK) {
//            if (requestCode == 1) {//背景图片
//                String backgroundImage=data.getStringExtra("backgroundImage");
//                Log.e("backgroundImage",backgroundImage+"111"+data.getStringExtra("backgroundImage"));
//                param.setParam("backgroundImage",backgroundImage);
//            }
//            else
            if (fragmentList != null) {
                for (Fragment fragment : fragmentList) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
            }
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

}
