package e.orz.toolset.api.model;

import java.util.ArrayList;
import java.util.List;

public class ExpressInfo {
    private String com;
    private List<ExpContext> contextList;

    public ExpressInfo(String com){
        this.com = com;
        contextList = new ArrayList<>();
    }

    public String getCom() {
        return com;
    }

    public List<ExpContext> getContextList() {
        return contextList;
    }

    public void addContext(String time, String context){
        contextList.add(new ExpContext(time, context));
    }



}
