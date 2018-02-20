package com.example.api.tweeter

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.mapper.reflect.BeanMapper
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.customizer.Define
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
/*
import org.skife.jdbi.v2.DBI
import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.BindBean
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.Define
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator
*/
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
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

@Repository
class ExampleDao(
        private val jdbi:Jdbi
) {

    private lateinit var db: ExampleDb

    @PostConstruct
    fun foo() {
        this.db = jdbi.onDemand(ExampleDb::class.java)
    }



    fun create(tweet: Tweet): Any? {
        return db.create(tweet)
    }

    operator fun get(id: UUID): Tweet? {
        return db[id]
    }

    /*
    fun getWhere(name: String, minimumId: Long): List<Example> {
        val where = String.format("name = '%s' AND id >= %s", name, minimumId)
        return db!!.getWhere(where)
    }
    */


    //@UseStringTemplate3StatementLocator
    @RegisterBeanMapper(ExampleMapper::class)
    abstract class ExampleDb {

        @SqlUpdate("INSERT INTO example (name) values (:name)")
        /*
        @GetGeneratedKeys
        internal abstract fun create(@BindBean tweet: Tweet): Long
        */


        internal abstract fun create(@BindBean tweet: Tweet): Any?

        @SqlQuery("SELECT * FROM example WHERE id = :id")
        internal abstract operator fun get(@Bind("id") id: UUID): Tweet?

        //@SqlQuery("SELECT * FROM example WHERE <where>")
        //internal abstract fun getWhere(@Define("where") where: String): List<Example>
    }
}