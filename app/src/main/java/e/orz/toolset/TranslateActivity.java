package e.orz.toolset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import e.orz.toolset.api.TranslateApi;


public class TranslateActivity extends AppCompatActivity {

    EditText et;
    String s;//原数据
    Button bt;//返回按钮
    Button bt_translate;//翻译按钮
    Spinner spinner;
    TextView textView;
    String[] languageCode = {"zh", "en", "yue", "wyw", "jp", "kor", "fra", "spa", "th", "ara", "ru", "pt",
            "de", "it", "el", "nl", "pl", "bul"};
    String[] language = {"中文", "英语", "粤语", "文言文", "日语", "韩语", "法语", "西班牙语", "泰语", "阿拉伯语", "俄语", "葡萄牙语",
            "德语", "意大利语", "希腊语", "荷兰语", "波兰语", "保加利亚语"};
    int i;
    String currentCode = null;//获取目标语言

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        et = findViewById(R.id.sourceText);
        bt = findViewById(R.id.returnButton);//返回按钮
        bt_translate = findViewById(R.id.translateButton);//翻译按钮
        textView = findViewById(R.id.resultText);
        spinner = findViewById(R.id.spin);
        bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
        bt_translate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                s = et.getText().toString();//原数据
                String selected = spinner.getSelectedItem().toString(); //获取spinner的数据
                for (i = 0; i < language.length; i++) {
                    if (language[i].equals(selected)) {
                        currentCode = languageCode[i]; //目标语言的代码
                        break;
                    }
                }
                String ss = TranslateApi.execute(currentCode, s); //翻译
                textView.setText(ss);
            }
        });
        et.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!TextUtils.isEmpty(et.getText())) {
                    textView.setText("");
                }
            }
        });
    }
}
