package com.ming.sjll.My.company;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.config.PictureConfig;
import com.maning.calendarlibrary.listeners.OnCalendarRangeChooseListener;
import com.maning.calendarlibrary.model.MNCalendarVerticalConfig;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.My.adapter.ShowcaseTagItemAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.ProjectManageTimeEditActivityBinding;
import com.ming.sjll.databinding.ProjectManageTimeEditBinding;
import com.ming.sjll.project.manage.TeamFragment;
import com.ming.sjll.project.manage.TimeFragment;
import com.ming.sjll.project.manage.adapter.TimeUserTiemEditLineAdapter;
import com.ming.sjll.search.adapter.ProjectListItemAdapter;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;
import com.ming.sjll.ui.PagedListView;
import com.rey.material.app.BottomSheetDialog;

import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 项目管理
 */
public class ShowcaseJoinProjectDateActivity extends BaseActivity {

    ProjectManageTimeEditActivityBinding binding;


    String startTime;
    String endTime;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.project_manage_time_edit_activity);
//        binding.dateFrame.setSelectedModel(SelectedModel.MULTIPLE);


        binding.titleBar.titleBarIvRight.setVisibility(View.VISIBLE);
        binding.titleBar.titleBarIvRight.setBackground(getResources().getDrawable(R.mipmap.btn_save_top9));

        binding.titleBar.titleBarIvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("startTime",startTime);
                intent.putExtra("endTime",endTime);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });

//        setContentView(R.layout.project_manage_team);
        initView();
    }


    private void initView() {

        List<String> mData = (List<String>) getIntent().getSerializableExtra("dateList");
        MNCalendarVerticalConfig mnCalendarVerticalConfig = new MNCalendarVerticalConfig.Builder()
                .setMnCalendar_showWeek(true)                   //是否显示星期栏
                .setMnCalendar_showLunar(false)                  //是否显示阴历
                .setMnCalendar_titleFormat("yyyy-MM")           //每个月的标题样式
                .setMnCalendar_colorRangeBg("#80B5FF")        //区间中间的背景颜色
                .setMnCalendar_colorRangeText("#FFFFFF")        //区间文字的颜色
                .setMnCalendar_colorStartAndEndBg("#0A3FFF")    //开始结束的背景颜色
                .setMnCalendar_countMonth(12)                    //显示多少月(默认6个月)
                .setMnCalendar_dateList(mData)
                .build();
        binding.mnCalendarVertical.setConfig(mnCalendarVerticalConfig);

        binding.mnCalendarVertical.setOnCalendarRangeChooseListener(new OnCalendarRangeChooseListener() {
            @Override
            public void onRangeDate(Date startDate, Date endDate) {
                startTime = sdf.format(startDate);
                endTime = sdf.format(endDate);
            }
        });
    }


}
