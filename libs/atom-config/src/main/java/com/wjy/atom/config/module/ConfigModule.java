package com.wjy.atom.config.module;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.binder.ConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.wjy.atom.config.AtomConfig;
import com.wjy.atom.config.annotation.ConfigImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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
            ConstantBindingBuilder bindingBuilder = bindConstant().annotatedWith(new ConfigImpl(k));
            to(k, v, bindingBuilder);
//            to(k, v, binder());
//            bind(v.getClass()).annotatedWith(new ConfigImpl(k)).toInstance(v);
        });
        bind(AtomConfig.class).toInstance(config);
    }

    private void to(String key, Object value, Binder binder) {
        if (value instanceof String) {
            String obj = config.getString(key);
            if(obj !=null)
                binder.bindConstant().annotatedWith(new ConfigImpl(key)).to(obj);
        } else if (value instanceof Integer) {
            Integer obj = config.getInteger(key);
            if(obj !=null)
                binder.bindConstant().annotatedWith(new ConfigImpl(key)).to(obj);
        } else if (value instanceof Long) {
            Long obj = config.getLong(key);
            if(obj !=null)
                binder.bindConstant().annotatedWith(new ConfigImpl(key)).to(obj);
        } else if (value instanceof Boolean) {
            Boolean obj = config.getBoolean(key);
            if(obj !=null)
                binder.bindConstant().annotatedWith(new ConfigImpl(key)).to(obj);
        } else if (value instanceof Float) {
            Float obj = config.getFloat(key);
            if(obj !=null)
                binder.bindConstant().annotatedWith(new ConfigImpl(key)).to(obj);
        } else if (value instanceof Double) {
            Double obj = config.getDouble(key);
            if(obj !=null)
                binder.bindConstant().annotatedWith(new ConfigImpl(key)).to(obj);
        } else if (value instanceof Byte) {
            Byte obj = config.getByte(key);
            if(obj !=null)
                binder.bindConstant().annotatedWith(new ConfigImpl(key)).to(obj);
        } else if (value instanceof Class) {
            Class obj = config.getClass(key);
            if(obj !=null)
                binder.bindConstant().annotatedWith(new ConfigImpl(key)).to(obj);
        }
    }

    private void to(String key, Object value, ConstantBindingBuilder bindingBuilder) {
        if (value instanceof String) {
            String obj = config.getString(key);
            if(obj !=null)
                bindingBuilder.to(obj);
        } else if (value instanceof Integer) {
            Integer obj = config.getInteger(key);
            if(obj !=null)
                bindingBuilder.to(obj);
        } else if (value instanceof Long) {
            Long obj = config.getLong(key);
            if(obj !=null)
                bindingBuilder.to(obj);
        } else if (value instanceof Boolean) {
            Boolean obj = config.getBoolean(key);
            if(obj !=null)
                bindingBuilder.to(obj);
        } else if (value instanceof Float) {
            Float obj = config.getFloat(key);
            if(obj !=null)
                bindingBuilder.to(obj);
        } else if (value instanceof Double) {
            Double obj = config.getDouble(key);
            if(obj !=null)
                bindingBuilder.to(obj);
        } else if (value instanceof Byte) {
            Byte obj = config.getByte(key);
            if(obj !=null)
                bindingBuilder.to(obj);
        } else if (value instanceof Class) {
            Class obj = config.getClass(key);
            if(obj !=null)
                bindingBuilder.to(obj);
        }
    }

}
