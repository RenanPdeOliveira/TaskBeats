package com.comunidadedevspace.taskbeats.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.comunidadedevspace.taskbeats.data.local.entity.NewsItem
import com.comunidadedevspace.taskbeats.databinding.ItemNewsBinding

class NewsListAdapter(
    private val onFavoriteClick: (news: NewsItem) -> Unit
) : ListAdapter<NewsItem, NewsListViewHolder>(NewsListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onFavoriteClick)
    }

    companion object : DiffUtil.ItemCallback<NewsItem>() {

        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.imageUrl == newItem.imageUrl
        }

    }

}

class NewsListViewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: NewsItem,
        onFavoriteClick: (news: NewsItem) -> Unit
    ) {
        with(binding) {
            textViewNews.text = item.title
            imageViewNews.load(item.imageUrl) {
                transformations(RoundedCornersTransformation(16f))
            }
            newsFavoriteButton.setImageResource(item.drawableResId)
            newsFavoriteButton.setOnClickListener {
                onFavoriteClick.invoke(item)
            }
        }
    }
}