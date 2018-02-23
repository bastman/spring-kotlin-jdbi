package com.example

//import org.springframework.data.jpa.repository.config.EnableJpaAuditing
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import java.util.*
import javax.annotation.PostConstruct


@SpringBootApplication
//@EnableTransactionManagement
//@EnableJpaRepositories
//@EnableJpaAuditing
@EnableAutoConfiguration(exclude = [JpaRepositoriesAutoConfiguration::class])
class RestApiApplication {
    @PostConstruct
    fun postConstruct() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(RestApiApplication::class.java, *args)
}