package com.example.api.tweeter

import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class TweeterApiController(val dao:ExampleDao) {

    /*

        @GetMapping("/api/tweeter")
    fun findAll() = tweeterRepo.getAllRecords().map { it.toDto() }

    @GetMapping("/api/tweeter/{id}")
    fun getOne(@PathVariable id: UUID) =
            tweeterRepo.requireOneById(id).toDto()
            */

    @PutMapping("/api/tweeter")
    fun createOne(@RequestBody req: CreateTweetRequest): TweetDto {
        val id=UUID.randomUUID()
        val tweet = Tweet(
                id = id,
                version = 0,
                createdAt = Instant.now(),
                modifiedAt = Instant.now(),
                message = "msg",
                comment = "cmt"
        )
        dao.create(tweet)

        val tweetReloaded = dao.get(id)
        return tweetReloaded!!.toTweetDto()
    }


}

data class CreateTweetRequest(val message: String, val comment: String?)
data class TweetDto(
        val id: UUID,
        val version: Int,
        val createdAt: Instant,
        val modifiedAt: Instant,
        val message: String,
        val comment: String?
)

fun Tweet.toTweetDto() = TweetDto(
        id=id,
        version = version,
        createdAt = createdAt,
        modifiedAt = modifiedAt,
        message = message,
        comment = comment
)