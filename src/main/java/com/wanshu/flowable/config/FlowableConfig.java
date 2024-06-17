package com.wanshu.flowable.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;


import javax.annotation.Resource;

/**
 * 处理流程引擎配置
 * @author zengc
 * @date 2024/05/25
 */
@Configuration
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {
    /**
     * 配置流程引擎
     * @param springProcessEngineConfiguration 流程引擎配置
     */
    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        // 防止中文乱码
        // 设置字体
        springProcessEngineConfiguration.setActivityFontName("宋体");
        // 设置注释字体
        springProcessEngineConfiguration.setAnnotationFontName("宋体");
        // 设置标签字体
        springProcessEngineConfiguration.setLabelFontName("宋体");



    }
}
