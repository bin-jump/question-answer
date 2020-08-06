package com.zhang.ddd.infrastructure.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Slf4j
@Configuration
public class DatasourceConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

//    @Value("${spring.datasource.driver-class-name}")
//    private String driverName;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource datasource() {
        log.info(datasourceUrl, username, password);

        return DataSourceBuilder.create()
                //.driverClassName(driverName)
                .url(datasourceUrl)
                .username(username)
                .password(password)
                .build();
    }
}
