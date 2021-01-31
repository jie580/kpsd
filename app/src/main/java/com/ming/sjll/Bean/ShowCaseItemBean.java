package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 橱窗分类
 */
public class ShowCaseItemBean extends BaseBean {

    private List<ShowCaseItemBean.DataBean> data;

    public List<ShowCaseItemBean.DataBean> getData() {
        return data;
    }

    public void setData(List<ShowCaseItemBean.DataBean> data) {
        this.data = data;
    }


    public static class DataBean implements Serializable {
        private String id;
        private String title;
        private String icon;
        private List<DataBeanX> child;

        public List<DataBeanX> getChild() {
            return child;
        }

        public void setChild(List<DataBeanX> child) {
            this.child = child;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public static class DataBeanX implements Serializable {
            private String class_id;
            private String title;
            private String icon;
            private String cross_icon;
            private String pid;

            public String getClass_id() {
                return class_id;
            }

            public void setClass_id(String class_id) {
                this.class_id = class_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getCross_icon() {
                return cross_icon;
            }

            public void setCross_icon(String cross_icon) {
                this.cross_icon = cross_icon;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }
        }

    }
}
