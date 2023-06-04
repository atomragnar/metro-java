package com.hornbrinck.utils;

import java.util.HashMap;

public class CaseInsensitiveMap<V> extends HashMap<String, V> {

    @Override
    public V get(Object key) {
        return super.get(((String) key).toLowerCase());
    }

    @Override
    public V put(String key, V value) {
        return super.put(key.toLowerCase(), value);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(((String) key).toLowerCase());
    }

    @Override
    public V computeIfAbsent(String key, java.util.function.Function<? super String, ? extends V> mappingFunction) {
        return super.computeIfAbsent(key.toLowerCase(), mappingFunction);
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return super.getOrDefault(((String) key).toLowerCase(), defaultValue);
    }

}

