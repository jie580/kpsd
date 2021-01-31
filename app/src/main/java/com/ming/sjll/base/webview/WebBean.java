package com.ming.sjll.base.webview;

import com.ming.sjll.base.bean.BaseBean;

/**
 * @author luoming
 * created at 2019-10-21 13:44
 */

public class WebBean extends BaseBean {


    private String title;
    private int id;
    private String url;
    private String orderNo;
    private boolean isFloat;
    private WxPayBean payJson;
    private String orderInfo;
    private int type;
    private String shareID;
    private String typeStr;

    public String getShareID() {
        return shareID;
    }

    public void setShareID(String shareID) {
        this.shareID = shareID;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public WxPayBean getPayJson() {
        return payJson;
    }

    public void setPayJson(WxPayBean payJson) {
        this.payJson = payJson;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public boolean isFloat() {
        return isFloat;
    }

    public void setFloat(boolean aFloat) {
        isFloat = aFloat;
    }
}