package com.assassin.traceless.logic;

import com.assassin.traceless.annotations.weaving.Using;

/**
 * Created by Le-q on 2018/2/2.
 */

public class User {

    public String name = "Jack";

    @Using
    public String id = "001";

    public String getUserId() {
        return id;
    }

}
