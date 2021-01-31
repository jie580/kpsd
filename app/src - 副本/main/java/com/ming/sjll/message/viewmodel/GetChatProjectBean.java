package com.ming.sjll.Message.viewmodel;


import android.text.TextUtils;
import android.view.View;

import com.ming.sjll.base.bean.BaseBean;
import com.ming.sjll.base.utils.Tools;

public class GetChatProjectBean extends BaseBean {


    /**
     * data : {"id":16,"background_image":"/uploads/20190907/035e7b15f49362107b217005622ff293.png","demand":"需求","brand":"品牌","budget":"3000.00","area_name":"钓鱼岛","closing_time":1571231236}
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
         * background_image : /uploads/20190907/035e7b15f49362107b217005622ff293.png
         * demand : 需求
         * brand : 品牌
         * budget : 3000.00
         * area_name : 钓鱼岛
         * closing_time : 1571231236
         */

        private int id;
        private String background_image;
        private String demand;
        private String brand;
        private String budget;
        private String area_name;
        private int closing_time;
        private String showTime;
        private int showTagVisible = View.GONE;
        private String showTag = "";
        private String time;


        public void setShowTag(String showTag) {
            this.showTag = showTag;
        }

        public String getShowTag() {

            return showTag;
        }

        public void setShowTagVisible(int showTagVisible) {
            this.showTagVisible = showTagVisible;
        }

        public int getShowTagVisible() {

            if (TextUtils.isEmpty(showTag)) {
                showTagVisible = View.GONE;
            } else {
                showTagVisible = View.VISIBLE;
            }

            return showTagVisible;
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


        public String showBudget() {
            return "￥" + budget;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String showTime() {
            return Tools.getDateformat(closing_time);
        }

        public void setClosing_time(int closing_time) {
            this.closing_time = closing_time;
        }
    }
}
