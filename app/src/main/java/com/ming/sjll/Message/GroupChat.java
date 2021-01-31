package com.ming.sjll.Message;

import android.os.Bundle;

import com.ming.sjll.R;
import com.ming.sjll.base.fragment.BaseFragment;

/**
 * 消息列表-群聊
 */
public class GroupChat extends BaseFragment {


    public static GroupChat newInstance() {

        return new GroupChat();
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.message_single_chat);
//        initView();
//        event();
    }

}
