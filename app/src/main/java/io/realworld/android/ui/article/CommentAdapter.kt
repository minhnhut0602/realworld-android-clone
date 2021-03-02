package io.realworld.android.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import io.realworld.android.R
import io.realworld.android.extensions.loadImage
import io.realworld.android.extensions.timeStamp
import io.realworld.api.models.entities.Comment

class CommentAdapter(val username:String?, val onCommentDeleteClicked: (id:Int) -> Unit):RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

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

                if(comment.author.username==username && username!=null) {
                    deleteIv.isVisible=true
                    deleteIv.setOnClickListener {
                        onCommentDeleteClicked(comment.id)
                    }
                }else{
                    deleteIv.isVisible=false
                }
        }
    }

    override fun getItemCount(): Int = allComment.size

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val commentTv :TextView=itemView.findViewById(R.id.commentTv)
        val profileImage :ImageView=itemView.findViewById(R.id.userProfileImage)
        val userTv: TextView =itemView.findViewById(R.id.userTv)
        val dateTv :TextView=itemView.findViewById(R.id.dateTv)
        val deleteIv :ImageView=itemView.findViewById(R.id.deleteIv)

    }
    fun updateComment(comment:List<Comment>){
        allComment.clear()
        allComment.addAll(comment)
        notifyDataSetChanged()
    }
}