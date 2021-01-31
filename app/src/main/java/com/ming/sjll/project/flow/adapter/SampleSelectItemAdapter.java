package com.ming.sjll.project.flow.adapter;


import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.ProjectFlowWorkSelectBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.utils.ImageHelper;

import java.util.List;

public class SampleSelectItemAdapter extends BaseQuickAdapter<ProjectFlowWorkSelectBean.DataBean, BaseViewHolder> {



    Activity mActivity;
    String projectId;
    String showType;
    public SampleSelectItemAdapter(@Nullable List<ProjectFlowWorkSelectBean.DataBean> data, Activity mActivity, String projectId,String showType ) {
        super(R.layout.project_flow_select_sample_item, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
        this.showType = showType;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, ProjectFlowWorkSelectBean.DataBean dataBean) {

        baseViewHolder.getView(R.id.wrap).setVisibility(View.VISIBLE);
        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.imageItem),
                Constant.BASE_IMAGE + dataBean.getImg());
        ((ImageView)baseViewHolder.getView(R.id.checkSelect)).setBackgroundResource(R.mipmap.btn_checked_un);
        if(showType.equals("4"))
        {
            if(dataBean.getIs_select().equals("1") )
            {
                if(dataBean.getIs_sample().equals("1"))
                {
                    ((ImageView)baseViewHolder.getView(R.id.checkSelect)).setBackgroundResource(R.mipmap.btn_checked_yellow);
                }
                else
                {
                    ((ImageView)baseViewHolder.getView(R.id.checkSelect)).setBackgroundResource(R.mipmap.btn_checked_un);
                }
            }
            else
            {
                baseViewHolder.getView(R.id.wrap).setVisibility(View.GONE);
            }
        }
        else
        {
            if(dataBean.getIs_select().equals("1") )
            {
                ((ImageView)baseViewHolder.getView(R.id.checkSelect)).setBackgroundResource(R.mipmap.btn_checked);
            }
            else
            {
                ((ImageView)baseViewHolder.getView(R.id.checkSelect)).setBackgroundResource(R.mipmap.btn_checked_un);
            }
            if(showType.equals("2") && !dataBean.getIs_select().equals("1"))
            {
                baseViewHolder.getView(R.id.wrap).setVisibility(View.GONE);
            }
            else if(showType.equals("3") && dataBean.getIs_select().equals("1"))
            {
                baseViewHolder.getView(R.id.wrap).setVisibility(View.GONE);
            }
        }


        baseViewHolder.addOnClickListener(R.id.wrap);
        baseViewHolder.addOnClickListener(R.id.checkSelect);

    }

}
