package e.orz.toolset.api;
// https://www.juhe.cn/docs/api/id/23
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CurrencyApi{
    private static final String DEF_CHATSET = "UTF-8";
    private static final int DEF_CONN_TIMEOUT = 30000;
    private static final int DEF_READ_TIMEOUT = 30000;
    private static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    private static String result;

    //配置您申请的KEY
    private static final String APPKEY ="a1c1c66dc4be45131093c35b951efb6f";

    public static double execute(String name, double money){
        double xxxx = 0;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result = getRequest1();
            }
        });
        thread.start();
        try{
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            double x = Double.parseDouble(jsonObject1.getJSONObject(name).getString("bankConversionPri"));
            xxxx = money*(100/x);
            System.out.println(jsonObject1.getJSONObject("美元").getString("bankConversionPri"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        DecimalFormat df=new DecimalFormat("#.00");
        return Double.parseDouble(df.format(xxxx));
    }

    //1.人民币牌价
    private static String getRequest1(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/exchange/rmbquot";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//APP Key
        params.put("type",1);//两种格式(0或者1,默认为0)

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

    //2.外汇汇率
    public static void getRequest2(){
        String result =null;
        String url ="http://web.juhe.cn:8080/finance/exchange/frate";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//APP Key
        params.put("type",1);//两种格式(0或者1,默认为0)

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static String net(String strUrl, Map params,String method) throws Exception {
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
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                try {
                    out.writeBytes(urlencode(params));
                }finally {
                    out.close();
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