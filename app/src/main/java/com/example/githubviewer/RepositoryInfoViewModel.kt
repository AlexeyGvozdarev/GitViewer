package com.example.githubviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RepositoryInfoViewModel(private val repository: AppRepository): ViewModel() {
    // val state: LiveData<State> = TODO()
    val state: MutableLiveData<DetailRepository> = MutableLiveData()

    fun getRepoInfo(name: String,repoName: String){
        viewModelScope.launch {
            state.value = repository.getInfo(name,repoName)
        }
    }
    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State

        data class Loaded(val gitHubRepo: Repo, val readmeState: ReadmeState) : State
    }

    sealed interface ReadmeState {
        object Loading : ReadmeState
        object Empty : ReadmeState

        data class Error(val error: String) : ReadmeState
        data class Loaded(val markDown: String) : ReadmeState
    }
}