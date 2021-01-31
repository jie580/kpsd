package com.ming.sjll.Message.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

public class GroupMemberBean extends BaseBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        private String default_avatar;
        private String name;
        private String personalized;
        private String uid;
        private String remark;
        private String project_id;
        private List<String> occupation;
        private String joinData;
        private boolean hasDelete = true;

        public boolean isHasDelete() {
            return hasDelete;
        }

        public void setHasDelete(boolean hasDelete) {
            this.hasDelete = hasDelete;
        }

        public String getPersonalized() {
            return personalized;
        }

        public void setPersonalized(String personalized) {
            this.personalized = personalized;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getDefault_avatar() {
            return default_avatar;
        }

        public void setDefault_avatar(String default_avatar) {
            this.default_avatar = default_avatar;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getOccupation() {
            return occupation;
        }

        public void setOccupation(List<String> occupation) {
            this.occupation = occupation;
        }

        public String getJoinData() {
            return joinData;
        }

        public void setJoinData(String joinData) {
            this.joinData = joinData;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.default_avatar);
            dest.writeString(this.name);
            dest.writeString(this.personalized);
            dest.writeString(this.uid);
            dest.writeString(this.remark);
            dest.writeString(this.project_id);
            dest.writeStringList(this.occupation);
            dest.writeString(this.joinData);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.default_avatar = in.readString();
            this.name = in.readString();
            this.personalized = in.readString();
            this.uid = in.readString();
            this.remark = in.readString();
            this.project_id = in.readString();
            this.occupation = in.createStringArrayList();
            this.joinData = in.readString();
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
