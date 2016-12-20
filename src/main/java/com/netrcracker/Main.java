package com.netrcracker;

import com.netrcracker.messageproviderrender.MessageRenderer;

public class Main {
    public static void main(String args[]) throws Exception {
        MessageRenderer renderer = (MessageRenderer) MessageSupportFactory.getInstance().getBean("renderer.class");
    }
}
