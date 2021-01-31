package com.ming.sjll.Bean;


import android.view.View;

import com.ming.sjll.base.bean.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 */
public class GetInfoBean extends BaseBean  implements Serializable{

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean extends BaseBean implements Serializable {

        private int id;
        private String uid;
        private String apply_id;
        private String user_id;
        private String background_image;
        private String demand;
        private String brand;
        private String budget;
        private String is_scheme;
        private String provinces_id;
        private String provinces;
        private String city_id;
        private String city;
        private String county_id;
        private String county;
        private String street;
        private String is_local;
        private String brief;
        private String is_determine;
        private String user_id_determine;
        private String created_time;
        private String update_time;
        private String type;
        private String status;
        private List<String> tags;
        private String area_name;
        private String closing_time;
        private String showTime;
        private int showTagVisible = View.GONE;
        private String showTag;
        private String name;
        private String default_avatar;
        private String default_avatar_rect;
        private String sex;
        private String personalized;
        private String company_id;
        private String company_name;
        private String occupation;
        private List<String> occupation_string_array;
        private boolean isSelect;
        private boolean is_select;
        private String payment_str;
        private String face_img;
        private String height;
        private String chest;
        private String waistline;
        private String hipline;
        private String shoe_size;
        private String bra_size;
        private String skin_color;
        private String is_join;
        private String service_str;
        private String service_type;
        private String business_license;
//        private List<paymentBean> payment;
//        public List<paymentBean> getPayment() {
//            return payment;
//        }
//        public void setPayment(List<paymentBean> payment) {
//            this.payment = payment;
//        }


        private String is_collect;
        public String getIs_collect() {
            return is_collect;
        }
        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }


        private String im_uid;
        public String getIm_uid() {
            return im_uid;
        }
        public void setIm_uid(String im_uid) {
            this.im_uid = im_uid;
        }


        private String im_name;
        public String getIm_name() {
            return im_name;
        }
        public void setIm_name(String im_name) {
            this.im_name = im_name;
        }


        private List<occupationInfoBean> occupationInfo;

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getApply_id() {
            return apply_id;
        }

        public void setApply_id(String apply_id) {
            this.apply_id = apply_id;
        }

        public String getPayment_str() {
            return payment_str;
        }

        public void setPayment_str(String payment_str) {
            this.payment_str = payment_str;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getFace_img() {
            return face_img;
        }

        public void setFace_img(String face_img) {
            this.face_img = face_img;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getChest() {
            return chest;
        }

        public void setChest(String chest) {
            this.chest = chest;
        }

        public String getWaistline() {
            return waistline;
        }

        public void setWaistline(String waistline) {
            this.waistline = waistline;
        }

        public String getHipline() {
            return hipline;
        }

        public void setHipline(String hipline) {
            this.hipline = hipline;
        }

        public String getShoe_size() {
            return shoe_size;
        }

        public void setShoe_size(String shoe_size) {
            this.shoe_size = shoe_size;
        }

        public String getBra_size() {
            return bra_size;
        }

        public void setBra_size(String bra_size) {
            this.bra_size = bra_size;
        }

        public String getSkin_color() {
            return skin_color;
        }

        public void setSkin_color(String skin_color) {
            this.skin_color = skin_color;
        }

        public String getIs_join() {
            return is_join;
        }

        public void setIs_join(String is_join) {
            this.is_join = is_join;
        }

        public boolean isIs_select() {
            return is_select;
        }

        public void setIs_select(boolean is_select) {
            this.is_select = is_select;
        }

        public String getService_str() {
            return service_str;
        }

        public void setService_str(String service_str) {
            this.service_str = service_str;
        }

        public String getService_type() {
            return service_type;
        }

        public void setService_type(String service_type) {
            this.service_type = service_type;
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

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public List<String> getOccupation_string_array() {
            return occupation_string_array;
        }

        public void setOccupation_string_array(List<String> occupation_string_array) {
            this.occupation_string_array = occupation_string_array;
        }

        public String getBusiness_license() {
            return business_license;
        }

        public void setBusiness_license(String business_license) {
            this.business_license = business_license;
        }

        /**
         * 接收职业薪酬
         */
        public static class occupationInfoBean implements Serializable
        {
            private String occupation_id;
            public String getOccupation_id() {
                return occupation_id;
            }
            public void setOccupation_id(String occupation_id) {
                this.occupation_id = occupation_id;
            }

            private String occupation;
            public String getOccupation() {
                return occupation;
            }
            public void setOccupation(String occupation) {
                this.occupation = occupation;
            }

            private String icon;
            public String getIcon() {
                return icon;
            }
            public void setIcon(String icon) {
                this.icon = icon;
            }

            private List<paymentBean> payment;

            public List<paymentBean> getPayment() {
                return payment;
            }
            public void setPayment(List<paymentBean> payment) {
                this.payment = payment;
            }

            /**
             * 职业薪酬
             */
            public static class paymentBean implements Serializable
            {
                private String price;
                public String getPrice() {
                    return price;
                }
                public void setPrice(String price) {
                    this.price = price;
                }

                private String units = "请选择";
                public String getUnits() {
                    return units;
                }
                public void setUnits(String units) {
                    this.units = units;
                }
                private String occupation_id;
                public String getOccupation_id() {
                    return occupation_id;
                }
                public void setOccupation_id(String occupation_id) {
                    this.occupation_id = occupation_id;
                }
            }

        }

        public List<occupationInfoBean> getOccupationInfo() {
            return occupationInfo;
        }
        public void setOccupationInfo(List<occupationInfoBean> occupationInfo) {
            this.occupationInfo = occupationInfo;
        }


        public String getCompany_name() {
            return company_name;
        }
        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_id() {
            return company_id;
        }
        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getDefault_avatar_rect() {
            return default_avatar_rect;
        }
        public void setDefault_avatar_rect(String default_avatar_rect) {
            this.default_avatar_rect = default_avatar_rect;
        }

        public String getPersonalized() {
            return personalized;
        }
        public void setPersonalized(String personalized) {
            this.personalized = personalized;
        }


        public String getSex() {
            return sex;
        }
        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDefault_avatar() {
            return default_avatar;
        }

        public void setDefault_avatar(String default_avatar) {
            this.default_avatar = default_avatar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getProvinces_id() {
            return provinces_id;
        }

        public void setProvinces_id(String provinces_id) {
            this.provinces_id = provinces_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCounty_id() {
            return county_id;
        }

        public void setCounty_id(String county_id) {
            this.county_id = county_id;
        }

        public String getIs_local() {
            return is_local;
        }

        public void setIs_local(String is_local) {
            this.is_local = is_local;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public String getIs_determine() {
            return is_determine;
        }

        public void setIs_determine(String is_determine) {
            this.is_determine = is_determine;
        }

        public String getUser_id_determine() {
            return user_id_determine;
        }

        public void setUser_id_determine(String user_id_determine) {
            this.user_id_determine = user_id_determine;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(String closing_time) {
            this.closing_time = closing_time;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public int getShowTagVisible() {
            return showTagVisible;
        }

        public void setShowTagVisible(int showTagVisible) {
            this.showTagVisible = showTagVisible;
        }

        public String getShowTag() {
            return showTag;
        }

        public void setShowTag(String showTag) {
            this.showTag = showTag;
        }



        private String full_company_name;
        public String getFull_company_name() {
            return full_company_name;
        }
        public void setFull_company_name(String full_company_name) {
            this.full_company_name = full_company_name;
        }

        private String short_company_name;
        public String getShort_company_name() {
            return short_company_name;
        }
        public void setShort_company_name(String short_company_name) {
            this.short_company_name = short_company_name;
        }

        private String describe;
        public String getDescribe() {
            return describe;
        }
        public void setDescribe(String describe) {
            this.describe = describe;
        }

        private String address;
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }

        private String is_approve;
        public String getIs_approve() {
            return is_approve;
        }
        public void setIs_approve(String is_approve) {
            this.is_approve = is_approve;
        }

        private String cover_img;
        public String getCover_img() {
            return cover_img;
        }
        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        private String collect_num;
        public String getCollect_num() {
            return collect_num;
        }
        public void setCollect_num(String collect_num) {
            this.collect_num = collect_num;
        }

        private String logo_rect;
        public String getLogo_rect() {
            return logo_rect;
        }
        public void setLogo_rect(String logo_rect) {
            this.logo_rect = logo_rect;
        }


        private String path;
        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }

        private boolean face;
        public boolean getFace() {
            return face;
        }
        public void setFace(boolean face) {
            this.face = face;
        }


    }
}
