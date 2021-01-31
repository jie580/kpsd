package com.ming.sjll.Bean;


import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

public class ProjectGoodsListBean extends BaseBean  implements Serializable{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean extends BaseBean {

        private List<GoodsItem.DataBean> data;

        public List<GoodsItem.DataBean> getData() {
            return data;
        }

        public void setData(List<GoodsItem.DataBean> data) {
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


}
