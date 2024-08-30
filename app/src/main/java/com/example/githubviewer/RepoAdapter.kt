package com.example.binproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubviewer.R
import com.example.githubviewer.Repo
import com.example.githubviewer.Repos
import com.example.githubviewer.databinding.GithubItemBinding

class RepoAdapter : RecyclerView.Adapter<RepoAdapter.RepoHolder>() {

    private var repoList = listOf<Repos>()
    private var onRepoClickListener: OnRepoClickListener? = null

    class RepoHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = GithubItemBinding.bind(item)

        fun bind(list: Repos) = with(binding) {
            repoTextView.text = list.name//body()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.github_item, parent, false)
        return RepoHolder(view)
    }

    override fun onBindViewHolder(holder: RepoHolder, position: Int) {
        holder.bind(repoList[position])
        holder.itemView.setOnClickListener {
            onRepoClickListener?.onRepoClick(repoList[position])
        }
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    fun addRepo(repo: List<Repos>) {
        repoList = repo
        notifyDataSetChanged()
    }
    fun setOnRepoClickListener(listener: OnRepoClickListener) {
        this.onRepoClickListener = listener
    }
    interface OnRepoClickListener{
        fun onRepoClick(repo: Repos)
    }
}