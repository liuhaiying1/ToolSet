package e.orz.toolset.api.model;

/**
 * Created by ORZ on 2018/6/7.
 */

public class ExpContext {
    private String time;
    private String content;

    protected ExpContext(String time, String context){
        this.time = time;
        this.content = context;
    }

    public String getContext() {
        return content;
    }

    public String getTime() {
        return time;
    }
}
