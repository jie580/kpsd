package com.ming.sjll.Bean;


import android.view.View;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 字符串列表
 */
public class StringListBean extends BaseBean  implements Serializable{

    private List<String>  data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

}
