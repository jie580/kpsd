package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 橱窗分类
 */
public class ShowCaseLIstBean extends BaseBean {

    private List<ShowCaseLIstBean.DataBean> data;

    public List<ShowCaseLIstBean.DataBean> getData() {
        return data;
    }

    public void setData(List<ShowCaseLIstBean.DataBean> data) {
        this.data = data;
    }


    public static class DataBean implements Serializable {
        public String class_id;
        public String group_name;
        public String icon;
        public String type;

    }
}
