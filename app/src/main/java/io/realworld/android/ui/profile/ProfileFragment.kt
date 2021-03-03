package io.realworld.android.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import io.realworld.android.R
import io.realworld.android.databinding.FragmentProfileBinding
import io.realworld.android.extensions.loadImage
import io.realworld.android.ui.feed.FeedAdapter
import io.realworld.android.ui.feed.FeedViewModel

class ProfileFragment : Fragment() {


    companion object {
        const val PREFS_FILE_AUTH= "prefs_auth"
        const val PREFS_USER="username"
    }
    private var _binding:FragmentProfileBinding?=null
    private val feedViewModel: FeedViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var feedAdapter : FeedAdapter
    private var sharedPreferences : SharedPreferences? =null
    private var userName:String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater,container,false)

        sharedPreferences= this.activity?.getSharedPreferences(PREFS_FILE_AUTH, Context.MODE_PRIVATE)
        feedAdapter= FeedAdapter(requireContext()) {openArticle(it)}
        _binding?.userFeedRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter=feedAdapter
        }

        userName = arguments?.getString(PREFS_USER)
            ?: sharedPreferences?.getString(PREFS_USER,null)

        userName?.let {
            profileViewModel.getUserProfile(it)
            feedViewModel.getUserFeed(it)
        }
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedViewModel.feedData.observe({lifecycle}) {
            feedAdapter.updateArticle(it)
        }
        _binding?.apply {
            profileViewModel.profile.observe({lifecycle}) {
                 profileUsername.text=it.username
                profileIv.loadImage(it.image,true)
                profileBioTv.text=it.bio
            }
            feedTabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab?.position){
                        0 -> {
                            feedViewModel.getUserFeed(userName)
                        }
                        1-> {
                            feedViewModel.getUserFavoriteFeed(userName)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }
            })

        }

    }

    private fun openArticle(articleId: String) {
        findNavController().navigate(
            R.id.action_open_user_article,
            bundleOf(
                resources.getString(R.string.arg_article_id) to articleId
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
}