package com.assassin.traceless;

import android.util.Log;

import com.assassin.traceless.annotations.compiler.Reporter;

/**
 * Created by Le-q on 2018/2/2.
 */

public class AnnotationTest {

    @Reporter(value = 1)
    public void report(){
        Log.d("qulei","AnnotationTest = report ");
    }
}
