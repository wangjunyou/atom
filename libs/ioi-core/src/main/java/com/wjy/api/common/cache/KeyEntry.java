package com.wjy.api.common.cache;

import java.util.Objects;

public class KeyEntry<K>{
    private K key;

    private long createTime;

    private long loadTime;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(long loadTime) {
        this.loadTime = loadTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyEntry<?> keyEntry = (KeyEntry<?>) o;
        return createTime == keyEntry.createTime && loadTime == keyEntry.loadTime && key.equals(keyEntry.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, createTime, loadTime);
    }

}
