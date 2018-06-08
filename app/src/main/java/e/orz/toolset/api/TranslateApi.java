package e.orz.toolset.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import e.orz.toolset.api.utils.TransApi;

public class TranslateApi{

    private static final String APP_ID = "20180606000172906";
    private static final String SECURITY_KEY = "1j6qwP4iY69JeUk5eiME";
    private static TransApi api = new TransApi(APP_ID, SECURITY_KEY);
    private static String result;

    public static String execute(final String to, final String text){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                result = api.getTransResult(text, "auto", to);
            }
        });
        thread.start();
        try{
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String s = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
            JSONObject jsonObject1 =jsonArray.getJSONObject(0);
            s = jsonObject1.getString("dst");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

}
