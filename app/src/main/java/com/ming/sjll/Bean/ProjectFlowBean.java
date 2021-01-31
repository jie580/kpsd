package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目流程
 */
public class ProjectFlowBean extends BaseBean implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean implements Serializable {
        private String flow_id;
        private String name;
        private String is_achieve;
        private String flow_identification;
        private String icon;

        public DataBean() {
        }

        public String getFlow_id() {
            return flow_id;
        }

        public void setFlow_id(String flow_id) {
            this.flow_id = flow_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIs_achieve() {
            return is_achieve;
        }

        public void setIs_achieve(String is_achieve) {
            this.is_achieve = is_achieve;
        }

        public String getFlow_identification() {
            return flow_identification;
        }

        public void setFlow_identification(String flow_identification) {
            this.flow_identification = flow_identification;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
