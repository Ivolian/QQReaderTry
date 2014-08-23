package com.example.QQReader.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.example.QQReader.R;


public class SplashActivity extends BaseActivity {

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            startActivityByClass(MainActivity.class);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        handler.sendEmptyMessageDelayed(0, 2000);
    }

}
