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
import io.realworld.android.extensions.loadImage
import io.realworld.android.extensions.timeStamp
import io.realworld.api.models.entities.Article

class FeedAdapter(val onArticleClicked: (slug:String)->Unit, val onFavoriteClicked: (slug:String,isFavorited:Boolean)->Unit):RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    private val allArticle =ArrayList<Article>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder =ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.article_item,parent, false
        ))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val article=allArticle[position]
        holder.apply {
            author.text=article.author.username
            dateTv.timeStamp=article.updatedAt
            title.text=article.title
            bodySnippet.text=article.body
            article.author.image.let{
                profileImage.loadImage(it)
            }
            if(article.favorited){
                favoriteIv.setImageResource(R.drawable.ic_unfavorite)
            }else{
                favoriteIv.setImageResource(R.drawable.ic_favorite)
            }
            itemView.setOnClickListener { onArticleClicked(article.slug) }

            favoriteIv.setOnClickListener {
                onFavoriteClicked(article.slug,article.favorited) }
        }
    }

    override fun getItemCount(): Int = allArticle.size


    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val author=itemView.findViewById<TextView>(R.id.authorTextView)
        val profileImage=itemView.findViewById<ImageView>(R.id.avatarImageView)
        val dateTv=itemView.findViewById<TextView>(R.id.dateTextView)
        val title=itemView.findViewById<TextView>(R.id.titleTextView)
        val bodySnippet=itemView.findViewById<TextView>(R.id.bodySnippetTextView)
        val favoriteIv=itemView.findViewById<ImageView>(R.id.favoriteImageView)
    }

    fun updateArticle(feed:List<Article>){
        allArticle.clear()
        allArticle.addAll(feed)
        notifyDataSetChanged()
    }
}