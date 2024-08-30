package com.example.githubviewer

data class DetailRepository(
    val name: String,
    val owner: RepoOwner,
    val description: String?,
    val html_url: String,
    val stargazers_count: Int,
    val issues: Int,
    val language: String?,
    val forks: Int,
    val stars: Int,
    val watchers: Int,
    val url: String,
    val license: License?,
    val readme: String?
)
data class License(
    val name: String?
)
