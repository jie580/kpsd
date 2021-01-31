package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品
 */
public class GoodsItem extends BaseBean implements Serializable {

    private GoodsItem.DataBean data;

    public GoodsItem.DataBean getData() {
        return data;
    }

    public void setData(GoodsItem.DataBean data) {
        this.data = data;
    }


    public static class DataBean implements Serializable {
        private String goods_id;
        private String company_id;
        private String user_id;
        private String uid;
        private String title;
        private String describe;
        private String cover_img;
        private String deposit;
        private String address;
        private String is_inventory;
        private String is_mail;
        private List<String> payment_str;
        private List<String> date_time;
        private String is_collect;
        private String im_uid;
        private String im_name;
        private String payment;
        private List<String> imgList;
        private  String price;
        private  String Value;
        private  String units;
        private  String name;
        private  String full_name;
        private  String default_avatar;
        private String type;
        private String area_code;
        private String provinces;
        private String city;
        private String county;
        private String address_title;
        private String street;
        private String meridian;
        private String weft;
        private String email;
        private String is_relevancy;
        private List<DataBeanX> paymentList;
        private List<String> paymentStr;
        private List<String> serviceType;
        private String serviceTypeStr;
        private List<String> sendType;
        private String sendTypeStr;
        private List<String> userIds;
        private String im_city;
        private String num;
        private GetInfoBean.DataBean modelInfo;

        private List<String> otherDate;
        private String countDown;
        private String order_no;
        private String send_type;
        private String create_time;
        private String date_str;
        private String total_price;
        private String status;
        private List<GetInfoBean.DataBean> memberList;
        private String start_time;
        private String end_time;
        private String phone;
        private String is_pay;
        private String shop_name;




        private List<GetInfoDateListBean.DataBean.DataBeanY> dateList;
        public List<GetInfoDateListBean.DataBean.DataBeanY> getDateList() {
            return dateList;
        }

        public void setDateList(List<GetInfoDateListBean.DataBean.DataBeanY> dateList) {
            this.dateList = dateList;
        }


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIs_relevancy() {
            return is_relevancy;
        }

        public void setIs_relevancy(String is_relevancy) {
            this.is_relevancy = is_relevancy;
        }

        public List<DataBeanX> getPaymentList() {
            return paymentList;
        }

        public void setPaymentList(List<DataBeanX> paymentList) {
            this.paymentList = paymentList;
        }

        public List<String> getPaymentStr() {
            return paymentStr;
        }

        public void setPaymentStr(List<String> paymentStr) {
            this.paymentStr = paymentStr;
        }

        public List<String> getServiceType() {
                return serviceType;
        }

        public void setServiceType(List<String> serviceType) {
            this.serviceType = serviceType;
        }

        public String getServiceTypeStr() {
            return serviceTypeStr;
        }

        public void setServiceTypeStr(String serviceTypeStr) {
            this.serviceTypeStr = serviceTypeStr;
        }

        public List<String> getUserIds() {
            return userIds;
        }

        public void setUserIds(List<String> userIds) {
            this.userIds = userIds;
        }

        public String getIm_city() {
            return im_city;
        }

        public void setIm_city(String im_city) {
            this.im_city = im_city;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public List<String> getSendType() {
            return sendType;
        }


        public void setSendType(List<String> sendType) {
            this.sendType = sendType;
        }

        public String getSendTypeStr() {
            return sendTypeStr;
        }

        public void setSendTypeStr(String sendTypeStr) {
            this.sendTypeStr = sendTypeStr;
        }

        public List<String> getPayment_str() {
            return payment_str;
        }

        public void setPayment_str(List<String> payment_str) {
            this.payment_str = payment_str;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String value) {
            Value = value;
        }

        public GetInfoBean.DataBean getModelInfo() {
            return modelInfo;
        }

        public void setModelInfo(GetInfoBean.DataBean modelInfo) {
            this.modelInfo = modelInfo;
        }

        public List<String> getOtherDate() {
            return otherDate;
        }

        public void setOtherDate(List<String> otherDate) {
            this.otherDate = otherDate;
        }

        public String getCountDown() {
            return countDown;
        }

        public void setCountDown(String countDown) {
            this.countDown = countDown;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getSend_type() {
            return send_type;
        }

        public void setSend_type(String send_type) {
            this.send_type = send_type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDate_str() {
            return date_str;
        }

        public void setDate_str(String date_str) {
            this.date_str = date_str;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<GetInfoBean.DataBean> getMemberList() {
            return memberList;
        }

        public void setMemberList(List<GetInfoBean.DataBean> memberList) {
            this.memberList = memberList;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(String is_pay) {
            this.is_pay = is_pay;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }


        public static class DataBeanX implements Serializable {
            private String price;
            private String type = "请选择";
            private String price_str;
            private boolean is_select;


            public String getPrice_str() {
                return price_str;
            }

            public void setPrice_str(String price_str) {
                this.price_str = price_str;
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

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getCover_img() {
            return cover_img;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIs_inventory() {
            return is_inventory;
        }

        public void setIs_inventory(String is_inventory) {
            this.is_inventory = is_inventory;
        }

        public String getIs_mail() {
            return is_mail;
        }

        public void setIs_mail(String is_mail) {
            this.is_mail = is_mail;
        }



        public List<String> getDate_time() {
            return date_time;
        }

        public void setDate_time(List<String> date_time) {
            this.date_time = date_time;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public String getIm_uid() {
            return im_uid;
        }

        public void setIm_uid(String im_uid) {
            this.im_uid = im_uid;
        }

        public String getIm_name() {
            return im_name;
        }

        public void setIm_name(String im_name) {
            this.im_name = im_name;
        }

        public String getPayment() {
            return payment;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public List<String> getImgList() {
            return imgList;
        }

        public void setImgList(List<String> imgList) {
            this.imgList = imgList;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
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
    }
}
