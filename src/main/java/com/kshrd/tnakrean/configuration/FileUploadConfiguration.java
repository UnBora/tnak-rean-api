package com.kshrd.tnakrean.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class FileUploadConfiguration implements WebMvcConfigurer {
    private String client;
    private String server;

    @Value("${file.upload.client.path}")
    public void setClient(String client) {
        this.client = client;
    }

    @Value("${file.upload.server.path}")
    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(client).addResourceLocations("file:" + server);
    }

}
