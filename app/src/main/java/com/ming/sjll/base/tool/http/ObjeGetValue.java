package com.ming.sjll.base.tool.http;

import android.util.Log;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ObjeGetValue {
//    private Object data;
//
//    public ObjeGetValue(Object o)
//    {
//        data = o;
//    }

    //    转化为List
    public static List<Object> getList(Object Data) {

        List<Object> listData = new LinkedList<>();
        if (Data instanceof JSONArray) {
            try {
                JSONArray tempData = (JSONArray) Data;
                int l = tempData.length();
                for (int i = 0; i < l; i++) {
                    Object d = tempData.get(i);
                    if (d == null) {
                        continue;
                    }
                    listData.add(d);
                }
            } catch (Exception e) {
                Log.e("getList错误：", e.getMessage());
            }
        }
        return listData;
    }

    /**
     * 直接获取key
     *
     * @param key
     * @param Data
     * @return
     */
    public static Object GetValu(String key, Object Data) {

        key = key.replace("@||@,", "");
        key = key.replace("[", "");
        key = key.replace("]", "");
        key = key.trim();
        String[] strArr = key.split("\\.");
        Object temp = null;

        if (Data instanceof JSONObject) {
            JSONObject tempData = (JSONObject) Data;
            try {
                temp = (Object) tempData.getJSONObject((strArr[0]));
//                temp =  (Object)tempData.optJSONObject(strArr[0]);
            } catch (Exception e) {
                Log.e("解析json错误", e.getMessage());
                try {
                    temp = (Object) tempData.getJSONArray((strArr[0]));
                } catch (Exception ee) {
                    Log.e("解析json错误2", ee.getMessage());
                    try {
                        temp = (Object) tempData.getBoolean((strArr[0]));
                    } catch (Exception eee) {
                        Log.e("解析json错误3", eee.getMessage());
                        try {
                            temp = (Object) tempData.getString((strArr[0]));
                        } catch (Exception eeee) {
                            Log.e("解析json错误4", eeee.getMessage());
                            try {
                                temp = (Object) tempData.getDouble((strArr[0]));
                            } catch (Exception eeeee) {
                                Log.e("解析json错误5", eeeee.getMessage());
                            }
                        }
                    }
                }
            }

        } else if (Data instanceof JSONArray) {
            JSONArray tempData = (JSONArray) Data;
            System.out.println(strArr);
            try {
                temp = (Object) tempData.get(Integer.parseInt(strArr[0]));
            } catch (Exception e) {
                Log.e("解析json错误", e.getMessage());
            }
        }

        System.out.println(strArr);
        if (temp == null || temp == "") {
            return null;
        }
        if (strArr.length <= 1) {
            return temp;
        } else {
            key = key.replace(strArr[0] + ".", "");
//                StringBuffer sb = new StringBuffer();
//                int i = 0;
//                for (int j = 0; j < strArr.length; j++) {
//
//                    if(j != 0 && !strArr[i].equals("")){
//                            sb.append(strArr[i].trim()+",");
////                        sb = sb + strArr[i] + ',';
//
//                    }
//                }

//                strArr[0] = "@||@";
//                if(key.equals(""))
//                {
//                    return null;
//                }
//                else
//                {
//                    sb.substring(key.length()-1,key.length());
            return GetValu(key, temp);
//                }
        }


    }


}
