package com.example.githubviewer

sealed interface State {
    class Idle(val name: String) : State
    object Loading : State
    data class InvalidInput(val reason: String) : State
}