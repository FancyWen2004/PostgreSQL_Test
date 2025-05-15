package com.kun.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfiguration {

    // 配置swagger的全局信息
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("学校管理系统 API")
                        .version("1.0")
                        .description("学校管理系统的 API 文档，包含学生和教师模块")
                        .termsOfService("https://example.com")
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }

    @Bean
    public GroupedOpenApi loginAPI() {
        return GroupedOpenApi
                .builder()
                .group("通用接口")
                .pathsToMatch(
                        "/common/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi teacherAPI() {
        return GroupedOpenApi
                .builder()
                .group("教师端接口")
                .pathsToMatch(
                        "/api/teacher/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi studentAPI() {
        return GroupedOpenApi
                .builder()
                .group("学生端接口")
                .pathsToMatch(
                        "/api/students/**"
                )
                .build();
    }
}