package io.realworld.android.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realworld.android.R
import io.realworld.android.extensions.loadImage
import io.realworld.android.extensions.timeStamp
import io.realworld.api.models.entities.Comment

class CommentAdapter(val onCommentDeleteClicked: (id:Int) -> Unit):RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private val allComment =ArrayList<Comment>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.comment_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment=allComment[position]
        holder.apply {
            commentTv.text=comment.body
            dateTv.timeStamp=comment.updatedAt
            profileImage.loadImage(comment.author.image,true)
            userTv.text=comment.author.username
            deleteIv.setOnClickListener {
                onCommentDeleteClicked(comment.id)
            }
        }
    }

    override fun getItemCount(): Int = allComment.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val commentTv=itemView.findViewById<TextView>(R.id.commentTv)
        val profileImage=itemView.findViewById<ImageView>(R.id.userProfileImage)
        val userTv=itemView.findViewById<TextView>(R.id.userTv)
        val dateTv=itemView.findViewById<TextView>(R.id.dateTv)
        val deleteIv=itemView.findViewById<ImageView>(R.id.deleteIv)

    }
    fun updateComment(comment:List<Comment>){
        allComment.clear()
        allComment.addAll(comment)
        notifyDataSetChanged()
    }
}