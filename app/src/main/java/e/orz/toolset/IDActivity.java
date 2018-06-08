package e.orz.toolset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import e.orz.toolset.api.IDApi;
import e.orz.toolset.api.model.Person;

public class IDActivity extends AppCompatActivity {
Button bt;
Button search;
Person person;
EditText idenEdit;
TextView birth;
TextView sex;
TextView area;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);
        bt=findViewById(R.id.returnButton);
        search=findViewById(R.id.search);
        idenEdit=findViewById(R.id.idenEdit);
        birth=findViewById(R.id.birth);
        sex=findViewById(R.id.sex);
        area=findViewById(R.id.area);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        //显示身份信息
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person= IDApi.execute(idenEdit.getText().toString());
                if(person==null){
                    idenEdit.setText("");
                    idenEdit.setHint("输入正确的身份证号");
                    birth.setText("");
                    sex.setText("");
                    area.setText("");
                }else{
                    birth.setText(person.getBirthday());
                    sex.setText(person.getSex());
                    area.setText(person.getArea());
                }

            }
        });
    }

}
