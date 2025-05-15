package com.kun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class PgsqlTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(PgsqlTestApplication.class,args);
    }
}
