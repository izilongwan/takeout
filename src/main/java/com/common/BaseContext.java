package com.common;

import java.util.HashMap;
import java.util.Map;

public class BaseContext {
    final public static ThreadLocal<Map<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        return new HashMap<String, Object>();
    });

    final public static Object get(String key) {
        return THREAD_LOCAL.get().get(key);
    }

    final public static void set(String key, Object value) {
        THREAD_LOCAL.get().put(key, value);
    }
}
