package com.example.so_codechallenge.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    val username: String,
    val location: String?,
    val answerCount: Int,
    val questionCount: Int,
    val linkProfile: String,
    val linkAvatar: String,
    val reputation: Int,
    val userId: Int,
    var tags: String?
) {
    @JsonCreator
    constructor(
        @JsonProperty("display_name") username: String,
        @JsonProperty("location") location: String?,
        @JsonProperty("answer_count") answerCount: Int,
        @JsonProperty("question_count") questionCount: Int,
        @JsonProperty("link") linkProfile: String,
        @JsonProperty("profile_image") linkAvatar: String,
        @JsonProperty("reputation") reputation: Int,
        @JsonProperty("user_id") userId: Int
    ) : this(username, location, answerCount, questionCount, linkProfile, linkAvatar, reputation, userId, null) {
    }
}
