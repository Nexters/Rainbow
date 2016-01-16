package com.nexters.rainbow.rainbowcouple.common.utils;

public abstract class ObjectUtils {
    public static boolean isEmpty(Object object) {
        return (object == null);
    }

    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }
}
