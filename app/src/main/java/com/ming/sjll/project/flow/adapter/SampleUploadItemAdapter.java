package com.ming.sjll.project.flow.adapter;


import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.project.manage.adapter.TimeUserTiemLineAdapter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SampleUploadItemAdapter extends BaseQuickAdapter<GetInfoBean.DataBean, BaseViewHolder> {



    Activity mActivity;
    String projectId;
    public SampleUploadItemAdapter(@Nullable List<GetInfoBean.DataBean> data, Activity mActivity, String projectId ) {
        super(R.layout.project_flow_upload_item, data);
        this.mActivity = mActivity;
        this.projectId = projectId;
    }



    @Override
    protected void convert(BaseViewHolder baseViewHolder, GetInfoBean.DataBean dataBean) {

        new ImageHelper().displayBackgroundLoading(baseViewHolder.getView(R.id.headImage),
                Constant.BASE_IMAGE + dataBean.getDefault_avatar());

        baseViewHolder.setText(R.id.name, dataBean.getName());
        baseViewHolder.setText(R.id.occupation, dataBean.getOccupation());

        baseViewHolder.addOnClickListener(R.id.openChar);

    }

}
