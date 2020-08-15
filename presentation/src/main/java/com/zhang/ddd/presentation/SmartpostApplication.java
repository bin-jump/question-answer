package com.zhang.ddd.presentation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;


@SpringBootApplication
@ComponentScan("com.zhang.ddd")
@MapperScan("com.zhang.ddd.infrastructure.persistence.mybatis")
public class SmartpostApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartpostApplication.class, args);
    }

}
