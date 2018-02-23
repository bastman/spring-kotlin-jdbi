package com.example.config

import org.jdbi.v3.core.Jdbi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
class JdbiConfig(private val dataSource: DataSource) {

    @Bean
    fun jdbi(): Jdbi = Jdbi.create(dataSource).installPlugins()

    /*

    @Bean
    fun userDao(jdbi: Jdbi): UserDao {
        return jdbi.onDemand(UserDao::class.java!!)
    }

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