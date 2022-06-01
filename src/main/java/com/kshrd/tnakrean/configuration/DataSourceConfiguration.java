package com.kshrd.tnakrean.configuration;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataSourceConfiguration {
    @Bean
    public javax.sql.DataSource myPostgresDb() {

        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName("org.postgresql.Driver");

        dataSource.url("jdbc:postgresql://110.74.194.124:5430/klassrooms");
        dataSource.username("klassroom");
        dataSource.password("klassroom");
        return dataSource.build();


    }
}
