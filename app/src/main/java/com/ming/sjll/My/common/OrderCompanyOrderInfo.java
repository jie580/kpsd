package com.ming.sjll.My.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.Bean.ProjectGoodsListBean;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.Bean.ScheduleItemBean;
import com.ming.sjll.My.adapter.OrderCompanyGoodsListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.databinding.MyOrderCompanyGoodsInfoBinding;
import com.ming.sjll.databinding.MyOrderCompanyOrderInfoBinding;
import com.ming.sjll.project.manage.adapter.TimeUserTiemEditLineAdapter;

/**
 * 个人中心-订单-商品
 */
public class OrderCompanyOrderInfo extends BaseActivity {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private ProjectGoodsListBean dataList;
    private OrderCompanyGoodsListAdapter listAdapter;
    RecyclerView listView;
    MyOrderCompanyOrderInfoBinding binding;



    public static OrderCompanyOrderInfo newInstance() {
        return new OrderCompanyOrderInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_order_company_goods_info);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_order_company_order_info, null, false);
        setContentView(binding.getRoot());
        initView();

    }


    private void initView() {
        getData();
    }

    private void getData(){
        String type = getIntent().getStringExtra("type");
        if(type == null)
        {
            ProjectOrderListBean.DataBeanX dataBean = (ProjectOrderListBean.DataBeanX)getIntent().getSerializableExtra("data");

            binding.startTime.setText(dataBean.getStart_time());
            binding.endTime.setText(dataBean.getEnd_time());

//            if(dataBean.getType().equals("1"))//按时
//            {
            binding.dateListView.setVisibility(View.VISIBLE);
            binding.dateListView.setLayoutManager(new LinearLayoutManager(this));
            TimeUserTiemEditLineAdapter timeUserTiemLineAdapter = new TimeUserTiemEditLineAdapter(dataBean.getDateList(), this, "","goods");
            binding.dateListView.setAdapter(timeUserTiemLineAdapter);
//            }
//        }

            binding.demandTip.setText(dataBean.getDemand());
            binding.brandTip.setText(dataBean.getBrand());
            binding.serviceTypeTip.setText(dataBean.getServiceTypeStr());
//        binding.address.setText(dataBean.getSendTypeStr().equals("1") ? "寄送地址"  : "取件地址");
//
//        binding.phoneTip.setText(dataBean.getPhone());
//        binding.addressTip.setText(dataBean.getAddress());
            binding.orderTip.setText(dataBean.getOrder_no());

            String s = dataBean.getProvinces() + "-" + dataBean.getCity() + "-" + dataBean.getCounty();
            binding.addressTip.setText(s);
            binding.addressTip2.setText(dataBean.getAddress());

        }
        else if(type.equals("project"))
        {
            ScheduleItemBean.DataBean dataBean = (ScheduleItemBean.DataBean)getIntent().getSerializableExtra("data");

//            String st = Tools.getDateformatDay(Long.parseLong(schedule.getData().getDateList().get(0).getStart_time()));

            if(dataBean.getDateList() == null || dataBean.getDateList().size() == 0)
            {
                binding.startTime.setText("暂无");
                binding.endTime.setText("暂无");
            }
            else if(dataBean.getDateList().size() == 1)
            {
//                startTimeDay.setText(st);
//                endTimeDay.setText(st);
                String std = Tools.getDateformat3(Long.parseLong(dataBean.getDateList().get(0).getStart_time()),"MM月dd日");
                binding.startTime.setText(std);
            binding.endTime.setText(std);
            }
            else if(dataBean.getDateList().size()>1)
            {
                String std = Tools.getDateformat3(Long.parseLong(dataBean.getDateList().get(0).getStart_time()),"MM月dd日");
//                String et = Tools.getDateformatDay(Long.parseLong(dataBean.getDateList().get(dataBean.getDateList().size()-1).getStart_time()));
                String etd = Tools.getDateformat3(Long.parseLong(dataBean.getDateList().get(dataBean.getDateList().size()-1).getStart_time()),"MM月dd日");
//                startTimeDay.setText(st);
//                endTimeDay.setText(et);

                binding.startTime.setText(std);
                binding.endTime.setText(etd);
            }

//            binding.startTime.setText(dataBean.getStart_time());
//            binding.endTime.setText(dataBean.getEnd_time());

//            if(dataBean.getType().equals("1"))//按时
//            {
            binding.dateListView.setVisibility(View.VISIBLE);
            binding.dateListView.setLayoutManager(new LinearLayoutManager(this));
            TimeUserTiemEditLineAdapter timeUserTiemLineAdapter = new TimeUserTiemEditLineAdapter(dataBean.getDateList(), this, "","goods");
            binding.dateListView.setAdapter(timeUserTiemLineAdapter);
//            }
//        }

            binding.demandTip.setText(dataBean.getDemand());
            binding.brandTip.setText(dataBean.getBrand());
            binding.serviceTypeWrap.setVisibility(View.GONE);
//        binding.address.setText(dataBean.getSendTypeStr().equals("1") ? "寄送地址"  : "取件地址");
//
//        binding.phoneTip.setText(dataBean.getPhone());
//        binding.addressTip.setText(dataBean.getAddress());
            binding.orderWrap.setVisibility(View.GONE);

            String s = dataBean.getProvinces() + "-" + dataBean.getCity() + "-" + dataBean.getCounty();
            binding.addressTip.setText(s);
            binding.addressTip2.setText(dataBean.getAddress());
        }
//
////        binding.timeWrap.setVisibility(View.GONE);
////        if(!dataBean.getType().equals("3"))
////        {
////            binding.timeWrap.setVisibility(View.VISIBLE);
//            //按天
//            binding.startTime.setText(dataBean.getStart_time());
//            binding.endTime.setText(dataBean.getEnd_time());
//
////            if(dataBean.getType().equals("1"))//按时
////            {
//                binding.dateListView.setVisibility(View.VISIBLE);
//                binding.dateListView.setLayoutManager(new LinearLayoutManager(this));
//                TimeUserTiemEditLineAdapter timeUserTiemLineAdapter = new TimeUserTiemEditLineAdapter(dataBean.getDateList(), this, "","goods");
//                binding.dateListView.setAdapter(timeUserTiemLineAdapter);
////            }
////        }
//
//        binding.demandTip.setText(dataBean.getDemand());
//        binding.brandTip.setText(dataBean.getBrand());
//        binding.serviceTypeTip.setText(dataBean.getServiceTypeStr());
////        binding.address.setText(dataBean.getSendTypeStr().equals("1") ? "寄送地址"  : "取件地址");
////
////        binding.phoneTip.setText(dataBean.getPhone());
////        binding.addressTip.setText(dataBean.getAddress());
//        binding.orderTip.setText(dataBean.getOrder_no());


    }


}
