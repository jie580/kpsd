package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 商品
 */
public class ProjectCost extends BaseBean implements Serializable {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataListBean implements Serializable {
        private List<DataBeanX> data;

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }
    }
    public static class DataBean implements Serializable {
        private String totalPrice;
        private String num;
        private String payMoney;
        private String unpaid;
        private List<DataBeanX> detailList;

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public List<DataBeanX> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DataBeanX> detailList) {
            this.detailList = detailList;
        }

        public String getPayMoney() {
            return payMoney;
        }

        public void setPayMoney(String payMoney) {
            this.payMoney = payMoney;
        }

        public String getUnpaid() {
            return unpaid;
        }

        public void setUnpaid(String unpaid) {
            this.unpaid = unpaid;
        }

    }


    public static class DataBeanX implements Serializable {
        private String detail_id;
        private String project_id;
        private String target_id;
        private String class_name;
        private String total_price;
        private String detail_type;
        private String target_name;
        private String img;
        private String invoice_status;
        private boolean isSelect = false;
        public String getDetail_id() {
            return detail_id;
        }

        public void setDetail_id(String detail_id) {
            this.detail_id = detail_id;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public String getTarget_id() {
            return target_id;
        }

        public void setTarget_id(String target_id) {
            this.target_id = target_id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getDetail_type() {
            return detail_type;
        }

        public void setDetail_type(String detail_type) {
            this.detail_type = detail_type;
        }

        public String getTarget_name() {
            return target_name;
        }

        public void setTarget_name(String target_name) {
            this.target_name = target_name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getInvoice_status() {
            return invoice_status;
        }

        public void setInvoice_status(String invoice_status) {
            this.invoice_status = invoice_status;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }

}
