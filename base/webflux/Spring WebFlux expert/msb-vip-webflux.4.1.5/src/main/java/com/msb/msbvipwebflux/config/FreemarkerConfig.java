package com.msb.msbvipwebflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;

@Configuration
@EnableWebFlux
public class FreemarkerConfig implements WebFluxConfigurer {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // 注册FreemarkerViewResolves 这样的模板
        registry.freeMarker();
    }
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(){
        // 设置配置器
        FreeMarkerConfigurer config = new FreeMarkerConfigurer();
        config.setTemplateLoaderPath("classpath:/templates");
        return config;
    }
}
