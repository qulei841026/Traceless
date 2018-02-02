package com.assassin.traceless;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("qulei", "LogoActivity = onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("qulei", "LogoActivity = onResume");
    }
}
