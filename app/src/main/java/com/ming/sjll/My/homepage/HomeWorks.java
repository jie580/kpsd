package com.ming.sjll.My.homepage;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.GoodsListItem;
import com.ming.sjll.Bean.ScheduleIListItem;
import com.ming.sjll.Bean.StringListBean;
import com.ming.sjll.My.Myfragemt;
import com.ming.sjll.My.adapter.GoodsListAdapter;
import com.ming.sjll.My.adapter.ScheduleListAdapter;
import com.ming.sjll.My.personage.Works;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.project.dialog.ProjectDate;
import com.ming.sjll.toolPage.DateFrame;
import com.rey.material.app.BottomSheetDialog;

/**
 * 主页-个人-工作
 */
public class HomeWorks extends BaseFragment {


    public int point;
    public String userId;



    LinearLayout monthWrap;
    //    GetInfoBeans userInfoList;
    RecyclerView recyclerList;

    private int lastDate = -1;

    public static HomeWorks newInstance() {
        return new HomeWorks();
    }


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.my_works);
        initView();
//        event();
    }


    private void initView()
    {

        ((HomepageActivity) getActivity()).setChildObjectForPosition(this,point);
//        linearLayout  = (LinearLayout) findViewById(R.id.linearLayout);
        dateLayout  = (LinearLayout) findViewById(R.id.dateLayout);
        month =(TextView)findViewById(R.id.month);
        recyclerList = (RecyclerView)findViewById(R.id.recyclerList);
        monthWrap  = (LinearLayout) findViewById(R.id.monthWrap);

        initDate();
        getProjectList();
        event();
    }


    private void showDateWindows(StringListBean scheduleList)
    {
        ProjectDate p = new ProjectDate(getContext());

        BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(p.getContext());
        bottomInterPasswordDialog.setContentView(R.layout.dialog_release_project_date_frame);
        bottomInterPasswordDialog.inDuration(300);
        bottomInterPasswordDialog.outDuration(200);
//        bottomInterPasswordDialog.inInterpolator(new BounceInterpolator());
//        bottomInterPasswordDialog.outInterpolator(new AnticipateInterpolator());
        DateFrame ateFrame = bottomInterPasswordDialog.findViewById(R.id.dateFrame);

        bottomInterPasswordDialog.setOnShowListener(new DialogInterface.OnShowListener(){
            @Override
            public void onShow(DialogInterface dialog)
            {
                ateFrame.jumpDate(nowYear+"-"+nowMonth+"-"+nowDay);
            }
        });
        ateFrame. setInitializeFormerlyDate();
        ateFrame.setPointList(scheduleList.getData());
        bottomInterPasswordDialog.show();
        Button cancel = bottomInterPasswordDialog.findViewById(R.id.cancel);
        View save = bottomInterPasswordDialog.findViewById(R.id.save);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bottomInterPasswordDialog.hide();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String date = ateFrame.getCheckedDateString();
                if(date.equals(""))
                {
                    ToastShow.s("请选择日期");
                    return;
                }
                int startYear = 0;
                int startMoth = 0;
                int startDay = 0;
                try {
                    String[] strings = date.split("-");
                    startYear = Integer.valueOf(strings[0]);
                    startMoth = Integer.valueOf(strings[1]);
                    startDay = Integer.valueOf(strings[2]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                initDate(startYear,startMoth,startDay);
                bottomInterPasswordDialog.hide();

            }
        });
    }
    private void event()
    {
        /**
         * 弹出日历选择
         */
        monthWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //        获取账号类型
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("user_id",userId);
                new HttpUtil().sendRequest(Constant.MEMBER_SCHEDULE_DATE, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        StringListBean scheduleList = new Gson().fromJson(data, StringListBean.class);
                        showDateWindows(scheduleList);
                    }
                });
            }
        });
    }

    LinearLayout dateLayout;
    HorizontalScrollView dateHorizontalScrollView;
    TextView month;
    int nowMonth,nowYear,nowDay;



    /**
     * 获取员工排期，初始化时间
     */
    private void getScheduleDate()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("user_id",userId);
        new HttpUtil().sendRequest(Constant.MEMBER_SCHEDULE_DATE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                StringListBean  scheduleList = new Gson().fromJson(data, StringListBean.class);
                for (int i = 0; i < dateLayout.getChildCount(); i++) {

                    for (int j = 0; j < scheduleList.getData().size(); j++) {
                        String  simple = "0";
                        String  simple2 = "";
                        try {
                            //                   是否是否相同
                            simple = Tools.dateToStamp(nowYear+"-"+nowMonth+"-"+(i+1),"yyyy-MM-dd" );
                            simple2 = Tools.dateToStamp(scheduleList.getData().get(j),"yyyy-MM-dd" );
                        }catch (Exception e)
                        {
                            Log.e("Exception",e.getMessage());
                        }

                        if(simple.equals(simple2))
                        {
                            dateLayout.getChildAt(i).findViewById(R.id.point).setVisibility(View.VISIBLE);
                        }

                    }

                }
            }
        });
    }

    private void initDate ()
    {
        nowMonth = Tools.getMonth();
        nowYear= Tools.getYear();
        nowDay = Tools.getDay();
        initDate(nowYear,nowMonth,nowDay);

    }

    private void initDate(int y, int m, int  d)
    {
        if(dateLayout.getChildCount() > 0)
        {
            dateLayout.removeAllViews();
        }

        nowMonth = m;
        nowYear= y;
        nowDay = d;

        dateHorizontalScrollView = (HorizontalScrollView)findViewById(R.id.dateHorizontalScrollView);
        month.setText(m+" 月");
        int dateCount = Tools.getMonthOfDay(y,m);
        for (int i = 1 ; i <= dateCount; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.my_schedule_date_text, null);
            view.setTag(i);
            TextView date = (TextView) view.findViewById(R.id.date);

            if(i <= 9)
            {
                date.setText("0"+i);
            }
            else
            {
                date.setText(i+"");
            }

//            if(i == 10 || i == 11 || i == 5)
//            {
//                ImageView point = (ImageView) view.findViewById(R.id.point);
//                point.setVisibility(View.VISIBLE);
//            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int indexOf = (int) v.getTag();
                    clickDate(indexOf);

                }
            });
            dateLayout.addView(view);;

        }

        clickDate(nowDay);
        getScheduleDate();

        //计算屏幕的宽度
//            WindowManager wm1 = this.getWindowManager();
//            int screenWidth = wm1.getDefaultDisplay().getWidth();
//        int screenWidth = dateLayout.getWidth();
//        int rb_px = (int) dateLayout.getChildAt(20).getX() + dateLayout.getChildAt(20).getWidth() / 2;
//        dateHorizontalScrollView.scrollTo(rb_px - screenWidth / 2, 0);

//        int scrollViewWidth = dateHorizontalScrollView.getWidth();
//        int rb_px = (int)dateLayout.getChildAt(20).getX() + dateLayout.getChildAt(20).getWidth() / 2;
//        dateHorizontalScrollView.scrollTo( rb_px - scrollViewWidth / 2 , 0);




    }

    private void clickDate(int day)
    {
        int indexOf = day - 1;
        dateLayout.post(new Runnable() {
            @Override
            public void run() {
                if(lastDate  >= 0)
                {
                    View lastView = dateLayout.getChildAt(lastDate);
                    ((TextView)lastView.findViewById(R.id.date)).setTextColor(Color.parseColor("#D7DAE5"));
                }
                lastDate = indexOf;
                View view = dateLayout.getChildAt(lastDate);
                ((TextView)view.findViewById(R.id.date)).setTextColor(Color.parseColor("#80B5FF"));

            }
        });

        dateHorizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                int scrollViewWidth = dateHorizontalScrollView.getWidth();
                int rb_px = (int)dateLayout.getChildAt(indexOf).getX() + dateLayout.getChildAt(indexOf).getWidth() / 2;
                dateHorizontalScrollView.scrollTo( rb_px - scrollViewWidth / 2 , 0);
            }
        });

        nowDay = day;
        /**
         * 刷新排期数据
         */
        getProjectList();



    }


    private void getProjectList()
    {
        HttpParamsObject param = new HttpParamsObject();
        param.setParam("user_id",userId);
        param.setParam("date_time", nowYear+"-"+nowMonth+"-"+nowDay);
        new HttpUtil().sendRequest(Constant.MEMBER_MEMBER_SCHEDULE, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                ScheduleIListItem scheduleList = new Gson().fromJson(data, ScheduleIListItem.class);
                recyclerList.setLayoutManager(new LinearLayoutManager(getActivity()));
                ScheduleListAdapter adapter =  new ScheduleListAdapter(scheduleList.getData());
                recyclerList.setAdapter(adapter);
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        ToastShow.s("点击项目");
                    }
                });
            }
        });

    }


}
