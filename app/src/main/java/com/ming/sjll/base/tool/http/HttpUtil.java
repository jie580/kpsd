package com.ming.sjll.base.tool.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.ming.sjll.api.Constant;

import org.json.JSONException;

public class HttpUtil {

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final int connectTimeout = 6000;
    private static final int readTimeout = 30000;

    /**
     * GET方式发送数据
     */
    public static String sendGet(String http, String data, String charset) {
        return request(http, data, charset, GET);
    }

    public static String sendGet(String http, HashMap<String, String> map, String charset) {
        return sendGet(http, map, charset, false);
    }

    public static String sendGet(String http, HashMap<String, String> map, String charset, boolean encode) {
        return sendGet(http, praseMap(map, charset, encode), charset);
    }

    /**
     * POST方式发送数据
     */
    public static String sendPost(String http, String data, String charset) {
        return request(http, data, charset, POST);
    }

    public static String sendPost(String http, HashMap<String, String> map, String charset) {
        return sendPost(http, map, charset, false);
    }

    public static String sendPost(String http, HashMap<String, String> map, String charset, boolean encode) {
        return sendPost(http, praseMap(map, charset, encode), charset);
    }

    /**
     * 解析map
     */
    private static String praseMap(HashMap<String, String> map, String charset, boolean encode) {
        StringBuffer sb = new StringBuffer();
        if (map != null && !map.isEmpty()) {
            try {
                boolean f = true;
                String v;
                for (String k : map.keySet()) {
                    if (k != null && !"".equals(k)) {
                        v = map.get(k).trim();
                        if (!f)
                            sb.append("&");
                        if (encode)
                            v = URLEncoder.encode(v, charset);
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

//    发送数据
    private static String request(String http, String data, String charset, String type) {
        StringBuffer sb = new StringBuffer();
        HttpURLConnection conn = null;
        OutputStreamWriter out = null;
        BufferedWriter bw = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            if (GET.equals(type) && data != null && !"".equals(data)) {
                http = http + "?" + data;
            }
            URL u = new URL(http);
            conn = (HttpURLConnection) u.openConnection();
//            添加头部信息
//            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestMethod(type);
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeout);
            if (POST.equals(type))
                conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();


            // 传送数据
            if (POST.equals(type)) {
                if (data != null && !"".equals(data)) {
                    out = new OutputStreamWriter(conn.getOutputStream(), charset);
                    bw = new BufferedWriter(out);
                    bw.write(data);
                    bw.flush();
                }
            }
            // 接收数据
            if (conn.getResponseCode() == 200) {
                isr = new InputStreamReader(conn.getInputStream(), charset);
                br = new BufferedReader(isr);
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append(System.getProperty("line.separator"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                isr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.e("sb:", sb.toString().trim());
        return sb.toString().trim();
    }

}