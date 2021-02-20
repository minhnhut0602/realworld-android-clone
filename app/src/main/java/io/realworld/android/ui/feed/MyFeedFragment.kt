package io.realworld.android.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.realworld.android.databinding.FragmentArticleBinding

class MyFeedFragment: Fragment() {

    private var _binding : FragmentArticleBinding? = null
    private lateinit var feedAdapter: FeedAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentArticleBinding.inflate(layoutInflater,container,false)

        feedAdapter= FeedAdapter(requireContext())
        _binding?.recyclerView?.layoutManager=LinearLayoutManager(context)
        _binding?.recyclerView?.adapter=feedAdapter
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}