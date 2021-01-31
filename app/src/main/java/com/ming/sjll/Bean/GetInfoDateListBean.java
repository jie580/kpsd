package com.ming.sjll.Bean;


import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 人员时间
 */
public class GetInfoDateListBean extends BaseBean  implements Serializable{

    private List<GetInfoDateListBean.DataBean> data;

    public List<GetInfoDateListBean.DataBean> getData() {
        return data;
    }

    public void setData(List<GetInfoDateListBean.DataBean> data) {
        this.data = data;
    }

    public static class DataBean extends BaseBean implements Serializable {
        private String uid;
        private String default_avatar;
        private String name;
        private String is_conflict;
        private List<DataBeanX> occupationList;

        public List<DataBeanX> getOccupationList() {
            return occupationList;
        }

        public void setOccupationList(List<DataBeanX> occupationList) {
            this.occupationList = occupationList;
        }

        public String getIs_conflict() {
            return is_conflict;
        }

        public void setIs_conflict(String is_conflict) {
            this.is_conflict = is_conflict;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDefault_avatar() {
            return default_avatar;
        }

        public void setDefault_avatar(String default_avatar) {
            this.default_avatar = default_avatar;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }


        public static class DataBeanX extends BaseBean implements Serializable {
            private String uid;
            private String default_avatar;
            private String name;
                private String occupation;
            private String occupation_id;
            private String is_conflict;
            private String type;
            private String total;
            private List<DataBeanY> dateTime;;

            public List<DataBeanY> getDateTime() {
                return dateTime;
            }

            public void setDateTime(List<DataBeanY> dateTime) {
                this.dateTime = dateTime;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getIs_conflict() {
                return is_conflict;
            }

            public void setIs_conflict(String is_conflict) {
                this.is_conflict = is_conflict;
            }

            public String getOccupation_id() {
                return occupation_id;
            }

            public void setOccupation_id(String occupation_id) {
                this.occupation_id = occupation_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDefault_avatar() {
                return default_avatar;
            }

            public void setDefault_avatar(String default_avatar) {
                this.default_avatar = default_avatar;
            }

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getOccupation() {
                return occupation;
            }

            public void setOccupation(String occupation) {
                this.occupation = occupation;
            }
        }

        public static class DataBeanY extends BaseBean implements Serializable {
            private String time_id;
            private String start_time;
            private String end_time;
            private String date_time;
            private String start_date;
            private String date_str;
            private String date_time_str;

            public String getDate_str() {
                return date_str;
            }

            public void setDate_str(String date_str) {
                this.date_str = date_str;
            }

            public String getStart_date() {
                return start_date;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public String getDate_time() {
                return date_time;
            }

            public void setDate_time(String date_time) {
                this.date_time = date_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getStart_time() {
                return start_time;
            }

            public void setStart_time(String start_time) {
                this.start_time = start_time;
            }

            public String getTime_id() {
                return time_id;
            }

            public void setTime_id(String time_id) {
                this.time_id = time_id;
            }

            public String getDate_time_str() {
                return date_time_str;
            }

            public void setDate_time_str(String date_time_str) {
                this.date_time_str = date_time_str;
            }
        }



    }


}
