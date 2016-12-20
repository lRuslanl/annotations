package com.netrcracker.messageproviderrender;

import com.netrcracker.annotations.Component;
import com.netrcracker.annotations.IsString;
import com.netrcracker.annotations.NotNull;

@Component
public class HelloWorldMessageProvider implements MessageProvider {
    @NotNull
    @IsString
    private String hello = "Hello World!";

    public String getMessage() {
        return hello;
    }
}