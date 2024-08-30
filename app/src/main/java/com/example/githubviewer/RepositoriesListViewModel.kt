package com.example.githubviewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RepositoriesListViewModel(private val repository: AppRepository): ViewModel() {
   //val state: MutableLiveData<List<Repos>> = MutableLiveData()
    val state: MutableLiveData<ListState> = MutableLiveData()
    fun runRepos(name: String){
        val myName = name
        viewModelScope.launch {
            try {
                state.value = ListState.Loading
                val result = repository.getReps(myName)
                state.value = ListState.Loaded(result)
            }catch (error: Throwable){
                state.value = ListState.Error(error.message.orEmpty())
            }
        }
    }

//     fun runRepos(name: String){
//         val myName = name
//        viewModelScope.launch {
//            state.value = repository.getReps(myName)
//        }
//    }

    sealed interface ListState {
        object Loading : ListState

        data class Loaded(val repos: List<Repos>) : ListState
        data class Error(val error: String) : ListState

        object Empty : ListState
    }
}


class Repo(
    val name: String,
    val url: String
)
{
    fun body(): String {
        val text = "$name\n" +
                "$url\n"
        return text
    }
}