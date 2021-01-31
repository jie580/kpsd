package com.ming.sjll.Bean;


import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

public class ProjectOrderListBean extends BaseBean  implements Serializable{

    public DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean extends BaseBean implements Serializable{

        public List<DataBeanX> data;

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }


        public int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int id) {
            this.total = total;
        }


        public int per_page;

        public int getPerPage() {
            return per_page;
        }

        public void setPerPage(int id) {
            this.per_page = per_page;
        }

        public int current_page;

        public int getCurrentPage() {
            return current_page;
        }

        public void setCurrentPage(int id) {
            this.current_page = current_page;
        }


        public int last_page;

        public int getLastPage() {
            return last_page;
        }

        public void setLastPage(int id) {
            this.last_page = last_page;
        }


    }








        public static class DataBeanX implements Serializable {

        private String  apply_id;
        private String  project_id;
        private String  user_id;
        private String  user_id_supplier;
        private String  occupation_id;
        private String  title;
        private String  service_type;
        private String  create_time;
        private String  status;
        private String  background_image;
        private String  demand;
        private String  brand;
        private String  order_no;
        private String  address;
        private String  im_uid;
        private String  im_name;
        private String  default_avatar;
        private String  im_city;
        private String  date_str;
        private String  serviceTypeStr;
        public List<GetInfoDateListBean.DataBean.DataBeanY>  dateList;
        public List<String>  otherDate;
        private String  is_pay;
        private String  countDown;

        private String  is_join;
        private String  created_time;
        private String  budget;
        private String  is_scheme;
        private String  is_local;
        private String  content;
        private String  memberStatus;
        public List<GetInfoBean.DataBean>  member;
        private List<String>  applyIds;
        private String  start_time;
        private String  end_time;


        private String revised_price;
        private String paid_money;
        private String balance;


        private String invoice_status;
        public InvoiceBean.DataBean invoice_info;
        private String closing_time;
        private String money_type;
        private String is_freeze;

        private String provinces;
        private String city;
        private String county;



        public String getApply_id() {
            return apply_id;
        }

        public void setApply_id(String apply_id) {
            this.apply_id = apply_id;
        }

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

        public String getUser_id_supplier() {
            return user_id_supplier;
        }

        public void setUser_id_supplier(String user_id_supplier) {
            this.user_id_supplier = user_id_supplier;
        }

        public String getOccupation_id() {
            return occupation_id;
        }

        public void setOccupation_id(String occupation_id) {
            this.occupation_id = occupation_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getService_type() {
            return service_type;
        }

        public void setService_type(String service_type) {
            this.service_type = service_type;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBackground_image() {
            return background_image;
        }

        public void setBackground_image(String background_image) {
            this.background_image = background_image;
        }

        public String getDemand() {
            return demand;
        }

        public void setDemand(String demand) {
            this.demand = demand;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getDefault_avatar() {
            return default_avatar;
        }

        public void setDefault_avatar(String default_avatar) {
            this.default_avatar = default_avatar;
        }

        public String getIm_city() {
            return im_city;
        }

        public void setIm_city(String im_city) {
            this.im_city = im_city;
        }

        public String getDate_str() {
            return date_str;
        }

        public void setDate_str(String date_str) {
            this.date_str = date_str;
        }

        public String getServiceTypeStr() {
            return serviceTypeStr;
        }

        public void setServiceTypeStr(String serviceTypeStr) {
            this.serviceTypeStr = serviceTypeStr;
        }

        public List<GetInfoDateListBean.DataBean.DataBeanY> getDateList() {
            return dateList;
        }

        public void setDateList(List<GetInfoDateListBean.DataBean.DataBeanY> dateList) {
            this.dateList = dateList;
        }

        public List<String> getOtherDate() {
            return otherDate;
        }

        public void setOtherDate(List<String> otherDate) {
            this.otherDate = otherDate;
        }

        public String getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(String is_pay) {
            this.is_pay = is_pay;
        }

        public String getCountDown() {
            return countDown;
        }

        public void setCountDown(String countDown) {
            this.countDown = countDown;
        }

        public String getIs_join() {
            return is_join;
        }

        public void setIs_join(String is_join) {
            this.is_join = is_join;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getIs_scheme() {
            return is_scheme;
        }

        public void setIs_scheme(String is_scheme) {
            this.is_scheme = is_scheme;
        }

        public String getIs_local() {
            return is_local;
        }

        public void setIs_local(String is_local) {
            this.is_local = is_local;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getMemberStatus() {
            return memberStatus;
        }

        public void setMemberStatus(String memberStatus) {
            this.memberStatus = memberStatus;
        }

        public List<GetInfoBean.DataBean> getMember() {
            return member;
        }

        public void setMember(List<GetInfoBean.DataBean> member) {
            this.member = member;
        }


        public List<String> getApplyIds() {
            return applyIds;
        }

        public void setApplyIds(List<String> applyIds) {
            this.applyIds = applyIds;
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

        public String getRevised_price() {
            return revised_price;
        }

        public void setRevised_price(String revised_price) {
            this.revised_price = revised_price;
        }

        public String getPaid_money() {
            return paid_money;
        }

        public void setPaid_money(String paid_money) {
            this.paid_money = paid_money;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getInvoice_status() {
            return invoice_status;
        }

        public void setInvoice_status(String invoice_status) {
            this.invoice_status = invoice_status;
        }

        public InvoiceBean.DataBean getInvoice_info() {
            return invoice_info;
        }

        public void setInvoice_info(InvoiceBean.DataBean invoice_info) {
            this.invoice_info = invoice_info;
        }

        public String getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(String closing_time) {
            this.closing_time = closing_time;
        }

        public String getMoney_type() {
            return money_type;
        }

        public void setMoney_type(String money_type) {
            this.money_type = money_type;
        }

            public String getIs_freeze() {
                return is_freeze;
            }

            public void setIs_freeze(String is_freeze) {
                this.is_freeze = is_freeze;
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
        }
}
