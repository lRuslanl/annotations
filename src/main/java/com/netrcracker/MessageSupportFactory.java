package com.netrcracker;

import com.netrcracker.annotations.analyzers.AnnotationAnalyzer;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import com.netrcracker.annotations.analyzers.*;
import com.netrcracker.messageproviderrender.*;

public class MessageSupportFactory {

    private static MessageSupportFactory instance;
    private Properties properties;
    private ArrayList<AnnotationAnalyzer> analyzers = new ArrayList<AnnotationAnalyzer>();

    private MessageSupportFactory() {
        properties = new Properties();

        try {
            properties.load(new FileInputStream("msf.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        addAnalyzers();

    }

    static {
        instance = new MessageSupportFactory();
    }

    private void addAnalyzer(AnnotationAnalyzer analyzer) {
        analyzers.add(analyzer);
    }

    private void addAnalyzers() {
        addAnalyzer(new AutowiredAnalyzer(properties));
        addAnalyzer(new NotNullAnalyzer());
        addAnalyzer(new AutoInjectAnalyzer(properties));
        addAnalyzer(new IsStringAnalyzer());
    }

    public static MessageSupportFactory getInstance() {
        return instance;
    }

    public Object getBean(String name) throws Exception {
        String className = properties.getProperty(name);

        return getBean(Class.forName(className));
    }

    public Object getBean(Class<?> clazz) throws Exception {

        Object instance = clazz.newInstance();
        if (ComponentAnalyzer.isComponent(clazz)) {
            for (AnnotationAnalyzer analyzer : analyzers) {
                analyzer.parse(instance.getClass(), instance);
            }
        }
        return instance;
    }

}
