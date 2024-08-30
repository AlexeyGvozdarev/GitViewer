package com.example.githubviewer

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.githubviewer.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding

    val viewModel: AuthViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthViewModel(repository = AppRepository(keyValueStorage = KeyValueStorage
                    (PreferenceManager.getDefaultSharedPreferences(requireContext())))) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.state.observe(this) {
            when (it) {
                is State.Idle -> {
                    val name = it.name
                    binding.tempView.text = name
                    binding.progressBar.visibility = View.GONE
                    (activity as MainActivity).openRepositories(name) //открытие экрана RepositiriesList
                }
                is State.InvalidInput -> {
                    val message = it.reason
                    binding.tempView.text = message
                    binding.progressBar.visibility = View.GONE
                    binding.textInputLayout.error = message
                    binding.tempView.text = "error"
                }
                State.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.tempView.text = "loading"
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener() {

            val currentToken = binding.textInputLayout.editText?.text

            val errorColor = Color.RED
            val errorColorStateList = ColorStateList.valueOf(errorColor)

            if (binding.textInputLayout.editText?.text.isNullOrEmpty()) {
                binding.textInputLayout.error = "input field is empty"
                binding.textInputLayout.setErrorTextColor(errorColorStateList)
            } else {
                viewModel.onSignButtonPressed(currentToken.toString())
            }
        }
    }
}