package com.skillink.fundme.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseConfig {

   

    @ConfigurationProperties(prefix="spring.datasource")
    @Bean(name = "splishpayDatasource")
    public DataSource getDataSource() {

    	//ComboPooledDataSource dataSource = new ComboPooledDataSource();
        // i was hoping this was going to pull my current datasource, as 
        // defined in application.properties
    	//return dataSource;
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();

        // the call to the above method
        dataSourceInitializer.setDataSource(getDataSource());


        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);

        return dataSourceInitializer;
    }
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
