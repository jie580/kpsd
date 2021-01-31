package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;


public class WalletBean extends BaseBean {
    private DataBean data;

    public  DataBean getData() {
        return data;
    }

    public void setData( DataBean data) {
        this.data = data;
    }


    public static class DataBean  {

        private String  user_id;
        private String  money;
        private String  pledge;
        private String  freeze_money;
        private String  projectAmount;
        private DataBeanX  companyAccount;
        private DataBeanX  aliPay;
        private String  invoiceNumber;

        public String getInvoiceNumber() {
            return invoiceNumber;
        }

        public void setInvoiceNumber(String invoiceNumber) {
            this.invoiceNumber = invoiceNumber;
        }

        public DataBeanX getAliPay() {
            return aliPay;
        }

        public void setAliPay(DataBeanX aliPay) {
            this.aliPay = aliPay;
        }

        public DataBeanX getCompanyAccount() {
            return companyAccount;
        }

        public void setCompanyAccount(DataBeanX companyAccount) {
            this.companyAccount = companyAccount;
        }

        public String getProjectAmount() {
            return projectAmount;
        }

        public void setProjectAmount(String projectAmount) {
            this.projectAmount = projectAmount;
        }

        public String getFreeze_money() {
            return freeze_money;
        }

        public void setFreeze_money(String freeze_money) {
            this.freeze_money = freeze_money;
        }

        public String getPledge() {
            return pledge;
        }

        public void setPledge(String pledge) {
            this.pledge = pledge;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }



    }
    public static class DataBeanX  {
        private String  id;
        private String  type;
        private String  card;
        private String  user_name;
        private String  bank;
        private String  user_id;
        private String  hidden_card;

        public String getHidden_card() {
            return hidden_card;
        }

        public void setHidden_card(String hidden_card) {
            this.hidden_card = hidden_card;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
