package com.ming.sjll.Message.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.R;
import com.ming.sjll.base.activity.MvpActivity;
import com.ming.sjll.base.utils.ImageLoaderUtil;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.viewmodel.ToolbarViewModel;
import com.ming.sjll.databinding.ActivityManagerInfoBinding;
import com.ming.sjll.Message.dialog.ManagerMemberInfoDialog;
import com.ming.sjll.Message.dialog.MemberOccupationListTagAdapter;
import com.ming.sjll.Message.dialog.ReceivedConfirmDialog;
import com.ming.sjll.Message.presenter.ManagerMemberInfoPresenter;
import com.ming.sjll.Message.view.ManagerMemberView;
import com.ming.sjll.Message.viewmodel.GetMemberInfoBean;
import com.ming.sjll.Message.viewmodel.GroupMemberBean;
import com.ming.sjll.Home.bean.OccupationBean;
import com.ming.sjll.view.FlowTagLayout;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ManagerMemberInfoActivity extends MvpActivity<ManagerMemberView, ManagerMemberInfoPresenter> implements ManagerMemberView {

    private ActivityManagerInfoBinding viewDataBinding;
    private MemberInfoApter adapter;
    private List<GroupMemberBean.DataBean> dataBeans;
    private OccupationBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_manager_info);
        viewDataBinding.setTitleViewModel(new ToolbarViewModel("成员信息"));
        viewDataBinding.rcMember.setLayoutManager(new LinearLayoutManager(this));
        if (mPresenter.isManager()) {
            viewDataBinding.rcMember.setSwipeMenuCreator(swipeMenuCreator);
            viewDataBinding.rcMember.setOnItemMenuClickListener(mMenuItemClickListener);
        }
        mPresenter.getData();
    }

    @Override
    public void onShowData(List<GroupMemberBean.DataBean> dataBeans) {

        for (int i = 0; i < dataBeans.size(); i++) {
            GroupMemberBean.DataBean dataBean = dataBeans.get(i);
            if (TextUtils.equals(dataBean.getUid(), mPresenter.getUserId())) {
                dataBean.setHasDelete(false);
            }
        }


        this.dataBeans = dataBeans;
        adapter = new MemberInfoApter(dataBeans);
        viewDataBinding.rcMember.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDelMember() {
        mPresenter.getData();
    }

    @Override
    public void onGroupMemberSuccess() {
        mPresenter.getData();
    }

    @Override
    public void onShowGroupMemberInfo(GetMemberInfoBean groupMemberBean) {

        ManagerMemberInfoDialog managerMemberInfoDialog = ManagerMemberInfoDialog.newInstance(groupMemberBean.getData(), bean.getData());
        managerMemberInfoDialog.setOnSaveListener(new ManagerMemberInfoDialog.OnSaveListener() {
            @Override
            public void onSave(String uid, String remark, List<String> occupationId) {
                mPresenter.groupManage(uid, remark, occupationId);
            }
        });
        managerMemberInfoDialog.show(ManagerMemberInfoActivity.this);
    }


    public class MemberInfoApter extends BaseQuickAdapter<GroupMemberBean.DataBean, BaseViewHolder> {

        public MemberInfoApter(@Nullable List<GroupMemberBean.DataBean> data) {
            super(R.layout.message_member_info_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, GroupMemberBean.DataBean dataBean) {
            FlowTagLayout flowTagLayout = baseViewHolder.getView(R.id.flowtag);
            List<String> tagList = new ArrayList<>();
            if (dataBean.getOccupation() != null && !dataBean.getOccupation().isEmpty()) {
                tagList.addAll(dataBean.getOccupation());
            }
            //tagList.add(dataBean.getJoinData() + "天");

            MemberOccupationListTagAdapter adapter = new MemberOccupationListTagAdapter(ManagerMemberInfoActivity.this, tagList);
            flowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);//支持多选
            flowTagLayout.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            if (!TextUtils.isEmpty(dataBean.getRemark())) {
                StringBuilder builder = new StringBuilder(dataBean.getName());
                builder.append("(");
                builder.append(dataBean.getRemark());
                builder.append(")");
                baseViewHolder.setText(R.id.name, builder.toString());
            } else {
                baseViewHolder.setText(R.id.name, dataBean.getName());
            }


            ImageLoaderUtil.display(baseViewHolder.getView(R.id.header), dataBean.getDefault_avatar());
        }

    }


    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = Tools.dip2px(ManagerMemberInfoActivity.this, 60);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;


            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                //如果是接单人那么就不能删除
                if (dataBeans.get(position).isHasDelete()) {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(ManagerMemberInfoActivity.this)
                            .setText("删除")
                            .setTextColor(Color.WHITE)
                            .setBackgroundColor(Color.parseColor("#FF5454"))
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
                }


                SwipeMenuItem addItem = new SwipeMenuItem(ManagerMemberInfoActivity.this)
                        .setText("管理")
                        .setTextColor(Color.WHITE)
                        .setBackgroundColor(Color.parseColor("#0A3FFF"))
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            }
        }
    };


    @Override
    public void getspOccupation(OccupationBean bean) {
        this.bean = bean;
    }


    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mMenuItemClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0 && dataBeans.get(position).isHasDelete()) {//删除
                    ReceivedConfirmDialog.newInstance("确认删除？")
                            .setConfirmListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //调用接口
                                    mPresenter.delMember(dataBeans.get(position).getUid());
                                }
                            }).show(ManagerMemberInfoActivity.this);

                } else {//管理
                    if (dataBeans != null) {
                        GroupMemberBean.DataBean viewModel = dataBeans.get(position);
                        mPresenter.getMemberInfo(viewModel.getUid());

                    }
                }

            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {

            }
        }
    };

}