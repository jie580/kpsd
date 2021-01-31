package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

public class ScheduleIListItem extends BaseBean {

    private List<ScheduleItemBean.DataBean> data;

    public List<ScheduleItemBean.DataBean> getData() {
        return data;
    }

    public void setData(List<ScheduleItemBean.DataBean> data) {
        this.data = data;
    }


}
