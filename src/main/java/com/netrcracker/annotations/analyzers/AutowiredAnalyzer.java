package com.netrcracker.annotations.analyzers;


import com.netrcracker.MessageSupportFactory;
import com.netrcracker.annotations.Autowired;
import com.netrcracker.annotations.IgnoreAnnotation;

import java.lang.reflect.Field;
import java.util.Properties;

public class AutowiredAnalyzer implements AnnotationAnalyzer {
    private Properties properties;

    public AutowiredAnalyzer(Properties properties) {
        this.properties = properties;
    }

    public void parse(Class<?> clazz, Object instance) throws Exception {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class) && !field.isAnnotationPresent(IgnoreAnnotation.class)) {
                field.setAccessible(true);
                field.set(instance,Class.forName(properties.getProperty(field.getAnnotation(Autowired.class).value())).newInstance());
            }
        }
    }
}
