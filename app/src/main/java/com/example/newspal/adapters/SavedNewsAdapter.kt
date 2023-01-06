package com.example.newspal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newspal.data.Article
import com.example.newspal.databinding.NewsItemRvBinding
import com.example.newspal.databinding.SavedNewsItemBinding

class SavedNewsAdapter(private val onItemClicked: (Article) -> Unit) : RecyclerView.Adapter<SavedNewsAdapter.NewsViewHolder>() {

    var articles = mutableListOf<Article>()
    fun setNewsList(movies: List<Article>) {
        this.articles = movies.toMutableList()
        notifyDataSetChanged()
    }

    class NewsViewHolder(private var binding: SavedNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: Article) {
            binding.news = news
            binding.tvSavedTitle.text = news.title
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SavedNewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentNews = articles[position]
        holder.itemView.setOnClickListener {
            onItemClicked(currentNews)
        }
        holder.bind(currentNews)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}
