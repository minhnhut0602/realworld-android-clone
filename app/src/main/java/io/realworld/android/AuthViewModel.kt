package io.realworld.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.realworld.android.repository.UserRepo
import io.realworld.api.models.entities.User
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

    fun logout(){
        _user.postValue(null)
        UserRepo.logout()
    }


}