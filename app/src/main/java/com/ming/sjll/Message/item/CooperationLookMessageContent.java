package com.ming.sjll.Message.item;

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
 * project_id：需求id
 * demand：需求标题
 */
@MessageTag(value = "KP:shareProject", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CooperationLookMessageContent extends MessageContent {

    private String content;
    private String projectId;
    private String demand;
    private String bgImg;

    public void setContent(String content) {
        this.content = content;
    }

    public String getprojectId() {
        return projectId;
    }

    public void setprojectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }


    public String getbgImg() {
        return bgImg;
    }
    public void setbgImg(String bgImg) {
        this.bgImg = bgImg;
    }

    private CooperationLookMessageContent(String content, String projectId, String demand, String applyId) {
        this.content = content;
        this.projectId = projectId;
        this.demand = demand;
        this.bgImg = bgImg;
    }

    public static CooperationLookMessageContent obtain(String content, String projectId, String demand, String applyId) {
        return new CooperationLookMessageContent(content,projectId,demand,applyId);
    }

    public CooperationLookMessageContent(Parcel in) {
        content = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        projectId = ParcelUtils.readFromParcel(in);
        demand = ParcelUtils.readFromParcel(in);

        bgImg = ParcelUtils.readFromParcel(in);
    }

    public static final Creator<CooperationLookMessageContent> CREATOR = new Creator<CooperationLookMessageContent>() {

        @Override
        public CooperationLookMessageContent createFromParcel(Parcel source) {
            return new CooperationLookMessageContent(source);
        }

        @Override
        public CooperationLookMessageContent[] newArray(int size) {
            return new CooperationLookMessageContent[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, content);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, projectId);
        ParcelUtils.writeToParcel(dest, demand);
        ParcelUtils.writeToParcel(dest, bgImg);


    }

    public CooperationLookMessageContent(byte[] data) {
        String jsonStr = null;
        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("content")) {
                content = jsonObj.optString("content");
            }
            if (jsonObj.has("projectId")) {
                projectId = jsonObj.optString("projectId");
            }
            if (jsonObj.has("demand")) {
                demand = jsonObj.optString("demand");
            }
            if (jsonObj.has("bgImg")) {
                bgImg = jsonObj.optString("bgImg");
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
            if (projectId != null) {
                jsonObj.put("projectId", projectId);
            }
            if (demand != null) {
                jsonObj.put("demand", demand);
            }
            if (bgImg != null) {
                jsonObj.put("bgImg", bgImg);
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
        return content;
    }
}
