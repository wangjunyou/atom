package com.wjy.ioi.runtime.web.http.router;

import java.lang.reflect.Method;

public class RouterMapping {
    private String mapping;
    private Method method;
    private boolean isGet;
    private RouterParameter[] parameters;

    public RouterMapping(String mapping, Method method, boolean isGet, RouterParameter[] parameters) {
        this.mapping = mapping;
        this.method = method;
        this.isGet = isGet;
        this.parameters = parameters;
    }

    public String getMapping() {
        return this.mapping;
    }

    public boolean isGet() {
        return this.isGet;
    }

    public Method getMethod() {
        return this.method;
    }

    public RouterParameter[] getParameters() {
        return this.parameters;
    }
}
