package com.ming.sjll.My.company;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.config.PictureConfig;
import com.ming.sjll.Bean.GetInfoDateListBean;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.My.adapter.ShowcaseTagItemAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.MyUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyShowCaseGoodsBuyBinding;
import com.ming.sjll.databinding.MyShowCaseServiceBuyBinding;
import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.pay.HomePay;
import com.ming.sjll.project.manage.adapter.TimeUserTiemEditLineAdapter;
import com.ming.sjll.search.bean.ProjectListItem;
import com.ming.sjll.toolPage.DateFrame;
import com.ming.sjll.ui.AntoLineFlowLayoutManager;
import com.rey.material.app.BottomSheetDialog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 商品下单页面
 */
public class ShowcaseGoodsSelectTimeActivity extends BaseActivity {

    MyShowCaseGoodsBuyBinding binding;

//    时
    private static final String HOUR = "1";
//    天
    private static final String DAY = "2";
//    件
    private static final String SELL = "3";
//    件
    private static final String PIECE = "4";
//    套
    private static final String SET = "5";
//    张
    private static final String SHEET = "6";
//     报价
    private static final String OFFER = "7";

//    选择时
    String timeDay = "";
    String startTime = "";
    String endTime = "";
    String sendType = "";

//    选择天
    List<String> dateTime = new LinkedList<>();

    HttpParamsObject commParam = new HttpParamsObject();

    String unitSelectType = "";
    GoodsItem.DataBean.DataBeanX unitSelect;
    String goodsId;
    GoodsItem mData;

    String deliveryDate = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.my_show_case_goods_buy);
//        binding.dateFrame.setSelectedModel(SelectedModel.MULTIPLE);


//        setContentView(R.layout.project_manage_team);
        initView();
    }

    ProjectListItem projectDataList;

    private void initView() {

        event();
        getData();



    }
    private void event()
    {
        addSub();
        selectType();
        binding.toDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_release_project_date_frame);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                DateFrame ateFrame = bottomInterPasswordDialog.findViewById(R.id.dateFrame);
                ateFrame.setPointList(mData.getData().getDate_time());

                bottomInterPasswordDialog.setOnShowListener(new DialogInterface.OnShowListener(){
                    @Override
                    public void onShow(DialogInterface dialog)
                    {
                        //日期跳转
//                        Log.e(TAG,"11111111");
                        if(!deliveryDate.equals(""))
                        {
                            ateFrame.jumpDate(Tools.getDateformat(Long.parseLong(deliveryDate)));
                        }

                    }
                });

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
                        String l = Tools.getDateformatCn(Long.parseLong(Tools.getLongformat(date)));

                        binding.startDay.setText(l);
                        timeDay  = l;
                        tagEvent();
                        bottomInterPasswordDialog.hide();

                    }
                });

            }
        });

        binding.startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = true;
                if(timeDay.equals(""))
                {
                    ToastShow.s("请先选择日期");
                    return;
                }
                initTimePicker(startTime);
            }
        });
        binding.endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStartTime = false;
                if(timeDay.equals(""))
                {
                    ToastShow.s("请先选择日期");
                    return;
                }
                initTimePicker(endTime);

            }
        });
        binding.daysWrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowcaseJoinProjectDateActivity.class);
                intent.putExtra("dateList", (Serializable) dateTime);
                startActivityForResult(intent, 22);
            }
        });



//        去付款
        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                commParam.setParam("type",unitSelectType);
                commParam.setParam("goodsId",goodsId);
                commParam.setParam("sendType",sendType);
                commParam.setParam("num",binding.countInput.getText().toString());
                commParam.setParam("startTime",startTime);
                commParam.setParam("endTime",endTime);
                commParam.setParam("dateTime",dateTime);
                commParam.setParam("name",binding.name.getText().toString());
                commParam.setParam("street",binding.street.getText().toString());

                new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_BUY_GOODS, commParam, new CommonCallback() {
                    @Override
                    public void onSuccess(Object data, int code) {
                        super.onSuccess(data, code);
                        Bundle bundle = new Bundle();
                        bundle.putString("orderNo", data.toString());
                        Tools.jump(getActivity(), HomePay.class, bundle, false);

                    }
                });

//                            HttpParamsObject param = new HttpParamsObject();
//
//                            param.setParam("goodsId", mData.getData().getGoods_id());
//                        //        param.setParam("sendType", "");
//                            param.setParam("type", mData.getData().getType());
//                            param.setParam("num", binding.countInput.getText().toString());
//                            param.setParam("price", mData.getData().getPrice());
//                            List<String> temList = new LinkedList<>();
//                            for (int i = 0; i < mData.getData().getDateList().size(); i++) {
//                                param.setParam("dateList[" + i + "][start]", mData.getData().getDateList().get(i).getStart_time());
//                                param.setParam("dateList[" + i + "][end]", mData.getData().getDateList().get(i).getEnd_time());
//                                temList.add(mData.getData().getDateList().get(i).getDate_time());
//                            }
//                            param.setParam("dateTime", temList);
//
//                            new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_ADD_PROJECT_GOODS, param, new CommonCallback() {
//                                @Override
//                                public void onSuccessJson(String data, int code) {
//                                    Intent intent = new Intent();
//                                    setResult(ShowcaseServiceSelectTimeActivity.RESULT_OK, intent);
//                                    ToastShow.s("添加成功");
//                                    finish();
//                                }
//                            });
//
            }
        });

    }


    private boolean isStartTime;

    /**
     * 选择开始和结束时间
     * @param date
     */
    private void initTimePicker(String date) {//Dialog 模式下，在底部弹出

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (date != null && !date.equals("")) {
            calendar.setTime(new Date(Long.parseLong(date) * 1000));
        }

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(TimeEditActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                String hm = Tools.getTimeFormatHourMinute(date.getTime());
                String s = timeDay + " " + hm + ":00";
                String time =Tools.getLongformatTime( s );
                if (isStartTime) {
                    startTime  = time;
                    binding.startTime.setText(hm);
                } else {
                    endTime  = time;
                    binding.endTime.setText(hm);
                }
                initMoney();
//                timeUserTiemLineAdapter.notifyDataSetChanged();
//                Log.i("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .isCyclic(true)
                .setDate(calendar)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
        pvTime.show();
    }



    /**
     * 数量加减
     */
    private void addSub()
    {
        binding.countInput.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                //s:变化后的所有字符


            }

            public void beforeTextChanged(CharSequence s, int start, int count,

                                          int after) {
                // TODO Auto-generated method stub

                //s:变化前的所有字符； start:字符开始的位置； count:变化前的总字节数；after:变化后的字节数


            }

            public void onTextChanged(CharSequence s, int start, int before,

                                      int count) {
                // TODO Auto-generated method stub

                //S：变化后的所有字符；start：字符起始的位置；before: 变化之前的总字节数；count:变化后的字节数
                initMoney();

            }

        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  countStr = binding.countInput.getText().toString();
                if(countStr.equals(""))
                {
                    countStr = "0";
                }
                String count = Integer.parseInt(countStr) + 1 + "";
                binding.countInput.setText(count);
                initMoney();
            }
        });

        binding.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  countStr = binding.countInput.getText().toString();
                if(countStr.equals(""))
                {
                    countStr = "0";
                }
                int count = Integer.parseInt(countStr) - 1;
                if(count <= 0)
                {
                    return;
                }
                String s = count+"";
                binding.countInput.setText(s);
                initMoney();
            }
        });


    }


    /**
     * 服务类型选择
     */
    private void selectType() {


//            BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
//            bottomInterPasswordDialog.setContentView(R.layout.dialog_show_case_buy_service);
//            bottomInterPasswordDialog.inDuration(300);
//            bottomInterPasswordDialog.outDuration(200);
//
//            LinearLayout guide1menu = bottomInterPasswordDialog.findViewById(R.id.guide1menu);
//            LinearLayout guide2menu = bottomInterPasswordDialog.findViewById(R.id.guide2menu);
//            LinearLayout guide3menu = bottomInterPasswordDialog.findViewById(R.id.guide3menu);
//
//            if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"1"))
//            {
//                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
//            }
//            if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"2"))
//            {
//                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
//            }
//            if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"3"))
//            {
//                ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
//            }
            binding.guide1menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.guide1Wrap.setBackgroundColor(Color.parseColor("#F2F3F7"));
                    binding.guide1.setBackgroundResource(R.mipmap.btn_checked);

                    binding.guide2Wrap.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    binding.guide2HideWrap.setVisibility(View.GONE);
                    binding.guide2.setBackgroundResource(R.mipmap.btn_checked_un);

//                    binding.guide3Wrap.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    binding.guide3.setBackgroundResource(R.mipmap.btn_checked_un);
//                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"1"))
//                    {
//                        return;
//                    }
//                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked);
//                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_un);
//                    ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_un);
//                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"1"))
//                    {
//                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide1)).setBackgroundResource(R.mipmap.btn_checked_forbid);
//                    }
//                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"2"))
//                    {
//                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide2)).setBackgroundResource(R.mipmap.btn_checked_forbid);
//                    }
//                    if(!MyUtil.ListStringHasItem(mData.getData().getServiceType(),"3"))
//                    {
//                        ((ImageView) bottomInterPasswordDialog.findViewById(R.id.guide3)).setBackgroundResource(R.mipmap.btn_checked_forbid);
//                    }
//                    bottomInterPasswordDialog.findViewById(R.id.selectType1).setVisibility(View.VISIBLE);
//                    bottomInterPasswordDialog.findViewById(R.id.selectType2).setVisibility(View.GONE);
                }
            });
            binding.guide2menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.guide1Wrap.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    binding.guide1.setBackgroundResource(R.mipmap.btn_checked_un);

                    binding.guide2Wrap.setBackgroundColor(Color.parseColor("#F2F3F7"));
                    binding.guide2HideWrap.setVisibility(View.VISIBLE);
                    binding.guide2.setBackgroundResource(R.mipmap.btn_checked);

//                    binding.guide3Wrap.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    binding.guide3.setBackgroundResource(R.mipmap.btn_checked_un);
                }
            });
//            地图
            binding.addressWrap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.init(getContext());
                    Intent intent = new Intent(getContext(), MyTencentMap.class);
                    startActivityForResult(intent, PictureConfig.MAP_HEAD_REQUEST);
                }
            });

//            binding.guide3menu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    binding.guide1Wrap.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    binding.guide1.setBackgroundResource(R.mipmap.btn_checked_un);
//
//                    binding.guide2Wrap.setBackgroundColor(Color.parseColor("#FFFFFF"));
//                    binding.guide2HideWrap.setVisibility(View.GONE);
//                    binding.guide2.setBackgroundResource(R.mipmap.btn_checked_un);
//
//                    binding.guide3Wrap.setBackgroundColor(Color.parseColor("#F2F3F7"));
//                    binding.guide3.setBackgroundResource(R.mipmap.btn_checked);
//                }
//            });





//
//        HttpParamsObject param = new HttpParamsObject();
//
//        param.setParam("goodsId", mData.getData().getGoods_id());
////        param.setParam("sendType", "");
//        param.setParam("type", mData.getData().getType());
//        param.setParam("num", binding.countInput.getText().toString());
//        param.setParam("price", mData.getData().getPrice());
//        List<String> temList = new LinkedList<>();
//        for (int i = 0; i < mData.getData().getDateList().size(); i++) {
//            param.setParam("dateList[" + i + "][start]", mData.getData().getDateList().get(i).getStart_time());
//            param.setParam("dateList[" + i + "][end]", mData.getData().getDateList().get(i).getEnd_time());
//            temList.add(mData.getData().getDateList().get(i).getDate_time());
//        }
//        param.setParam("dateTime", temList);
//
//        new HttpUtil().sendRequest(Constant.PROJECT_MANAGE_ADD_PROJECT_GOODS, param, new CommonCallback() {
//            @Override
//            public void onSuccessJson(String data, int code) {
//                Intent intent = new Intent();
//                setResult(ShowcaseServiceSelectTimeActivity.RESULT_OK, intent);
//                ToastShow.s("添加成功");
//                finish();
//            }
//        });

    }

    /**
     * 获取数据和初始化数据
     */
    private void getData() {
        goodsId = getIntent().getStringExtra("goodsId");
        mData = (GoodsItem) getIntent().getSerializableExtra("DataBean");
        initData();
        initMoney();
        initGuideMenu();
    }

    /**
     * 初始化服务方式
     */
    private void initGuideMenu()
    {

      binding.guide1Wrap.setVisibility(View.GONE);
      binding.guide2Wrap.setVisibility(View.GONE);
//      binding.guide3Wrap.setVisibility(View.GONE);

        if(MyUtil.ListStringHasItem(mData.getData().getSendType(),"1"))
        {
            binding.guide1Wrap.setVisibility(View.VISIBLE);
            sendType = "1";
        }
        if(MyUtil.ListStringHasItem(mData.getData().getSendType(),"2"))
        {
            sendType = "2";
            binding.guide2Wrap.setVisibility(View.VISIBLE);
        }

    }

    private void initData() {

        mData.getData().setDateList(new LinkedList<>());
        new ImageHelper().displayBackgroundLoading(binding.image,
                Constant.BASE_IMAGE + mData.getData().getCover_img());
        binding.text1.setText(mData.getData().getTitle());
        binding.text2.setText(mData.getData().getDescribe());
        binding.text3.setText(mData.getData().getShop_name());
//
//        binding.selectTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ShowcaseServiceSelectTimeActivity.this, ShowcaseJoinProjectDateActivity.class);
//                intent.putExtra("dateList", (Serializable) mData.getData().getDate_time());
//                startActivityForResult(intent, 22);
//            }
//        });

        initTag();
        tagEvent();
    }



    ShowcaseTagItemAdapter adapter;


    /**
     * 初始化单位
     */
    private void initTag() {

        RecyclerView recyclerList = binding.recyclerList;
        AntoLineFlowLayoutManager layout = new AntoLineFlowLayoutManager(ShowcaseGoodsSelectTimeActivity.this, true);
        recyclerList.setLayoutManager(layout);
        adapter = new ShowcaseTagItemAdapter(mData.getData().getPaymentList());
        recyclerList.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < mData.getData().getPaymentList().size(); i++) {
                    mData.getData().getPaymentList().get(i).setIs_select(false);
                }
                mData.getData().getPaymentList().get(position).setIs_select(true);
                mData.getData().setType(mData.getData().getPaymentList().get(position).getType());
                unitSelect = mData.getData().getPaymentList().get(position);
                unitSelectType = mData.getData().getPaymentList().get(position).getType();
                tagEvent();
                adapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * 初始化计算价格
     */
    private void initMoney()
    {
        String  countStr = binding.countInput.getText().toString();
        int Days = 1;
        if(countStr.equals(""))
        {
            countStr = "0";
        }
        else if (unitSelectType.equals(DAY) ) {
            Days = dateTime.size();
        }
        else if (unitSelectType.equals(HOUR) ) {
            if(startTime.equals("") || startTime.equals(""))
            {
                binding.money1.setText("0");
                binding.money2.setText("0");
                return;
            }
            double price = 0;
            for (int i = 0; i < mData.getData().getPaymentList().size(); i++) {
                if(unitSelectType.equals(mData.getData().getPaymentList().get(i).getType())) {
                    price = Double.parseDouble(mData.getData().getPaymentList().get(i).getPrice());
                    break;
                }
            }

            loadingDailog.show();
            HttpParamsObject param = new HttpParamsObject();
            param.setParam("goodsId", goodsId);
            param.setParam("type", HOUR);
            param.setParam("num", countStr);
            param.setParam("price", price+"");
            param.setParam("dateTime[]", timeDay);
            param.setParam("dateList[0][start]", startTime);
            param.setParam("dateList[0][end]", endTime);
            new HttpUtil().sendRequest(Constant.ORDER_ORDER_MONEY, param, new CommonCallback() {
                @Override
                public void onSuccess(Object data, int code) {
                    super.onSuccess(data, code);
                    binding.money1.setText(data.toString());
                    binding.money2.setText(data.toString());
                }

                @Override
                public void onFinal() {
                    super.onFinal();
                    loadingDailog.hide();
                }
            });

            return;
        }

        int count = Integer.parseInt(countStr);
        double price = 0;
        double money = 0;
        for (int i = 0; i < mData.getData().getPaymentList().size(); i++) {
            if(unitSelectType.equals(mData.getData().getPaymentList().get(i).getType())) {
                price = Double.parseDouble(mData.getData().getPaymentList().get(i).getPrice());
                break;
            }
        }
        if(count <= 0 || price <= 0 || Days <= 0)
        {
            money = 0;
        }
        else
        {
            money = count * price * Days;
        }
        String s  = money+"";
        binding.money1.setText(s);
        binding.money2.setText(s);

    }

    private void tagEvent() {


//        binding.selectCountWrap.setVisibility(View.GONE);
//        binding.selectQuoteWrap.setVisibility(View.GONE);
//
//        binding.dateTitle.setVisibility(View.GONE);
//        binding.selectTime.setVisibility(View.GONE);
//        binding.dateTip.setVisibility(View.GONE);
//        binding.dateListView.setVisibility(View.GONE);

        binding.daysWrap.setVisibility(View.GONE);
        binding.timesWrap.setVisibility(View.GONE);
        binding.selectDayWrap.setVisibility(View.GONE);

        if(unitSelectType.equals(HOUR))
        {
            binding.selectDayWrap.setVisibility(View.VISIBLE);
            binding.timesWrap.setVisibility(View.VISIBLE);
            String s = timeDay.equals("") ? "请选择日期" : timeDay;
            binding.toDay.setText(s);


            String s2 = startTime.equals("") ? "请选择" : Tools.getDateformatHourMinute(Long.parseLong(startTime));
            binding.startTime.setText(s2);

            String s3 = endTime.equals("") ? "请选择" : Tools.getDateformatHourMinute(Long.parseLong(endTime));
            binding.endTime.setText(s3);
        }
        else if (unitSelectType.equals(DAY)) {
            binding.selectDayWrap.setVisibility(View.VISIBLE);
            binding.daysWrap.setVisibility(View.VISIBLE);
            if(dateTime.size() == 0)
            {
                binding.startDay.setText("请选择日期");
                binding.endDay.setText("请选择日期");
            }
            else if(dateTime.size() == 1)
            {
                binding.startDay.setText(dateTime.get(0));
                binding.endDay.setText(dateTime.get(0));
            }
            else
            {
                binding.startDay.setText(dateTime.get(0));
                binding.endDay.setText(dateTime.get(dateTime.size() -1 ));
            }
        }

//        else {
//            binding.initHide.setVisibility(View.VISIBLE);
//            binding.selectCountWrap.setVisibility(View.VISIBLE);
//          if (unitSelectType.equals(PIECE)) {
//                binding.countInput.setText(mData.getData().getValue());
//                binding.unit.setText("件");
//            } else if (unitSelectType.equals(SET)) {
//                binding.countInput.setText(mData.getData().getValue());
//                binding.unit.setText("套");
//            } else if (unitSelectType.equals(SHEET)) {
//                binding.countInput.setText(mData.getData().getValue());
//                binding.unit.setText("张");
//            }
//        }

        initMoney();

    }

//    TimeUserTiemEditLineAdapter timeUserTiemLineAdapter;

//    private void changeDate() {
//        if (!unitSelectType.equals("1")) {
//            binding.dateListView.setVisibility(View.GONE);
//            return;
//        }
//        binding.dateListView.setVisibility(View.VISIBLE);
//        binding.dateListView.setLayoutManager(new LinearLayoutManager(this));
//        timeUserTiemLineAdapter = new TimeUserTiemEditLineAdapter(mData.getData().getDateList(), this);
//        binding.dateListView.setAdapter(timeUserTiemLineAdapter);
//
//        timeUserTiemLineAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if (view.getId() == R.id.startTime) {
//                    selectPoint = position;
//                    isStartTime = true;
//                    initTimePicker(mData.getData().getDateList().get(position).getStart_time());
//                } else if (view.getId() == R.id.endTime) {
//                    selectPoint = position;
//                    isStartTime = false;
//                    initTimePicker(mData.getData().getDateList().get(position).getEnd_time());
//                }
//
//            }
//        });
//
//    }

//    private void initTimePicker(String date) {//Dialog 模式下，在底部弹出
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        if (date != null && !date.equals("")) {
//            calendar.setTime(new Date(Long.parseLong(date) * 1000));
//        }
//
//        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {
////                Toast.makeText(TimeEditActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
//                String hm = Tools.getTimeFormatHourMinute(date.getTime());
//                String s = mData.getData().getDateList().get(selectPoint).getDate_time() + " " + hm + ":00";
//                String time =Tools.getLongformatTime( s );
//                if (isStartTime) {
//                    mData.getData().getDateList().get(selectPoint).setStart_time(time);
//                } else {
//                    mData.getData().getDateList().get(selectPoint).setEnd_time(time);
//                }
//                timeUserTiemLineAdapter.notifyDataSetChanged();
//                Log.i("pvTime", "onTimeSelect");
//
//            }
//        })
//                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
//                    @Override
//                    public void onTimeSelectChanged(Date date) {
//                        Log.i("pvTime", "onTimeSelectChanged");
//                    }
//                })
//                .setType(new boolean[]{false, false, false, true, true, false})
//                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
//                .addOnCancelClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Log.i("pvTime", "onCancelClickListener");
//                    }
//                })
//                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
//                .setLineSpacingMultiplier(2.0f)
//                .isAlphaGradient(true)
//                .isCyclic(true)
//                .setDate(calendar)
//                .build();
//
//        Dialog mDialog = pvTime.getDialog();
//        if (mDialog != null) {
//
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    Gravity.BOTTOM);
//
//            params.leftMargin = 0;
//            params.rightMargin = 0;
//            pvTime.getDialogContainerLayout().setLayoutParams(params);
//
//            Window dialogWindow = mDialog.getWindow();
//            if (dialogWindow != null) {
//                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
//                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
//                dialogWindow.setDimAmount(0.3f);
//            }
//        }
//        pvTime.show();
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.MAP_HEAD_REQUEST) {
                double longitude = data.getDoubleExtra("longitude", 0);
                double latitude = data.getDoubleExtra("latitude", 0);
                String area_code = data.getStringExtra("area_code");
                String title = data.getStringExtra("title");
                String adname = data.getStringExtra("address");
                String province = data.getStringExtra("province");
                String city = data.getStringExtra("city");
                String district = data.getStringExtra("district");

                commParam.setParam("area_code", area_code);
                commParam.setParam("provinces", province);
                commParam.setParam("city", city);
                commParam.setParam("county", district);
                commParam.setParam("address_title", title);
                commParam.setParam("address", adname);
                commParam.setParam("meridian", longitude + "");
                commParam.setParam("weft", latitude + "");

                binding.addressTip.setText(title);
            }
            else if (requestCode == 22) {
                List<String> dateList = new LinkedList<>();
//                mData.getData().setDateList(new LinkedList<>());
                String startTime = data.getStringExtra("startTime");
                String endTime = data.getStringExtra("endTime");
                if(startTime == null || startTime.equals("") || endTime == null || endTime.equals(""))
                {
                    return;
                }
                long s = Long.parseLong(Tools.getLongformat( startTime));
                long e = Long.parseLong(Tools.getLongformat( endTime));
                binding.startDay.setText(Tools.getDateformatCn(s));
                binding.endDay.setText(Tools.getDateformatCn(e));
                if(startTime.equals(endTime))
                {
                    dateTime.add(endTime);

                    initMoney();
                    return;
                }
//                String hm = Tools.getTimeFormatHourMinute(date.getTime());

                dateList =  betweenDays(s * 1000,e* 1000);

                dateTime.addAll(dateList);
                initMoney();
//                for (int i = 0; i < dateList.size(); i++) {
//
//                    GetInfoDateListBean.DataBean.DataBeanY date = new GetInfoDateListBean.DataBean.DataBeanY();
//                    date.setDate_time(dateList.get(i));
//                    mData.getData().getDateList().add(date);
//
//
//                }
//                binding.startTime.setText(startTime);
//                binding.endTime.setText(endTime);

//                changeDate();
            }
        }
    }


    private long ONE_DAY_MS = 24 * 60 * 60 * 1000;

    /**
     * 计算两个日期之间的日期
     *
     * @param startTime
     * @param endTime
     */
    private List<String> betweenDays(long startTime, long endTime) {

        List<String> list = new LinkedList<>();
        Date date_start = new Date(startTime);
        Date date_end = new Date(endTime);

        //计算日期从开始时间于结束时间的0时计算
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(date_start);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(date_end);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        int s = (int) ((toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) / (ONE_DAY_MS));
        if (s > 0) {
            for (int i = 0; i <= s; i++) {
                long todayDate = fromCalendar.getTimeInMillis() + i * ONE_DAY_MS;
                /**
                 * yyyy-MM-dd E :2012-09-01
                 */
                Log.i("打印日期", getCustonFormatTime(todayDate, "yyyy-MM-dd"));

                list.add(getCustonFormatTime(todayDate, "yyyy-MM-dd"));
                //取出多少号  添加集合  九宫格展示


            }
        }
//        else {//此时在同一天之内
//            Log.i("打印日期",getCustonFormatTime(startTime,"yyyy-MM-dd"));
//        }
        return list;
    }

    /**
     * 格式化传入的时间
     *
     * @param time      需要格式化的时间
     * @param formatStr 格式化的格式
     * @return
     */
    public String getCustonFormatTime(long time, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        Date d1 = new Date(time);
        return format.format(d1);
    }


}
