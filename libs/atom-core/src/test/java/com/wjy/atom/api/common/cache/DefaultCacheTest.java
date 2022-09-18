package com.wjy.atom.api.common.cache;

import com.wjy.atom.api.common.cache.Cache;
import com.wjy.atom.api.common.cache.DefaultCache;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

public class DefaultCacheTest {

    @Test
    public void defaultCache() throws InterruptedException {
        Cache<String, String> cache = new DefaultCache<>(5000);
        cache.put("hello","hello");
        cache.put("world","world");
        Thread.sleep(3000);
        String hello = cache.getIfPresent("hello");
        System.out.println(hello);
        Thread.sleep(3000);
        String world = cache.getIfPresent("world");
        System.out.println(world);
        String hello1 = cache.getIfPresent("hello");
        System.out.println(hello1);
        Thread.sleep(7000);
        cache.put("he","he");
        Thread.sleep(4000);
        String he = cache.getIfPresent("he");
        System.out.println(he);
//        Thread.sleep(12000);
        Map<String, String> allPresent = cache.getAllPresent(Arrays.asList("hello", "world", "he"));
        allPresent.forEach((k,v) -> {
            System.out.println(k+"===="+v);
        });
        Thread.sleep(100000);
    }
}
