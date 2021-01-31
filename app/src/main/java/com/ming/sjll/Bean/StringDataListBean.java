package com.ming.sjll.Bean;


import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 字符串列表
 */
public class StringDataListBean extends BaseBean  implements Serializable{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseBean  implements Serializable{

        private List<DataBeanX> data;

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }

        public static class DataBeanX implements Serializable{
            private String id;
            private String color_id;
            private String skin_color;
            private String search_img;
            private String create_time;
            private String title;
            private String brand_id;
            private boolean isSelect;
            private boolean realIsSelect = false;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSearch_img() {
                return search_img;
            }

            public void setSearch_img(String search_img) {
                this.search_img = search_img;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(String brand_id) {
                this.brand_id = brand_id;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public boolean isRealIsSelect() {
                return realIsSelect;
            }

            public void setRealIsSelect(boolean realIsSelect) {
                this.realIsSelect = realIsSelect;
            }

            public String getColor_id() {
                return color_id;
            }

            public void setColor_id(String color_id) {
                this.color_id = color_id;
            }

            public String getSkin_color() {
                return skin_color;
            }

            public void setSkin_color(String skin_color) {
                this.skin_color = skin_color;
            }
        }


        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int id) {
            this.total = total;
        }


        private int per_page;

        public int getPerPage() {
            return per_page;
        }

        public void setPerPage(int id) {
            this.per_page = per_page;
        }

        private int current_page;

        public int getCurrentPage() {
            return current_page;
        }

        public void setCurrentPage(int id) {
            this.current_page = current_page;
        }


        private int last_page;

        public int getLastPage() {
            return last_page;
        }

        public void setLastPage(int id) {
            this.last_page = last_page;
        }



    }

}
