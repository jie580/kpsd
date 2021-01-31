package com.ming.sjll.search;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.GetInfoBeans;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.Cache;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageLoaderUtil;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.SearchMemberProjectMultipleBinding;
import com.rey.material.app.BottomSheetDialog;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 搜索模块对应的 分享消息列表
 */
public class SearchMemberProjectMultipleActivity extends BaseActivity {

    private SearchMemberProjectMultipleBinding binding;

    public static final String SHARE_TYPE = "shareType";
    public static final String SHARE_ID = "shareId";

    //0-服务 1-人员 2-商品,3-作品
    public static final String TYPE_SAMPLE = "shareType";

    private String listType = "";
    private String projectId = "";
    private String occupationId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.search_member_project_multiple);

        Bundle bundle = new Bundle();
        listType = getIntent().getStringExtra("listType");
        projectId = getIntent().getStringExtra("projectId");
        occupationId = getIntent().getStringExtra("occupationId");

//        binding.setTitleViewModel(new ToolbarViewModel("分享到"));
        binding.titleBar.titleBarIvRight.setVisibility(View.VISIBLE);
        binding.titleBar.titleBarIvRight.setBackground(getResources().getDrawable(R.mipmap.btn_save_top7));

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
        onShowData("");


        binding.titleBar.titleBarIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MemberSelectData selectMember = new MemberSelectData();
                selectMember.userList = new LinkedList<>();
                for (int i = 0; i < dataBeans.getData().size(); i++) {
                    if (dataBeans.getData().get(i).isSelect()) {
                        MemberSelectData.Database temp = new MemberSelectData.Database();
                        temp.user_id = dataBeans.getData().get(i).getUid();
                        temp.service_type = dataBeans.getData().get(i).getService_type();
                        selectMember.userList.add(temp);
                    }
                }
                if (selectMember.userList.size() == 0) {
                    ToastShow.s("请选择后再保存");
                    return;
                }
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("projectId", projectId);
                param.setParam("occupationId", occupationId);
                for (int i = 0; i < selectMember.userList.size(); i++) {
                    param.setParam("userList[" + i + "]" + "[user_id]", selectMember.userList.get(i).user_id);
                    ;
                    param.setParam("userList[" + i + "]" + "[service_type]", selectMember.userList.get(i).service_type);
                    ;
                }

                new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_ADD_OCCUPATION_USER, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        Intent intent = new Intent();
                        SearchMemberProjectMultipleActivity.this.setResult(SearchMemberProjectMultipleActivity.RESULT_OK, intent);
                        SearchMemberProjectMultipleActivity.this.finish();
                    }
                });

            }
        });

    }


    static class MemberSelectData {

        public List<Database> userList;

        static class Database {
            public String user_id;
            public String service_type;

        }
    }


    GetInfoBeans dataBeans;

    public void onShowData(String s) {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("search", s);
        param.setParam("projectId", projectId);
        param.setParam("occupationId", occupationId);
        param.setParam("type", listType);
        new HttpUtil().sendRequest(Constant.PROJECT_MEMBER_MEMBER_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                dataBeans = new Gson().fromJson(data, GetInfoBeans.class);
                ShareMessageAdapter adapter = new ShareMessageAdapter(dataBeans.getData());
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        if (dataBeans.getData().get(position).isIs_select()) {
                            return;
                        }

                        if (dataBeans.getData().get(position).isSelect()) {
                            dataBeans.getData().get(position).setSelect(!dataBeans.getData().get(position).isSelect());
                            adapter.notifyDataSetChanged();
                            return;
                        }
                        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(SearchMemberProjectMultipleActivity.this);
                        bottomInterPasswordDialog.setContentView(R.layout.dialog_project_add_member_type);
                        bottomInterPasswordDialog.inDuration(300);
                        bottomInterPasswordDialog.outDuration(200);
                        bottomInterPasswordDialog.findViewById(R.id.guide1menu).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(true);
                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(false);
                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).setChecked(false);
                            }
                        });
                        bottomInterPasswordDialog.findViewById(R.id.guide2menu).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(false);
                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(true);
                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).setChecked(false);
                            }
                        });
                        bottomInterPasswordDialog.findViewById(R.id.guide3menu).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).setChecked(false);
                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).setChecked(false);
                                ((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).setChecked(true);
                            }
                        });
                        bottomInterPasswordDialog.findViewById(R.id.tv_nextPage).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide1)).isChecked()) {
                                    dataBeans.getData().get(position).setService_type("1");
                                    dataBeans.getData().get(position).setService_str("买家到场/寄送");
                                } else if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide2)).isChecked()) {
                                    dataBeans.getData().get(position).setService_type("2");
                                    dataBeans.getData().get(position).setService_str("到场服务");
                                } else if (((CheckBox) bottomInterPasswordDialog.findViewById(R.id.guide3)).isChecked()) {
                                    dataBeans.getData().get(position).setService_type("3");
                                    dataBeans.getData().get(position).setService_str("线上传输");
                                } else {
                                    ToastShow.s("请选择服务方式");
                                    return;
                                }
                                dataBeans.getData().get(position).setSelect(!dataBeans.getData().get(position).isSelect());
                                adapter.notifyDataSetChanged();
                                bottomInterPasswordDialog.hide();
                            }
                        });
                        bottomInterPasswordDialog.findViewById(R.id.tv_goBack).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomInterPasswordDialog.hide();
                            }
                        });
                        bottomInterPasswordDialog.show();

                    }
                });
//                        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//
//
//                            }
//                        });
                binding.rvMyColleague.setAdapter(adapter);

            }
        });

    }


    public static class ShareMessageAdapter extends BaseQuickAdapter<GetInfoBean.DataBean, BaseViewHolder> {

        public ShareMessageAdapter(@Nullable List<GetInfoBean.DataBean> data) {
            super(R.layout.search_member_project_multiple_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean dataBean) {
            baseViewHolder.setText(R.id.tv_name, dataBean.getName());
            baseViewHolder.setText(R.id.tv_info, dataBean.getOccupation());


            baseViewHolder.getView(R.id.contentText).setVisibility(View.GONE);
            ImageLoaderUtil.display(baseViewHolder.getView(R.id.iv_header), dataBean.getDefault_avatar());
            if (dataBean.isIs_select()) {
                ((ImageView) baseViewHolder.getView(R.id.is_read)).setBackgroundResource(R.mipmap.btn_checked_ed);
                baseViewHolder.setText(R.id.contentText, dataBean.getService_str());
                baseViewHolder.getView(R.id.contentText).setVisibility(View.VISIBLE);
            } else {
                if (dataBean.isSelect()) {
                    ((ImageView) baseViewHolder.getView(R.id.is_read)).setBackgroundResource(R.mipmap.btn_checked);
                    baseViewHolder.setText(R.id.contentText, dataBean.getService_str());
                    baseViewHolder.getView(R.id.contentText).setVisibility(View.VISIBLE);
                } else {
                    ((ImageView) baseViewHolder.getView(R.id.is_read)).setBackgroundResource(R.mipmap.btn_checked_un);
                }
            }
            baseViewHolder.addOnClickListener(R.id.itemWrap);
//            baseViewHolder.addOnClickListener(R.id.iv_header);
//            baseViewHolder.addOnClickListener(R.id.tv_name);
//            baseViewHolder.addOnClickListener(R.id.tv_info);
        }

        @Override
        public void setNewData(@Nullable List<GetInfoBean.DataBean> data) {
            this.mData = data;
            notifyDataSetChanged();
        }
    }
}
