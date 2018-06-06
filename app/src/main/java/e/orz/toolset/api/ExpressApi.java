package e.orz.toolset.api;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

public class ExpressApi implements Runnable{

    @Override
    public void run() {
        CloseableHttpClient httpClient = HttpClients.custom().build();
        String no = "889336977680997508";
        HttpGet httpGet = new HttpGet("http://www.kuaidi100.com/autonumber/autoComNum?resultv2=1&text="+no);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            HttpEntity entity = response.getEntity();
            String result = new String(EntityUtils.toString(entity).getBytes(), "utf8");
            System.out.println(result);
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = (JSONArray)jsonObject.get("auto");
            JSONObject jsonObject1 = (JSONObject)jsonArray.get(0);
            System.out.println(jsonObject1.get("comCode"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HttpGet httpGet1 = new HttpGet("http://www.kuaidi100.com/query?type=yuantong&postid=889336977680997508");
        CloseableHttpResponse response2 = null;
        try {
            response2 = httpClient.execute(httpGet1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            HttpEntity entity = response2.getEntity();
            String result = new String(EntityUtils.toString(entity).getBytes(), "utf8");
            System.out.println(result);
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = (JSONArray)jsonObject.get("data");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);

                System.out.println(jsonObject1.get("time"));
                System.out.println(jsonObject1.get("context"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                response2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}