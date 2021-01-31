package com.ming.sjll.base.tool.http;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;

public class HttpParamsObject {

    private HashMap<String, String> stringMap;
    private HashMap<String, String[]> arrayMap;
    private HashMap<String, Integer> intMap;
    private List<List> StringList;

    public HttpParamsObject() {
        stringMap = new HashMap<String, String>();
        intMap = new HashMap<String, Integer>();
        arrayMap = new HashMap<String, String[]>();
    }

//    public void setParam(String key, List list)
//    {
////        for (int i = 0; i < list.size(); i++) {
//        StringList.add(list);
////        }
//    }

    public void setParam(String key, String value) {
        stringMap.put(key, value);
    }

    public HashMap<String, String> getStringMapParam() {
        return stringMap;
    }

    public void setParam(String key, Integer value) {
        intMap.put(key, value);
    }

    public HashMap<String, Integer> getIntegerMapParam() {
        return intMap;
    }


    public void setParam(String key, String[] value) {
        arrayMap.put(key, value);
    }

    public HashMap<String, String[]> getArrayMapParam() {
        return arrayMap;
    }


}
