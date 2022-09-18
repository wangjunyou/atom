package com.wjy.atom.api.common.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;

/**
 * 缓存类
 * */

public interface Cache<K, V> {

    V getIfPresent(K key);
    V getIfPresent(K key, Callable<? extends V> loader);
    Map<K, V> getAllPresent(Iterable<? extends K> keys);
    void put(K key, V value);
    void putAll(Map<? extends K, ? extends V> m);
    void invalidate(K key);
    void invalidateAll(Iterable<? extends K> keys);
    void invalidateAll();
    int size();

}
