package com.wjy.ioi.runtime.web.http.router;


import com.wjy.ioi.runtime.web.handler.IoiServlet;
import com.wjy.ioi.runtime.web.http.annotation.ParameterName;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * router映射图信息
 * 支持格式：[/ioi/examples, /ioi/examples/:wjy]
 */

public class RouterGraph {

    private String pathPattern;

    private Class<? extends IoiServlet> pathClass;

    private List<RouterMapping> routerMappings;

    public RouterGraph() {
    }

    public RouterGraph(String pathPattern, Class pathClass, List<RouterMapping> routerMethods) {
        this.pathPattern = requireNonNull(pathPattern);
        this.pathClass = requireNonNull(pathClass);
        this.routerMappings = requireNonNull(routerMethods);
    }

    public void setPathPattern(String pathPattern) {
        this.pathPattern = pathPattern;
    }

    public void setPathClass(Class pathClass) {
        this.pathClass = pathClass;
    }

    public void setRouterMappings(List<RouterMapping> routerMappings) {
        this.routerMappings = routerMappings;
    }

    public String getPathPattern() {
        return this.pathPattern;
    }

    public Class getPathClass() {
        return this.pathClass;
    }

    public List<RouterMapping> getRouterMappings() {
        return this.routerMappings;
    }

    public List<String> getPaths() {
        List<String> paths = new ArrayList<>(this.routerMappings.size());
        this.routerMappings.forEach(rm -> paths.add(this.pathPattern + rm.getMapping()));
        return paths;
    }

    /* 两种解析方式:[/ioi/examples, /ioi/examples/:wjy],目前版本支持第一种解析方式 */
    public RouterMapping getMapping(String mapping) {
//        int indexOf = path.indexOf(this.pathPattern);
//        final String mapping = path.substring(indexOf + this.pathPattern.length());
        List<RouterMapping> mappings = routerMappings.stream().filter(ms -> ms.getMapping().equals(mapping)).collect(Collectors.toList());
        return (mappings != null && !mappings.isEmpty()) ? mappings.get(0) : null;
    }
}
