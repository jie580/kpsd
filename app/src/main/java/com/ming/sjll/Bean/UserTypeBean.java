package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;

public class UserTypeBean extends BaseBean implements Serializable {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String type;
        private String is_visible;
        private String is_serve;
        private String is_synced;
        private String is_manage;
        private String is_schedule;


        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }



        public String getIs_serve() {
            return is_serve;
        }

        public void setIs_serve(String is_serve) {
            this.is_serve = is_serve;
        }



        public String getIs_visible() {
            return is_visible;
        }

        public void setIs_visible(String is_visible) {
            this.is_visible = is_visible;
        }


        public String getIs_synced() {
            return is_synced;
        }

        public void setIs_synced(String is_synced) {
            this.is_synced = is_synced;
        }

        public String getIs_manage() {
            return is_manage;
        }

        public void setIs_manage(String is_manage) {
            this.is_manage = is_manage;
        }

        public String getIs_schedule() {
            return is_schedule;
        }

        public void setIs_schedule(String is_schedule) {
            this.is_schedule = is_schedule;
        }
    }
}
