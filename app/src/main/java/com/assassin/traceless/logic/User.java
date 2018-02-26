package com.assassin.traceless.logic;

import com.assassin.traceless.annotations.weaving.Burying;

/**
 * Created by Le-q on 2018/2/2.
 */

public class User {

    public String name = "Jack";

    @Burying
    public String getUserId() {
        return "001";
    }

}
