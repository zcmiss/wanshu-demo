package com.wanshu.wanshu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebViewConfiguration implements WebMvcConfigurer {

    /**
     * 视图映射器
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login.html").setViewName("/login");
        registry.addViewController("/index_v1.html").setViewName("/index_v1");
    }
}
