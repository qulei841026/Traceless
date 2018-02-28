package com.assassin.traceless.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Le-q on 2018/2/2.
 */

public class Collections {

    private static final String TAG = "Collections";

    private List<Photo> list = new ArrayList<Photo>() {
        {
            add(new Photo());
            add(new Photo());
            add(new Photo());
        }
    };

    public Collections(String uid) {

    }

    public List<Photo> fetchCollections(String uid) {
        if (uid != null)
            return list;
        else
            return null;
    }

    public void testAbc() {
        Log.d(TAG, "testAbc");
    }

    public interface FetchListener {
        void onCall(Collections collections);
    }
}
