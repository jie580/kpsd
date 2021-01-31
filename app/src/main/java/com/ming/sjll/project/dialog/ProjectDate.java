package com.ming.sjll.project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ming.sjll.R;
import com.ming.sjll.toolPage.DateFrame;
import com.ming.sjll.toolPage.LoadingDailog;
import com.necer.calendar.BaseCalendar;
import com.necer.enumeration.SelectedModel;

import java.time.LocalDate;
import java.util.List;

public class ProjectDate extends Dialog {



    private Context context;
    View view;
    TextView msgText;
    LoadingDailog loadingDailog;

    public ProjectDate(Context context) {
        super(context, R.style.ToolPageLoadingDailog);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
//            View view=inflater.inflate(R.layout.dialog_loading,null);
        view=inflater.inflate(R.layout.dialog_release_project_date_frame,null);

//        loadingDailog=new LoadingDailog(context,R.style.ToolPageLoadingDailog);
//             msgText= (TextView) view.findViewById(R.id.tipTextView);
//            if(isShowMessage){
//                msgText.setText(message);
//            }else{
//                msgText.setVisibility(View.GONE);
//            }
        this.setContentView(view);
        initShow();
//        msgText= (TextView) view.findViewById(R.id.tipTextView);
    }

    public ProjectDate(Context context, int themeResId) {
        super(context, themeResId);
    }

    private Fragment[] fragmentList;

    private void initShow()
    {
//
//                DateFrame df = DateFrame.newInstance(SelectedModel.SINGLE_UNSELECTED,null, new DateFrame.Listener(){
//
//                    @Override
//                    public void onCalendarChangedListener(BaseCalendar baseCalendar, int year, int month, LocalDate localDate) {
//                        super.onCalendarChangedListener(baseCalendar, year, month, localDate);
//                    }
//
//                    @Override
//                    public void setOnCalendarMultipleChangedListener(BaseCalendar baseCalendar, int year, int month, List<LocalDate> currPagerCheckedList, List<LocalDate> totalCheckedList) {
//                        super.setOnCalendarMultipleChangedListener(baseCalendar, year, month, currPagerCheckedList, totalCheckedList);
//                    }
//                });
    }




}