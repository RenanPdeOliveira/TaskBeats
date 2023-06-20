package com.comunidadedevspace.taskbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.local.NewsItem

class NewsListAdapter() : ListAdapter<NewsItem, NewsListViewHolder>(NewsListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object : DiffUtil.ItemCallback<NewsItem>() {

        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.title == newItem.title && oldItem.imageUrl == newItem.imageUrl
        }

    }

}

class NewsListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    val tvNews: TextView = view.findViewById(R.id.textViewNews)
    val imageView: ImageView = view.findViewById(R.id.imageViewNews)

    fun bind(
        item: NewsItem
    ) {
        tvNews.text = item.title
        imageView.load(item.imageUrl) {
            transformations(RoundedCornersTransformation(16f))
        }
    }
}