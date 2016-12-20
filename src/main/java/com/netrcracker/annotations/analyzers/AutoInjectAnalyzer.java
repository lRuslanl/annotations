package com.netrcracker.annotations.analyzers;

import com.netrcracker.annotations.AutoInject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;


public class AutoInjectAnalyzer implements AnnotationAnalyzer{
    private Properties properties;

    public AutoInjectAnalyzer(Properties properties){
        this.properties = properties;
    }
    public void parse(Class<?> clazz, Object instance ) throws Exception{
        Method[] methods = clazz.getDeclaredMethods();
        Field[] fields = clazz.getDeclaredFields();

        for(Method method : methods){
            if(method.isAnnotationPresent(AutoInject.class)){
                method.setAccessible(true);
                method.invoke(instance, Class.forName(properties.getProperty(method.getAnnotation(AutoInject.class).value())).newInstance());
                method.setAccessible(false);
            }
        }

        for(Field field:fields){
            if (field.isAnnotationPresent(AutoInject.class)){
                field.set(instance, Class.forName(properties.getProperty(field.getAnnotation(AutoInject.class).value())).newInstance());
            }
        }

    }
}
