package io.realworld.android.ui.article

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.realworld.android.MainActivity
import io.realworld.android.R
import io.realworld.android.databinding.FragmentCreateArticleBinding
import io.realworld.android.extensions.loadImage
import io.realworld.android.extensions.timeStamp

class UpdateArticleFragment: Fragment() {


    private var _binding:FragmentCreateArticleBinding?= null
    private lateinit var articleViewModel:ArticleViewModel
    private var articleSlug:String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentCreateArticleBinding.inflate(layoutInflater,container,false)
        articleViewModel= ViewModelProvider(this).get(ArticleViewModel::class.java)

        arguments?.let {
            articleSlug=it.getString(resources.getString(R.string.arg_article_id))
        }

        articleSlug?.let{ slug ->
            articleViewModel.getArticle(slug)
        }

        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.apply {

            articleTitleTv.requestFocus()
            submitButton.setOnClickListener{
                articleViewModel.updateArticle(
                    articleSlug!!,
                    title=articleTitleTv.text.toString().takeIf { it.isNotBlank() },
                    description = articleDesciptionTv.text.toString().takeIf { it.isNotBlank() },
                    body = articleBodyTv.text.toString().takeIf{it.isNotBlank()},
                    tagList = articleTagTv.text.toString().split("\\s".toRegex())
                )
                Toast.makeText(requireContext(),"Article Updated",Toast.LENGTH_SHORT).show()
                openUpdatedArticle()
                closeKeyBoard()
            }
        }
        //
        articleViewModel.article.observe({lifecycle}){
            _binding?.apply {
                       articleTitleTv.setText(it.title)
                        articleDesciptionTv.setText(it.description)
                        articleBodyTv.setText(it.body)
                        articleTagTv.setText(it.tagList.toString().removePrefix("[").removeSuffix("]").replace(",",""))
            }
        }


    }

    private fun openUpdatedArticle() {
        articleViewModel.article.observe({lifecycle}) {
            findNavController().navigate(
                R.id.action_open_updated_Article,
                bundleOf(
                    resources.getString(R.string.arg_article_id) to it.slug
                )
            )
        }
    }

    private fun closeKeyBoard() {
        val activity = activity as MainActivity
        val view = activity.currentFocus
        if(view!=null){
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}