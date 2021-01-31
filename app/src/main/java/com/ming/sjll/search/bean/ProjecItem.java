package com.ming.sjll.search.bean;

import com.ming.sjll.Bean.GetInfoBean;
import com.ming.sjll.Bean.OccupationList;
import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

public class ProjecItem extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }




    public  static class DataBean extends BaseBean{

        private String project_id;
        private String demand;
        private String user_id;
        private String background_image;
        private String budget;
        private String is_scheme;
        private String area_code;
        private String provinces;
        private String city;
        private String county;
        private String address_title;
        private String is_local;
        private String brief;
        private String is_determine;
        private String user_id_determine;
        private String address;
        private String meridian;
        private String weft;
        private String im_uid;
        private String im_name;
        private String is_display;
        private List<String> occupation_string_array;
        private boolean isSelect;


        private List<String> tags;
        private List<OccupationList.DataBean> push;
        private List<GetInfoBean.DataBean> pushUser;

        private String occupation_background;


        private boolean is_info;

        public DataBean() {
        }

        public boolean isIs_info() {
            return is_info;
        }
        public void setIs_info(boolean is_info) {
            this.is_info = is_info;
        }

        private boolean is_manage;
        public boolean isIs_manage() {
            return is_manage;
        }
        public void setIs_manage(boolean is_manage) {
            this.is_manage = is_manage;
        }

        private boolean is_schedule;
        public boolean isIs_schedule() {
            return is_schedule;
        }
        public void setIs_schedule(boolean is_schedule) {
            this.is_schedule = is_schedule;
        }

        private  boolean is_chat;
        public boolean isIs_chat() {
            return is_chat;
        }
        public void setIs_chat(boolean is_chat) {
            this.is_chat = is_chat;
        }



        boolean isChange = true;

        public boolean getIsChange() {
            return isChange;
        }

        public void setIsChange(boolean isChange) {
            this.isChange = isChange;
        }

        private String closing_time;

        public String getClosingTime() {
            return closing_time;
        }

        public void setClosingTime(String closing_time) {
            this.closing_time = closing_time;
        }


        private String brand;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }


        private String[] tag;

        public String[] getTag() {
            return tag;
        }

        public void setTag(String[] tag) {
            this.tag = tag;
        }


        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        private boolean isNotice;

        public boolean getIsNotice() {
            return isNotice;
        }

        public void setIsNotice(boolean isNotice) {
            this.isNotice = isNotice;
        }


        private List<TimeList> time_list;

        public List<TimeList> getTimeList() {
            return time_list;
        }

        public void setTimeList(List<TimeList> time_list) {
            this.time_list = time_list;
        }

        public String getDemand() {
            return demand;
        }

        public void setDemand(String demand) {
            this.demand = demand;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBackground_image() {
            return background_image;
        }

        public void setBackground_image(String background_image) {
            this.background_image = background_image;
        }

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getIs_scheme() {
            return is_scheme;
        }

        public void setIs_scheme(String is_scheme) {
            this.is_scheme = is_scheme;
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

        public String getAddress_title() {
            return address_title;
        }

        public void setAddress_title(String address_title) {
            this.address_title = address_title;
        }

        public String getIs_local() {
            return is_local;
        }

        public void setIs_local(String is_local) {
            this.is_local = is_local;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getIs_determine() {
            return is_determine;
        }

        public void setIs_determine(String is_determine) {
            this.is_determine = is_determine;
        }

        public String getUser_id_determine() {
            return user_id_determine;
        }

        public void setUser_id_determine(String user_id_determine) {
            this.user_id_determine = user_id_determine;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMeridian() {
            return meridian;
        }

        public void setMeridian(String meridian) {
            this.meridian = meridian;
        }

        public String getWeft() {
            return weft;
        }

        public void setWeft(String weft) {
            this.weft = weft;
        }

        public String getIm_uid() {
            return im_uid;
        }

        public void setIm_uid(String im_uid) {
            this.im_uid = im_uid;
        }

        public String getIm_name() {
            return im_name;
        }

        public void setIm_name(String im_name) {
            this.im_name = im_name;
        }

        public String getIs_display() {
            return is_display;
        }

        public void setIs_display(String is_display) {
            this.is_display = is_display;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<OccupationList.DataBean> getPush() {
            return push;
        }

        public void setPush(List<OccupationList.DataBean> push) {
            this.push = push;
        }

        public List<GetInfoBean.DataBean> getPushUser() {
            return pushUser;
        }

        public void setPushUser(List<GetInfoBean.DataBean> pushUser) {
            this.pushUser = pushUser;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getOccupation_background() {
            return occupation_background;
        }

        public void setOccupation_background(String occupation_background) {
            this.occupation_background = occupation_background;
        }

        public List<String> getOccupation_string_array() {
            return occupation_string_array;
        }

        public void setOccupation_string_array(List<String> occupation_string_array) {
            this.occupation_string_array = occupation_string_array;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public  class TimeList {
            private String start_time;
            private String end_time;

            public String getStartTime() {
                return start_time;
            }

            public void setStartTime(String start_time) {
                this.start_time = start_time;
            }




            public String getEndTime() {
                return end_time;
            }

            public void setEndTime(String end_time) {
                this.end_time = end_time;
            }

        }


    }

}
