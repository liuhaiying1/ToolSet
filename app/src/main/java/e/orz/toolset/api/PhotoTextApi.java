package e.orz.toolset.api;

import com.baidu.aip.ocr.AipOcr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// http://ai.baidu.com/docs#/OCR-Pricing/top
public class PhotoTextApi {
    //设置APPID/AK/SK
    private static final String APP_ID = "11356053";
    private static final String API_KEY = "uvxjk2caLrwY5lPVHckd6Gj0";
    private static final String SECRET_KEY = "svjAbHoWEnqk7fx8kdBitCpll1Ezncai";
    private static AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    private static JSONObject result;
    private static List<String> rstStr;
    public static List<String> execute(final String path) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result = client.basicGeneral(path, new HashMap<String, String>());
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray = result.getJSONArray("words_result");
            rstStr = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                String s = jsonArray.getJSONObject(i).getString("words");
                rstStr.add(s);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rstStr;
    }

}
