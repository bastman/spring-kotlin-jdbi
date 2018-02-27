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
        INSERT INTO $TABLE_TWEET
        (id, version, created_at, updated_at, message, comment)
        VALUES
        (:tweet.id, :tweet.version, :tweet.createdAt, :tweet.modifiedAt, :tweet.message, :tweet.comment)
        """
    )
    fun insert(tweet: Tweet): Number

    @SqlUpdate("""
        UPDATE $TABLE_TWEET
        SET (updated_at, message, comment)
        = (:modifiedAt, :message, :comment)
        WHERE id = :id AND updated_at = :optimisticModifiedAt
        """
    )
    fun update(
            id: UUID, optimisticModifiedAt: Instant,
            modifiedAt: Instant, message: String, comment: String?
    ): Int

    @SqlQuery("SELECT * FROM $TABLE_TWEET WHERE id = :id")
    operator fun get(@Bind("id") id: UUID): Tweet?

    @SqlQuery("SELECT * FROM $TABLE_TWEET")
    fun findAll(): List<Tweet>

    @SqlQuery("SELECT * FROM $TABLE_TWEET where id = any(:ids) order by id")
    fun findByIdList(ids: List<UUID>): List<Tweet>

    companion object {
        const val TABLE_TWEET: String = "tweet"
    }
}

//@Repository
@Component
class TweetDao(jdbi: Jdbi) {
    private val ops: TweetDaoOps = jdbi.onDemand(TweetDaoOps::class.java)

    fun insert(tweet: Tweet): Tweet {
        ops.insert(tweet)
        return ops[tweet.id] ?: throw DaoAccessException("Failed to load entity after insert. (Tweet.id=${tweet.id}")
    }

    operator fun get(id: UUID): Tweet? = ops[id]
    fun requireOneById(id: UUID): Tweet = ops[id] ?: throw EntityNotFoundException("Tweet Not Found (id=$id)")
    fun findAll() = ops.findAll()
    fun findByIdList(ids: List<UUID>) = ops.findByIdList(ids)

    fun update(cmd: UpdateCommand): Tweet {
        val oldRow = requireOneById(id = cmd.id)
        val affectedRows = ops.update(
                id = cmd.id, optimisticModifiedAt = oldRow.modifiedAt,
                modifiedAt = cmd.modifiedAt, message = cmd.message, comment = cmd.comment
        )
        if (affectedRows < 1) {
            throw DaoAccessException("Update Entity Failed! reason: OptimisticLockingError. (Tweet.id=${cmd.id}")
        }
        return ops[cmd.id] ?: throw DaoAccessException("Failed to load entity after insert. (Tweet.id=${cmd.id}")
    }
}

data class UpdateCommand(val id: UUID, val message: String, val comment: String?, val modifiedAt: Instant)