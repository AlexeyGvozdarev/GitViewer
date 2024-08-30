package com.example.githubviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.githubviewer.databinding.FragmentDetailInfoBinding


class DetailInfoFragment : Fragment() {
     lateinit var binding: FragmentDetailInfoBinding

    val viewModel: RepositoryInfoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return RepositoryInfoViewModel(repository = AppRepository(keyValueStorage = KeyValueStorage(
                    PreferenceManager.getDefaultSharedPreferences(requireContext())))) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.state.observe(this){
            val owner = it.name
            val forks = it.forks
            val repoUrl = it.url
            val stars = it.stars
            val watchers = it.watchers
            val licens = it.license?.name
            val readme = it.readme
            binding.forksTextView.text = "Forks - $forks"
            binding.starsTextView.text = "Stars - " + stars.toString()
            binding.watchersTextView.text = "Watchers - " + watchers.toString()
            binding.urlTextView.text = "URL - " + repoUrl
            binding.licenseTextView.text = "License - " + licens
            binding.readmeTextView.text = "Readme - " + readme
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repoId = requireArguments().getString("REPO_NAME")
        val myName = requireArguments().getString("MY_NAME") ?:
        return

        if (repoId != null) {
            viewModel.getRepoInfo(repoId,myName)
        }
        binding.backButton.setOnClickListener {
            try {
                requireActivity().onBackPressed()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}