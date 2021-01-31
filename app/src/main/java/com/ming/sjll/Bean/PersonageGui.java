package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

/**
 * 个人中心->个人菜单
 */
public class PersonageGui extends BaseBean {
    private List<OccupationList.DataBean> data;

    public  List<OccupationList.DataBean> getData() {
        return data;
    }

    public void setData( List<OccupationList.DataBean> data) {
        this.data = data;
    }


    public static class DataBean  {

        private int id;
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }

        private boolean isetSelected;
        public boolean getIsetSelected() {
            return isetSelected;
        }
        public void setIsetSelected(boolean isetSelected) {
            this.isetSelected = isetSelected;
        }

        private int icon;
        public int getIcon() {
            return icon;
        }
        public void setIcon(int icon) {
            this.icon = icon;
        }

        private int selectIcon;
        public int getSelectIcon() {
            return selectIcon;
        }
        public void setSelectIcon(int selectIcon) {
            this.selectIcon = selectIcon;
        }

    }
}
