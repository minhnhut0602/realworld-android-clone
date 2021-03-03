package io.realworld.android.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.realworld.android.AuthViewModel
import io.realworld.android.R
import io.realworld.android.databinding.FragmentArticleBinding
import io.realworld.android.databinding.FragmentFeedBinding

class MyFeedFragment: Fragment() {

    private var _binding : FragmentFeedBinding? = null
    private lateinit var feedAdapter: FeedAdapter
    private val viewModel:FeedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentFeedBinding.inflate(layoutInflater,container,false)
        feedAdapter= FeedAdapter(requireContext()) { openArticle(it) }
        _binding?.feedRecyclerView?.layoutManager=LinearLayoutManager(context)
        _binding?.feedRecyclerView?.adapter=feedAdapter
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMyFeed()
        viewModel.feedData.observe({lifecycle}){
            feedAdapter.updateArticle(it)
        }

    }

    private fun openArticle(articleId:String){
        findNavController().navigate(
            R.id.action_myFeed_openArticle,
            bundleOf(
                resources.getString(R.string.arg_article_id) to articleId
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}