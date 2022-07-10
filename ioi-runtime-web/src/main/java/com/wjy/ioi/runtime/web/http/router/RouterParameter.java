package com.wjy.ioi.runtime.web.http.router;

public class RouterParameter {
    private String paramName;
    private int index;
    private Class type;

    public RouterParameter(String paramName, int index, Class type) {
        this.paramName = paramName;
        this.index = index;
        this.type = type;
    }

    public String getParamName() {
        return this.paramName;
    }

    public int getIndex() {
        return this.index;
    }

    public Class getType() {
        return this.type;
    }
}
