package com.nexters.rainbow.rainbowcouple.common.utils;

import java.util.Collection;

public abstract class CollectionUtils {
    public static int size(Collection<?> collection) {
        return isEmpty(collection) ? 0 : collection.size();
    }

    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
