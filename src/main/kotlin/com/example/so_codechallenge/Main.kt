package com.example.so_codechallenge

import com.example.so_codechallenge.model.User
import com.example.so_codechallenge.service.SoService
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.jackson.JacksonConverterFactory


fun main() {
    val baseUrl = "https://api.stackexchange.com/2.3/"
    val pagesToGet = 25 // pages of results to fetch while searching for users
    val searchedLocations = listOf("Romania", "Moldova")
    val minReputation = 223
    val minAnsweredQuestions = 1
    val searchedTags = listOf("java", ".net", "docker", "c#")
    // note: the API has no filtering options based on location etc. that could be possible
    // to be specified during the request, so all we can do is fetch a number of users and filter in code.
    // when testing, beware of rate limits.

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JacksonConverterFactory.create())
        .build()

    val service = retrofit.create(SoService::class.java)

    runBlocking {
        (1..pagesToGet).map { page -> async { getUsersInfo(service, page) } }.awaitAll()
            .flatten()
            .filter { user ->
                searchedLocations.any { user.location?.contains(it) ?: false } &&
                        user.reputation >= minReputation &&
                        user.answerCount >= minAnsweredQuestions &&
                        searchedTags.any { user.tags?.contains(it) ?: false }
            }
            .forEach { user ->
                println(
                    "Username: ${user.username}, " +
                            "Location: ${user.location}, " +
                            "Answer count: ${user.answerCount}, " +
                            "Question count: ${user.questionCount}, " +
                            "Tags: \"${user.tags}\", " +
                            "Link to profile: ${user.linkProfile}, " +
                            "Link to avatar: ${user.linkAvatar}"
                )
            }
    }
}

suspend fun getUsersInfo(service: SoService, page: Int): List<User> {
    try {
        val usersResponse = service.getUsers(page).awaitResponse()

        if (!usersResponse.isSuccessful) {
            println("${usersResponse.code()} ${usersResponse.message()}")
            return listOf()
        }

        val users = usersResponse.body()?.items ?: return listOf()
        users.forEach { user ->
            user.tags = getUsersTopTags(service, user.userId)
        }

        return users
    } catch (e: Exception) {
        println("Error: ${e.message}")
        return listOf()
    }
}

suspend fun getUsersTopTags(service: SoService, userId: Int): String {
    try {
        // get just one page of top tags
        val userTopTagsResponse = service.getTopTags(userId).awaitResponse()

        if (!userTopTagsResponse.isSuccessful) {
            println("${userTopTagsResponse.code()} ${userTopTagsResponse.message()}")
            return ""
        }

        return userTopTagsResponse.body()?.items?.joinToString { it.tagName } ?: ""
    } catch (e: Exception) {
        println("Error: ${e.message}")
        return ""
    }
}