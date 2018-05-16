package com.assassin.traceless.core.external;

/**
 * Created by Le-q on 2018/3/21.
 */

public interface BuryingTransfer {

    void process(final Object target, final String invokeMethod, final Object[] args, final Object schema);

}
