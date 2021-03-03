package io.realworld.android.ui.article

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.realworld.android.R
import io.realworld.android.databinding.FragmentArticleBinding
import io.realworld.android.extensions.loadImage
import io.realworld.android.extensions.timeStamp

class ArticleFragment: Fragment() {


    companion object {
        const val PREFS_FILE_AUTH= "prefs_auth"
        const val PREFS_USER="username"
    }
    private lateinit var articleViewModel:ArticleViewModel
    private var _binding:FragmentArticleBinding? =null
    private var articleId:String? = null
    private lateinit var commentAdapter: CommentAdapter
    private var userName:String?=null
    private var sharedPreferences: SharedPreferences?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentArticleBinding.inflate(layoutInflater,container,false)
        articleViewModel=ViewModelProvider(this).get(ArticleViewModel::class.java)

        sharedPreferences = this.activity?.getSharedPreferences(PREFS_FILE_AUTH, Context.MODE_PRIVATE)


        arguments?.let{
            articleId=it.getString(resources.getString(R.string.arg_article_id))
        }

        articleId?.let{
            articleViewModel.getArticle(it)
        articleViewModel.getComment(it)
        }

        sharedPreferences?.getString(PREFS_USER,null)?.let{
            userName=it
        }

        commentAdapter=CommentAdapter(userName) { deleteClickedComment(it) }


        _binding?.commentRecyclerView?.layoutManager= LinearLayoutManager(context)
        _binding?.commentRecyclerView?.adapter=commentAdapter

        _binding?.apply {
            editArticleBtn.visibility=View.GONE
            deleteArticleBtn.visibility=View.GONE

        }
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articleViewModel.article.observe({lifecycle}){
            _binding?.apply {
                titleTextView.text = it.title
                bodyTextView.text = it.body
                authorTextView.text = it.author.username
                dateTextView.timeStamp = it.createdAt
                avatarImageView.loadImage(it.author.image, true)
                if(userName==it.author.username){
                    editArticleBtn.visibility=View.VISIBLE
                    deleteArticleBtn.visibility=View.VISIBLE
                }else{
                    editArticleBtn.visibility=View.GONE
                    deleteArticleBtn.visibility=View.GONE
                }
            }
        }

        articleViewModel.comment.observe({lifecycle}) {
            commentAdapter.updateComment(it)
        }

        _binding?.apply {
            submitComment.setOnClickListener{
               articleId?.let { slug->
                   commentEditText.text.toString().takeIf { it.isNotBlank() }?.let { it1 ->
                       articleViewModel.addComment(slug, it1)
                   }
               }
                commentEditText.setText("")
            }

            deleteArticleBtn.setOnClickListener{
                articleId?.let { slug ->
                    articleViewModel.deleteArticle(slug)
                }
                findNavController().navigate(R.id.nav_feed)
            }

            editArticleBtn.setOnClickListener {
                articleId?.let {
                    findNavController().navigate(
                        R.id.action_openUpdateArticle,
                        bundleOf(
                            resources.getString(R.string.arg_article_id) to it
                        )
                    )
                }
            }
        }
    }

    private fun deleteClickedComment(id:Int){
        articleId?.let{
            articleViewModel.deleteComment(it,id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}