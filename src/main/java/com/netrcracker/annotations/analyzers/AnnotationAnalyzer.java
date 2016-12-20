package com.netrcracker.annotations.analyzers;


public interface AnnotationAnalyzer {
    public void parse(Class<?> clazz, Object instance) throws Exception;
}
