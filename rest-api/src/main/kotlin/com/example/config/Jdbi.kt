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
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.jdbc.datasource.DriverManagerDataSource




@Configuration
@EnableTransactionManagement
class JdbiConfig(private val dataSource: DataSource) {

    @Bean
    fun jdbi(): Jdbi {
        return Jdbi.create(dataSource).installPlugins()
    }

    /*
    @Bean
    fun jdbiFactory(): JdbiFactoryBean {
        return JdbiFactoryBean()
                //.setDataSource(DriverManagerDataSource())
                .setDataSource(dataSource)
    }
    */

    /*
    @Bean
    fun jdbiOperations(jdbi: Jdbi): JdbiOperations {
        return JdbiTemplate(jdbi)
    }

    @Bean
    fun transactionManager(jdbi: Jdbi): PlatformTransactionManager {
        return JdbiTransactionManager(jdbi)
    }

    @Bean
    fun templateUsingService(operations: JdbiOperations): TemplateUsingService {
        return TemplateUsingService(operations)
    }


    @Bean
    fun userDao(jdbi: Jdbi): UserDao {
        return jdbi.onDemand(UserDao::class.java!!)
    }
    */


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


    /*
    @Autowired
    @Bean
    fun dbiFactory(ds: DataSource): JdbiFactoryBean {
        val dbiFactoryBean = JdbiFactoryBean()
        dbiFactoryBean.setDataSource(ds)
        return dbiFactoryBean
    }
    */


}