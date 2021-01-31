package com.ming.sjll.Share;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.ming.sjll.Bean.ShareMessageBean;
import com.ming.sjll.Bean.WorkListItem;
import com.ming.sjll.Message.utils.RongImUtils;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.ImageLoaderUtil;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.DialogShareMessageBinding;


import java.util.List;

/**
 * 搜索模块对应的 分享消息列表
 */
public class ShareMessageActivity extends BaseActivity {

    private DialogShareMessageBinding binding;

    public static final String SHARE_TYPE = "shareType";
    public static final String SHARE_ID = "shareId";

    //0-服务 1-人员 2-商品,3-作品
    public static final String TYPE_SAMPLE = "3";

    private String shareType = "";
    private String shareId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.dialog_share_message);

        Bundle bundle = new Bundle();
        shareType = getIntent().getStringExtra("shareType");
        shareId = getIntent().getStringExtra("shareId");

//        binding.setTitleViewModel(new ToolbarViewModel("分享到"));

        binding.rvMyColleague.setLayoutManager(new LinearLayoutManager(this));
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //修改一次请求接口一次
                onShowData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dataBean == null)
                {
                    ToastShow.s("请选择");
                    return;
                }
                sendMessage(dataBean);
            }
        });
        onShowData("");

    }


    ShareMessageBean.DataBean dataBean;
    ShareMessageBean dataBeans;
    public void onShowData(String s) {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("search",s);
        new HttpUtil().sendRequest(Constant.RC_GET_CHAT_LIST, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        dataBeans = new Gson().fromJson(data, ShareMessageBean.class);
                        ShareMessageAdapter adapter = new ShareMessageAdapter(dataBeans.getData());
                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                //分享当前的数据
                                dataBean = dataBeans.getData().get(position);
                                for (int i = 0; i < dataBeans.getData().size(); i++) {
                                        dataBeans.getData().get(i).setSelect(false);
                                }
                                dataBeans.getData().get(position).setSelect(true);
                                adapter.notifyDataSetChanged();
//                                sendMessage(dataBean);
                            }
                        });
                        binding.rvMyColleague.setAdapter(adapter);

                    }
                });

    }


    public void sendMessage(ShareMessageBean.DataBean dataBean) {

        loadingDailog.showLoading();
//        调用接口发送消息
        String target_id = dataBean.getTarget_id();
        String msg_type = "";
        boolean privateChat = TextUtils.equals(dataBean.getType(), "1");
        if (privateChat) {
            msg_type = "1";
        } else {
            msg_type = "3";
        }
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("share_type",shareType);
        param.setParam("share_id",shareId);
        param.setParam("target_id",target_id);
        param.setParam("msg_type",msg_type);
        new HttpUtil().sendRequest(Constant.SHARE_SHARE_APP_MSG, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        if (privateChat) {
                            RongImUtils.privateChat(ShareMessageActivity.this, target_id, dataBean.getTitle());
//                            RongIMUtils.INSTANCE.privateChat(getActivity(), target_id, dataBean.getTitle());
                        } else {
                            RongImUtils.groupChat(ShareMessageActivity.this, target_id, dataBean.getTitle());
//                            RongIMUtils.INSTANCE.groupChat(getActivity(), target_id, dataBean.getTitle());
                        }

                        ToastShow.showLong("分享成功");
                        //分享成功，关闭当前页面
                       finish();
                    }

            @Override
            public void onFinal() {
                super.onFinal();
                loadingDailog.hide();
            }
        });


    }


    public static class ShareMessageAdapter extends BaseQuickAdapter<ShareMessageBean.DataBean, BaseViewHolder> {

        public ShareMessageAdapter(@Nullable List<ShareMessageBean.DataBean> data) {
            super(R.layout.user_item_share_message, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, ShareMessageBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.name, dataBean.getTitle());
            baseViewHolder.setText(R.id.personalized, dataBean.getOccupationStr());
            new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.headImage),
                    Constant.BASE_API + dataBean.getDefault_avatar());

            baseViewHolder.getView(R.id.status).setVisibility(View.GONE);
            baseViewHolder.getView(R.id.wrap).setBackgroundColor(Color.parseColor("#FFFFFF"));

            if(dataBean.isSelect())
            {
                baseViewHolder.getView(R.id.wrap).setBackgroundResource(R.drawable.shape_gray_10_corner);
            }
        }

        @Override
        public void setNewData(@Nullable List<ShareMessageBean.DataBean> data) {
            this.mData = data;
            notifyDataSetChanged();
        }
    }
}
