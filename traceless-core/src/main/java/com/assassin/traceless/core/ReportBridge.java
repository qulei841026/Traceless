package com.assassin.traceless.core;

/**
 * ReportBridge
 * Created by Le-q on 2018/2/6.
 */
public interface ReportBridge {

    void onActionBefore(String action);

    void onActionAfter(String action);

}
