package com.ming.sjll.Home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

/**
 * 默认头像
 */
public class AdsBanner extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {

        private String banner_img;
        private int ads_id;

        public String getBannerImg() {
            return banner_img;
        }

        public void setBannerImg(String banner_img) {
            this.banner_img = banner_img;
        }


        public int getAdsId() {
            return ads_id;
        }

        public void setAdsId(int ads_id) {
            this.ads_id = ads_id;
        }

    }

//
//    private List<DataBean> data;
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }

//    public static class DataBean {
//
//        private String pic;
//        private boolean isetSelected;
//
//        public String getPic() {
//            return pic;
//        }
//
//        public void setPic(String pic) {
//            this.pic = pic;
//        }
//        public boolean getIsetSelected() {
//            return isetSelected;
//        }
//        public void setIsetSelected(boolean isetSelected) {
//            this.isetSelected = isetSelected;
//        }
//    }


}
