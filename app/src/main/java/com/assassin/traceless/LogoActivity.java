package com.assassin.traceless;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.assassin.traceless.logic.Collections;
import com.assassin.traceless.logic.Photo;
import com.assassin.traceless.logic.User;

import java.util.List;


public class LogoActivity extends AppCompatActivity implements Collections.FetchListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("qulei", "LogoActivity = onCreate");

        String userId = new User().getUserId();
        fetchData(userId, this);
    }

    public void fetchData(final String userId, final Collections.FetchListener callBack) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callBack.onCall(new Collections(userId));

            }
        }.start();

    }

    @Override
    public void onCall(Collections collections) {
        Log.d("qulei", "LogoActivity = onCall");
        List<Photo> list = collections.fetchCollections("001");
        collections.testAbc();
        Log.d("qulei", "list:" + list.size());
        for (int i = 0; i < list.size(); i++) {
            list.get(i).show(i);
        }
    }
}
