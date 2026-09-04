package com.example.icetasksandroid.api

import com.example.icetasksandroid.models.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    
    @GET("posts")
    suspend fun getAllPosts(): List<Post>
    
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post
    
    @GET("posts/{id}")
    suspend fun getPostByUserId(@Path("id") userId: Int): Post
}