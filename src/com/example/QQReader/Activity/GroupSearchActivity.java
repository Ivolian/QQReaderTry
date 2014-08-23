package com.example.QQReader.Activity;

import android.os.Bundle;
import android.view.View;
import com.example.QQReader.R;


public class GroupSearchActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_search);

        findViewById(R.id.llBack).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
