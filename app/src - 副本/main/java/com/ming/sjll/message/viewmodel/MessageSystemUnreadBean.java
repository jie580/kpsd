package com.ming.sjll.Message.viewmodel;

import com.ming.sjll.base.bean.BaseBean;

public class MessageSystemUnreadBean extends BaseBean {
    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    private DataBean data;

    public static class DataBean {


        private int unread_count;

        public int getUnread_count() {
            return unread_count;
        }

        public void setUnread_count(int unread_count) {
            this.unread_count = unread_count;
        }
    }
}
