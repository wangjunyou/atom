package com.wjy.atom.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

    public static <T> Constructor<T> getConstructor(final Class<T> clazz,
                                                    final Class<?>... parameterTypes) {
        Constructor<T> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return constructor;
    }

    public static <T> Constructor<T> getAccessibleConstructor(final Class<T> clazz,
                                                              final Class<?>... parameterTypes) {
        Constructor<T> constructor = getConstructor(clazz, parameterTypes);
        if (constructor != null) constructor.setAccessible(true);
        return constructor;
    }


    public static Field getField(final Class<?> clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // ignore
        }

        return field;
    }

    public static Field getAccessibleField(final Class<?> clazz, final String fieldName) {

        Field field = getField(clazz, fieldName);
        if (field != null) field.setAccessible(true);
        return field;

    }

    public static Method getMethod(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) {
        Method method = null;
        try {
            method = clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            // ignore
        }
        return method;
    }

    public static Method getAccessibleMethod(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) {
        Method method = getMethod(clazz, methodName, parameterTypes);
        if (method == null) return null;
        method.setAccessible(true);
        return method;
    }

    public static <T> T invokeConstructor(final Class<T> clazz, Object[] args, Class<?>... parameterTypes)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = getAccessibleConstructor(clazz, parameterTypes);
        if (constructor == null) throw new NoSuchMethodException(clazz.getName() + " is not found constructor method.");
        return constructor.newInstance(args);
    }

    public static Object invokeMethod(final Object object, final String methodName, final Object[] args, final Class<?>... parameterTypes)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Class<? extends Object> clazz = object.getClass();
        Method method = getAccessibleMethod(clazz, methodName, parameterTypes);
        if (method == null) throw new NoSuchMethodException(clazz.getName() + " is not found method: " + methodName);
        return method.invoke(object, args);
    }

    public static Object readField(final Object object, final String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        final Class<? extends Object> clazz = object.getClass();
        Field field = getAccessibleField(clazz, fieldName);
        if (field == null) throw new NoSuchFieldException(clazz.getName() + " is not found field: " + fieldName);
        return field.get(object);
    }

    public static void writeField(final Object object, final String fieldName, final Object value)
            throws NoSuchFieldException, IllegalAccessException {
        final Class<? extends Object> clazz = object.getClass();
        Field field = getAccessibleField(clazz, fieldName);
        if (field == null) throw new NoSuchFieldException(clazz.getName() + " is not found field: " + fieldName);
        field.set(object, value);
    }

}
