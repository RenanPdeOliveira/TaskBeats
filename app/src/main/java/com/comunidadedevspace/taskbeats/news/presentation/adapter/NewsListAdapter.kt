package com.comunidadedevspace.taskbeats.news.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.comunidadedevspace.taskbeats.news.data.local.NewsItem
import com.comunidadedevspace.taskbeats.databinding.ItemNewsBinding
import com.comunidadedevspace.taskbeats.news.domain.model.NewsDomain

class NewsListAdapter(
    private val onFavoriteClick: (news: NewsDomain) -> Unit
) : ListAdapter<NewsDomain, NewsListViewHolder>(NewsListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onFavoriteClick)
    }

    companion object : DiffUtil.ItemCallback<NewsDomain>() {

        override fun areItemsTheSame(oldItem: NewsDomain, newItem: NewsDomain): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsDomain, newItem: NewsDomain): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.imageUrl == newItem.imageUrl
        }

    }

}

class NewsListViewHolder(private val binding: ItemNewsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        item: NewsDomain,
        onFavoriteClick: (news: NewsDomain) -> Unit
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