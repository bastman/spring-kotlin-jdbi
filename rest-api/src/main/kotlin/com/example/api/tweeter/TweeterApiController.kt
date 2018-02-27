package com.example.api.tweeter

import com.example.api.tweeter.db.Tweet
import com.example.api.tweeter.db.TweetDao
import com.example.api.tweeter.db.UpdateCommand
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.*

@RestController
class TweeterApiController(private val dao: TweetDao) {

    @GetMapping("/api/tweeter")
    fun findAll(): List<TweetDto> =
            dao.findAll().map { it.toTweetDto() }

    @PostMapping("/api/tweeter/find-by-ids")
    fun findByIdList(@RequestBody req:FindByIdsRequest): List<TweetDto> =
            dao.findByIdList(req.ids).map { it.toTweetDto() }

    @GetMapping("/api/tweeter/{id}")
    fun getOne(@PathVariable id: UUID): TweetDto = dao.requireOneById(id).toTweetDto()

    @PutMapping("/api/tweeter")
    fun createOne(@RequestBody req: CreateTweetRequest): TweetDto {
        val now = Instant.now()
        val tweet = Tweet(
                id = UUID.randomUUID(), version = 0, createdAt = now, modifiedAt = now,
                message = req.message, comment = req.comment
        )
        return dao.insert(tweet).toTweetDto()
    }
    @PostMapping("/api/tweeter/{id}")
    fun updateOne(@PathVariable id: UUID, @RequestBody req: UpdateTweetRequest): TweetDto {
        val cmd = UpdateCommand(id=id, modifiedAt = Instant.now(), message = req.message, comment = req.comment)
        return dao.update(cmd).toTweetDto()
    }
}

data class CreateTweetRequest(val message: String, val comment: String?)
data class UpdateTweetRequest(val message: String, val comment: String?)
data class FindByIdsRequest(val ids:List<UUID>)
data class TweetDto(
        val id: UUID, val version: Int, val createdAt: Instant, val modifiedAt: Instant,
        val message: String, val comment: String?
)

private fun Tweet.toTweetDto() = TweetDto(
        id = id, version = version, createdAt = createdAt, modifiedAt = modifiedAt,
        message = message, comment = comment
)