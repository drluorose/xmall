package com.douyu.wsd.cradle.utils;

public class IdUtils {

    public static final String getId() {
        return ObjectId.get().toHexString();
    }
}
