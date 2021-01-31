package com.ming.sjll.Bean;


import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目用户时间
 */
public class GetInfoDateInfoBean extends BaseBean  implements Serializable{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean extends BaseBean  implements Serializable{

        private List<PaymentBean> payment;
        private String type;
        private String value;
        private String price;
        private List<GetInfoDateListBean.DataBean.DataBeanY> dateList;
        private List<String> conflictDate;

        public List<String> getConflictDate() {
            return conflictDate;
        }

        public void setConflictDate(List<String> conflictDate) {
            this.conflictDate = conflictDate;
        }

        public List<GetInfoDateListBean.DataBean.DataBeanY> getDateList() {
            return dateList;
        }

        public void setDateList(List<GetInfoDateListBean.DataBean.DataBeanY> dateList) {
            this.dateList = dateList;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<PaymentBean> getPayment() {
            return payment;
        }

        public void setPayment(List<PaymentBean> payment) {
            this.payment = payment;
        }
    }


    public static class PaymentBean extends BaseBean  implements Serializable{
        private String price;
        private String type;
        private boolean is_select;
        private String payment_str;

        public String getPayment_str() {
            return payment_str;
        }

        public void setPayment_str(String payment_str) {
            this.payment_str = payment_str;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public boolean isIs_select() {
            return is_select;
        }

        public void setIs_select(boolean is_select) {
            this.is_select = is_select;
        }
    }



}
