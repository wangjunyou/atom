package com.wjy.ioi.runtime.web.manager;


import com.wjy.ioi.runtime.web.handler.IoiServlet;
import com.wjy.ioi.runtime.web.http.annotation.Mapping;
import com.wjy.ioi.runtime.web.http.annotation.ParameterName;
import com.wjy.ioi.runtime.web.http.annotation.Post;
import com.wjy.ioi.runtime.web.http.router.RouterGraph;
import com.wjy.ioi.runtime.web.http.router.RouterMapping;
import com.wjy.ioi.runtime.web.http.router.RouterParameter;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.reflections.util.ReflectionUtilsPredicates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public class ServerletManager {

    private static final Logger LOG = LoggerFactory.getLogger(ServerletManager.class);

    private static final String DEFAULT_PACKAGE = "com.wjy.ioi";

    private List<? extends RouterGraph> routerGraphList;


    public RouterGraph router(String path) {
        return this.routerGraphList.stream()
                .filter(routerGraph -> {
                    return routerGraph.getPaths().contains(path);
                })
                .findFirst()
                .orElse(null);
    }

    public void forPackages(String includePackage) {
        forPackages(includePackage, null, ServerletManager.class.getClassLoader());
    }

    public void forPackages(String includePackage, String excludePackage, ClassLoader classLoader) {
        FilterBuilder filter = new FilterBuilder();
        filter.includePackage(includePackage);
        if (excludePackage != null && ("").equals(excludePackage))
            filter.excludePackage(excludePackage);

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
                .forPackages(DEFAULT_PACKAGE)
                .addClassLoaders(classLoader)
                .filterInputsBy(filter);

        Reflections reflections = new Reflections(configurationBuilder);
        Set<Class<? extends IoiServlet>> servlets = getServlets(reflections, IoiServlet.class);
        this.routerGraphList = getRouterGraphs(servlets);
    }


    public List<? extends RouterGraph> getRouterGraphs(Set<Class<? extends IoiServlet>> servlets) {
        List<RouterGraph> routerGraphList = new ArrayList<>();
        RouterGraph routerGraph = new RouterGraph();
        servlets.forEach(servlet -> {
            Mapping classAnnotation = servlet.getAnnotation(Mapping.class);
            String cmValue = requireNonNull(classAnnotation.value());
            routerGraph.setPathPattern(cmValue);
            routerGraph.setPathClass(servlet);

            Set<Method> methods = ReflectionUtils.getMethods(servlet, ReflectionUtilsPredicates.withAnnotation(Mapping.class));
            List<RouterMapping> routerMappingList = new ArrayList<>(methods.size());
            methods.forEach(method -> {
                Mapping methodAnnotation = method.getAnnotation(Mapping.class);
                String mmValue = requireNonNull(methodAnnotation.value());
                boolean isGet = method.getAnnotation(Post.class) == null;

                Parameter[] parameters = method.getParameters();
                RouterParameter[] methodParameters = new RouterParameter[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    ParameterName parameterAnnotation = parameter.getAnnotation(ParameterName.class);
                    String paramName = parameterAnnotation == null ? parameter.getName() : parameterAnnotation.value();
                    methodParameters[i] = new RouterParameter(paramName, i + 1, parameter.getType());
                }

                routerMappingList.add(new RouterMapping(mmValue, method, isGet, methodParameters));
            });

            routerGraph.setRouterMappings(routerMappingList);
            routerGraphList.add(routerGraph);
        });
        return routerGraphList.size() == 0 ? null : routerGraphList;
    }

    public Set getServlets(Reflections reflections, Class<? extends IoiServlet> interfaceClass) {
        return reflections.getSubTypesOf(interfaceClass);
    }
}
