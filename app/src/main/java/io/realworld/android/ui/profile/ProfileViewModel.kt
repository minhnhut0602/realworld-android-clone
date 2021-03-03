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
}