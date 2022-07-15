package com.kshrd.tnakrean;


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


@TestConfiguration
public class DataSourceConfiguration {
    @Bean
    public DataSource myPostgresDb() {

        DataSourceBuilder<?> dataSource = DataSourceBuilder.create();
        dataSource.driverClassName("org.postgresql.Driver");

        dataSource.url("jdbc:postgresql://110.74.194.124:5430/postgres");
        dataSource.username("postgres");
        dataSource.password("seanghorn");
        return dataSource.build();
    }
}
