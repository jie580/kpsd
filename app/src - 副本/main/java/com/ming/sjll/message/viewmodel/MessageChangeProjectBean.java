package com.ming.sjll.Message.viewmodel;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

public class MessageChangeProjectBean extends BaseBean implements Serializable {


    /**
     * data : {"total":2,"per_page":"20","current_page":1,"last_page":1,"data":[{"id":19,"background_image":"/uploads/20190907/035e7b15f49362107b217005622ff293.png","demand":"需求","brand":"品牌","budget":"30000.00","area_name":"钓鱼岛","closing_time":1571231236,"created_time":1569859200,"tags":[]},{"id":16,"background_image":"/uploads/20190907/035e7b15f49362107b217005622ff293.png","demand":"需求","brand":"品牌","budget":"3000.00","area_name":"钓鱼岛","closing_time":1571231236,"created_time":1567267200,"tags":["轻奢风","模特"]}]}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX implements Serializable {
        /**
         * total : 2
         * per_page : 20
         * current_page : 1
         * last_page : 1
         * data : [{"id":19,"background_image":"/uploads/20190907/035e7b15f49362107b217005622ff293.png","demand":"需求","brand":"品牌","budget":"30000.00","area_name":"钓鱼岛","closing_time":1571231236,"created_time":1569859200,"tags":[]},{"id":16,"background_image":"/uploads/20190907/035e7b15f49362107b217005622ff293.png","demand":"需求","brand":"品牌","budget":"3000.00","area_name":"钓鱼岛","closing_time":1571231236,"created_time":1567267200,"tags":["轻奢风","模特"]}]
         */

        private int total;
        private String per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getPer_page() {
            return per_page;
        }

        public void setPer_page(String per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * id : 19
             * background_image : /uploads/20190907/035e7b15f49362107b217005622ff293.png
             * demand : 需求
             * brand : 品牌
             * budget : 30000.00
             * area_name : 钓鱼岛
             * closing_time : 1571231236
             * created_time : 1569859200
             * tags : []
             */

            private int id;
            private String background_image;
            private String demand;
            private String brand;
            private String budget;
            private String area_name;
            private int closing_time;
            private int created_time;
            private List<String> tags;
            private int type;
            private int schedule_id;

            public int getSchedule_id() {
                return schedule_id;
            }

            public void setSchedule_id(int schedule_id) {
                this.schedule_id = schedule_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public int getClosing_time() {
                return closing_time;
            }

            public void setClosing_time(int closing_time) {
                this.closing_time = closing_time;
            }

            public int getCreated_time() {
                return created_time;
            }

            public void setCreated_time(int created_time) {
                this.created_time = created_time;
            }

            public List<String> getTags() {
                return tags;
            }

            public void setTags(List<String> tags) {
                this.tags = tags;
            }
        }
    }
}
