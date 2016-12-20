package com.netrcracker.annotations.analyzers;


import com.netrcracker.MessageSupportFactory;
import com.netrcracker.messageproviderrender.MessageProvider;

import java.lang.reflect.Field;
import java.util.Properties;

public class AutowiredAnalyzer implements AnnotationAnalyzer{
    private Properties properties;

    public AutowiredAnalyzer(Properties properties){
        this.properties = properties;
    }

    public void parse(Class<?> clazz, Object instance) throws Exception {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(instance, MessageSupportFactory.getInstance().getMessageProvider());
        }
    }
}
