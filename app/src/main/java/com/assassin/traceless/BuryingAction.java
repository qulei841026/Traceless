package com.assassin.traceless;

import android.os.Bundle;
import android.util.Log;

import com.assassin.traceless.burying.build.BuryingMasterSchema;
import com.assassin.traceless.logic.Collections;

import java.lang.ref.WeakReference;

/**
 * Created by Le-q on 2018/3/21.
 */
public class BuryingAction implements BuryingMasterSchema {


    @Override
    public void LogoActivity_onCreate(WeakReference<LogoActivity> target, Bundle savedInstanceState) {
        Log.d("qulei", "[BuryingAction]->" + "LogoActivity_onCreate"
                + ", target=" + target.get()
                + ", savedInstanceState=" + savedInstanceState);

    }

    @Override
    public void LogoActivity_fetchData(WeakReference<LogoActivity> target, String userId, Collections.FetchListener callBack) {
        Log.d("qulei", "[BuryingAction]->" + "LogoActivity_fetchData"
                + ", target=" + target.get()
                + ", userId=" + userId
                + ", callBack=" + callBack);
    }
}
