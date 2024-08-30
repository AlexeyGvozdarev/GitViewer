package com.example.githubviewer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.binproject.RepoAdapter
import com.example.githubviewer.databinding.FragmentAuthBinding
import com.example.githubviewer.databinding.FragmentRepositoriesListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RepositoriesListFragment : Fragment() {
    lateinit var binding: FragmentRepositoriesListBinding
    private val adapter = RepoAdapter()

    val viewModel: RepositoriesListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RepositoriesListViewModel(repository = AppRepository(keyValueStorage = KeyValueStorage(
                    PreferenceManager.getDefaultSharedPreferences(requireContext())))) as T
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myName = requireArguments().getString("MY_NAME") ?: return

        viewModel.runRepos(myName)

        adapter.setOnRepoClickListener(object : RepoAdapter.OnRepoClickListener{
            override fun onRepoClick(repo: Repos) {
                (activity as MainActivity).openInfo(repo.name,myName)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentRepositoriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.state.observe(this){
            when (it) {
                is RepositoriesListViewModel.ListState.Loading ->{
                    binding.temp.text = "Loading"
                }
                is RepositoriesListViewModel.ListState.Error ->{
                    binding.temp.text = "Error"
                }
                is RepositoriesListViewModel.ListState.Empty ->{
                    binding.temp.text = "Empty"
                }
                is RepositoriesListViewModel.ListState.Loaded -> {
                    init(it.repos)
                    binding.temp.text = "Loaded"
                }
                else -> {

                    binding.temp.text = ""
                }
            }
        }
    }
 private fun init(repos: List<Repos>){
     binding.apply {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
         adapter.addRepo(repos)
     }
 }
}


