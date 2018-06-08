package e.orz.toolset;

/**
 * Created by Lenovo on 2018/6/7 0007.
 */

public class Trace {
    private String acceptTime;
    private String acceptStation;

    public Trace(){}

    public Trace(String acceptTime,String acceptStation){
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
    }

    public String getAcceptTime(){
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime){
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation(){
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation){
        this.acceptStation = acceptStation;
    }
}


















