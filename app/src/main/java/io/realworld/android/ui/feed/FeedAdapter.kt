package io.realworld.android.ui.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.realworld.android.R
import io.realworld.api.models.entities.Article

class FeedAdapter(private val context: Context):RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private val allArticle =ArrayList<Article>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder =ViewHolder(LayoutInflater.from(context).inflate(
            R.layout.article_item,parent, false
        ))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article=allArticle[position]
        holder.author.text=article.author.username
        holder.dateTv.text=article.updatedAt
        holder.title.text=article.title
        holder.bodySnippet.text=article.body
        article.author.image.let{
            Glide.with(context).load(it).into(holder.profileImage)
        }
    }

    override fun getItemCount(): Int = allArticle.size


    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val author=itemView.findViewById<TextView>(R.id.authorTextView)
        val profileImage=itemView.findViewById<ImageView>(R.id.avatarImageView)
        val dateTv=itemView.findViewById<TextView>(R.id.dateTextView)
        val title=itemView.findViewById<TextView>(R.id.titleTextView)
        val bodySnippet=itemView.findViewById<TextView>(R.id.bodySnippetTextView)

    }

    fun updateArticle(article:List<Article>){
        allArticle.clear()
        allArticle.addAll(article)
        notifyDataSetChanged()
    }
}