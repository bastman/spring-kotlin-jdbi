package com.example.api.tweeter

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.mapper.reflect.BeanMapper
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct

data class Tweet(
        val id: UUID,
        val version: Int,
        val createdAt: Instant,
        val modifiedAt: Instant,
        val message: String,
        val comment: String?
)

class ExampleMapper : BeanMapper<Tweet>(Tweet::class.java)
//@UseStringTemplate3StatementLocator
@RegisterBeanMapper(ExampleMapper::class)
interface ExampleDaoOps {
    @SqlUpdate("INSERT INTO example (name) values (:name)")
    fun create(@BindBean tweet: Tweet): Any?

    @SqlQuery("SELECT * FROM example WHERE id = :id")
    operator fun get(@Bind("id") id: UUID): Tweet?
}

//@Repository
@Component
class ExampleDao(private val jdbi: Jdbi) {

    private lateinit var ops: ExampleDaoOps
    @PostConstruct
    fun foo() {
        this.ops = jdbi.onDemand(ExampleDaoOps::class.java)
    }

    fun create(tweet: Tweet): Any? {
        return ops.create(tweet)
    }

    operator fun get(id: UUID): Tweet? {
        return ops[id]
    }
}