package com.wjy.core.finder;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 反射获取指定包下符合条件的类
 * */
public class PackageFinder {

    private ConfigurationBuilder config = new ConfigurationBuilder();

    private FilterBuilder filterBuilder = new FilterBuilder();


    public PackageFinder toPackage(String pkg) {
        ClassLoader classLoader = PackageFinder.class.getClassLoader();
        return toPackage(pkg, classLoader);
    }

    public PackageFinder toPackage(String pkg, ClassLoader classLoader) {
        config.forPackage(pkg, classLoader);
        return this;
    }


    /**
     * (java.lang) or (java.lang..*)
     */
    public PackageFinder filterPackage(String includePackage, String excludePackage) {
        if (includePackage != null && "".equals(includePackage)) {
            filterBuilder.includePackage(includePackage);
        }
        if (excludePackage != null && "".equals(excludePackage)) {
            filterBuilder.excludePackage(excludePackage);
        }
        return this;
    }

    public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> clazz) {
        Reflections reflections = new Reflections(
                config.filterInputsBy(filterBuilder)
                        .addScanners(Scanners.TypesAnnotated));
        return reflections.getTypesAnnotatedWith(clazz);
    }

}
