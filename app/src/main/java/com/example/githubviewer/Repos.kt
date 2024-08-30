package com.example.githubviewer

import retrofit2.http.GET
import retrofit2.http.Path

data class Repos(
    val id: Int,
    val name: String
)
interface GitApi {
    @GET("users/{username}/repos")
     suspend fun getReps(@Path("username") username: String): List<Repos>
}