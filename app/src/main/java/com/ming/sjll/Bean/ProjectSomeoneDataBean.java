package com.ming.sjll.Bean;


import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 字符串列表
 */
public class ProjectSomeoneDataBean extends BaseBean implements Serializable {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<UserDataBean> user;
        private List<GoodsItem.DataBean> site;
        private List<GoodsItem.DataBean> props;

        public List<GoodsItem.DataBean> getProps() {
            return props;
        }

        public void setProps(List<GoodsItem.DataBean> props) {
            this.props = props;
        }

        public List<GoodsItem.DataBean> getSite() {
            return site;
        }

        public void setSite(List<GoodsItem.DataBean> site) {
            this.site = site;
        }

        public List<UserDataBean> getUser() {
            return user;
        }

        public void setUser(List<UserDataBean> user) {
            this.user = user;
        }

        public static class UserDataBean {
            private String title;
            private String icon;
            private List<GetInfoBean.DataBean> userList;

            public List<GetInfoBean.DataBean> getUserList() {
                return userList;
            }

            public void setUserList(List<GetInfoBean.DataBean> userList) {
                this.userList = userList;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }




    }

}
