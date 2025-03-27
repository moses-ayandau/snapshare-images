//package com.moses.fun.config;
//
//import com.moses.fun.service.ParameterStoreService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Autowired
//    private ParameterStoreService parameterStoreService;
//
//    @Bean
//    public DataSource dataSource() {
////        System.out.println("url "+parameterStoreService.getParameter("/snapshare/db/url") );
////        System.out.println("username "+parameterStoreService.getParameter("/snapshare/db/username") );
////        System.out.println("password "+parameterStoreService.getParameter("/snapshare/db/password") );
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
////        dataSource.setUrl(parameterStoreService.getParameter("/snapshare/db/url"));
////        dataSource.setUsername(parameterStoreService.getParameter("/snapshare/db/username"));
////        dataSource.setPassword(parameterStoreService.getParameter("/snapshare/db/password"));
//
//        dataSource.setUrl("jdbc:postgresql://database-1.cra46k0ggz73.eu-central-1.rds.amazonaws.com:5432/testdb");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("Mos21311");
//
//
//
////        dataSource.setDriverClassName("org.postgresql.Driver");
////        dataSource.setUrl(parameterStoreService.getParameter("/test/db/snapshare/url"));
////        dataSource.setUsername(parameterStoreService.getParameter("/test/db/snapshare/username"));
////        dataSource.setPassword(parameterStoreService.getParameter("/test/db/snapshare/password"));
//        return dataSource;
//    }
//}
