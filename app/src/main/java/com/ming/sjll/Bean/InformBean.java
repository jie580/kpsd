package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

/**
 * 商品列表
 */
public class InformBean extends BaseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean extends BaseBean {

        private List<DataBeanX> data;

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
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
    public static class DataBeanX extends BaseBean {
        private String content;
        private String create_time;
        private String logo;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
