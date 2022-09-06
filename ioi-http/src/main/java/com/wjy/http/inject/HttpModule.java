package com.wjy.http.inject;

import com.google.inject.AbstractModule;

import java.util.Set;

public class HttpModule extends AbstractModule {

    private Set<Class<?>> classes;

    public HttpModule(Set<Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    protected void configure() {
        classes.forEach(clazz ->{
            bind(clazz);
        });
    }
}
