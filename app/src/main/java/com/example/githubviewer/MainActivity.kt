package com.example.githubviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.githubviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.top_placeHolder, AuthFragment()).commit()

        }

    }


   fun openRepositories(name: String) {
       val tempName = name
       val fragment = RepositoriesListFragment()

       fragment.arguments = bundleOf("MY_NAME" to tempName)
       supportFragmentManager.beginTransaction().replace(R.id.top_placeHolder, fragment).addToBackStack(null).commit()
   }

   fun openInfo(repoName: String,name: String) {
       val myName = name
       val fragment = DetailInfoFragment()

       fragment.arguments = bundleOf("REPO_NAME" to repoName,"MY_NAME" to myName)
       supportFragmentManager.beginTransaction().replace(R.id.top_placeHolder,fragment).addToBackStack(null).commit()
   }
}
