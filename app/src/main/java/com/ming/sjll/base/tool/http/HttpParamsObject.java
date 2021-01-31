package com.ming.sjll.base.tool.http;

import android.util.Log;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HttpParamsObject implements Serializable {

    private HashMap<String, String> stringMap;
//    private HashMap<String, String[]> arrayMap;
    //    private HashMap<String,Integer> intMap ;
    private HashMap<String, List<String>> StringList;

    public HttpParamsObject() {
        stringMap = new HashMap<String, String>();
        StringList = new HashMap<String, List<String>>();
//        arrayMap = new HashMap<String, String[]>();
    }

//    public void setParam(String key, List list)
//    {
////        for (int i = 0; i < list.size(); i++) {
//        StringList.add(list);
////        }
//    }

    /**
     * @return
     */
    public String getUrlParam() {
        String str = "";
        String str1 = praseMap(stringMap);
        String str2 = praseArrayMap(StringList);
        if (!str1.equals("") && !str2.equals("")) {
            str = str1 + "&" + str2;
        } else {
            str = str1.equals("") ? str2 : str1;
        }
        return str;
    }

    /**
     * 解析map
     */
    private String praseArrayMap(HashMap<String, List<String>> map) {
        StringBuffer sb = new StringBuffer();
        if (map != null && !map.isEmpty()) {
            try {
                boolean f = true;
                String v;
                for (String k : map.keySet()) {
                    if (k != null && !"".equals(k)) {
                        List<String> Vallarr = map.get(k);
                        if(Vallarr == null)
                        {
                            continue;
                        }
                        for (int i = 0; i < Vallarr.size(); i++) {
                            v = Vallarr.get(i).trim();
                            if (!f)
                                sb.append("&");
                            sb.append(k+"[]").append("=").append(v);
                            f = false;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }



    /**
     * 打印hashMap内部结构
     *
     * @param map map
     * @param <K> 泛型key
     * @param <V> 泛型value
     * @throws Exception
     */
    public static <K, V> void printHashMapStructures(HashMap<K, V> map) throws Exception {
        Class<?> hashMap = Class.forName("java.util.HashMap");
        Class<?> node = Class.forName("java.util.HashMap$Node");
        Field[] fields = hashMap.getDeclaredFields();
        K[] tableArr = null;
        AccessibleObject.setAccessible(fields, true);
        //获取table
        for (Field field : fields) {
            if ("table".equals(field.getName())) {
                tableArr = ((K[]) field.get(map));
            }
        }
        int i = 0;
        //遍历数组
        for (K o : tableArr) {
            System.out.println("index=" + i++);
            if (o != null) {
                //打印node
                printHashMapNode(node, o);
                Field fieldNode = node.getDeclaredField("next");
                fieldNode.setAccessible(true);
                while ((o = (K) fieldNode.get(o)) != null) {
                    System.out.print("--");
                    printHashMapNode(node, o);
                }
            }
        }
    }

    /**
     * 打印hashMap内部Node
     *
     * @param node
     * @param o
     * @throws Exception
     */
    public static void printHashMapNode(Class node, Object o) throws Exception {
        Field hash1 = node.getDeclaredField("hash");
        Field key1 = node.getDeclaredField("key");
        Field value1 = node.getDeclaredField("value");
        hash1.setAccessible(true);
        key1.setAccessible(true);
        value1.setAccessible(true);
        System.out.println("-->hash=" + hash1.get(o) + ";key=" + key1.get(o) + ";value=" + value1.get(o));
    }

    /**
     * 解析map
     */
    private String praseMap(HashMap<String, String> map) {
        try {
            printHashMapStructures(map);
        } catch (Exception e) {

        }

        StringBuffer sb = new StringBuffer();
        if (map != null && !map.isEmpty()) {
            try {
                boolean f = true;
                String v;

                for (String k : map.keySet()) {
                    if (k != null && !"".equals(k)) {
//                        Log.e("打印纸", "建：" + k);
//                        Log.e("打印纸", "值：" + map.get(k));
                        if(map.get(k) == null)
                        {
                            v = "";
                        }
                        else
                        {
                            v = map.get(k).toString().trim();
                        }

                        if (!f)
                            sb.append("&");
                        sb.append(k).append("=").append(v);
                        f = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString().trim();
    }

    public void setParam(String key, String value) {
        stringMap.put(key, value);
    }

    public void setParam(String key, boolean value) {
        stringMap.put(key, value+"");
    }

    public String getStringMapParam(String key) {

        return stringMap.get(key) == null ? "": stringMap.get(key);
    }

    public void setParam(String key, Integer value) {
        stringMap.put(key, value.toString());
    }
//    public String[] getStringArrayParam(String key) {
//
//        return arrayMap.get(key) == null ? imgs : arrayMap.get(key);
//    }

    public void setParam(String key, List<String> value) {
        StringList.put(key, value);
    }

    public List<String> getStringListParam(String key) {

        return StringList.get(key) == null ? (new LinkedList<>()): StringList.get(key);
    }


//    public HashMap<String,Integer> getIntegerMapParam()
//    {
//        return intMap;
//    }


//    public void setParam(String key, String[] value) {
//        arrayMap.put(key, value);
//    }

    public HashMap<String, List<String>> getArrayMapParam() {
        return StringList;
    }


}
