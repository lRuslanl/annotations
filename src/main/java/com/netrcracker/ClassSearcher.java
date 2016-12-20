package com.netrcracker;


import java.net.URL;
import java.util.List;
import java.io.File;
import java.util.*;

public class ClassSearcher {

    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(className);
        }
    }

    public static List<Class<?>> find(File file, String pkgname) {

        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

        String[] files = file.list();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];
            String className = null;

            if (fileName.endsWith(".class")) {
                className = pkgname.substring(1) + '.' + fileName.substring(0, fileName.length() - 6);
            }

            if (className != null) {
                classes.add(loadClass(className));
            }

            File subdir = new File(file, fileName);
            if (subdir.isDirectory()) {
                classes.addAll(find(subdir, pkgname + '.' + fileName));
            }
        }
        return classes;
    }

    public static List<Class<?>> find(String scannedPackage) {
        String scannedPath = scannedPackage.replace('.', '/');
        URL scannedUrl = ClassLoader.getSystemClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException("This package doesn't exist");
        }

        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }
}
