package com.ming.sjll.My.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.ming.sjll.Bean.InvoiceBean;
import com.ming.sjll.Bean.ProjectOrderListBean;
import com.ming.sjll.MainActivity;
import com.ming.sjll.My.adapter.MoneyProjectAdapter;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.utils.Tools;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyMoneyInvoiceBinding;
import com.ming.sjll.databinding.MyMoneyInvoiceEditBinding;
import com.rey.material.app.BottomSheetDialog;

/**
 * 个人中心-资金-发票
 */
public class MoneyInvoiceEdit extends BaseActivity {



    MyMoneyInvoiceEditBinding binding;

    int type = 1;
    int invoiceType = 1;
    String  invoiceId = "";


    public static MoneyInvoiceEdit newInstance() {
        return new MoneyInvoiceEdit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_money_invoice_edit, null, false);
        setContentView(binding.getRoot());
        initView();
        event();

    }

    private void event()
    {
        binding.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(type == 1)
              {
                  type = 2;
                  binding.typeImage.setBackgroundResource(R.mipmap.ic_invoice_type2);
                  binding.personageWrap.setVisibility(View.GONE);
                  binding.companyWrap.setVisibility(View.VISIBLE);
              }
              else
              {
                  type = 1;
                  binding.typeImage.setBackgroundResource(R.mipmap.ic_invoice_type);
                  binding.personageWrap.setVisibility(View.VISIBLE);
                  binding.companyWrap.setVisibility(View.GONE);
              }
            }
        });

        binding.invoiceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomInterPasswordDialog = new BottomSheetDialog(getContext());
                bottomInterPasswordDialog.setContentView(R.layout.dialog_invoice_type_select);
                bottomInterPasswordDialog.inDuration(300);
                bottomInterPasswordDialog.outDuration(200);
                bottomInterPasswordDialog.show();
                bottomInterPasswordDialog.findViewById(R.id.man).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        invoiceType = 1;
                        selectInvoiceType();
                        bottomInterPasswordDialog.hide();
                    }
                });
                bottomInterPasswordDialog.findViewById(R.id.woman).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        invoiceType = 2;
                        selectInvoiceType();
                        bottomInterPasswordDialog.hide();
                    }
                });


            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                param.setParam("invoiceId", invoiceId);
                param.setParam("type", type);
                if(type == 1)
                {
                    param.setParam("name", binding.personageName.getText().toString());
                }
                else
                {
                    param.setParam("name", binding.nameText.getText().toString());
                }

                param.setParam("invoiceType", invoiceType);
                param.setParam("dutyParagraph", binding.dutyParagraphText.getText().toString());
                param.setParam("phone", binding.phoneText.getText().toString());
                param.setParam("address", binding.addressText.getText().toString());
                param.setParam("bank", binding.bankText.getText().toString());
                param.setParam("bankCard", binding.bankCardText.getText().toString());

                new HttpUtil().sendRequest(Constant.INVOICE_SAVE_INVOICE, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("保存成功");
                        setResult(RESULT_OK);
                        finish();
                        }
                    });
            }
        });

    }


    private void selectInvoiceType()
    {
        if(invoiceType == 1)
        {
            binding.invoiceTypeTip.setText("增值税普票");
            binding.phoneStar.setVisibility(View.GONE);
            binding.addressStar.setVisibility(View.GONE);
            binding.bankStar.setVisibility(View.GONE);
            binding.bankCardStar.setVisibility(View.GONE);
        }
        else
        {
            binding.invoiceTypeTip.setText("增值税专票");
            binding.phoneStar.setVisibility(View.VISIBLE);
            binding.addressStar.setVisibility(View.VISIBLE);
            binding.bankStar.setVisibility(View.VISIBLE);
            binding.bankCardStar.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        InvoiceBean.DataBeanX dataBeanX =  (InvoiceBean.DataBeanX)getIntent().getSerializableExtra("data");
        if(dataBeanX == null)
        {
            return;
        }
        invoiceId = dataBeanX.getInvoiceId();
        if(dataBeanX.getType().equals("1"))
        {
            type = 1;
            binding.typeImage.setBackgroundResource(R.mipmap.ic_invoice_type);
            binding.personageWrap.setVisibility(View.VISIBLE);
            binding.companyWrap.setVisibility(View.GONE);

            binding.personageName.setText(dataBeanX.getName());
        }
        else
        {
            type = 2;
            invoiceType = Integer.parseInt(dataBeanX.getInvoice_type());
            selectInvoiceType();
            binding.typeImage.setBackgroundResource(R.mipmap.ic_invoice_type2);
            binding.personageWrap.setVisibility(View.GONE);
            binding.companyWrap.setVisibility(View.VISIBLE);

            binding.nameText.setText(dataBeanX.getName());
            binding.dutyParagraphText.setText(dataBeanX.getDuty_paragraph());
            binding.phoneText.setText(dataBeanX.getPhone());
            binding.addressText.setText(dataBeanX.getAddress());
            binding.bankText.setText(dataBeanX.getBank());
            binding.bankCardText.setText(dataBeanX.getBank_card());
        }


    }




}
