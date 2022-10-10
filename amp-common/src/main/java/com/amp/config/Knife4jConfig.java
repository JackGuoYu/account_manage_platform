package com.amp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author guoyu
 * @date 2021/09/05 21:08
 * @description
 */
@EnableSwagger2WebMvc
@Configuration
@ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class Knife4jConfig {

    @Bean
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Account-Manage-Platform RESTful APIs")
                        .description("帐号聚合管理平台API")
                        .version("1.0")
                        .build())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.amp.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
