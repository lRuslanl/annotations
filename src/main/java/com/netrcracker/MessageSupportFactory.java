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
    private MessageRenderer renderer;
    private MessageProvider provider;
    private ArrayList<AnnotationAnalyzer> analyzers = new ArrayList<AnnotationAnalyzer>();
    private MessageSupportFactory() {
        properties = new Properties();

        try {
            properties.load(new FileInputStream("msf.properties"));
            String rendererClass = properties.getProperty("renderer.class");
            String providerClass = properties.getProperty("provider.class");
            renderer = (MessageRenderer)Class.forName(rendererClass).newInstance();
            provider = (MessageProvider)Class.forName(providerClass).newInstance();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        addAnalyzers();
        //renderer.setMessageProvider(provider);
        //renderer.render();
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
    }

    public static MessageSupportFactory getInstance() {
        return instance;
    }

    public MessageRenderer getMessageRenderer() {
        return renderer;
    }

    public MessageProvider getMessageProvider() {
        return provider;
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
