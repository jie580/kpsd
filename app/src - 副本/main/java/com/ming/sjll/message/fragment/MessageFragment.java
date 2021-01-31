package com.ming.sjll.Message.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.api.EventConstant;
import com.ming.sjll.base.bean.EventBusBean;
import com.ming.sjll.base.fragment.MvpFragment;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.databinding.FragemtMessageBinding;
import com.ming.sjll.Message.presenter.MessagePresenter;
import com.ming.sjll.Message.view.MessageView;
import com.ming.sjll.my.bean.WelcomeBean;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeAnchor;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge.BadgeRule;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;


public class MessageFragment extends MvpFragment<MessageView, MessagePresenter> implements MessageView {
    private FragemtMessageBinding dataBinding;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    private TextView badgeTextView;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        dataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragemt_message, null, false);
        setContentView(dataBinding.getRoot());
        initIndicator();

    }

    public void initIndicator() {

        List<String> mTitleDataList = new ArrayList<>();
        mTitleDataList.add(" 消息 ");
        mTitleDataList.add(" 通知 ");
        mTitleDataList.add(" 系统 ");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(createConversationListFragment());
        fragments.add(MessageNotificationListFragment.newInstance());
        fragments.add(MessageSystemNotificationListFragment.newInstance());
        dataBinding.viewPager.setAdapter(new MessageFragmentAdapter(getChildFragmentManager(), fragments));
        dataBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                try {
                    if (i == 2) {
                        showUnreadMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(false);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {


                final BadgePagerTitleView badgePagerTitleView = new BadgePagerTitleView(context);

                final ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#000000"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#0A3FFF"));
                colorTransitionPagerTitleView.setTextSize(18);
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataBinding.viewPager.setCurrentItem(index);
                        if (badgePagerTitleView != null) {
                            badgePagerTitleView.setBadgeView(null); // cancel badge when click tab
                        }
                    }
                });
                if (index == 2) {

                    badgePagerTitleView.setInnerPagerTitleView(colorTransitionPagerTitleView);
                    badgeTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.message_notify_badge, null);
                    badgePagerTitleView.setBadgeView(badgeTextView);
                    badgeTextView.setVisibility(View.GONE);
                    badgePagerTitleView.setXBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_RIGHT, -UIUtil.dip2px(context, 0)));
                    badgePagerTitleView.setYBadgeRule(new BadgeRule(BadgeAnchor.CONTENT_TOP, UIUtil.dip2px(context, 7)));
                    return badgePagerTitleView;

                }


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
        dataBinding.magicIndicator.setNavigator(commonNavigator);
        dataBinding.viewPager.setOffscreenPageLimit(mTitleDataList.size());
        ViewPagerHelper.bind(dataBinding.magicIndicator, dataBinding.viewPager);
    }


    public ConversationListFragment createConversationListFragment() {
        ConversationListFragment mConversationListFragment = MessageConversationListFragment.newInstance();
        try {
            Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                    .build();
            mConversationListFragment.setUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mConversationListFragment;

    }

    @Override
    public void ShowData(WelcomeBean bean) {
        dataBinding.tvName.setText(bean.getData().getStr());
        ImageHelper.display(dataBinding.ivImg, bean.getData().getDefault_avatar());
    }

    @Override
    public void showUnreadMessage(int unread_count) {
        if (unread_count == 0) {
            badgeTextView.setVisibility(View.GONE);
        } else {
            badgeTextView.setText(unread_count + "");
            badgeTextView.setVisibility(View.VISIBLE);
        }
    }

    class MessageFragmentAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragments = new ArrayList<>();

        public MessageFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
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
    protected boolean useEventBus() {
        return true;
    }

    @Subscribe
    public void onEventReceive(EventBusBean event) {
        if (EventConstant.PERSONALDATASICESS.equals(event.getEventName())) {
            mPresenter.welcome();
        }
    }
}
