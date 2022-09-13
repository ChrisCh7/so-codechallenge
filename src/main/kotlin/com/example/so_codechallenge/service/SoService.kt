package com.example.so_codechallenge.service

import com.example.so_codechallenge.model.UsersResponse
import com.example.so_codechallenge.model.UsersTopTagsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SoService {
    @GET("users?site=stackoverflow&filter=!6VvPDzOQa-NkZ")
    fun getUsers(@Query("page") page: Int): Call<UsersResponse>

    @GET("users/{id}/top-tags?site=stackoverflow")
    fun getTopTags(@Path("id") userId: Int, @Query("page") page: Int = 1): Call<UsersTopTagsResponse>
}