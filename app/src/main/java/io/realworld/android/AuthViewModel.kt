package io.realworld.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realworld.android.repository.UserRepo
import io.realworld.api.models.entities.User
import io.realworld.api.models.entities.UserUpdateData
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private var _user=MutableLiveData<User?>()
    val user:LiveData<User?> = _user


    fun getCurrentUser(token:String)=viewModelScope.launch {
        UserRepo.getCurrentUser(token).let{
            _user.postValue(it)
        }
    }
    fun login(email:String,password:String)=viewModelScope.launch {
        UserRepo.loginUser(email,password).let {
            _user.postValue(it)
        }
    }

    fun registerUser(username:String,email:String,password:String) =viewModelScope.launch {
        UserRepo.registerUser(username,email,password).let{
            _user.postValue(it)
        }
    }

    fun updateUser(
        username: String?,
        email: String?,
        bio:String?,
        password: String?,
        image:String?) = viewModelScope.launch {
        UserRepo.updateUser(
                username=username,
                email = email,
                bio = bio,
                image = image,
                password = password
        )?.let {
            _user.postValue(it)
        }
    }

    fun logout(){
        _user.postValue(null)
        UserRepo.logout()
    }


}