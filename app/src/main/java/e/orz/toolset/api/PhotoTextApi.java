package e.orz.toolset.api;

import android.content.Context;
import android.net.Uri;

import com.baidu.aip.ocr.AipOcr;
import com.baidu.aip.util.ImageUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import e.orz.toolset.MainActivity;
import e.orz.toolset.R;

// http://ai.baidu.com/docs#/OCR-Pricing/top
public class PhotoTextApi implements Runnable{
    //设置APPID/AK/SK
    private static final String APP_ID = "11356053";
    private static final String API_KEY = "uvxjk2caLrwY5lPVHckd6Gj0";
    private static final String SECRET_KEY = "svjAbHoWEnqk7fx8kdBitCpll1Ezncai";

    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 调用接口
        String path = "q.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String,String>());
        System.out.println(res);
    }

    @Override
    public void run() {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 调用接口
        String path = "q.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String,String>());
        System.out.println(res);
    }

    private static Uri resourceIdToUri(Context context, int resourceId) {
        final String ANDROID_RESOURCE = "android.resource://";
        final String FOREWARD_SLASH = "/";
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

}
