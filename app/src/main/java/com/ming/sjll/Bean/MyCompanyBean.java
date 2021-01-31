package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 我的公司
 */
public class MyCompanyBean extends BaseBean implements Serializable {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private String company_id;
        public String getCompany_id() {
            return company_id;
        }
        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        private String user_id;
        public String getUser_id() {
            return user_id;
        }
        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        private String default_avatar;
        public String getDefault_avatar() {
            return default_avatar;
        }
        public void setDefault_avatar(String default_avatar) {
            this.default_avatar = default_avatar;
        }


        private String default_avatar_rec;
        public String getDefault_avatar_rec() {
            return default_avatar_rec;
        }
        public void setDefault_avatar_rec(String default_avatar_rec) {
            this.default_avatar_rec = default_avatar_rec;
        }


        private String short_company_name;
        public String getShort_company_name() {
            return short_company_name;
        }
        public void setShort_company_name(String short_company_name) {
            this.short_company_name = short_company_name;
        }



        private String full_company_name;
        public String getFull_company_name() {
            return full_company_name;
        }
        public void setFull_company_name(String full_company_name) {
            this.full_company_name = full_company_name;
        }


        private String cover_img;
        public String getCover_img() {
            return cover_img;
        }
        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }


        private String is_approve;
        public String getIs_approve() {
            return is_approve;
        }
        public void setIs_approve(String is_approve) {
            this.is_approve = is_approve;
        }

        private List<GetInfoBean.DataBean> memberList;
        public List<GetInfoBean.DataBean> getMemberList() {
            return memberList;
        }
        public void setMemberList(List<GetInfoBean.DataBean> memberList) {
            this.memberList = memberList;
        }

    }
}
