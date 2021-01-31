package com.ming.sjll.Message.viewmodel;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

public class ProjectDetailBean extends BaseBean {


    /**
     * data : {"id":16,"user_id":4,"background_image":"/uploads/20191125/099ada29c78180dd98d83c4c2cc4862d.jpg","demand":"需求222","brand":"品牌","budget":"3000.00","is_scheme":0,"provinces_id":123,"city_id":900000,"county_id":12,"is_local":0,"closing_time":"2019/10/16-2019/09/07","brief":"项目简介","is_determine":1,"user_id_determine":3,"created_time":1567842031,"update_time":"2019-10-14 13:44:46","type":0,"is_display":0,"status":"统筹中","tags":["轻奢风","模特"],"area_name":"钓鱼岛"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 16
         * user_id : 4
         * background_image : /uploads/20191125/099ada29c78180dd98d83c4c2cc4862d.jpg
         * demand : 需求222
         * brand : 品牌
         * budget : 3000.00
         * is_scheme : 0
         * provinces_id : 123
         * city_id : 900000
         * county_id : 12
         * is_local : 0
         * closing_time : 2019/10/16-2019/09/07
         * brief : 项目简介
         * is_determine : 1
         * user_id_determine : 3
         * created_time : 1567842031
         * update_time : 2019-10-14 13:44:46
         * type : 0
         * is_display : 0
         * status : 统筹中
         * tags : ["轻奢风","模特"]
         * area_name : 钓鱼岛
         */

        private int id;
        private int user_id;
        private String background_image;
        private String demand;
        private String brand;
        private String budget;
        private int is_scheme;
        private int provinces_id;
        private int city_id;
        private int county_id;
        private int is_local;
        private String closing_time;
        private String brief;
        private int is_determine;
        private int user_id_determine;
        private int created_time;
        private String update_time;
        private int type;
        private int is_display;
        private String status;
        private String area_name;
        private List<String> tags;
        private String title_tags;
        private String closing_str;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle_tags() {
            return title_tags;
        }

        public void setTitle_tags(String title_tags) {
            this.title_tags = title_tags;
        }

        public String getClosing_str() {
            return closing_str;
        }

        public void setClosing_str(String closing_str) {
            this.closing_str = closing_str;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
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

        public int getIs_scheme() {
            return is_scheme;
        }

        public void setIs_scheme(int is_scheme) {
            this.is_scheme = is_scheme;
        }

        public int getProvinces_id() {
            return provinces_id;
        }

        public void setProvinces_id(int provinces_id) {
            this.provinces_id = provinces_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public int getCounty_id() {
            return county_id;
        }

        public void setCounty_id(int county_id) {
            this.county_id = county_id;
        }

        public int getIs_local() {
            return is_local;
        }

        public void setIs_local(int is_local) {
            this.is_local = is_local;
        }

        public String getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(String closing_time) {
            this.closing_time = closing_time;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public int getIs_determine() {
            return is_determine;
        }

        public void setIs_determine(int is_determine) {
            this.is_determine = is_determine;
        }

        public int getUser_id_determine() {
            return user_id_determine;
        }

        public void setUser_id_determine(int user_id_determine) {
            this.user_id_determine = user_id_determine;
        }

        public int getCreated_time() {
            return created_time;
        }

        public void setCreated_time(int created_time) {
            this.created_time = created_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIs_display() {
            return is_display;
        }

        public void setIs_display(int is_display) {
            this.is_display = is_display;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
