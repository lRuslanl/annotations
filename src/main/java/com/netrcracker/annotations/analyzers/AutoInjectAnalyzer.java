package com.netrcracker.annotations.analyzers;

import com.netrcracker.annotations.AutoInject;
import com.netrcracker.annotations.IgnoreAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;


public class AutoInjectAnalyzer implements AnnotationAnalyzer {
    private Properties properties;

    public AutoInjectAnalyzer(Properties properties) {
        this.properties = properties;
    }

    public void parse(Class<?> clazz, Object instance) throws Exception {
        Method[] methods = clazz.getDeclaredMethods();
        Field[] fields = clazz.getDeclaredFields();

        for (Method method : methods) {
            if (method.isAnnotationPresent(AutoInject.class) && !method.isAnnotationPresent(IgnoreAnnotation.class)) {
                method.setAccessible(true);
                method.invoke(instance);
                method.setAccessible(false);
            }
        }

    }
}
