package e.orz.toolset.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import e.orz.toolset.api.model.ExpressInfo;

public class ExpressApi{

    private static String result;
    private static String result2;

    public static ExpressInfo execute(String number){


        final String url = "http://www.kuaidi100.com/autonumber/autoComNum?resultv2=1&text="+number;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result= doGet(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try{
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String comCode=null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("auto");
            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
            comCode = jsonObject1.getString("comCode");
        } catch (JSONException e) {
            return null;
        }
        ExpressInfo expressInfo = new ExpressInfo(comCode);
        final String url2 = "http://www.kuaidi100.com/query?type="+comCode+"&postid="+number;
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result2 = doGet(url2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();
        try{
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result2);
        try {
            JSONObject jsonObject = new JSONObject(result2);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String time = jsonObject1.getString("time");
                String context = jsonObject1.getString("context");
                expressInfo.addContext(time, context);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return expressInfo;
    }

    private static String doGet(String url) throws Exception {
        URL localURL = new URL(url);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }

        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer.toString();
    }

}