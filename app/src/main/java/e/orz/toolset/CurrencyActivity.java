package e.orz.toolset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import e.orz.toolset.api.CurrencyApi;

public class CurrencyActivity extends AppCompatActivity {
    Button bt;
    EditText editText1;
    EditText editText2;
    Spinner spinner;
    Button translate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        editText1=findViewById(R.id.editText1);
        editText2=findViewById(R.id.editText2);
        //editText2.setKeyListener(null);
        bt=findViewById(R.id.returnButton);
        spinner=findViewById(R.id.spinner);
        translate=findViewById(R.id.button1);//换算
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selected= spinner.getSelectedItem().toString(); //获取spinner的数据
                double money=Double.valueOf(editText1.getText().toString());
                double result= CurrencyApi.execute(selected,money);
                editText2.setText(result+"");
            }
        });
    }

}
