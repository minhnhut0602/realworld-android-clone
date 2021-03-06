package io.realworld.android.ui.feed


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.realworld.android.R
import io.realworld.android.extensions.loadImage
import io.realworld.android.extensions.timeStamp
import io.realworld.api.models.entities.Article
import io.realworld.android.databinding.ArticleItemBinding

class FeedAdapter(val onArticleClicked: (slug:String)->Unit,
                  val onFavoriteClicked: (slug:String,isFavorited:Boolean)->Unit):
    ListAdapter<Article, FeedAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem === newItem
            }
        }
    ) {

    private val allArticle =ArrayList<Article>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ArticleItemBinding.inflate(
            parent.context.getSystemService(LayoutInflater::class.java),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val article=allArticle[position]
        ArticleItemBinding.bind(holder.itemView).apply {
            authorTextView.text=article.author.username
            dateTextView.timeStamp=article.updatedAt
            titleTextView.text=article.title
            bodySnippetTextView.text=article.body
            article.author.image.let{
                avatarImageView.loadImage(it)
            }
//            favoriteIv.isClickable=true
            if(article.favorited){
                favoriteImageView.setImageResource(R.drawable.ic_unfavorite)
                favoriteImageView.tag = "1"
            }else{
                favoriteImageView.setImageResource(R.drawable.ic_favorite)
                favoriteImageView.tag= "0"
            }
            root.setOnClickListener { onArticleClicked(article.slug) }

            favoriteImageView.setOnClickListener {
//                favoriteIv.isClickable=false
//                if(it.tag=="0"){
//                    favoriteIv.setImageResource(R.drawable.ic_unfavorite)
//                    favoriteIv.tag="1"
//                }else{
//                    favoriteIv.setImageResource(R.drawable.ic_favorite)
//                    favoriteIv.tag="0"
//                }
                onFavoriteClicked(article.slug,article.favorited)
            }
        }
    }

    override fun getItemCount(): Int = allArticle.size


    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    fun updateArticle(feed:List<Article>){
        allArticle.clear()
        allArticle.addAll(feed)
        notifyDataSetChanged()
    }
}