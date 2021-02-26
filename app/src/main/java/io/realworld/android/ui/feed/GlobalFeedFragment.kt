package io.realworld.android.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import io.realworld.android.databinding.FragmentFeedBinding

class GlobalFeedFragment : Fragment() {

    private var _binding :FragmentFeedBinding? = null
    private lateinit var feedAdapter: FeedAdapter

    val viewModel:FeedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentFeedBinding.inflate(layoutInflater,container,false)

        feedAdapter= FeedAdapter(requireContext())
        _binding?.feedRecyclerView?.layoutManager= LinearLayoutManager(context)
        _binding?.feedRecyclerView?.adapter=feedAdapter
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getGlobalFeed()
        viewModel.feedData.observe({lifecycle}){
            feedAdapter.updateArticle(it)
        }
    }
}