package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 抬头
 */
public class InvoiceBean extends BaseBean implements Serializable {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private  List<DataBeanX> invoiceList;

        private String needInvoice;
        private String issueInvoice;
        private String bank;
        private String bank_card;

        public String getNeedInvoice() {
            return needInvoice;
        }

        public void setNeedInvoice(String needInvoice) {
            this.needInvoice = needInvoice;
        }

        public String getIssueInvoice() {
            return issueInvoice;
        }

        public void setIssueInvoice(String issueInvoice) {
            this.issueInvoice = issueInvoice;
        }

        public List<DataBeanX> getInvoiceList() {
            return invoiceList;
        }

        public void setInvoiceList(List<DataBeanX> invoiceList) {
            this.invoiceList = invoiceList;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getBank_card() {
            return bank_card;
        }

        public void setBank_card(String bank_card) {
            this.bank_card = bank_card;
        }
    }


    public static class DataBeanList  extends BaseBean implements Serializable {

        private List<DataBeanX> data;

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }
    }


    public static class DataBeanX implements Serializable{
        private String invoiceId;
        private String type;
        private String user_id;
        private String invoice_type;
        private String name;
        private String duty_paragraph;
        private String phone;
        private String address;
        private String bank;
        private String bank_card;
        private String is_default;
        private String create_time;
        private String send_type;
        private String email;
        private String area_code;
        private String provinces;
        private String city;
        private String county;
        private String address_title;
        private String meridian;
        private String weft;
        private String street;
        private String address_info;

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getBank_card() {
            return bank_card;
        }

        public void setBank_card(String bank_card) {
            this.bank_card = bank_card;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDuty_paragraph() {
            return duty_paragraph;
        }

        public void setDuty_paragraph(String duty_paragraph) {
            this.duty_paragraph = duty_paragraph;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInvoice_type() {
            return invoice_type;
        }

        public void setInvoice_type(String invoice_type) {
            this.invoice_type = invoice_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getSend_type() {
            return send_type;
        }

        public void setSend_type(String send_type) {
            this.send_type = send_type;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getProvinces() {
            return provinces;
        }

        public void setProvinces(String provinces) {
            this.provinces = provinces;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getAddress_title() {
            return address_title;
        }

        public void setAddress_title(String address_title) {
            this.address_title = address_title;
        }

        public String getMeridian() {
            return meridian;
        }

        public void setMeridian(String meridian) {
            this.meridian = meridian;
        }

        public String getWeft() {
            return weft;
        }

        public void setWeft(String weft) {
            this.weft = weft;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getAddress_info() {
            return address_info;
        }

        public void setAddress_info(String address_info) {
            this.address_info = address_info;
        }
    }
}
