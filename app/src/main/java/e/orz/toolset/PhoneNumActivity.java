package e.orz.toolset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import e.orz.toolset.api.PhoneNumApi;

public class PhoneNumActivity extends AppCompatActivity {
Button bt;
Button searchBt;
TextView des;
TextView carrier;
EditText phoneEdit;
String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_num);
        bt=findViewById(R.id.testButton);//退出
        searchBt=findViewById(R.id.search);//查询
        des=findViewById(R.id.des);//归属地
        carrier=findViewById(R.id.carrier);//运营商
        phoneEdit=findViewById(R.id.phoneEdit);//手机号
        bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result= PhoneNumApi.execute(phoneEdit.getText().toString());
                if(result==null){
                    phoneEdit.setText("");
                    phoneEdit.setHint("请输入正确的手机号码");
                }else {
                    String[] results = result.split(" ");
                    des.setText(results[0] + " " + results[1]);
                    carrier.setText(results[2]);
                }
            }
        });

        phoneEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(phoneEdit.getText()))
                {
                    phoneEdit.setText("");
                    des.setText("");
                    carrier.setText("");
                }
            }
        });
    }

}
