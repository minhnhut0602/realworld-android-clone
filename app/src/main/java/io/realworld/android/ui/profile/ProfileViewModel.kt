package io.realworld.android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realworld.android.repository.UserRepo
import io.realworld.api.models.entities.Profile
import kotlinx.coroutines.launch

class ProfileViewModel:ViewModel() {

    private val _profile = MutableLiveData<Profile>()
    val profile: LiveData<Profile> = _profile


    fun getUserProfile(username:String) =viewModelScope.launch {
        UserRepo.getProfile(username)?.let {
            _profile.postValue(it)
        }
    }
    fun followUnfollowProfile(username: String,isFollowed:String?) {
        if(isFollowed=="follow")
            followUser(username)
        else
            unfollowUser(username)
    }

    fun followUser(username:String) = viewModelScope.launch {
        UserRepo.followUser(username)
    }
    fun unfollowUser(username:String) =viewModelScope.launch {
        UserRepo.unfollowUser(username)
    }
}