package com.ming.sjll.project.bean;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

public class ProjecCoverBg extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }




    public  class DataBean {

        boolean isSelect = true;

        public boolean getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(boolean isSelect) {
            this.isSelect = isSelect;
        }



        private String img;

        public String getImg() {
            return img;
        }
        public void setImg(String img) {
            this.img = img;
        }


        private String bg_img;
        public String getBgImg() {
            return bg_img;
        }

        public void setBgImg(String bg_img) {
            this.bg_img = bg_img;
        }
    }

}
