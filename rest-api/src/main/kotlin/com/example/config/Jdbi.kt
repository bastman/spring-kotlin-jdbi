package com.example.config

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.spring4.JdbiFactoryBean
import org.jdbi.v3.sqlobject.SqlObjectPlugin

import org.springframework.context.annotation.Configuration

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@Configuration
class JdbiConfig {


    /*
    @Autowired
    @Bean
    fun dbi(dataSource: DataSource): Jdbi {
        synchronized(Jdbi::class.java) {
            return Jdbi.create(dataSource)
                    // detect and install all plugins
                    .installPlugins()

            //Jdbi(dataSource)
        }
    }
    */


    @Autowired
    @Bean
    fun dbiFactory(ds: DataSource): JdbiFactoryBean {
        val dbiFactoryBean = JdbiFactoryBean()
        dbiFactoryBean.setDataSource(ds)
        return dbiFactoryBean
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }
}