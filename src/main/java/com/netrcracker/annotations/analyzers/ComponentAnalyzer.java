package com.netrcracker.annotations.analyzers;


import com.netrcracker.annotations.Component;

public class ComponentAnalyzer {
    public static boolean isComponent(Class<?> clazz) {
        return clazz.isAnnotationPresent(Component.class);
    }
}
