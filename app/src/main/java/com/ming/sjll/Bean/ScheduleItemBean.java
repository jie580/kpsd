package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

public class ScheduleItemBean extends BaseBean  implements Serializable{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }




    public static  class DataBean implements Serializable {

        private String address;
        private String address_title;
        private String date_type;
        private List<GetInfoDateListBean.DataBean.DataBeanY> dateList;

        private String area_code;
        private String provinces;
        private String city;
        private String county;
        private int type;
        private String weft;
        private String meridian;



        private String schedule_id;
        public String issShedule_id() {
            return schedule_id;
        }
        public void setSchedule_id(String schedule_id) {
            this.schedule_id = schedule_id;
        }

        private String user_id;
        public String isUser_id() {
            return user_id;
        }
        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }



        private String project_id;


        private  String background_image;
        public String getBackground_image() {
            return background_image;
        }
        public void setBackground_image(String background_image) {
            this.background_image = background_image;
        }

        private String demand;
        public String getDemand() {
            return demand;
        }
        public void setDemand(String demand) {
            this.demand = demand;
        }


        private String brand;
        public String getBrand() {
            return brand;
        }
        public void setBrand(String brand) {
            this.brand = brand;
        }


        private String budget;
        public String getBudget() {
            return budget;
        }
        public void setBudget(String budget) {
            this.budget = budget;
        }


        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }

        private List<GetInfoBean.DataBean> member;
        public List<GetInfoBean.DataBean> getMember() {
            return member;
        }
        public void setMember(List<GetInfoBean.DataBean> member) {
            this.member = member;
        }


        private List<String> dateListString;
        public List<String> getDateListString() {
            return dateListString;
        }
        public void setDateListString(List<String> dateListString) {
            this.dateListString = dateListString;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress_title() {
            return address_title;
        }

        public void setAddress_title(String address_title) {
            this.address_title = address_title;
        }

        public String getDate_type() {
            return date_type;
        }

        public void setDate_type(String date_type) {
            this.date_type = date_type;
        }

        public List<GetInfoDateListBean.DataBean.DataBeanY> getDateList() {
            return dateList;
        }

        public void setDateList(List<GetInfoDateListBean.DataBean.DataBeanY> dateList) {
            this.dateList = dateList;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getProvinces() {
            return provinces;
        }

        public void setProvinces(String provinces) {
            this.provinces = provinces;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getWeft() {
            return weft;
        }

        public void setWeft(String weft) {
            this.weft = weft;
        }

        public String getMeridian() {
            return meridian;
        }

        public void setMeridian(String meridian) {
            this.meridian = meridian;
        }
    }

}
