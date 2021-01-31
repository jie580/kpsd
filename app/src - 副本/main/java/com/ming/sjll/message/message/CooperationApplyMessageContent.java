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
 * project_id：需求id
 * demand：需求标题
 * applyId 申请id
 */
@MessageTag(value = "KP:bCooperation", flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED)
public class CooperationApplyMessageContent extends MessageContent {

    private String content;
    private String projectId;
    private String demand;
    private String applyId;


    private CooperationApplyMessageContent(String content, String projectId, String demand, String applyId) {
        this.content = content;
        this.projectId = projectId;
        this.demand = demand;
        this.applyId = applyId;
    }

    public static CooperationApplyMessageContent obtain(String content, String projectId, String demand, String applyId) {
        return new CooperationApplyMessageContent(content, projectId, demand, applyId);
    }


    public CooperationApplyMessageContent(Parcel in) {
        content = ParcelUtils.readFromParcel(in);//该类为工具类，消息属性
        projectId = ParcelUtils.readFromParcel(in);
        demand = ParcelUtils.readFromParcel(in);
        applyId = ParcelUtils.readFromParcel(in);
    }

    public static final Creator<CooperationApplyMessageContent> CREATOR = new Creator<CooperationApplyMessageContent>() {

        @Override
        public CooperationApplyMessageContent createFromParcel(Parcel source) {
            return new CooperationApplyMessageContent(source);
        }

        @Override
        public CooperationApplyMessageContent[] newArray(int size) {
            return new CooperationApplyMessageContent[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, content);//该类为工具类，对消息中属性进行序列化
        ParcelUtils.writeToParcel(dest, projectId);
        ParcelUtils.writeToParcel(dest, demand);
        ParcelUtils.writeToParcel(dest, applyId);
    }

    public CooperationApplyMessageContent(byte[] data) {
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
            if (jsonObj.has("applyId")) {
                applyId = jsonObj.optString("applyId");
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
            if (applyId != null) {
                jsonObj.put("applyId", applyId);
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

    public String getapplyId() {
        return applyId;
    }

    public void setapplyId(String applyId) {
        this.applyId = applyId;
    }
}
