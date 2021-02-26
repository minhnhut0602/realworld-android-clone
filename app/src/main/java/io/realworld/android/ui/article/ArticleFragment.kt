package io.realworld.android.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import io.realworld.android.R
import io.realworld.android.databinding.FragmentArticleBinding
import io.realworld.android.extensions.loadImage
import io.realworld.android.extensions.timeStamp

class ArticleFragment: Fragment() {

    private lateinit var articleViewModel:ArticleViewModel
    private var _binding:FragmentArticleBinding? =null
    private var articleId:String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentArticleBinding.inflate(layoutInflater,container,false)
        articleViewModel=ViewModelProvider(this).get(ArticleViewModel::class.java)
        arguments?.let{
            articleId=it.getString(resources.getString(R.string.arg_article_id))
        }
        articleId?.let{ articleViewModel.getArticle(it)}

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
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}