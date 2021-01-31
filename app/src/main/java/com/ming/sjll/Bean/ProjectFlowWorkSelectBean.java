package com.ming.sjll.Bean;

import android.graphics.Bitmap;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;


public class ProjectFlowWorkSelectBean extends BaseBean implements Serializable {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean implements Serializable {
        private String img_id;
        private String img;
        private String newImg;
        private Bitmap imgBitmap;
        private String is_select;
        private String is_sample;
        private String img_master;
        private String img_rect;
        private String amend_opinion;
        private String edit_img;
        private boolean isOpen;



        public String getImg_id() {
            return img_id;
        }

        public void setImg_id(String img_id) {
            this.img_id = img_id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getIs_select() {
            return is_select;
        }

        public void setIs_select(String is_select) {
            this.is_select = is_select;
        }

        public String getIs_sample() {
            return is_sample;
        }

        public void setIs_sample(String is_sample) {
            this.is_sample = is_sample;
        }

        public String getImg_master() {
            return img_master;
        }

        public void setImg_master(String img_master) {
            this.img_master = img_master;
        }

        public String getImg_rect() {
            return img_rect;
        }

        public void setImg_rect(String img_rect) {
            this.img_rect = img_rect;
        }

        public String getAmend_opinion() {
            return amend_opinion;
        }

        public void setAmend_opinion(String amend_opinion) {
            this.amend_opinion = amend_opinion;
        }

        public String getEdit_img() {
            return edit_img;
        }

        public void setEdit_img(String edit_img) {
            this.edit_img = edit_img;
        }

        public boolean getIsOpen() {
            return isOpen;
        }

        public void setIsOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }

        public Bitmap getImgBitmap() {
            return imgBitmap;
        }

        public void setImgBitmap(Bitmap imgBitmap) {
            this.imgBitmap = imgBitmap;
        }

        public String getNewImg() {
            return newImg;
        }

        public void setNewImg(String newImg) {
            this.newImg = newImg;
        }
    }
}
