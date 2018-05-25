package com.assassin.traceless.core.external;

/**
 * BuryingTransfer
 * Created by Qulit on 2018/3/21.
 */
public interface BuryingTransfer {

    void process(final Object target, final String invokeMethod, final Object[] args, final Object schema);

}
