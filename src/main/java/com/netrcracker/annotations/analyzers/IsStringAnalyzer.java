package com.netrcracker.annotations.analyzers;


import com.netrcracker.annotations.IgnoreAnnotation;
import com.netrcracker.annotations.IsString;

import java.lang.reflect.Field;

public class IsStringAnalyzer implements AnnotationAnalyzer {
    public void parse(Class<?> clazz, Object instance) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            field.setAccessible(true);

            if (field.isAnnotationPresent(IsString.class) && !field.isAnnotationPresent(IgnoreAnnotation.class)) {
                if (!field.getType().equals(String.class)) {
                    throw new Exception("Annotation IsString should be on String element");
                }
            }
            field.setAccessible(false);

        }
    }
}
