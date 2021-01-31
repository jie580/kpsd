package com.ming.sjll.Message;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.login.LoginActivity;
import com.ming.sjll.project.MyProjectFragment;
import com.ming.sjll.ui.AboutHeightViewpager;
import com.ming.sjll.ui.BadgeView;
import com.ming.sjll.ui.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * 消息
 */
public class Messagefragemt extends BaseFragment {

//    @BindView(R.id.rb_1)
    RadioButton rb1;
//    @BindView(R.id.rb_2)
    RadioButton rb2;
//    @BindView(R.id.rb_3)
    RadioButton rb3;
//    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
//    @BindView(R.id.viewPager)
    ViewPager viewPager;

    RadioButton btn1, btn2, btn3, btn4;

    private List<Fragment> fragmentList;
    private SingleChat singleChat;
    private GroupChat groupChat;

    public static Messagefragemt newInstance() {
        return new Messagefragemt();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_home_fragemt, container, false);
        super.setContentView(view);
        init();
        return view;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }
    private void init(){
//        setContentView(R.layout.message_home_fragemt);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        rb1 = (RadioButton)findViewById(R.id.rb_1);
        rb2 = (RadioButton)findViewById(R.id.rb_2);
        rb3 = (RadioButton)findViewById(R.id.rb_3);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);

        initView();
        event();


    }

    private static int private_read = 0;
    private static int group_read = 0;
    private void initView() {

        singleChat = SingleChat.newInstance();
        groupChat = GroupChat.newInstance();
        fragmentList = new ArrayList<>();
        fragmentList.add(createConversationListFragment());
        fragmentList.add(GroupCreateConversationListFragment());
        fragmentList.add(MessageInformFragemt.newInstance());

        fragmentList.add(groupChat);


        viewPager.setAdapter(new Messagefragemt.Adaper(getChildFragmentManager()));

        viewPager.setCurrentItem(0);


        View rb_1_red = (View)findViewById(R.id.rb_1_red);
        rb_1_red.setVisibility(View.INVISIBLE);

        View rb_2_red = (View)findViewById(R.id.rb_2_red);
        rb_2_red.setVisibility(View.INVISIBLE);

        View rb_3_red = (View)findViewById(R.id.rb_3_red);
        rb_3_red.setVisibility(View.INVISIBLE);

        TextView readCount = ((TextView)findViewById(R.id.readCount));
        RongIM.getInstance().addUnReadMessageCountChangedObserver(i -> {
            Log.i("Lyb", "unRead===" + i + "");
            // i 是未读数量
            if(i == 0)
            {
                rb_1_red.setVisibility(View.INVISIBLE);
            }
            else
            {
                rb_1_red.setVisibility(View.VISIBLE);
            }
            private_read = i;
            String s = group_read+private_read+"";
            readCount.setText(s);
        }, Conversation.ConversationType.PRIVATE);

        RongIM.getInstance().addUnReadMessageCountChangedObserver(i -> {
            Log.i("Lyb", "unRead===" + i + "");
            // i 是未读数量
            if(i == 0)
            {
                rb_2_red.setVisibility(View.INVISIBLE);
            }
            else
            {
                rb_2_red.setVisibility(View.VISIBLE);
            }
            group_read = i;
            String s = group_read+private_read+"";
            readCount.setText(s);

        }, Conversation.ConversationType.GROUP);

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
                        rb1.setChecked(true);
                        break;
                    case 1:
                        rb2.setChecked(true);
                        break;
                    case 2:
                        rb3.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    public ConversationListFragment createConversationListFragment() {
        ConversationListFragment mConversationListFragment = MessageConversationListFragment.newInstance();
        try {
//            Uri uri = Uri.parse("rong://" + Messagefragemt.this.getApplicationInfo().packageName).buildUpon()
            Uri uri = Uri.parse("rong://" + getContext().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true")
//                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
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

    public ConversationListFragment GroupCreateConversationListFragment() {
        MessageGroupChatConversationListFragment mConversationListFragment = MessageGroupChatConversationListFragment.newInstance();
        try {
            Uri uri = Uri.parse("rong://" + getContext().getApplicationInfo().packageName).buildUpon()
                    .appendPath("conversationlist")
//                    .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "true")
                    .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
//                    .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "true")
//                    .appendQueryParameter(Conversation.ConversationType.APP_PUBLIC_SERVICE.getName(), "true")
//                    .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                    .build();
            mConversationListFragment.setUri(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mConversationListFragment;

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
