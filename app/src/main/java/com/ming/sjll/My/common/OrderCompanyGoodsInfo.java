package com.ming.sjll.My.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.ming.sjll.Bean.GoodsItem;
import com.ming.sjll.Bean.ProjectGoodsListBean;
import com.ming.sjll.My.adapter.OrderCompanyGoodsListAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.fragment.BaseFragment;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.databinding.MyOrderCompanyGoodsInfoBinding;
import com.ming.sjll.project.manage.adapter.TimeUserTiemEditLineAdapter;

/**
 * 个人中心-订单-商品
 */
public class OrderCompanyGoodsInfo  extends BaseActivity {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 6;
    private ProjectGoodsListBean dataList;
    private OrderCompanyGoodsListAdapter listAdapter;
    RecyclerView listView;
    MyOrderCompanyGoodsInfoBinding binding;



    public static OrderCompanyGoodsInfo newInstance() {
        return new OrderCompanyGoodsInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_order_company_goods_info);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_order_company_goods_info, null, false);
        setContentView(binding.getRoot());
        initView();

    }


    private void initView() {
        getData();
    }

    private void getData(){
        GoodsItem.DataBean dataBean = (GoodsItem.DataBean)getIntent().getSerializableExtra("data");
        binding.timeWrap.setVisibility(View.GONE);
        if(!dataBean.getType().equals("3"))
        {
            binding.timeWrap.setVisibility(View.VISIBLE);
            //按天
            binding.startTime.setText(dataBean.getStart_time());
            binding.endTime.setText(dataBean.getEnd_time());

            if(dataBean.getType().equals("1"))//按时
            {
                binding.dateListView.setVisibility(View.VISIBLE);
                binding.dateListView.setLayoutManager(new LinearLayoutManager(this));
                TimeUserTiemEditLineAdapter timeUserTiemLineAdapter = new TimeUserTiemEditLineAdapter(dataBean.getDateList(), this, "","goods");
                binding.dateListView.setAdapter(timeUserTiemLineAdapter);
            }
        }

        binding.numTip.setText(dataBean.getNum());
        binding.sendTypeTip.setText(dataBean.getSendTypeStr().equals("1") ? "商家寄送"  : "买家自取");
        binding.address.setText(dataBean.getSendTypeStr().equals("1") ? "寄送地址"  : "取件地址");
        binding.nameTip.setText(dataBean.getName());
        binding.phoneTip.setText(dataBean.getPhone());
        binding.addressTip.setText(dataBean.getAddress());
        binding.orderTip.setText(dataBean.getOrder_no());


    }


}
