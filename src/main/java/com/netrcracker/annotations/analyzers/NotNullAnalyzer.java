package com.netrcracker.annotations.analyzers;

import com.netrcracker.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Properties;

public class NotNullAnalyzer implements AnnotationAnalyzer {
    public void parse(Class<?> clazz, Object instance) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            boolean access = false;
            if (!field.isAccessible()) {
                field.setAccessible(true);
                access = true;
            }
            if (field.isAnnotationPresent(NotNull.class)) {
                if (field.get(instance) == null)
                    throw new Exception("Field couldn't be null "+field.getName());
            }
            if (access) {
                field.setAccessible(false);
            }
        }
    }
}
