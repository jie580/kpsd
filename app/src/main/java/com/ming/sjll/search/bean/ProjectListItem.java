package com.ming.sjll.search.bean;

import com.ming.sjll.base.bean.BaseBean;

import java.util.List;

public class ProjectListItem extends BaseBean {

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }


    public static class DataBeanX {

        private List<ProjecItem.DataBean> data;

        public List<ProjecItem.DataBean> getData() {
            return data;
        }

        public void setData(List<ProjecItem.DataBean> data) {
            this.data = data;
        }


        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int id) {
            this.total = total;
        }


        private int per_page;

        public int getPerPage() {
            return per_page;
        }

        public void setPerPage(int id) {
            this.per_page = per_page;
        }

        private int current_page;

        public int getCurrentPage() {
            return current_page;
        }

        public void setCurrentPage(int id) {
            this.current_page = current_page;
        }


        private int last_page;

        public int getLastPage() {
            return last_page;
        }

        public void setLastPage(int id) {
            this.last_page = last_page;
        }


//        public static class DataBean {
//
//            boolean isChange = true;
//
//            public boolean getIsChange() {
//                return isChange;
//            }
//
//            public void setIsChange(boolean isChange) {
//                this.isChange = isChange;
//            }
//
//            private String closing_time;
//
//            public String getClosingTime() {
//                return closing_time;
//            }
//
//            public void setClosingTime(String closing_time) {
//                this.closing_time = closing_time;
//            }
//
//            private String background_image;
//
//            public String getBackgroundImage() {
//                return background_image;
//            }
//
//            public void setBackgroundImage(String background_image) {
//                this.background_image = background_image;
//            }
//
//            private String brand;
//
//            public String getBrand() {
//                return brand;
//            }
//
//            public void setBrand(String brand) {
//                this.brand = brand;
//            }
//
//
//            private String[] tag;
//
//            public String[] getTag() {
//                return tag;
//            }
//
//            public void setTag(String[] tag) {
//                this.tag = tag;
//            }
//
//
//            private int id;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//
//            private boolean isNotice;
//
//            public boolean getIsNotice() {
//                return isNotice;
//            }
//
//            public void setIsNotice(boolean isNotice) {
//                this.isNotice = isNotice;
//            }
//
//
//            private List<TimeList> time_list;
//
//            public List<TimeList> getTimeList() {
//                return time_list;
//            }
//
//            public void setTimeList(List<TimeList> time_list) {
//                this.time_list = time_list;
//            }
//
//            public static class TimeList {
//                private String start_time;
//
//                public String getStartTime() {
//                    return start_time;
//                }
//
//                public void setStartTime(String start_time) {
//                    this.start_time = start_time;
//                }
//
//
//                private String end_time;
//
//                public String getEndTime() {
//                    return end_time;
//                }
//
//                public void setEndTime(String end_time) {
//                    this.end_time = end_time;
//                }
//
//            }
//
//
//        }
    }
}
