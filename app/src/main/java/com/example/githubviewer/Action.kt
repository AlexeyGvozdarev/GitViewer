package com.example.githubviewer

sealed interface Action {
    data class ShowError(val message: String) : Action
    object RouteToMain : Action
}