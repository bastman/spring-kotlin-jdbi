package com.example.api.tweeter.db

import com.example.api.common.DaoAccessException
import com.example.api.common.EntityNotFoundException
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.mapper.reflect.ColumnName
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

data class Tweet(
        val id: UUID,
        val version: Int,
        val createdAt: Instant,
        @ColumnName("updated_at") val modifiedAt: Instant,
        val message: String,
        val comment: String?
)

interface TweetDaoOps {
    @SqlUpdate("""
        insert into tweet
        (id, version, created_at, updated_at, message, comment)
        values
        (:tweet.id, :tweet.version, :tweet.createdAt, :tweet.modifiedAt, :tweet.message, :tweet.comment)
        """
    )
    fun create(tweet: Tweet): Number

    @SqlQuery("SELECT * FROM tweet WHERE id = :id")
    operator fun get(@Bind("id") id: UUID): Tweet?

    @SqlQuery("SELECT * FROM tweet")
    fun findAll(): List<Tweet>
}

//@Repository
@Component
class TweetDao(jdbi: Jdbi) {
    private val ops: TweetDaoOps = jdbi.onDemand(TweetDaoOps::class.java)

    fun create(tweet: Tweet): Tweet {
        ops.create(tweet)
        return ops[tweet.id] ?: throw DaoAccessException("Failed to load entity after insert. (Tweet.id=${tweet.id}")
    }

    operator fun get(id: UUID): Tweet? = ops[id]
    fun requireOneById(id: UUID): Tweet = ops[id] ?: throw EntityNotFoundException("Tweet Not Found (id=$id)")
    fun findAll() = ops.findAll()
}