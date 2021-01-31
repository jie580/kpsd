package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;


public class WalletAccountBean extends BaseBean {
    private List<WalletBean.DataBeanX> data;

    public  List<WalletBean.DataBeanX> getData() {
        return data;
    }

    public void setData( List<WalletBean.DataBeanX> data) {
        this.data = data;
    }


}
