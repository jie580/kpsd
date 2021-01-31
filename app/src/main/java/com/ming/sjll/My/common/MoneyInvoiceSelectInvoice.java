package com.ming.sjll.My.common;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.ming.sjll.Bean.InvoiceBean;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.My.adapter.MoneyProjectAdapter;
import com.ming.sjll.My.company.CompanyInfo;
import com.ming.sjll.My.company.ShowcaseAddTwoActivity;
import com.ming.sjll.My.personage.PersonageOccupation;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.manager.AppManager;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.AppUtils;
import com.ming.sjll.base.utils.ImageHelper;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyMoneyInvoiceBinding;
import com.ming.sjll.databinding.MyMoneyInvoiceSelectInvoiceBinding;
import com.ming.sjll.map.MapMainActivity;
import com.ming.sjll.map.tencent.MyTencentMap;
import com.ming.sjll.project.ReleaseProject;

import org.json.JSONObject;

import java.util.List;

/**
 * 个人中心-资金-选择抬头
 */
public class MoneyInvoiceSelectInvoice extends BaseActivity {


    private boolean isLoad = false;
    private int page = 1;
    private int pageSize = 20;
    MyMoneyInvoiceSelectInvoiceBinding binding;
    String type = "need";

    String invoiceId;
    String sendType = "2";

    //    总数据
    HttpParamsObject commParam = new HttpParamsObject();

    public static MoneyInvoiceSelectInvoice newInstance() {
        return new MoneyInvoiceSelectInvoice();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_money_invoice_select_invoice, null, false);
        setContentView(binding.getRoot());
        initView();
        event();

    }

    private void event()
    {
//        地图选择
        binding.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.init(MoneyInvoiceSelectInvoice.this);
                Intent intent = new Intent(MoneyInvoiceSelectInvoice.this, MyTencentMap.class);
                startActivityForResult(intent, PictureConfig.MAP_HEAD_REQUEST);
            }
        });

        binding.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendType.equals("1"))
                {
                    sendType = "2";
                    binding.typeImage.setBackgroundResource(R.mipmap.ic_invoice_send_type);
                    binding.sendTypeWrap1.setVisibility(View.VISIBLE);
                    binding.sendTypeWrap2.setVisibility(View.GONE);
                }
                else
                {
                    sendType = "1";
                    binding.typeImage.setBackgroundResource(R.mipmap.ic_invoice_send_type2);
                    binding.sendTypeWrap1.setVisibility(View.GONE);
                    binding.sendTypeWrap2.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commParam.setParam("invoiceId",invoiceId);
                commParam.setParam("sendType",sendType);
                commParam.setParam("email",binding.email.getText().toString());
                commParam.setParam("street",binding.street.getText().toString());

                new HttpUtil().sendRequest(Constant.INVOICE_APPLY_INVOICE, commParam, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        AppManager.getAppManager().finishActivity(MoneyInvoiceSelectProject.class);
                        setResult(RESULT_OK);
                        finish();
                    }
                });

            }
        });
    }

    private void initView() {
        commParam.setParam("projectId",getIntent().getStringExtra("projectId"));
        commParam.setParam("targetIds",(List<String >)getIntent().getSerializableExtra("targetIds"));
        getData();
    }

    private void getData(){
        HttpParamsObject param = new HttpParamsObject();
        new HttpUtil().sendRequest(Constant.INVOICE_INVOICE_LIST, param, new CommonCallback() {
            @Override
            public void onSuccessJson(String data, int code) {
                InvoiceBean.DataBeanList dataList = new Gson().fromJson(data, InvoiceBean.DataBeanList.class);

                for (int j = 0; j < dataList.getData().size(); j++) {
                    View view = LayoutInflater.from(MoneyInvoiceSelectInvoice.this).inflate(R.layout.my_money_invoice_select_invoice_item, null);
                    RelativeLayout warp = (RelativeLayout) view.findViewById(R.id.warp);
                    warp.setTag(j);

                    ((TextView)view.findViewById(R.id.name)).setText(dataList.getData().get(j).getName());
                    ((TextView)view.findViewById(R.id.duty_paragraph)).setText(dataList.getData().get(j).getDuty_paragraph());
                    if (dataList.getData().get(j).getType().equals("1"))
                    {
                        view.findViewById(R.id.personage).setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        if(dataList.getData().get(j).getInvoice_type().equals("1"))
                        {
                            view.findViewById(R.id.ordinary).setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            view.findViewById(R.id.special).setVisibility(View.VISIBLE);
                        }
                    }
                    warp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < binding.dataLayout.getChildCount(); i++) {
                                binding.dataLayout.getChildAt(i).findViewById(R.id.warp).setBackgroundResource(R.mipmap.bg_invoice);
                            }
                            v.setBackgroundResource(R.mipmap.bg_invoice_select);
                            invoiceId = dataList.getData().get(Integer.parseInt(v.getTag().toString())).getInvoiceId();
                            binding.dataLayout.requestLayout();
                        }
                    });
                    binding.dataLayout.addView(view);
                }


            }
        });


    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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

        }
    }


}
