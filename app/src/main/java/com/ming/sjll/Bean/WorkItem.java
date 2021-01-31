package com.ming.sjll.Bean;

import com.ming.sjll.base.bean.BaseBean;
import com.ming.sjll.search.bean.ProjecItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作品
 */
public class WorkItem extends BaseBean {

    private WorkItem.DataBean data;

    public WorkItem.DataBean getData() {
        return data;
    }

    public void setData(WorkItem.DataBean data) {
        this.data = data;
    }


    public static class DataBean implements Serializable {
        private int id;
        private int uid;
        private String user_id;
        private String name;
        private String default_avatar;
        private int work_id;
        private String cover_img;
        private String title;
        private String describe;
        private int is_like;
        private boolean is_show_work;
        private List<String> occupation;
        private ArrayList<String> work_img;
        private ArrayList<String> imgList;
        private int type;
        private String  video_url;
        private String  created_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public boolean getIs_show_work() {
            return is_show_work;
        }

        public void setIs_show_work(boolean is_show_work) {
            this.is_show_work = is_show_work;
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

        public int getWorkId() {
            return work_id;
        }

        public void setWorkId(int work_id) {
            this.work_id = work_id;
        }

        public String getCoverImg() {
            return cover_img;
        }

        public void setCoverImg(String cover_img) {
            this.cover_img = cover_img;
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



        public int getIsLike() {
            return is_like;
        }

        public void setIsLike(int is_like) {
            this.is_like = is_like;
        }

        public List<String> getOccupation() {
            return occupation;
        }

        public void setOccupation(List<String> occupation) {
            this.occupation = occupation;
        }

        public ArrayList<String> getWorkImg() {
            return work_img;
        }
        public void setWorkImg(ArrayList<String> work_img) {
            this.work_img = work_img;
        }

        public ArrayList<String> getimgList() {
            return imgList;
        }
        public void setimgList(ArrayList<String> imgList) {
            this.imgList = imgList;
        }

        public int gettype() {
            return type;
        }
        public void settype(int type) {
            this.type = type;
        }

        public String getvideo_url() {
            return video_url;
        }
        public void setvideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getcreated_time() {
            return created_time;
        }
        public void setcreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
