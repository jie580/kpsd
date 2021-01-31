package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;

public class ShareBean extends BaseBean implements Serializable {

    /**
     * data : {"title":"商品分享标题","text":"商品分享内容","img":"商品分享图片","url":"商品地址"}
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
         * title : 商品分享标题
         * text : 商品分享内容
         * img : 商品分享图片
         * url : 商品地址
         */

        private String title;
        private String text;
        private String img;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
