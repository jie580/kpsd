package com.ming.sjll.Message.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.ming.sjll.base.bean.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class GetMemberInfoBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private String project_id;
        private String user_id;
        private String remark;
        private List<OccupationBean> occupation;

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public List<OccupationBean> getOccupation() {
            return occupation;
        }

        public void setOccupation(List<OccupationBean> occupation) {
            this.occupation = occupation;
        }

        public static class OccupationBean {
            private String title;
            private String occupation_id;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getOccupation_id() {
                return occupation_id;
            }

            public void setOccupation_id(String occupation_id) {
                this.occupation_id = occupation_id;
            }
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.project_id);
            dest.writeString(this.user_id);
            dest.writeString(this.remark);
            dest.writeList(this.occupation);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.project_id = in.readString();
            this.user_id = in.readString();
            this.remark = in.readString();
            this.occupation = new ArrayList<OccupationBean>();
            in.readList(this.occupation, OccupationBean.class.getClassLoader());
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
