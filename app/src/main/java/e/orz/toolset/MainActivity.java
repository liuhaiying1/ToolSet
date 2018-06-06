package e.orz.toolset;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import e.orz.toolset.api.CurrencyApi;
import e.orz.toolset.api.ExpressApi;
import e.orz.toolset.api.ExpressApi2;
import e.orz.toolset.api.IDApi;
import e.orz.toolset.api.PhoneNumApi;
import e.orz.toolset.api.PhotoTextApi;
import e.orz.toolset.api.TranslateApi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new ExpressApi2()).start();
        new Thread(new CurrencyApi()).start();
        new Thread(new IDApi()).start();
        new Thread(new PhoneNumApi()).start();
        new Thread(new PhotoTextApi()).start();
        new Thread(new TranslateApi()).start();
    }
}
