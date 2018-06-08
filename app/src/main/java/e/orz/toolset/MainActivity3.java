package e.orz.toolset;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import e.orz.toolset.api.CurrencyApi;
import e.orz.toolset.api.ExpressApi;
import e.orz.toolset.api.IDApi;
import e.orz.toolset.api.PhoneNumApi;
import e.orz.toolset.api.TranslateApi;
import e.orz.toolset.api.model.ExpContext;
import e.orz.toolset.api.model.ExpressInfo;
import e.orz.toolset.api.model.Person;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        String s = TranslateApi.execute("en", "你好");
        System.out.println(s);
        ExpressInfo expressInfo = ExpressApi.execute("889336977680997508");
        for(ExpContext context:expressInfo.getContextList()){
            System.out.println(context.getTime()+" : "+context.getContext());
        }
        System.out.println(PhoneNumApi.execute("18652030106"));
        Person person = IDApi.execute("14272419960206331X");
        System.out.println(person.getSex()+"\n"+person.getBirthday()+"\n"+person.getArea());
        System.out.println(CurrencyApi.execute("美元", 100));
    }

}
