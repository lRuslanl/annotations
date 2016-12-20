package com.netrcracker.messageproviderrender;

import com.netrcracker.annotations.AutoInject;
import com.netrcracker.annotations.Autowired;
import com.netrcracker.annotations.Component;
import com.netrcracker.annotations.IgnoreAnnotation;

@Component
public class StandardOutMessageRenderer implements MessageRenderer {
    @Autowired
    private MessageProvider messageProvider;

    @AutoInject
    public void render() {
        if (messageProvider == null) {
            throw new RuntimeException("You must set the property messageProvider of class:" +
                    StandardOutMessageRenderer.class.getName());
        }
        System.out.println(messageProvider.getMessage());
    }

    @AutoInject
    @IgnoreAnnotation
    public void setMessageProvider(MessageProvider provider){
        this.messageProvider = provider;
    }

    public MessageProvider getMessageProvider(){
        return this.messageProvider;
    }
}