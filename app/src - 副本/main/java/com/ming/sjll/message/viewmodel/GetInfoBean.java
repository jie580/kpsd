package com.ming.sjll.Message.viewmodel;


import android.view.View;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

public class GetInfoBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int id;
        private String user_id;
        private String background_image;
        private String demand;
        private String brand;
        private String budget;
        private String is_scheme;
        private String provinces_id;
        private String city_id;
        private String county_id;
        private String is_local;
        private String brief;
        private String is_determine;
        private String user_id_determine;
        private String created_time;
        private String update_time;
        private String type;
        private String status;
        private List<String> tags;
        private String area_name;
        private String closing_time;
        private String showTime;
        private int showTagVisible = View.GONE;
        private String showTag = "";
        private String name = "";
        private String default_avatar = "";

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getDemand() {
            return demand;
        }

        public void setDemand(String demand) {
            this.demand = demand;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
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

        public String getProvinces_id() {
            return provinces_id;
        }

        public void setProvinces_id(String provinces_id) {
            this.provinces_id = provinces_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCounty_id() {
            return county_id;
        }

        public void setCounty_id(String county_id) {
            this.county_id = county_id;
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

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(String closing_time) {
            this.closing_time = closing_time;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public int getShowTagVisible() {
            return showTagVisible;
        }

        public void setShowTagVisible(int showTagVisible) {
            this.showTagVisible = showTagVisible;
        }

        public String getShowTag() {
            return showTag;
        }

        public void setShowTag(String showTag) {
            this.showTag = showTag;
        }
    }
}
