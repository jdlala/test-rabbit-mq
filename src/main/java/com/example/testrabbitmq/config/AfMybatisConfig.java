package com.example.testrabbitmq.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <p> description: mybatis配置, 添加一个mybatis插件
 * <p> 2018/08/30
 *
 * @author ssj
 * @version 1.0.0
 */
@Configuration
public class AfMybatisConfig {

    @Bean(name = "myDataSource")
    @Qualifier("myDataSource")
    @ConfigurationProperties(prefix="spring.datasource.other")
    public DataSource getMyDataSource(){
        return DataSourceBuilder.create().build();
    }
}
