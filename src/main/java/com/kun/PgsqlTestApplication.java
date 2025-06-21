package com.kun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.kun.mapper")
public class PgsqlTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PgsqlTestApplication.class,args);
    }

    // 注入RestTemplate,用于发送HTTP请求
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
