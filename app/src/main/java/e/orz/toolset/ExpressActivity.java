package e.orz.toolset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import e.orz.toolset.api.ExpressApi;
import e.orz.toolset.api.model.ExpContext;
import e.orz.toolset.api.model.ExpressInfo;

public class ExpressActivity extends AppCompatActivity{
    private ListView listView;
    private List<Trace> traceList = new ArrayList<>(10);
    private TraceListAdapter adapter;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        listView = findViewById(R.id.lvTrace);
        editText = findViewById(R.id.et_tit);
        button = findViewById(R.id.bu_query);
        adapter = new TraceListAdapter(ExpressActivity.this, traceList);
        listView.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }


    private void updateData(){
        String expressNo = editText.getText().toString();
        ExpressInfo expressInfo = ExpressApi.execute(expressNo);
        traceList.clear();
        if(expressInfo==null||expressInfo.getContextList().size()==0){
            editText.setText("");
            editText.setHint("请输入正确的快递单号");

        }else{
            for(ExpContext expContext: expressInfo.getContextList()){
                traceList.add(new Trace(expContext.getTime(), expContext.getContext()));
            }

        }
        adapter.notifyDataSetChanged();
    }

}
