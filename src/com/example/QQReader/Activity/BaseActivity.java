package com.example.QQReader.Activity;

import android.app.Activity;
import android.content.Intent;


public class BaseActivity extends Activity {

    protected final void startActivityByClass(Class activityClass) {

        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

}
