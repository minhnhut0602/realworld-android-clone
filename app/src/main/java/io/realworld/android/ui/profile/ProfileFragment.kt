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
import java.util.*

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
    private var myFeedSelected:Boolean =true
    private var userName:String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater,container,false)

        sharedPreferences= this.activity?.getSharedPreferences(PREFS_FILE_AUTH, Context.MODE_PRIVATE)
        feedAdapter= FeedAdapter({openArticle(it)} ,{slug,isFavorited-> favoriteArticle(slug,isFavorited)})
        _binding?.userFeedRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter=feedAdapter
        }

//        userName = arguments?.getString(PREFS_USER)
//            ?: sharedPreferences?.getString(PREFS_USER,null);
        arguments?.getString(PREFS_USER).let {
            if(it!=null){
                userName=it
                _binding?.followUserBtn?.visibility=View.VISIBLE
            }else{
                userName=sharedPreferences?.getString(PREFS_USER,null)
                _binding?.followUserBtn?.visibility=View.GONE
            }
        }
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
            profileViewModel.profile.observe({lifecycle}) { it ->
                profileUsername.text=it.username
                profileIv.loadImage(it.image,true)
                profileBioTv.text=it.bio
                it.following.let {
                    if(it) {
                        followUserBtn.text = "Unfollow"
                        followUserBtn.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_remove,0,0,0)
                    }
                    else{
                        followUserBtn.text="Follow"
                        followUserBtn.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_add,0,0,0)
                    }
                }

                followUserBtn.let{button->
                    button.setOnClickListener {
                        userName?.let {username->
                            profileViewModel.followUnfollowProfile(username,button.text.toString()
                                .toLowerCase(Locale.getDefault()))
                            profileViewModel.getUserProfile(username)
                        }
                    }
                }

            }
            feedTabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab?.position){
                        0 -> {
                            feedViewModel.getUserFeed(userName)
                            myFeedSelected=true
                        }
                        1-> {
                            feedViewModel.getUserFavoriteFeed(userName)
                            myFeedSelected=false
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

    private fun favoriteArticle(slug:String,isFavorited:Boolean){
        feedViewModel.markFeedAsFavoriteUnFavorite(slug,isFavorited)
        myFeedSelected.let {
            if(it)
                feedViewModel.getUserFeed(userName)
            else
                feedViewModel.getUserFavoriteFeed(userName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
}