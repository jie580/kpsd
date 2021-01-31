package com.ming.sjll.Bean;

import android.view.animation.Animation;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

/**
 * 职业列表
 */
public class OccupationList  extends BaseBean {
    private List<DataBean> data;

    public  List<DataBean> getData() {
        return data;
    }

    public void setData( List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean extends BaseBean {


        private boolean showDelWarp = false;
        private boolean showShare = false;



        private String occupation_id;
        private boolean virtualIsSelect;
        private List<GetInfoBean.DataBean> member;

        private int id;
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }

        private boolean is_select;

        private boolean isSelect;

        private String title;
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        private String icon;
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }



        private String small_icon;
        public String getSmall_icon() {
            return small_icon;
        }
        public void setSmall_icon(String small_icon) {
            this.small_icon = small_icon;
        }

        public Animation animation;
        public Animation getAnimation() {
            return animation;
        }
        public void setAnimation(Animation animation) {
            this.animation = animation;
        }


        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getOccupation_id() {
            return occupation_id;
        }

        public void setOccupation_id(String occupation_id) {
            this.occupation_id = occupation_id;
        }

        public boolean isVirtualIsSelect() {
            return virtualIsSelect;
        }

        public void setVirtualIsSelect(boolean virtualIsSelect) {
            this.virtualIsSelect = virtualIsSelect;
        }

        public boolean isIs_select() {
            return is_select;
        }

        public void setIs_select(boolean is_select) {
            this.is_select = is_select;
        }

        public List<GetInfoBean.DataBean> getMember() {
            return member;
        }

        public void setMember(List<GetInfoBean.DataBean> member) {
            this.member = member;
        }

        public boolean isShowDelWarp() {
            return showDelWarp;
        }

        public void setShowDelWarp(boolean showDelWarp) {
            this.showDelWarp = showDelWarp;
        }

        public boolean isShowShare() {
            return showShare;
        }

        public void setShowShare(boolean showShare) {
            this.showShare = showShare;
        }
    }
}