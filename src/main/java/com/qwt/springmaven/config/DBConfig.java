package com.qwt.springmaven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

/**
 * Created by Wentao Qian on 12/25/2017.
 */
@Configuration
//@ComponentScan(basePackages = "com.qwt.springmaven")
public class DBConfig {

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3316/spring_security");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("12345");
        return driverManagerDataSource;
    }

    //this is another way to connect to database, by look up property file for jboss
    /*@Bean
    public DataSource ds() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        //when using spring 4.3.9, there's an error on dsLookup.getDataSource
        //Incompatible types.
        // Found: 'org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException', required: 'java.lang.Throwable'
        // after switching to 4.3.11, it fixed
        // go deep into the method, finally found spring-tx is not properly installed because of 4.3.9 --- interesting...
        DataSource dataSource = dsLookup.getDataSource("java:jboss/padvisorDS");
        return dataSource;
    }*/

}
