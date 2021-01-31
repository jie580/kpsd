package com.ming.sjll.Message.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MessageChatViewModel extends BaseObservable {

    private int projectVisible = View.GONE;
    private GetChatProjectBean getChatProjectBean = new GetChatProjectBean();

    private int groupInfoVisible = View.GONE;
    private List<String> memberInfos = new ArrayList<>();

    public int getProjectVisible() {
        return projectVisible;
    }

    public void setProjectVisible(int projectVisible) {
        this.projectVisible = projectVisible;
    }

    public GetChatProjectBean getGetChatProjectBean() {
        return getChatProjectBean;
    }

    public void setGetChatProjectBean(GetChatProjectBean getChatProjectBean) {
        this.getChatProjectBean = getChatProjectBean;
    }

    public int getGroupInfoVisible() {
        return groupInfoVisible;
    }

    public void setGroupInfoVisible(int groupInfoVisible) {
        this.groupInfoVisible = groupInfoVisible;
    }

    @Bindable
    public List<String> getMemberInfos() {
        return memberInfos;
    }

    public void setMemberInfos(List<String> memberInfos) {
        this.memberInfos = memberInfos;
        notifyPropertyChanged(com.ming.sjll.BR.memberInfos);

    }
}
