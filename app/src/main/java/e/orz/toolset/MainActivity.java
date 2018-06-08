package e.orz.toolset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import e.orz.toolset.api.CurrencyApi;
import e.orz.toolset.api.PhotoTextApi;
import e.orz.toolset.api.TranslateApi;

public class MainActivity extends AppCompatActivity {
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageView image6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1=findViewById(R.id.image1);
        image2=findViewById(R.id.image2);
        image3=findViewById(R.id.image3);
        image4=findViewById(R.id.image4);
        image5=findViewById(R.id.image5);
        image6=findViewById(R.id.image6);
        //翻译
        image1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,TranslateActivity.class);
                startActivity(intent);
            }
        });
        //图片文字识别
        image2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PhotoTextActivity.class);
                startActivity(intent);
            }
        });
        //身份
        image3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,IDActivity.class);
                startActivity(intent);
            }
        });
        //货币
        image4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CurrencyActivity.class);
                startActivity(intent);
            }
        });
        //快递查询
        image5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ExpressActivity.class);
                startActivity(intent);
            }
        });
        //手机归属地
        image6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,PhoneNumActivity.class);
                startActivity(intent);
            }
        });
    }

}
