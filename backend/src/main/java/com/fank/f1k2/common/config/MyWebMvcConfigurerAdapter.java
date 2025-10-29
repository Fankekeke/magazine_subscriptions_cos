package com.fank.f1k2.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagesWeb/**").addResourceLocations("file:F:/Project/20250705在线杂志订阅系统/db/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
