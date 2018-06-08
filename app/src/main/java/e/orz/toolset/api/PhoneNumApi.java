package e.orz.toolset.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

// https://www.juhe.cn/docs/api/id/11
public class PhoneNumApi{
    private static final String DEF_CHATSET = "UTF-8";
    private static final int DEF_CONN_TIMEOUT = 30000;
    private static final int DEF_READ_TIMEOUT = 30000;
    private static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    private static String result;
    //配置您申请的KEY
    private static final String APPKEY ="68f4e33a88c83e5cdbdf66a5171de35d";

    public static String execute(final String phoneNo){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result = getRequest1(phoneNo);
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
            String province = jsonObject1.getString("province");
            String city = jsonObject1.getString("city");
            String company = jsonObject1.getString("company");
            result = province+" "+city+" "+company;
        } catch (Exception e) {
            return null;
        }

        return result;
    }


    private static String getRequest1(String phoneNo){
        String result =null;
        String result2 = null;
        String url ="http://apis.juhe.cn/mobile/get";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("phone",phoneNo);//需要查询的手机号码或手机号码前7位
        params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype","json");//返回数据的格式,xml或json，默认json

        try {
            result =net(url, params, "GET");
            JSONObject object = new JSONObject(result);
            if(object.getInt("error_code")==0){
                result2 = object.get("result").toString();
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result2;
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
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
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
    private static String urlencode(Map<String,String> data) {
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
