package com.wjy.atom.config.annotation;

import com.google.inject.internal.Annotations;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class ConfigImpl implements Config, Serializable {

    private static final long serialVersionUID = 0;

    private final String value;

    public ConfigImpl(String value) {
        this.value = value;
    }

    @Override
    public String value() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Config)) {
            return false;
        } else {
            Config other = (Config)o;
            return this.value.equals(other.value());
        }
    }

    @Override
    public int hashCode() {
        return 127 * "value".hashCode() ^ this.value.hashCode();
    }

    @Override
    public String toString() {
        return '@' + Config.class.getName() + '(' + Annotations.memberValueString("value", value) + ')';
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return Config.class;
    }
}
