package com.ming.sjll.Message.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.R;
import com.ming.sjll.base.fragment.MvpFragment;
import com.ming.sjll.base.utils.ImageLoaderUtil;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.Message.dialog.ChangeProjectDialog;
import com.ming.sjll.Message.presenter.ProjectListPresenter;
import com.ming.sjll.Message.view.MessageChangeProjectView;
import com.ming.sjll.Message.viewmodel.MessageChangeProjectBean;

import java.util.List;

import butterknife.BindView;


public class ProjectListFragment extends MvpFragment<MessageChangeProjectView, ProjectListPresenter> implements MessageChangeProjectView {


    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    public static final String TYPE = "type";
    private ChangeProjectDialog.onClickProjectListener onClickProjectListener;

    public static ProjectListFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(TYPE, type);
        ProjectListFragment fragment = new ProjectListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragemt_comprehensive);
    }


    public ProjectListFragment setItemClick(ChangeProjectDialog.onClickProjectListener onClickProjectListener) {
        this.onClickProjectListener = onClickProjectListener;
        return this;
    }

    @Override
    public void onShowData(MessageChangeProjectBean viewModel) {
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        Comprehendapter adapter = new Comprehendapter(viewModel.getData().getData(), onClickProjectListener);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.card) {
                    MessageChangeProjectBean.DataBeanX.DataBean dataBean = viewModel.getData().getData().get(position);
                    int id = dataBean.getId();
                    String projectName = dataBean.getDemand();
                    onClickProjectListener.onClickProject(mPresenter.getType(), id, projectName, null);
                }
            }
        });
        recyclerview.setAdapter(adapter);
    }

    public class Comprehendapter extends BaseQuickAdapter<MessageChangeProjectBean.DataBeanX.DataBean, BaseViewHolder> {
        ChangeProjectDialog.onClickProjectListener onClickProjectListener;

        public Comprehendapter(@Nullable List<MessageChangeProjectBean.DataBeanX.DataBean> data, ChangeProjectDialog.onClickProjectListener onClickProjectListener) {
            super(R.layout.comprehend_item, data);
            this.onClickProjectListener = onClickProjectListener;
        }

        public Comprehendapter(@Nullable List<MessageChangeProjectBean.DataBeanX.DataBean> data) {
            super(R.layout.comprehend_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, MessageChangeProjectBean.DataBeanX.DataBean dataBean) {
            baseViewHolder.setText(R.id.tv_title, dataBean.getDemand());
            ImageLoaderUtil.display((ImageView) baseViewHolder.getView(R.id.iv_img), dataBean.getBackground_image());
            baseViewHolder.setText(R.id.tv_city, dataBean.getArea_name());
            baseViewHolder.setText(R.id.tv_timer, Tools.getDateformat(dataBean.getClosing_time()));
            baseViewHolder.setText(R.id.tv_pinpai, dataBean.getBrand());
            baseViewHolder.setText(R.id.tv_price, "¥" + dataBean.getBudget());
            if (dataBean.getTags() == null || dataBean.getTags().isEmpty()) {
                baseViewHolder.setVisible(R.id.tv_tag, false);
            } else {
                baseViewHolder.setVisible(R.id.tv_tag, true);
                List<String> tags = dataBean.getTags();

                if (tags != null && !tags.isEmpty()) {
                    baseViewHolder.setText(R.id.tv_tag, tags.get(0));
                }
            }
            baseViewHolder.addOnClickListener(R.id.card);


        }

    }
}
