package e.orz.toolset.api;

import e.orz.toolset.utils.TransApi;

public class TranslateApi implements Runnable{

    private static final String APP_ID = "20180606000172906";
    private static final String SECURITY_KEY = "1j6qwP4iY69JeUk5eiME";

    public static void main(String[] args) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);

        String query = "高度600米";
        System.out.println(api.getTransResult(query, "auto", "en"));
    }
    @Override
    public void run() {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String query = "高度600米";
        System.out.println(api.getTransResult(query, "auto", "en"));
    }
}
