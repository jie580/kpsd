package com.ming.sjll.Bean;


import android.view.View;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 */
public class GetInfoBeans extends BaseBean  implements Serializable{

    private List<GetInfoBean.DataBean> data;

    public List<GetInfoBean.DataBean> getData() {
        return data;
    }

    public void setData(List<GetInfoBean.DataBean> data) {
        this.data = data;
    }


}
