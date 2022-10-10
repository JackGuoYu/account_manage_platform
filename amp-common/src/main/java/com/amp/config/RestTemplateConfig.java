package com.amp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * 初始化RestTemplate配置类
 */
@Configuration
public class RestTemplateConfig {

    @ConditionalOnMissingBean(RestTemplate.class)
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder.build();
    }

}
