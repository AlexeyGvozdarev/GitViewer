package com.example.githubviewer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AppRepository
): ViewModel() {
    val state: MutableLiveData<State> = MutableLiveData()

    fun onSignButtonPressed(token: String) {
        viewModelScope.launch {
            try {
                state.value = State.Loading
                val result = repository.signIn(token)
                state.value = State.Idle(result.name)
            }catch (error: Throwable) {
                state.value = State.InvalidInput(error.message.orEmpty())
            }
        }
    }

}