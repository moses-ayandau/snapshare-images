package com.moses.fun.config;

import com.moses.fun.service.ParameterStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Autowired
    private ParameterStoreService parameterStoreService;

    @Bean
    public DataSource dataSource() {
        System.out.println("url "+parameterStoreService.getParameter("/snapshare/db/url") );
        System.out.println("username "+parameterStoreService.getParameter("/snapshare/db/username") );
        System.out.println("password "+parameterStoreService.getParameter("/snapshare/db/password") );

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(parameterStoreService.getParameter("/snapshare/db/url"));
        dataSource.setUsername(parameterStoreService.getParameter("/snapshare/db/username"));
        dataSource.setPassword(parameterStoreService.getParameter("/snapshare/db/password"));
        return dataSource;
    }
}
