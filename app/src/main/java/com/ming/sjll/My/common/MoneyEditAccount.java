package com.ming.sjll.My.common;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.lzy.okgo.model.HttpParams;
import com.ming.sjll.R;
import com.ming.sjll.api.Constant;
import com.ming.sjll.base.activity.BaseActivity;
import com.ming.sjll.base.tool.HttpUtil;
import com.ming.sjll.base.tool.http.CommonCallback;
import com.ming.sjll.base.tool.http.HttpParamsObject;
import com.ming.sjll.base.widget.ToastShow;
import com.ming.sjll.databinding.MyMoneyEditAccountBinding;

/**
 * 个人中心-添加账号
 */
public class MoneyEditAccount extends BaseActivity {


    MyMoneyEditAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.my_money_edit_account);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_money_edit_account, null, false);
        setContentView(binding.getRoot());
        event();
    }

    private void event() {
        binding.toCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.personageWrap.setVisibility(View.GONE);
                binding.companyWrap.setVisibility(View.VISIBLE);
            }
        });

        binding.toPersonage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.companyWrap.setVisibility(View.GONE);
                binding.personageWrap.setVisibility(View.VISIBLE);
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpParamsObject param = new HttpParamsObject();
                if(binding.personageWrap.getVisibility() == View.VISIBLE)
                {
                    param.setParam("type",1);
                    param.setParam("card",binding.account.getText().toString());
                    param.setParam("userName",binding.name.getText().toString());
                }
                else
                {
                    param.setParam("type",2);
                    param.setParam("card",binding.card.getText().toString());
                    param.setParam("userName",binding.userName.getText().toString());
                    param.setParam("userName",binding.bank.getText().toString());
                }
                new HttpUtil().sendRequest(Constant.WALLET_ADD_ACCOUNT, param, new CommonCallback() {
                    @Override
                    public void onSuccessJson(String data, int code) {
                        ToastShow.s("保存成功");
                        finish();
                    }
                });

            }
        });

    }


}
