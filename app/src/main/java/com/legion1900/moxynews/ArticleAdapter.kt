package com.legion1900.moxynews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.legion1900.moxynews.contracts.NewsContract

class ArticleAdapter(val listener: View.OnClickListener) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private val dataSet = ArrayList<NewsContract.Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_rv, parent, false)
        itemView.setOnClickListener(listener)
        return ArticleViewHolder(itemView)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = dataSet[position]
        holder.author.text = article.author
        holder.title.text = article.title
        holder.publishedAt.text = article.publishedAt
    }

    fun updateData(data: List<NewsContract.Article>) {
        dataSet.clear()
        dataSet.addAll(data)
        notifyDataSetChanged()
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.findViewById(R.id.tv_author)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val publishedAt: TextView = itemView.findViewById(R.id.tv_publishedAt)
    }
}