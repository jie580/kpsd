package com.ming.sjll.login.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

/**
 * 默认头像
 */
public class DefaultAvatar extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean implements Parcelable {

        private String photo;
        private int id;
        private int oldHeight;

        public String gethoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        public int getOldHeight() {
            return oldHeight;
        }

        public void setOldHeight(int oldHeight) {
            this.oldHeight = oldHeight;
        }

        protected DataBean(Parcel in) {
            photo = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(photo);
        }

//        protected DataBean(Parcel in) {
//            title = in.readString();
//            icon = in.readString();
//
//        }

//        public static final Creator<DataBean> CREATOR = new Creator<DataBean>()
//
//        @Override
//        public int describeContents() {
//            return 0;
//        }
//
//        @Override
//        public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(photo);
//
//        }
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
