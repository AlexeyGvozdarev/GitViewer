package com.example.githubviewer

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class AppRepository(private val keyValueStorage: KeyValueStorage) {
     //suspend fun getRepositories(): List<Repo>{}
    //suspend fun getRepository(repoId: String): Repodetails{}
    // suspend fun getRepositoryReadme(ownerName: String,repositoryName:String,branchName:String):String{}

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(
            OkHttpClient.Builder().addInterceptor {
                val request = it.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${keyValueStorage.authToken}").build()
                it.proceed(request)
            }.build()
        ).build()

    val gitHubClient = retrofitBuilder.create(GithubApi::class.java)
    val gitRep = retrofitBuilder.create(GitApi::class.java)
    val repInfo = retrofitBuilder.create(GitHubService::class.java)

    suspend fun signIn(token: String): UserInfo {
        keyValueStorage.authToken = token
        delay(2000)
        try {
            return gitHubClient.signIn()
        } catch (error: Throwable) {
            keyValueStorage.authToken = ""
            throw error
        }
    }
    suspend fun getReps(name: String): List<Repos>{
        val reps = gitRep.getReps(name)
        return reps
    }
    suspend fun getInfo(name: String,repoName: String): DetailRepository{
        val rep = repInfo.getInfo(repoName,name)
        return rep
    }
}

class UserInfo(
    @SerializedName("id")
    val userId: Int,
    @SerializedName("login")
    val name: String
)
interface GithubApi {
    @GET("user")
    suspend fun signIn(): UserInfo
    @GET("users/{username}/repos")
    suspend fun getReps(@Path("username") username: String): List<Repo>
}
