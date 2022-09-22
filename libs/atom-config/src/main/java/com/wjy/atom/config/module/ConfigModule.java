package com.wjy.atom.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.binder.ConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.util.Types;
import com.wjy.atom.config.AtomConfig;
import com.wjy.atom.config.annotation.ConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ConfigModule extends AbstractModule {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigModule.class);

    private final AtomConfig config;

    public ConfigModule(Map<String, Object> map) {
        this.config = AtomConfig.fromMap(map);
    }

    public ConfigModule(Properties props) {
        this.config = AtomConfig.fromProp(props);
    }

    @Override
    protected void configure() {

        LOG.info("Config module load.");

        Map<String, Object> datas = config.toMap();
        datas.forEach((k, v) -> {
            Class clazz = getClass(k);
            if (clazz != null)
                bind(Key.get(clazz, new ConfigImpl(k))).toInstance(v);
        });
        bind(AtomConfig.class).toInstance(config);
    }

    private Class getClass(Object obj) {
        if (obj instanceof String) {
            return String.class;
        } else if (obj instanceof Integer) {
            return Integer.class;
        } else if (obj instanceof Long) {
            return Long.class;
        } else if (obj instanceof Boolean) {
            return Boolean.class;
        } else if (obj instanceof Float) {
            return Float.class;
        } else if (obj instanceof Double) {
            return Double.class;
        } else if (obj instanceof Byte) {
            return Byte.class;
        } else if (obj instanceof Map) {
            return Map.class;
        } else if (obj instanceof List) {
            return List.class;
        } else if (obj instanceof Set) {
            return Set.class;
        } else if (obj instanceof Class) {
            return Class.class;
        } else {
            return null;
        }
    }

}
