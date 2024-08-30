package com.example.githubviewer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("repos/{owner}/{repo}")
    suspend fun getInfo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): DetailRepository
}