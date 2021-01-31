package com.ming.sjll.Message.dialog;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ming.sjll.R;
import com.ming.sjll.base.dialog.BaseDialog;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.databinding.DialogChangeProjectBinding;
import com.ming.sjll.Message.fragment.ProjectListFragment;
import com.ming.sjll.Home.activity.PublishProjectAcitivity;
import com.ming.sjll.Home.bean.ProjectManagementBean;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 切换项目
 */
public class ChangeProjectDialog extends BaseDialog {

    private DialogChangeProjectBinding binding;
    private String title;

    public static ChangeProjectDialog newInstance() {

        Bundle args = new Bundle();

        ChangeProjectDialog fragment = new ChangeProjectDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_change_project;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewDataBinding binding = getLayoutBind();
        View view = binding.getRoot();
        onCreateView(binding);
        return view;
    }

    public void onCreateView(ViewDataBinding dataBinding) {
        binding = (DialogChangeProjectBinding) dataBinding;
        if (TextUtils.isEmpty(title)) {
            binding.title.setText(title);
        }

        initIndicator();

        binding.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加项目
                Tools.jump(getActivity(), PublishProjectAcitivity.class, false);
                dismissAllowingStateLoss();
            }
        });
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void initView(ViewDataBinding dataBinding) {
    }

    public void initIndicator() {

        List<String> mTitleDataList = new ArrayList<>();
        mTitleDataList.add("我发起的");
        mTitleDataList.add("我接单的");
        mTitleDataList.add("项目合伙");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ProjectListFragment.newInstance("1").setItemClick(onClickProjectListener));
        fragments.add(ProjectListFragment.newInstance("2").setItemClick(onClickProjectListener));
        fragments.add(ProjectListFragment.newInstance("3").setItemClick(onClickProjectListener));
        binding.viewPager.setAdapter(new ProjectFragmentAdapter(getChildFragmentManager(), fragments));

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                final ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context) {
                    @Override
                    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                        super.onEnter(index, totalCount, enterPercent, leftToRight);
                        setTypeface(Typeface.DEFAULT_BOLD);
                    }

                    @Override
                    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                        super.onLeave(index, totalCount, leavePercent, leftToRight);
                        setTypeface(Typeface.DEFAULT_BOLD);
                    }
                };
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#000000"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#0A3FFF"));
                colorTransitionPagerTitleView.setTextSize(18);
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.viewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(Color.parseColor("#0A3FFF"));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setRoundRadius(5);
                indicator.setLineWidth(UIUtil.dip2px(context, 12));
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                return indicator;
            }
        });
        binding.indicator.setNavigator(commonNavigator);
        binding.viewPager.setOffscreenPageLimit(mTitleDataList.size());
        ViewPagerHelper.bind(binding.indicator, binding.viewPager);
    }

    class ProjectFragmentAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragments = new ArrayList<>();

        public ProjectFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public boolean isBottom() {
        return true;
    }

    private onClickProjectListener onClickProjectListener;

    public ChangeProjectDialog setOnClickProjectListener(ChangeProjectDialog.onClickProjectListener onClickProjectListener) {
        this.onClickProjectListener = onClickProjectListener;
        return this;
    }

    public interface onClickProjectListener {
        void onClickProject(String type, int projectId, String projectName, ProjectManagementBean.DataBeanX.DataBean.ListBean listBean);
    }
}
