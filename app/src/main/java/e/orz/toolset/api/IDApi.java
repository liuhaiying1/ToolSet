package e.orz.toolset.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.IDN;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import e.orz.toolset.api.model.Person;


public class IDApi{
    private static final String DEF_CHATSET = "UTF-8";
    private static final int DEF_CONN_TIMEOUT = 30000;
    private static final int DEF_READ_TIMEOUT = 30000;
    private static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    private static final String APPKEY ="ae58d50ec1171370b11d204f4d52ceb1";

    private static String result;
    private static Person person;

    public static Person execute(final String IDNo){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result = getRequest1(IDNo);
            }
        });
        thread.start();
        try{
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject1 = new JSONObject(result);
            String sex = jsonObject1.getString("sex");
            String birthday = jsonObject1.getString("birthday");
            String area = jsonObject1.getString("area");
            person = new Person(birthday, sex, area+"市");
        } catch (JSONException e) {
            return null;
        }
        return person;
    }



    //1.身份证信息查询
    public static String getRequest1(String IDNo){
        String result =null;
        String url ="http://apis.juhe.cn/idcard/index";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("cardno",IDNo);//身份证号码
        params.put("dtype","");//返回数据格式：json或xml,默认json
        params.put("key",APPKEY);//你申请的key

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                result = object.get("result").toString();
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    public static String net(String strUrl, Map params,String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if(method==null || method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if(method==null || method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params!= null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String,Object>data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}