package com.ming.sjll.Message.message;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.push.common.RLog;

/**
 * KP:shareServe
 * <p>
 * userId: 用户id
 * headImg:用户图像
 * goodsId:分享的服务id
 * goodsTitle:分享的服务标题
 * address:分享的服务地址
 * payment:分享的薪酬(字符串)
 */
@MessageTag(value = "KP:shareServe", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class ShareServiceMessageContent extends MessageContent {

    private String userId;
    private String headImg;
    private String goodsId;
    private String goodsTitle;
    private String address;
    private String payment;
//    private String content;

//    public void setContent(String content) {
//        this.content = content;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    private ShareServiceMessageContent(String userId, String headImg, String goodsId, String goodsTitle, String address, String payment) {
        this.userId = userId;
        this.headImg = headImg;
        this.goodsId = goodsId;
        this.goodsTitle = goodsTitle;
        this.address = address;
        this.payment = payment;
//        this.content = content;
    }

    public static ShareServiceMessageContent obtain(String userId, String headImg, String goodsId, String goodsTitle, String address, String payment) {
        return new ShareServiceMessageContent(userId, headImg, goodsId, goodsTitle, address, payment);
    }


    public ShareServiceMessageContent(Parcel in) {
        userId = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        headImg = ParcelUtils.readFromParcel(in);
        goodsId = ParcelUtils.readFromParcel(in);
        goodsTitle = ParcelUtils.readFromParcel(in);
        address = ParcelUtils.readFromParcel(in);
        payment = ParcelUtils.readFromParcel(in);
//        content = ParcelUtils.readFromParcel(in);
    }

    public static final Creator<ShareGoodsMessageContent> CREATOR = new Creator<ShareGoodsMessageContent>() {

        @Override
        public ShareGoodsMessageContent createFromParcel(Parcel source) {
            return new ShareGoodsMessageContent(source);
        }

        @Override
        public ShareGoodsMessageContent[] newArray(int size) {
            return new ShareGoodsMessageContent[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        ParcelUtils.writeToParcel(dest, userId);
        ParcelUtils.writeToParcel(dest, headImg);
        ParcelUtils.writeToParcel(dest, goodsId);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, goodsTitle);
        ParcelUtils.writeToParcel(dest, address);
        ParcelUtils.writeToParcel(dest, payment);
//        ParcelUtils.writeToParcel(dest, content);
    }

    public ShareServiceMessageContent(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("userId")) {
                userId = jsonObj.optString("userId");
            }
            if (jsonObj.has("headImg")) {
                headImg = jsonObj.optString("headImg");
            }
            if (jsonObj.has("goodsId")) {
                goodsId = jsonObj.optString("goodsId");
            }
            if (jsonObj.has("goodsTitle")) {
                goodsTitle = jsonObj.optString("goodsTitle");
            }

            if (jsonObj.has("address")) {
                address = jsonObj.optString("address");
            }
            if (jsonObj.has("payment")) {
                payment = jsonObj.optString("payment");
            }

        } catch (JSONException e) {
            RLog.e("JSONException", e.getMessage());
        }

    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", getContent());
            if (userId != null) {
                jsonObj.put("userId", userId);
            }
            if (headImg != null) {
                jsonObj.put("headImg", headImg);
            }
            if (goodsId != null) {
                jsonObj.put("goodsId", goodsId);
            }

            if (goodsTitle != null) {
                jsonObj.put("goodsTitle", goodsTitle);
            }

            if (address != null) {
                jsonObj.put("address", address);
            }

            if (payment != null) {
                jsonObj.put("payment", payment);
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getContent() {
        return "";
    }
}
