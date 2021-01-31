package com.ming.sjll.Message.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.R;
import com.ming.sjll.base.activity.MvpActivity;
import com.ming.sjll.base.utils.ImageLoaderUtil;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.databinding.SearchMyColleagueActivityBinding;
import com.ming.sjll.Message.presenter.ShareMessagePresenter;
import com.ming.sjll.Message.view.ShareMessageView;
import com.ming.sjll.Message.viewmodel.ShareMessageBean;

import java.util.List;

/**
 * 搜索模块对应的 分享消息列表
 */
public class ShareMessageActivity extends MvpActivity<ShareMessageView, ShareMessagePresenter> implements ShareMessageView {

    private SearchMyColleagueActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.search_my_colleague_activity);

        binding.setTitleViewModel(new ToolbarViewModel("分享到"));

        binding.rvMyColleague.setLayoutManager(new LinearLayoutManager(this));
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //修改一次请求接口一次
                mPresenter.getData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    public void onShowData(ShareMessageBean dataBeans) {
        ShareMessageAdapter adapter = new ShareMessageAdapter(dataBeans.getData());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //分享当前的数据
                ShareMessageBean.DataBean dataBean = dataBeans.getData().get(position);
                mPresenter.sendMessage(dataBean);

            }
        });
        binding.rvMyColleague.setAdapter(adapter);
    }


    public static class ShareMessageAdapter extends BaseQuickAdapter<ShareMessageBean.DataBean, BaseViewHolder> {

        public ShareMessageAdapter(@Nullable List<ShareMessageBean.DataBean> data) {
            super(R.layout.message_share_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, ShareMessageBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.tv_name, dataBean.getTitle());
            ImageLoaderUtil.display(baseViewHolder.getView(R.id.iv_header), dataBean.getDefault_avatar());
        }

        @Override
        public void setNewData(@Nullable List<ShareMessageBean.DataBean> data) {
            this.mData = data;
            notifyDataSetChanged();
        }
    }
}
