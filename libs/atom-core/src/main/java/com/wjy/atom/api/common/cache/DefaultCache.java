package com.wjy.atom.api.common.cache;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 默认缓存类
 */

public class DefaultCache<K, V> implements Cache<K, V> {


    private static final long DEFAULT_TRIGGER_MILLISECONDS = 10000l;
    private final ConcurrentHashMap<KeyEntry<K>, V> caches = new ConcurrentHashMap();
    private Thread ticker;
    private long ttl = DEFAULT_TRIGGER_MILLISECONDS;
    private boolean isClean;

    public DefaultCache(boolean isClean) {
        this.isClean = isClean;
    }

    public DefaultCache(long ttl) {
        this.isClean = true;
        this.ttl = ttl;
    }

    public DefaultCache(TimeUnit timeUnit, long ttl) {
        this.ttl = timeUnit.toMillis(ttl);
    }

    @Override
    public V getIfPresent(K key) {

        KeyEntry<K> oldKe = getKeyEntry(key);
        if (oldKe == null) return null;
        V value = caches.get(oldKe);
        if (isClean)
            oldKe.setLoadTime(new Date().getTime());

        return value;
    }

    @Override
    public V getIfPresent(K key, Callable<? extends V> loader) {
        V v = getIfPresent(key);
        if (v == null) {
            try {
                return loader.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return v;
    }

    @Override
    public Map<K, V> getAllPresent(Iterable<? extends K> keys) {

        Map<K, V> kv = new HashMap<>();

        keys.forEach(k -> {
            V v = getIfPresent(k);
            if (v != null) kv.put(k, v);
        });

        return kv.size() > 0 ? kv : null;
    }

    public KeyEntry<K> getKeyEntry(K key) {
        Set<KeyEntry<K>> kes = caches.keySet();
        for (KeyEntry<K> ke : kes) {
            if (key.equals(ke.getKey()))
                return ke;
        }
        return null;
    }

    @Override
    public void put(K key, V value) {

        long time = new Date().getTime();

        KeyEntry<K> oldKe = getKeyEntry(key);
        if (oldKe != null) {
            caches.put(oldKe, value);
            oldKe.setLoadTime(time);
            return;
        }

        KeyEntry k = new KeyEntry();
        k.setKey(key);
        k.setCreateTime(time);
        k.setLoadTime(time);
        caches.put(k, value);

        triggerClean();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void invalidate(K key) {
        KeyEntry<K> oldKe = getKeyEntry(key);
        if (oldKe != null) caches.remove(oldKe);
    }


    @Override
    public void invalidateAll(Iterable<? extends K> keys) {
        keys.forEach(this::invalidate);
    }

    @Override
    public void invalidateAll() {
        caches.clear();
    }

    @Override
    public int size() {
        return caches.size();
    }

    private void triggerClean() {
        if (isClean && (ticker == null || !ticker.isAlive())) {
            ticker = new Thread(new TriggerClean<K, V>(caches, ttl));
            ticker.setDaemon(true);
            ticker.start();
        }
    }

    class TriggerClean<K, V> implements Runnable {

        private static final long DEFAULT_FLASH = 5000l;
        private Map<KeyEntry<K>, V> caches;
        private long ttl;

        public TriggerClean(Map caches, long ttl) {
            this.caches = caches;
            this.ttl = ttl;
        }

        @Override
        public void run() {
            boolean run = true;
            while (run) {
                try {
                    Set<KeyEntry<K>> key = caches.keySet();
                    key.forEach(k -> {
                        if (k.getLoadTime() + ttl < new Date().getTime())
                            caches.remove(k);
                    });
                    TimeUnit.MILLISECONDS.sleep(DEFAULT_FLASH);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (caches.size() == 0) {
                    run = false;
                    continue;
                }

            }
        }
    }
}
