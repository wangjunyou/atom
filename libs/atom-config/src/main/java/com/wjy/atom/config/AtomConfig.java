package com.wjy.atom.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AtomConfig {

    private final HashMap<String, Object> confData;

    public AtomConfig() {
        this.confData = new HashMap<>();
    }

    public static AtomConfig fromMap(Map<String, Object> map) {
        AtomConfig config = new AtomConfig();
        map.forEach(config::set);
        return config;
    }

    public static AtomConfig fromProp(Properties props) {
        AtomConfig config = new AtomConfig();
        for (Map.Entry<Object, Object> entry : props.entrySet()) {
            config.set((String) entry.getKey(), entry.getValue());
        }
        return config;
    }

    public void set(String key, Object value) {
        confData.put(key, value);
    }

    public Object get(String key) {
        return confData.get(key);
    }

    public Map<String, Object> toMap() {
        return new HashMap<>(confData);
    }

    public Object getObject(String key) {
        return confData.get(key);
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultVaule) {
        Object obj = getObject(key);
        String val = String.valueOf(obj);
        if ("null".equals(val)) return defaultVaule;
        return val;
    }

    public Integer getInteger(String key) {
        return getInteger(key, null);
    }

    public Integer getInteger(String key, Integer defaultVaule) {
        String obj = getString(key);
        try {
            return Integer.valueOf(obj);
        } catch (Exception e) {
            return defaultVaule;
        }
    }

    public Long getLong(String key) {
        return getLong(key, null);
    }

    public Long getLong(String key, Long defaultVaule) {
        String obj = getString(key);
        try {
            return Long.valueOf(obj);
        } catch (Exception e) {
            return defaultVaule;
        }
    }

    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Boolean getBoolean(String key, Boolean defaultVaule) {
        String obj = getString(key);
        try {
            return Boolean.valueOf(obj);
        } catch (Exception e) {
            return defaultVaule;
        }
    }

    public Float getFloat(String key) {
        return getFloat(key, null);
    }

    public Float getFloat(String key, Float defaultVaule) {
        String obj = getString(key);
        try {
            return Float.valueOf(obj);
        } catch (Exception e) {
            return defaultVaule;
        }
    }

    public Double getDouble(String key) {
        return getDouble(key, null);
    }

    public Double getDouble(String key, Double defaultVaule) {
        String obj = getString(key);
        try {
            return Double.valueOf(obj);
        } catch (Exception e) {
            return defaultVaule;
        }
    }

    public Byte getByte(String key) {
        return getByte(key, null);
    }

    public Byte getByte(String key, Byte defaultVaule) {
        String obj = getString(key);
        try {
            return Byte.valueOf(obj);
        } catch (Exception e) {
            return defaultVaule;
        }
    }

    public Class getClass(String key) {
        return getClass(key, null);
    }

    public <T> Class<T> getClass(String key, Class<T> defaultVaule) {
        Object obj = getObject(key);
        if (obj instanceof Class) {
            return (Class<T>) obj;
        }
        return defaultVaule;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        confData.forEach((k, v) -> sb.append(k + "=" + v + "\n"));
        return "AtomConfig{\n" +
                sb.toString() +
                '}';
    }

}
