package com.ming.sjll.Message.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.R;
import com.ming.sjll.base.fragment.MvpFragment;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.databinding.MessageChatNotifyFragmentBinding;
import com.ming.sjll.Message.presenter.ManagerSystemNotificationPresenter;
import com.ming.sjll.Message.view.MessageSystemNotificationView;
import com.ming.sjll.Message.viewmodel.MessageSystemNotificationBean;

import java.util.List;

public class MessageSystemNotificationListFragment extends MvpFragment<MessageSystemNotificationView, ManagerSystemNotificationPresenter> implements MessageSystemNotificationView {
    private MessageChatNotifyFragmentBinding dataBinding;

    public static MessageSystemNotificationListFragment newInstance() {

        Bundle args = new Bundle();

        MessageSystemNotificationListFragment fragment = new MessageSystemNotificationListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.message_chat_notify_fragment, null, false);
        setContentView(dataBinding.getRoot());

        dataBinding.rcNotification.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onShowData(List<MessageSystemNotificationBean.DataBean.DataBeanX> dataBeans) {
        dataBinding.rcNotification.setAdapter(new MessageNotifyAdapter(dataBeans));
    }

    public class MessageNotifyAdapter extends BaseQuickAdapter<MessageSystemNotificationBean.DataBean.DataBeanX, BaseViewHolder> {

        public MessageNotifyAdapter(@Nullable List<MessageSystemNotificationBean.DataBean.DataBeanX> data) {
            super(R.layout.message_system_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, MessageSystemNotificationBean.DataBean.DataBeanX dataBean) {
            baseViewHolder.setText(R.id.content, dataBean.getContent());
            baseViewHolder.setText(R.id.time, Tools.getDateformat(dataBean.getCreate_time()));

        }

    }

}

